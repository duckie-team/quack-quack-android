/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("ModifierParameter")

package team.duckie.quackquack.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ParentDataModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import kotlinx.coroutines.launch
import team.duckie.quackquack.animation.animatedQuackTextStyleAsState
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.ui.util.fastFilterById
import team.duckie.quackquack.ui.util.onDrawFront
import team.duckie.quackquack.ui.util.rememberLtrTextMeasurer
import team.duckie.quackquack.util.fastMaxOf

/**
 * [QuackTab]을 그리는데 사용할 색상을 나타냅니다.
 *
 * @param background 배경 색상
 * @param underline 전체 탭 레이아웃 하단에 그려질 선의 색상
 * @param indicate 선택된 탭 하단에 그려질 선의 색상
 * @param contentColor 선택된 탭에 그려지는 컨텐츠를 그릴 때 사용할 색상
 * @param disableContentColor 선택되지 않은 탭에 그려지는 컨텐츠를 그릴 때 사용할 색상
 */
@Immutable
public class QuackTabColors private constructor(
  public val background: QuackColor = QuackColor.White,
  public val underline: QuackColor = QuackColor.Gray4,
  public val indicate: QuackColor = QuackColor.DuckieOrange,
  public val contentColor: QuackColor = QuackColor.Black,
  public val disableContentColor: QuackColor = QuackColor.Gray1,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is QuackTabColors) return false

    if (background != other.background) return false
    if (underline != other.underline) return false
    if (indicate != other.indicate) return false
    if (contentColor != other.contentColor) return false

    return disableContentColor == other.disableContentColor
  }

  override fun hashCode(): Int {
    var result = background.hashCode()
    result = 31 * result + underline.hashCode()
    result = 31 * result + indicate.hashCode()
    result = 31 * result + contentColor.hashCode()
    result = 31 * result + disableContentColor.hashCode()
    return result
  }

  /** 일부 필드를 수정하여 새로운 [QuackTabColors] 인스턴스를 만듭니다. */
  @Stable
  public fun copy(
    background: QuackColor = this.background,
    underline: QuackColor = this.underline,
    indicate: QuackColor = this.indicate,
    contentColor: QuackColor = this.contentColor,
    disableContentColor: QuackColor = this.disableContentColor,
  ): QuackTabColors =
    QuackTabColors(
      background = background,
      underline = underline,
      indicate = indicate,
      contentColor = contentColor,
      disableContentColor = disableContentColor,
    )

  public companion object {
    /** 디자인 가이드라인에 정의된 기본 색상을 가져옵니다. */
    @Stable
    public fun defaultTabColors(): QuackTabColors = QuackTabColors()
  }
}

/** [QuackTab]에서 탭을 추가하기 위한 스코프를 제공합니다. */
@Immutable
public interface QuackTabScope {
  /**
   * 신규 탭이 배치될 인덱스. 예를 들어 기존에 탭을 2개 추가한 상태에서
   * 새로운 탭(세 번째 탭)을 추가하려 할 때 이 값은 2가 됩니다.
   *
   * ```
   * QuackTabScope {
   *   tab("tab 1") {} // candidateIndex == 0
   *   tab("tab 2") {} // candidateIndex == 1
   *
   *   tab("tab 3") {} // candidateIndex == 2
   * }
   * ```
   *
   * 탭을 추가할 때 탭 별칭으로 인덱스를 포함하는 경우를 고려하여 제공됩니다.
   *
   * ```
   * QuackTabScope {
   *   tab("Tab #$candidateIndex") {}
   *   // 결과: 탭 별칭으로 `Tab #0`이 표시됩니다.
   * }
   * ```
   */
  public val candidateIndex: Int

  /**
   * 탭을 추가합니다.
   *
   * @param label 탭의 별칭
   * @param onClick 탭이 클릭됐을 때 실행할 람다식. 인자로 제공되는 `index`는
   * 클릭된 탭의 인덱스를 나타냅니다. 인자로 제공되는 `index`와 [candidateIndex]는
   * 엄연히 다른 개념이므로 주의하세요. 람다는 lazy하게 실행되므로 [onClick] 람다 안에서
   * [candidateIndex]를 참조하면 값은 항상 `추가된 탭의 마지막 인덱스 + 1`이 됩니다.
   */
  public fun tab(label: String, onClick: (index: Int) -> Unit)
}

