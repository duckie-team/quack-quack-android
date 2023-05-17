/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.aide.annotation.DecorateModifier
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackPadding
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.quackSurface
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.runtime.quackMaterializeOf
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.ui.util.QuackDsl
import team.duckie.quackquack.ui.util.asLoose
import team.duckie.quackquack.ui.util.fontScaleAwareIconSize
import team.duckie.quackquack.ui.util.wrappedDebugInspectable
import team.duckie.quackquack.util.applyIf
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull

/**
 * 태그의 디자인 스펙을 나타냅니다.
 *
 * @see QuackTagStyle
 */
@QuackDsl
public interface TagStyleMarker

/** 기본으로 제공되는 [TagStyleMarker]의 스펙들에서 공통되는 필드를 나타냅니다. */
@Immutable
public interface QuackTagStyle<T : TagStyleMarker> {
  /** 사용할 색상들 */
  public val colors: QuackTagColors

  /** 모서리의 둥글기 정도 */
  public val radius: Dp

  /** 컨텐츠 주변에 들어갈 패딩 */
  public val contentPadding: QuackPadding

  /** 배치되는 아이콘과 텍스트 사이의 간격 */
  public val iconSpacedBy: Dp

  /** 테두리의 굵기 */
  public val borderThickness: Dp

  /** 선택 상태에서 표시될 텍스트의 타이포그래피 */
  public val typography: QuackTypography

  /** 비선택 상태에서 표시될 텍스트의 타이포그래피 */
  public val unselectedTypography: QuackTypography

  /** 디자인 스펙을 변경하는 람다 */
  @Stable
  public operator fun invoke(styleBuilder: T.() -> Unit): T

  public companion object {
    /** 태그 디자인 가이드의 `outlined` 디자인 스펙을 가져옵니다. */
    @Stable
    public val Outlined: QuackTagStyle<QuackOutlinedTagDefaults>
      get() = QuackOutlinedTagDefaults()

    /** 태그 디자인 가이드의 `filled` 디자인 스펙을 가져옵니다. */
    @Stable
    public val Filled: QuackTagStyle<QuackFilledTagDefaults>
      get() = QuackFilledTagDefaults()

    /** 태그 디자인 가이드의 `grayscale, flat` 디자인 스펙을 가져옵니다. */
    @Stable
    public val GrayscaleFlat: QuackTagStyle<QuackGrayscaleFlatTagDefaults>
      get() = QuackGrayscaleFlatTagDefaults()

    /** 태그 디자인 가이드의 `grayscale, outlined` 디자인 스펙을 가져옵니다. */
    @Stable
    public val GrayscaleOutlined: QuackTagStyle<QuackGrayscaleOutlinedTagDefaults>
      get() = QuackGrayscaleOutlinedTagDefaults()
  }
}

/** [QuackTagStyle]의 필드들을 [InspectorInfo]로 기록합니다. */
@SuppressLint("ModifierFactoryExtensionFunction")
@Stable
public fun QuackTagStyle<*>.wrappedDebugInspectable(baseline: Modifier): Modifier =
  baseline.wrappedDebugInspectable {
    name = toString()
    properties["colors"] = colors
    properties["radius"] = radius
    properties["contentPadding"] = contentPadding
    properties["iconSpacedBy"] = iconSpacedBy
    properties["borderThickness"] = borderThickness
    properties["typography"] = typography
    properties["unselectedTypography"] = unselectedTypography
  }

/**
 * 태그에서 사용할 색상들을 정의합니다.
 *
 * @param backgroundColor 선택 상태의 배경 색상
 * @param unselectedBackgroundColor 비선택 상태의 배경 색상
 * @param contentColor 선택 상태의 컨텐츠 색상 (아이콘 색상은 [iconColor]로
 * 관리되며, 컨텐츠라 하면 태그의 텍스트를 의미합니다.)
 * @param unselectedContentColor 비선택 상태의 컨텐츠 색상
 * @param borderColor 선택 상태의 테두리 색상
 * @param unselectedBorderColor 비선택 상태의 테두리 색상
 * @param iconColor 선택 상태의 아이콘 색상
 * @param unselectedIconColor 비선택 상태의 아이콘 색상
 * @param rippleColor 선택 상태에 관계 없이 항상 사용할 리플 색상
 */