private typealias TabLabelAndOnClickPair = Pair<String, (index: Int) -> Unit>

@VisibleForTesting
internal class QuackTabScopeScope(
  private val tabs: MutableVector<TabLabelAndOnClickPair>,
) : QuackTabScope {
  override val candidateIndex get() = tabs.size // must start with 0
  override fun tab(label: String, onClick: (index: Int) -> Unit) {
    tabs += label to onClick
  }
}

private const val TabLabelLayoutId = "TabLabel"

private const val TabAnimationMillis = 300
public const val QuackTabIndicatorXOffsetInitialValue: Int = Int.MIN_VALUE

private val tabLabelSpacedBy = 2.dp
private val tabTweenSpec = tween<Int>(TabAnimationMillis)
private val tabSnapSpec = snap<Int>()

/**
 * 꽥꽥 디자인 가이드라인에 의거한 탭 레이아웃을 그립니다.
 *
 * 이 컴포저블의 높이는 자체적으로 결정되며, 사용자가 제공한 값은 영향을 미치지 않습니다.
 * 이 컴포저블의 높이는 탭 레이블 중에서 가장 긴 높이를 갖는 레이블을 기준으로 결정됩니다.
 *
 * @param index 선택된 탭의 인덱스
 * @param colors 탭 레이아웃을 그리는데 사용할 색상 모음
 * @param indicatorStartXOffsetAnimatable 선택된 탭의 인디케이터를 그리는데 참조할
 * start-x 오프셋을 제공하는 [Animatable] 객체. `initialValue` 값으로 [QuackTabIndicatorXOffsetInitialValue]를
 * 제공해야 합니다.
 * @param indicatorEndXOffsetAnimatable 선택된 탭의 인디케이터를 그리는데 참조할
 * end-x 오프셋을 제공하는 [Animatable] 객체. `initialValue` 값으로 [QuackTabIndicatorXOffsetInitialValue]를
 * 제공해야 합니다.
 * @param content 탭을 추가할 수 있는 스코프.
 *
 * ```
 * var index by mutableIntStateOf(0)
 * QuackTab(index) {
 *   tab("첫 번째 탭") { index = it }
 *   tab("두 번째 탭") { index = it }
 *   tab("세 번째 탭") { index = it }
 * }
 * ```
 *
 * 자세한 내용은 [QuackTabScope]를 참고하세요.
 */
@NoSugar
@Composable
public fun QuackTab(
  index: Int,
  colors: QuackTabColors = QuackTabColors.defaultTabColors(),
  indicatorStartXOffsetAnimatable: Animatable<Int, AnimationVector1D> = remember {
    Animatable(
      initialValue = QuackTabIndicatorXOffsetInitialValue,
      typeConverter = Int.VectorConverter,
      label = "QuackTabIndicatorStartXOffset",
    )
  },
  indicatorEndXOffsetAnimatable: Animatable<Int, AnimationVector1D> = remember {
    Animatable(
      initialValue = QuackTabIndicatorXOffsetInitialValue,
      typeConverter = Int.VectorConverter,
      label = "QuackTabIndicatorEndXOffset",
    )
  },
  modifier: Modifier = Modifier,
  content: QuackTabScope.() -> Unit,
) {
  fun Modifier.drawUnderline() =
    drawWithCache {
      val underline = object {
        val thickness = 1.dp.toPx()
        val color = colors.underline.value

        val startOffset = Offset(
          x = 0f,
          y = size.height - thickness / 2, // Why need to divide by 2?
        )
        val endOffset = Offset(
          x = size.width,
          y = size.height - thickness / 2,
        )
      }
      onDrawFront {
        drawLine(
          color = underline.color,
          start = underline.startOffset,
          end = underline.endOffset,
          strokeWidth = underline.thickness,
        )
      }
    }

  val tabs = remember<List<TabLabelAndOnClickPair>>(content) {
    MutableVector<TabLabelAndOnClickPair>().also { tabs ->
      QuackTabScopeScope(tabs).content()
    }.asMutableList()
  }

  if (tabs.isEmpty())
    return Box(
      modifier = modifier
        .background(color = colors.background.value)
        .drawUnderline(),
    )

  val tabSize = remember(tabs, calculation = tabs::size)
  require(index in 0 until tabSize) { "index($index) must be 0 until $tabSize" }

  val tabTypography = remember(colors.contentColor) {
    QuackTypography.Title2.change(color = colors.contentColor)
  }
  val disabledTabTypography = remember(colors.disableContentColor) {
    QuackTypography.Body1.change(color = colors.disableContentColor)
  }

  val coroutineScope = rememberCoroutineScope()
  val textMeasurer = rememberLtrTextMeasurer()

  Layout(
    modifier = modifier
      .background(color = colors.background.value)
      .drawWithCache {
        val indicator = object {
          val thickness = 2.dp.toPx()
          val color = colors.indicate.value

          val startOffset = Offset(
            x = indicatorStartXOffsetAnimatable.value.toFloat(),
            y = size.height - thickness / 2,
          )
          val endOffset = Offset(
            x = indicatorEndXOffsetAnimatable.value.toFloat(),
            y = size.height - thickness / 2,
          )
        }
        onDrawFront {
          drawLine(
            color = indicator.color,
            start = indicator.startOffset,
            end = indicator.endOffset,
            strokeWidth = indicator.thickness,
          )
        }
      }
      .drawUnderline(),
    content = {
      tabs.fastForEachIndexed { tabIndex, (label, onClick) ->
        val labelTypography =
          animatedQuackTextStyleAsState(
            targetValue = if (index == tabIndex) tabTypography else disabledTabTypography,
            label = "QuackTabLabelTypography",
          )
        val labelMeasureResult = remember(label, labelTypography) {
          textMeasurer
            .measure(
              text = label,
              style = labelTypography.asComposeStyle(),
              softWrap = false,
              maxLines = 1,
            )
        }
        Box(
          Modifier
            .layoutIdWithTabIndexPairTextLayoutResult(
              layoutId = TabLabelLayoutId,
              tabIndex = tabIndex,
              textLayoutResult = labelMeasureResult,
            )
            .background(color = colors.background.value)
            .quackClickable(
              role = Role.Tab,
              onClick = { onClick(tabIndex) },
              rippleEnabled = false,
            )
            .drawWithCache {
              val labelPlacementTopLeftOffset =
                Offset(
                  x = tabLabelSpacedBy.toPx(),
                  y = size.height / 2 - labelMeasureResult.size.height / 2,
                )
              onDrawFront {
                drawText(
                  textLayoutResult = labelMeasureResult,
                  topLeft = labelPlacementTopLeftOffset,
                )
              }
            },
        )
      }
    },
  ) { measurables, constraints ->
    // Ensure tab sorted with index.
    @Suppress("NAME_SHADOWING")
    val tabs = measurables.fastFilterById(TabLabelLayoutId).sortedBy(Measurable::tabIndex)
    val tabMaxHeight = tabs.fastMaxOf { it.textLayoutResult.size.height }

    val tabVerticalPaddingPx = 12.dp.roundToPx()
    val tabHorizontalPaddingPx = 16.dp.roundToPx()

    val tabSpacedByPx = 16.dp.roundToPx()
    val tabLabelSpacedByPx = tabLabelSpacedBy.roundToPx()

    val tabLabelContainerHeight = tabMaxHeight + tabVerticalPaddingPx * 2
    val tabPlaceables =
      tabs.fastMap { tab ->
        @Suppress("NAME_SHADOWING")
        val constraints =
          Constraints.fixed(
            height = tabLabelContainerHeight,
            width = tab.textLayoutResult.size.width + tabLabelSpacedByPx * 2,
          )
        tab.measure(constraints)
      }

    val eachTabXOffsets = run {
      var x = 0
      List(tabSize) { index ->
        x +=
          if (index == 0) tabHorizontalPaddingPx
          else tabPlaceables[index - 1].width + tabSpacedByPx
        x
      }
    }

    coroutineScope.launch {
      val animationSpec =
        if (indicatorStartXOffsetAnimatable.value == QuackTabIndicatorXOffsetInitialValue) tabSnapSpec
        else tabTweenSpec
      launch {
        indicatorStartXOffsetAnimatable
          .animateTo(
            targetValue = eachTabXOffsets[index],
            animationSpec = animationSpec,
          )
      }
      launch {
        indicatorEndXOffsetAnimatable
          .animateTo(
            targetValue = eachTabXOffsets[index] + tabPlaceables[index].width,
            animationSpec = animationSpec,
          )
      }
    }

    val ensuredMaxWidth =
      run {
        fun <T> List<T>.last() = get(tabSize - 1)

        val maxWidth = constraints.maxWidth
        val tabLayoutWidth = eachTabXOffsets.last() + tabPlaceables.last().width + tabHorizontalPaddingPx
        if (tabLayoutWidth > maxWidth)
          error("The width of the tab that will actually be drawn is greater than the given maximum width.")

        maxWidth
      }

    layout(width = ensuredMaxWidth, height = tabLabelContainerHeight) {
      tabPlaceables.fastForEachIndexed { index, tab ->
        tab.place(x = eachTabXOffsets[index], y = 0)
      }
    }
  }
}