@Immutable
public class QuackTagColors internal constructor(
  internal val backgroundColor: QuackColor,
  internal val unselectedBackgroundColor: QuackColor,
  internal val contentColor: QuackColor,
  internal val unselectedContentColor: QuackColor,
  internal val borderColor: QuackColor,
  internal val unselectedBorderColor: QuackColor,
  internal val iconColor: QuackColor,
  internal val unselectedIconColor: QuackColor,
  internal val rippleColor: QuackColor,
) {
  /** 기존 색상에서 일부 값만 변경하여 새로운 인스턴스를 반환합니다. */
  @Stable
  public fun copy(
    backgroundColor: QuackColor = this.backgroundColor,
    unselectedBackgroundColor: QuackColor = this.unselectedBackgroundColor,
    contentColor: QuackColor = this.contentColor,
    unselectedContentColor: QuackColor = this.unselectedContentColor,
    borderColor: QuackColor = this.borderColor,
    unselectedBorderColor: QuackColor = this.unselectedBorderColor,
    iconColor: QuackColor = this.iconColor,
    unselectedIconColor: QuackColor = this.unselectedIconColor,
    rippleColor: QuackColor = this.rippleColor,
  ): QuackTagColors =
    QuackTagColors(
      backgroundColor = backgroundColor,
      unselectedBackgroundColor = unselectedBackgroundColor,
      contentColor = contentColor,
      unselectedContentColor = unselectedContentColor,
      borderColor = borderColor,
      unselectedBorderColor = unselectedBorderColor,
      iconColor = iconColor,
      unselectedIconColor = unselectedIconColor,
      rippleColor = rippleColor,
    )

  @Suppress("RedundantIf")
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is QuackTagColors) return false

    if (backgroundColor != other.backgroundColor) return false
    if (unselectedBackgroundColor != other.unselectedBackgroundColor) return false
    if (contentColor != other.contentColor) return false
    if (unselectedContentColor != other.unselectedContentColor) return false
    if (borderColor != other.borderColor) return false
    if (unselectedBorderColor != other.unselectedBorderColor) return false
    if (iconColor != other.iconColor) return false
    if (unselectedIconColor != other.unselectedIconColor) return false
    if (rippleColor != other.rippleColor) return false

    return true
  }

  override fun hashCode(): Int {
    var result = backgroundColor.hashCode()
    result = 31 * result + unselectedBackgroundColor.hashCode()
    result = 31 * result + contentColor.hashCode()
    result = 31 * result + unselectedContentColor.hashCode()
    result = 31 * result + borderColor.hashCode()
    result = 31 * result + unselectedBorderColor.hashCode()
    result = 31 * result + iconColor.hashCode()
    result = 31 * result + unselectedIconColor.hashCode()
    result = 31 * result + rippleColor.hashCode()
    return result
  }

  override fun toString(): String =
    "QuackTagColors(" +
      "backgroundColor=$backgroundColor, " +
      "unselectedBackgroundColor=$unselectedBackgroundColor, " +
      "contentColor=$contentColor, " +
      "unselectedContentColor=$unselectedContentColor, " +
      "borderColor=$borderColor, " +
      "unselectedBorderColor=$unselectedBorderColor, " +
      "iconColor=$iconColor, " +
      "unselectedIconColor=$unselectedIconColor, " +
      "rippleColor=$rippleColor" +
      ")"
}