/**
 * [Measurable]의 `layoutId`, [QuackTab]에서 배치될 탭의 인덱스, [QuackTab]에서 배치될 탭의
 * [별칭을 measure한 결과][TextLayoutResult]를 [ParentDataModifierNode]로 제공합니다.
 */
@VisibleForTesting
@Stable
internal fun Modifier.layoutIdWithTabIndexPairTextLayoutResult(
  layoutId: Any,
  tabIndex: Int,
  textLayoutResult: TextLayoutResult,
) =
  this then
    LayoutIdWithTabIndexPairTextLayoutResultElement(
      layoutId = layoutId,
      tabIndex = tabIndex,
      textLayoutResult = textLayoutResult,
    )

private data class LayoutIdWithTabIndexPairTextLayoutResultElement(
  private val layoutId: Any,
  private val tabIndex: Int,
  private val textLayoutResult: TextLayoutResult,
) : ModifierNodeElement<LayoutIdWithTabIndexPairTextLayoutResultModifier>() {
  override fun create() =
    LayoutIdWithTabIndexPairTextLayoutResultModifier(
      layoutId = layoutId,
      tabIndex = tabIndex,
      textLayoutResult = textLayoutResult,
    )

  override fun update(node: LayoutIdWithTabIndexPairTextLayoutResultModifier) {
    node.layoutId = layoutId
    node.tabIndex = tabIndex
    node.textLayoutResult = textLayoutResult
  }

  override fun InspectorInfo.inspectableProperties() {
    name = "LayoutIdWithTabIndexPairTextLayoutResult"
    properties["layoutId"] = layoutId
    properties["tabIndex"] = tabIndex
    properties["textLayoutResult"] = textLayoutResult
  }
}

private class LayoutIdWithTabIndexPairTextLayoutResultModifier(
  layoutId: Any,
  tabIndex: Int,
  textLayoutResult: TextLayoutResult,
) : ParentDataModifierNode, Modifier.Node(),
  LayoutIdParentData, TabIndexParentData, TextLayoutResultParentData {
  override var layoutId = layoutId
    internal set

  override var tabIndex = tabIndex
    internal set

  override var textLayoutResult = textLayoutResult
    internal set

  override fun Density.modifyParentData(parentData: Any?) =
    this@LayoutIdWithTabIndexPairTextLayoutResultModifier
}

private interface TabIndexParentData {
  val tabIndex: Int
}

private interface TextLayoutResultParentData {
  val textLayoutResult: TextLayoutResult
}

@VisibleForTesting
@Stable
internal val Measurable.tabIndex: Int
  get() = requireNotNull((parentData as? TabIndexParentData)).tabIndex

@VisibleForTesting
@Stable
internal val Measurable.textLayoutResult: TextLayoutResult
  get() = requireNotNull((parentData as? TextLayoutResultParentData)).textLayoutResult