/** 태그 디자인 가이드의 `outlined` 디자인 스펙을 정의합니다. */
@Immutable
public class QuackOutlinedTagDefaults internal constructor() :
  QuackTagStyle<QuackOutlinedTagDefaults>, TagStyleMarker {

  override var colors: QuackTagColors = tagColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding =
    QuackPadding(
      horizontal = 12.dp,
      vertical = 8.dp,
    )

  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Title2
  override var unselectedTypography: QuackTypography = typography

  @Stable
  public fun tagColors(
    backgroundColor: QuackColor = QuackColor.White,
    unselectedBackgroundColor: QuackColor = backgroundColor,
    contentColor: QuackColor = QuackColor.DuckieOrange,
    unselectedContentColor: QuackColor = QuackColor.Black,
    borderColor: QuackColor = QuackColor.DuckieOrange,
    unselectedBorderColor: QuackColor = QuackColor.Gray3,
    iconColor: QuackColor = QuackColor.DuckieOrange,
    unselectedIconColor: QuackColor = QuackColor.Gray2,
    rippleColor: QuackColor = QuackColor.Unspecified,
  ): QuackTagColors =
    QuackTagColors(
      backgroundColor = backgroundColor,
      unselectedBackgroundColor = unselectedBackgroundColor,
      contentColor = contentColor,
      unselectedContentColor = unselectedContentColor,
      borderColor = borderColor,
      unselectedBorderColor = unselectedBorderColor,
      iconColor = iconColor,
      unselectedIconColor = unselectedIconColor,
      rippleColor = rippleColor,
    )

  override fun invoke(styleBuilder: QuackOutlinedTagDefaults.() -> Unit): QuackOutlinedTagDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/** 태그 디자인 가이드의 `filled` 디자인 스펙을 정의합니다. */
@Immutable
public class QuackFilledTagDefaults internal constructor() :
  QuackTagStyle<QuackFilledTagDefaults>, TagStyleMarker {

  override var colors: QuackTagColors = tagColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding =
    QuackPadding(
      horizontal = 16.dp,
      vertical = 8.dp,
    )

  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Title2
  override var unselectedTypography: QuackTypography = typography

  @Stable
  public fun tagColors(
    backgroundColor: QuackColor = QuackColor.DuckieOrange,
    unselectedBackgroundColor: QuackColor = QuackColor.White,
    contentColor: QuackColor = QuackColor.White,
    unselectedContentColor: QuackColor = QuackColor.Black,
    borderColor: QuackColor = QuackColor.DuckieOrange,
    unselectedBorderColor: QuackColor = QuackColor.Gray3,
    iconColor: QuackColor = QuackColor.White,
    unselectedIconColor: QuackColor = QuackColor.Gray2,
    rippleColor: QuackColor = QuackColor.Unspecified,
  ): QuackTagColors =
    QuackTagColors(
      backgroundColor = backgroundColor,
      unselectedBackgroundColor = unselectedBackgroundColor,
      contentColor = contentColor,
      unselectedContentColor = unselectedContentColor,
      borderColor = borderColor,
      unselectedBorderColor = unselectedBorderColor,
      iconColor = iconColor,
      unselectedIconColor = unselectedIconColor,
      rippleColor = rippleColor,
    )

  override fun invoke(styleBuilder: QuackFilledTagDefaults.() -> Unit): QuackFilledTagDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

// TODO(3): 데코레이터 사용 불가능 린트 제공
/** 태그 디자인 가이드의 `grayscale, flat` 디자인 스펙을 정의합니다. */
@Immutable
public class QuackGrayscaleFlatTagDefaults internal constructor() :
  QuackTagStyle<QuackGrayscaleFlatTagDefaults>, TagStyleMarker {

  override var colors: QuackTagColors = tagColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding =
    QuackPadding(
      horizontal = 8.dp,
      vertical = 4.dp,
    )

  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Body2
  override var unselectedTypography: QuackTypography = typography

  @Stable
  public fun tagColors(
    backgroundColor: QuackColor = QuackColor.Gray4,
    unselectedBackgroundColor: QuackColor = QuackColor.Unspecified,
    contentColor: QuackColor = QuackColor.Gray1,
    unselectedContentColor: QuackColor = QuackColor.Unspecified,
    borderColor: QuackColor = QuackColor.Gray4,
    unselectedBorderColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
    unselectedIconColor: QuackColor = QuackColor.Unspecified,
    rippleColor: QuackColor = QuackColor.Unspecified,
  ): QuackTagColors =
    QuackTagColors(
      backgroundColor = backgroundColor,
      unselectedBackgroundColor = unselectedBackgroundColor,
      contentColor = contentColor,
      unselectedContentColor = unselectedContentColor,
      borderColor = borderColor,
      unselectedBorderColor = unselectedBorderColor,
      iconColor = iconColor,
      unselectedIconColor = unselectedIconColor,
      rippleColor = rippleColor,
    )

  override fun invoke(styleBuilder: QuackGrayscaleFlatTagDefaults.() -> Unit): QuackGrayscaleFlatTagDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/** 태그 디자인 가이드의 `grayscale, outlined` 디자인 스펙을 정의합니다. */
@Immutable
public class QuackGrayscaleOutlinedTagDefaults internal constructor() :
  QuackTagStyle<QuackGrayscaleOutlinedTagDefaults>, TagStyleMarker {

  override var colors: QuackTagColors = tagColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding =
    QuackPadding(
      horizontal = 12.dp,
      vertical = 8.dp,
    )

  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Title2
  override var unselectedTypography: QuackTypography = typography

  @Stable
  public fun tagColors(
    backgroundColor: QuackColor = QuackColor.White,
    unselectedBackgroundColor: QuackColor = backgroundColor,
    contentColor: QuackColor = QuackColor.DuckieOrange,
    unselectedContentColor: QuackColor = QuackColor.Gray2,
    borderColor: QuackColor = QuackColor.DuckieOrange,
    unselectedBorderColor: QuackColor = QuackColor.Gray3,
    iconColor: QuackColor = QuackColor.DuckieOrange,
    unselectedIconColor: QuackColor = QuackColor.Gray2,
    rippleColor: QuackColor = QuackColor.Unspecified,
  ): QuackTagColors =
    QuackTagColors(
      backgroundColor = backgroundColor,
      unselectedBackgroundColor = unselectedBackgroundColor,
      contentColor = contentColor,
      unselectedContentColor = unselectedContentColor,
      borderColor = borderColor,
      unselectedBorderColor = unselectedBorderColor,
      iconColor = iconColor,
      unselectedIconColor = unselectedIconColor,
      rippleColor = rippleColor,
    )

  override fun invoke(styleBuilder: QuackGrayscaleOutlinedTagDefaults.() -> Unit): QuackGrayscaleOutlinedTagDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 태그에 표시할 후행 아이콘의 정보를 저장합니다.
 *
 * @see Modifier.trailingIcon
 */
// TODO: leading도 지원해야 하나?
@Stable
private data class TagTrailingIconData(
  val icon: QuackIcon,
  val size: Dp,
  val onClick: () -> Unit,
) : QuackDataModifierModel

/**
 * 태그에 후행 아이콘을 표시합니다.
 *
 * @param icon 표시할 아이콘
 * @param iconSize 표시할 아이콘의 사이즈
 * @param onClick 후행 아이콘 영역을 클릭했을 때 실행될 람다.
 * "후행 아이콘 영역"은 [iconSize] 사이즈의 영역이 아닙니다. 이는 자체 정책으로 결정됩니다.
 * 자세한 내용은 [QuackTag] 문서를 참고하세요.
 */
@DecorateModifier
@Stable
public fun Modifier.trailingIcon(
  icon: QuackIcon = QuackIcon.Close,
  iconSize: Dp = 16.dp,
  onClick: () -> Unit,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "trailingIcon"
      properties["icon"] = icon
      properties["iconSize"] = iconSize
      properties["onClick"] = onClick
    },
  ) {
    TagTrailingIconData(
      icon = icon,
      size = iconSize,
      onClick = onClick,
    )
  }

@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun <T : TagStyleMarker> QuackTag(
  @CasaValue("\"QuackTagPreview\"") text: String,
  modifier: Modifier = Modifier,
  selected: Boolean = true,
  rippleEnabled: Boolean = true,
  @SugarToken @CasaValue("QuackTagStyle.Outlined") style: QuackTagStyle<T>,
  @CasaValue("{}") onClick: () -> Unit,
) {
  var isSizeSpecified = false
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier) { currentModifier ->
    if (!isSizeSpecified) {
      isSizeSpecified = currentModifier is LayoutModifier
    }
  }
  val trailingIconData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TagTrailingIconData>()
  }

  val backgroundColor = style.colors.backgroundColor
  val unselectedBackgroundColor = style.colors.unselectedBackgroundColor
  val currentBackgroundColor = if (selected) backgroundColor else unselectedBackgroundColor

  val contentColor = style.colors.contentColor
  val unselectedContentColor = style.colors.unselectedContentColor
  val currentContentColor = if (selected) contentColor else unselectedContentColor

  val borderThickness = style.borderThickness
  val borderColor = style.colors.borderColor
  val unselectedBorderColor = style.colors.unselectedBorderColor
  val currentBorder = remember(
    selected,
    borderThickness,
    borderColor,
    unselectedBorderColor,
  ) {
    QuackBorder(
      thickness = borderThickness,
      color = if (selected) borderColor else unselectedBorderColor,
    )
  }

  val rippleColor = style.colors.rippleColor
  val currentRippleEnabled = if (selected) rippleEnabled else false

  val radius = style.radius

  val shape = remember(radius) {
    RoundedCornerShape(size = radius)
  }

  val contentPadding = style.contentPadding
  val currentContentPadding = if (isSizeSpecified) null else contentPadding

  val iconSpacedBy = style.iconSpacedBy

  val typography = style.typography
  val unselectedTypography = style.unselectedTypography
  val currentTypography = remember(
    selected,
    typography,
    unselectedTypography,
    currentContentColor,
  ) {
    (if (selected) typography else unselectedTypography).change(color = currentContentColor)
  }

  val iconColor = style.colors.iconColor
  val unselectedIconColor = style.colors.unselectedIconColor
  val currentIconColor = if (selected) iconColor else unselectedIconColor

  val trailingIcon = trailingIconData?.icon
  val trailingIconSize = trailingIconData?.size
  val trailingIconOnClick = trailingIconData?.onClick

  val inspectableModifier = style
    .wrappedDebugInspectable(composeModifier)
    .wrappedDebugInspectable {
      name = "QuackTag"
      properties["text"] = text
      properties["backgroundColor"] = currentBackgroundColor
      properties["rippleColor"] = rippleColor
      properties["rippleEnabled"] = currentRippleEnabled
      properties["shape"] = shape
      properties["border"] = currentBorder
      properties["typography"] = currentTypography
      properties["contentPadding"] = currentContentPadding
      properties["iconSpacedBy"] = iconSpacedBy
      properties["iconColor"] = currentIconColor
      properties["trailingIcon"] = trailingIcon
      properties["trailingIconSize"] = trailingIconSize
      properties["trailingIconOnClick"] = trailingIconOnClick
      properties["onClick"] = onClick
    }

  QuackBaseTag(
    modifier = inspectableModifier,
    text = text,
    backgroundColor = currentBackgroundColor,
    rippleColor = rippleColor,
    rippleEnabled = currentRippleEnabled,
    shape = shape,
    border = currentBorder,
    typography = currentTypography,
    contentPadding = currentContentPadding,
    iconSpacedBy = iconSpacedBy,
    iconColor = currentIconColor,
    trailingIcon = trailingIcon,
    trailingIconSize = trailingIconSize,
    trailingIconOnClick = trailingIconOnClick,
    onClick = onClick,
  )
}

private const val TextLayoutId = "QuackBaseTagText"
private const val TrailingIconContainerLayoutId = "QuackBaseTagTrailingIconContainer"
private const val FakeTrailingIconLayoutId = "QuackBaseTagFakeTrailingIcon"

/**
 * 고유한 배치 정책으로 태그를 그립니다. 배치 정책의 자세한 정보는 [QuackTag] 문서를 참고하세요.
 *
 * 이 컴포넌트는 [QuackTagStyle]의 필드를 개별 인자로 받습니다.
 */
@ExperimentalQuackQuackApi
@NoSugar
@Composable
public fun QuackBaseTag(
  modifier: Modifier,
  text: String,
  backgroundColor: QuackColor,
  rippleColor: QuackColor,
  rippleEnabled: Boolean,
  shape: Shape,
  border: QuackBorder,
  typography: QuackTypography,
  contentPadding: QuackPadding?,
  iconSpacedBy: Dp,
  iconColor: QuackColor?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconOnClick: (() -> Unit)?,
  onClick: (() -> Unit)?,
) {
  if (trailingIcon != null) {
    requireNotNull(trailingIconSize) {
      "A trailing icon was given, but the size of the trailing icon was not given."
    }
  }

  val currentIconColorFilter = remember(iconColor) {
    ColorFilter.tint(iconColor?.value ?: Color.Unspecified)
  }

  Layout(
    modifier = modifier
      .testTag("tag")
      .quackSurface(
        shape = shape,
        backgroundColor = backgroundColor,
        border = border,
        role = Role.Tab,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick,
      ),
    content = {
      QuackText(
        modifier = Modifier
          .testTag("text")
          .layoutId(TextLayoutId),
        text = text,
        typography = typography,
        singleLine = true,
        softWrap = false,
      )
      if (trailingIcon != null) {
        Layout(
          modifier = Modifier
            .testTag("trailingIconContainer")
            .layoutId(TrailingIconContainerLayoutId)
            .quackClickable(
              role = Role.Tab,
              rippleEnabled = false,
              onClick = trailingIconOnClick,
            ),
          content = {
            Box(
              Modifier
                .testTag("trailingIcon")
                .fontScaleAwareIconSize(baseline = trailingIconSize!!)
                .paint(
                  painter = trailingIcon.asPainter(),
                  colorFilter = currentIconColorFilter,
                  contentScale = ContentScale.Fit,
                )
            )
          }
        ) { measurables, constraints ->
          val measurable = measurables.single()

          val halfIconSpacedByPx = (iconSpacedBy / 2).roundToPx()
          val placeable = measurable.measure(constraints.asLoose(width = true, height = true))

          layout(width = constraints.minWidth, height = constraints.minHeight) {
            placeable.place(
              x = halfIconSpacedByPx,
              y = Alignment.CenterVertically.align(
                size = placeable.height,
                space = constraints.minHeight,
              )
            )
          }
        }
        Box(
          Modifier
            .layoutId(FakeTrailingIconLayoutId)
            .fontScaleAwareIconSize(baseline = trailingIconSize!!)
        )
      }
    },
  ) { measurables, constraints ->
    val textMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == TextLayoutId
    }!!
    val trailingIconContainerMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == TrailingIconContainerLayoutId
    }
    val fakeTrailingIconMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == FakeTrailingIconLayoutId
    }

    val looseConstraints = constraints.asLoose(width = true, height = true)

    val iconSpacedByPx = iconSpacedBy.roundToPx()
    val halfIconSpacedByPx = iconSpacedByPx / 2

    val startHorizontalPadding = contentPadding?.horizontal?.roundToPx() ?: 0
    val trailingIconAwareEndHorizontalPadding = startHorizontalPadding
      .applyIf(trailingIcon != null) { minus(iconSpacedByPx) }
    val verticalPadding = (contentPadding?.vertical?.times(2)?.roundToPx()) ?: 0

    val textPlaceable = textMeasurable.measure(looseConstraints)
    val fakeTrailingIconPlaceable = fakeTrailingIconMeasurable?.measure(looseConstraints)

    val height = constraints.constrainHeight(textPlaceable.height + verticalPadding)

    val trailingIconContainerConstraints = fakeTrailingIconPlaceable?.let { baseline ->
      Constraints.fixed(
        width = halfIconSpacedByPx + baseline.measuredWidth + trailingIconAwareEndHorizontalPadding,
        height = height,
      )
    }
    val trailingIconContainerPlaceable = trailingIconContainerMeasurable?.measure(trailingIconContainerConstraints!!)

    val width = constraints.constrainWidth(
      0
        .plus(startHorizontalPadding)
        .plus(textPlaceable.width)
        .applyIf(trailingIcon != null) { plus(halfIconSpacedByPx) }
        .plus(trailingIconContainerConstraints?.minWidth ?: trailingIconAwareEndHorizontalPadding)
    )

    layout(width = width, height = height) {
      textPlaceable.place(
        x = startHorizontalPadding,
        y = Alignment.CenterVertically.align(
          size = textPlaceable.height,
          space = height,
        ),
      )

      trailingIconContainerPlaceable?.place(
        x = startHorizontalPadding + textPlaceable.width + halfIconSpacedByPx,
        y = Alignment.CenterVertically.align(
          size = trailingIconContainerPlaceable.height,
          space = height,
        ),
      )
    }
  }
}
