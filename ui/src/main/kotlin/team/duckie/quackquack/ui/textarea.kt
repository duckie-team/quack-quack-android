/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("ModifierParameter")

package team.duckie.quackquack.ui

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.sp
import kotlin.math.max
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackPadding
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackSurface
import team.duckie.quackquack.material.theme.LocalQuackTextFieldTheme
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.runtime.quackMaterializeOf
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.ui.plugin.interceptor.rememberInterceptedStyleSafely
import team.duckie.quackquack.ui.util.LazyValue
import team.duckie.quackquack.ui.util.buildInt
import team.duckie.quackquack.ui.util.rememberLtrTextMeasurer
import team.duckie.quackquack.util.applyIf
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull

@Immutable
public interface TextAreaStyleMarker

@Immutable
public interface QuackTextAreaStyle {
  public val radius: Dp
  public val minHeight: Dp
  public val colors: TextAreaColors

  public val border: QuackBorder
  public val contentPadding: QuackPadding

  public val placeholderTypography: QuackTypography
  public val typography: QuackTypography

  public operator fun invoke(style: QuackTextAreaDefaultStyle.() -> Unit): QuackTextAreaStyle

  public companion object {
    @Stable
    public val Default: QuackTextAreaStyle get() = QuackTextAreaDefaultStyle()
  }
}

public data class TextAreaColors internal constructor(
  public val backgroundColor: QuackColor,
  public val contentColor: QuackColor,
  public val placeholderColor: QuackColor,
)

@Immutable
public class QuackTextAreaDefaultStyle : QuackTextAreaStyle {
  override var radius: Dp = 8.dp
  override var minHeight: Dp = 140.dp
  override var colors: TextAreaColors =
    TextAreaColors(
      backgroundColor = QuackColor.White,
      contentColor = QuackColor.Black,
      placeholderColor = QuackColor.Gray2,
    )

  override var border: QuackBorder = QuackBorder(thickness = 1.dp, color = QuackColor.Gray3)
  override var contentPadding: QuackPadding = QuackPadding(horizontal = 12.dp, vertical = 12.dp)

  override var placeholderTypography: QuackTypography = QuackTypography.Body1
  override var typography: QuackTypography = QuackTypography.Body1

  public override fun invoke(style: QuackTextAreaDefaultStyle.() -> Unit): QuackTextAreaStyle =
    apply(style)
}

@Stable
private data class TextAreaCounterData(
  val baseColor: QuackColor,
  val highlightColor: QuackColor,
  val typography: QuackTypography,
  val baseAndHighlightGap: Dp,
  val maxLength: Int,
  val contentGap: Dp,
) : QuackDataModifierModel

/**
 * TextArea에 counter를 표시합니다.
 *
 * counter는 다음과 같은 구조를 갖습니다.
 *
 * ```
 * // 현재 10자가 입력됐고, 최대 20자까지 허용이라면
 *
 * 10 /20
 * ```
 *
 * 위 예시에서 현재 입력된 글자 수를 의미하는 `10` 부분을 highlight 영역이라 하고,
 * 최대 글자 수를 의미하는 `/20` 부분을 base 영역이라 합니다.
 *
 * @param maxLength 허용하는 최대 글자 수 (base 영역으로 표시할 글자 수)
 * @param baseColor base 영역의 색상
 * @param highlightColor highlight 영역의 색상
 * @param typography base 영역과 highlight 영역 모두에 공통으로 사용할 타이포그래피
 * @param baseAndHighlightGap base 영역과 highlight 영역 사이 공간
 * @param contentGap TextArea 컨텐츠와 counter 영역 사이 공간
 */
@Stable
public fun Modifier.textAreaCounter(
  @IntRange(from = 1) maxLength: Int,
  baseColor: QuackColor = QuackColor.Gray2,
  highlightColor: QuackColor = QuackColor.Black,
  typography: QuackTypography = QuackTypography.Body1,
  baseAndHighlightGap: Dp = 1.dp,
  contentGap: Dp = 8.dp,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "textAreaCounter"
      properties["maxLength"] = maxLength
      properties["baseColor"] = baseColor
      properties["highlightColor"] = highlightColor
      properties["typography"] = typography
      properties["baseAndHighlightGap"] = baseAndHighlightGap
      properties["contentGap"] = contentGap
    },
  ) {
    TextAreaCounterData(
      baseColor = baseColor,
      highlightColor = highlightColor,
      typography = typography,
      baseAndHighlightGap = baseAndHighlightGap,
      maxLength = maxLength,
      contentGap = contentGap,
    )
  }

@[NonRestartableComposable Composable]
public fun QuackTextArea(
  @CasaValue("\"QuackTextAreaPreview\"") value: String,
  @CasaValue("{}") onValueChange: (value: String) -> Unit,
  @[SugarToken CasaValue("QuackTextAreaStyle.Default")] style: QuackTextAreaStyle = QuackTextAreaStyle.Default,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  placeholderText: String? = null,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  @IntRange(from = 1) minLines: Int = 1,
  @IntRange(from = 1) maxLines: Int = Int.MAX_VALUE,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit = {},
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  // start --- COPIED FROM BasicTextField (string value variant)

  // 최신 내부 텍스트 필드 값 상태를 보유합니다. 컴포지션의 올바른 값을 갖기 위해 이 값을 유지해야 합니다.
  var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }

  // 재구성된 최신 TextFieldValue를 보유합니다. 컴포지션을 보존해야 하기 때문에 `TextFieldValue(text = value)`를
  // CoreTextField에 단순히 전달할 수 없었습니다.
  val textFieldValue = textFieldValueState.copy(text = value)

  SideEffect {
    if (textFieldValue.selection != textFieldValueState.selection ||
      textFieldValue.composition != textFieldValueState.composition
    ) {
      textFieldValueState = textFieldValue
    }
  }

  // 텍스트 필드가 재구성되었거나 onValueChange 콜백에서 업데이트된 마지막 문자열 값입니다.
  // 이 값을 추적하여 다음과 같은 경우 동일한 문자열에 대해 onValueChange(String)를 호출하지 않도록 합니다.
  // CoreTextField의 onValueChange가 중간에 재구성되지 않고 여러 번 호출되는 것을 방지합니다.
  var lastTextValue by remember(value) { mutableStateOf(value) }

  QuackTextArea(
    value = textFieldValue,
    onValueChange = { newTextFieldValueState ->
      textFieldValueState = newTextFieldValueState

      val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
      lastTextValue = newTextFieldValueState.text

      if (stringChangedSinceLastInvocation) {
        onValueChange(newTextFieldValueState.text)
      }
    },
    // end --- COPIED FROM BasicTextField (string value variant)
    style = style,
    modifier = modifier,
    enabled = enabled,
    readOnly = readOnly,
    placeholderText = placeholderText,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    interactionSource = interactionSource,
  )
}

@[NonRestartableComposable Composable]
public fun QuackTextArea(
  @CasaValue("\"QuackTextAreaPreview\"") value: TextFieldValue,
  @CasaValue("{}") onValueChange: (value: TextFieldValue) -> Unit,
  @[SugarToken CasaValue("QuackTextAreaStyle.Default")] style: QuackTextAreaStyle = QuackTextAreaStyle.Default,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  placeholderText: String? = null,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  @IntRange(from = 1) minLines: Int = 1,
  @IntRange(from = 1) maxLines: Int = Int.MAX_VALUE,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit = {},
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  @Suppress("NAME_SHADOWING")
  val style = rememberInterceptedStyleSafely<QuackTextAreaStyle>(style = style, modifier = modifier)

  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
  val counterData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TextAreaCounterData>()
  }

  val radius = style.radius
  val minHeight = style.minHeight

  val backgroundColor = style.colors.backgroundColor
  val contentColor = style.colors.contentColor
  val placeholderColor = style.colors.placeholderColor

  val border = style.border
  val contentPadding = style.contentPadding.asPaddingValues()

  val typography = remember(style.typography, contentColor) {
    style.typography.change(color = contentColor)
  }
  val placeholderTypography = remember(style.typography, placeholderColor) {
    style.typography.change(color = placeholderColor)
  }

  QuackBaseTextArea(
    value = value,
    onValueChange = onValueChange,
    modifier = composeModifier,
    radius = radius,
    minHeight = minHeight,
    border = border,
    enabled = enabled,
    readOnly = readOnly,
    placeholderText = placeholderText,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    interactionSource = interactionSource,
    backgroundColor = backgroundColor,
    contentPadding = contentPadding,
    typography = typography,
    placeholderTypography = placeholderTypography,
    // decorators
    counterBaseColor = counterData?.baseColor,
    counterHighlightColor = counterData?.highlightColor,
    counterTypography = counterData?.typography,
    counterBaseAndHighlightGap = counterData?.baseAndHighlightGap,
    counterMaxLength = counterData?.maxLength,
    counterContentGap = counterData?.contentGap,
  )
}

/** 텍스트 필드의 너비가 지정되지 않았을 떄 기본으로 사용할 너비 */
private val DefaultMinWidth = 200.dp

@Composable
public fun QuackBaseTextArea(
  value: TextFieldValue,
  onValueChange: (value: TextFieldValue) -> Unit,
  modifier: Modifier,
  radius: Dp,
  minHeight: Dp,
  border: QuackBorder,
  enabled: Boolean,
  readOnly: Boolean,
  placeholderText: String?,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions,
  minLines: Int,
  maxLines: Int,
  visualTransformation: VisualTransformation,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit,
  interactionSource: MutableInteractionSource,
  backgroundColor: QuackColor,
  contentPadding: PaddingValues,
  typography: QuackTypography,
  placeholderTypography: QuackTypography,
  // decorators
  counterBaseColor: QuackColor?,
  counterHighlightColor: QuackColor?,
  counterTypography: QuackTypography?,
  counterBaseAndHighlightGap: Dp?,
  counterMaxLength: Int?,
  counterContentGap: Dp?,
) {
  assertTextAreaValidState(
    counterBaseColor = counterBaseColor,
    counterHighlightColor = counterHighlightColor,
    counterTypography = counterTypography,
    counterBaseAndHighlightGap = counterBaseAndHighlightGap,
    counterMaxLength = counterMaxLength,
    contentGap = counterContentGap,
  )

  val shape = remember { RoundedCornerShape(size = radius) }

  val currentCursorColor = LocalQuackTextFieldTheme.current.cursorColor
  val currentDensity = LocalDensity.current
  val currentCursorBrush = remember(currentCursorColor, calculation = currentCursorColor::toBrush)
  val currentTextStyle = remember(typography, calculation = typography::asComposeStyle)
  val currentRecomposeScope = currentRecomposeScope

  val topPaddingPx = with(currentDensity) { contentPadding.calculateTopPadding().roundToPx() }
  val bottomPaddingPx = with(currentDensity) { contentPadding.calculateBottomPadding().roundToPx() }
  val rightPaddingPx =
    with(currentDensity) { contentPadding.calculateRightPadding(LayoutDirection.Ltr).roundToPx() }

  val lazyCoreTextFieldWidth = remember { LazyValue<Int>() }

  val placeholderComposeTypography =
    remember(placeholderTypography, calculation = placeholderTypography::asComposeStyle)
  val placeholderConstraints =
    remember(lazyCoreTextFieldWidth.value) {
      if (lazyCoreTextFieldWidth.value != null) Constraints(maxWidth = lazyCoreTextFieldWidth.value!!)
      else Constraints()
    }
  val placeholderTextMeasurer = rememberLtrTextMeasurer(/*cacheSize = 5*/) // TODO(pref): param size?
  val placeholderTextMeasureResult =
    remember(
      placeholderComposeTypography,
      placeholderConstraints,
      placeholderTextMeasurer,
      placeholderText,
    ) {
      if (placeholderText != null) {
        placeholderTextMeasurer.measure(
          text = placeholderText,
          style = placeholderComposeTypography,
          constraints = placeholderConstraints,
        )
      } else {
        null
      }
    }

  val counterComposeTypography = remember(counterTypography) {
    counterTypography?.change(textAlign = TextAlign.End)?.asComposeStyle()
  }
  val counterPlaceholders = remember(currentDensity, counterBaseAndHighlightGap, value.text.length) {
    if (counterBaseAndHighlightGap == null) return@remember emptyList<AnnotatedString.Range<Placeholder>>()

    val currentLength = value.text.length
    listOf(
      AnnotatedString.Range(
        item = Placeholder(
          width = with(currentDensity) { counterBaseAndHighlightGap.toSp() },
          height = 1.sp,
          placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
        ),
        start = "$currentLength".length,
        end = "$currentLength".length + 1,
      ),
    )
  }

  val counterTextMeasurer = rememberLtrTextMeasurer(/*cacheSize = 7*/) // TODO(pref): param size?
  val counterTextMeasureResult =
    remember(
      counterPlaceholders,
      counterComposeTypography,
      counterTextMeasurer,
      value.text,
      counterMaxLength,
      counterHighlightColor,
      counterBaseColor,
    ) {
      if (counterMaxLength != null) {
        counterTextMeasurer.measure(
          text = buildAnnotatedString {
            withStyle(SpanStyle(color = counterHighlightColor!!.value)) {
              append(value.text.length.toString())
            }
            withStyle(SpanStyle(color = counterBaseColor!!.value)) {
              append(" /$counterMaxLength")
            }
          },
          placeholders = counterPlaceholders,
          style = counterComposeTypography!!,
          maxLines = 1,
        )
      } else {
        null
      }
    }

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = currentTextStyle,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = false,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    interactionSource = interactionSource,
    cursorBrush = currentCursorBrush,
  ) { coreTextField ->
    Layout(
      modifier = modifier
        .quackSurface(
          shape = shape,
          backgroundColor = backgroundColor,
          border = border,
        )
        .applyIf(counterTextMeasureResult != null) {
          drawBehind {
            drawText(
              textLayoutResult = counterTextMeasureResult!!,
              topLeft = Offset(
                x = size.width - rightPaddingPx - counterTextMeasureResult.size.width,
                y = size.height - bottomPaddingPx - counterTextMeasureResult.size.height,
              ),
            )
          }
        },
      content = {
        Box(
          modifier = Modifier
            .then(Modifier)
            .applyIf(placeholderTextMeasureResult != null) {
              drawBehind {
                if (value.text.isEmpty()) drawText(placeholderTextMeasureResult!!)
              }
            },
          propagateMinConstraints = true,
        ) {
          coreTextField()
        }
      },
    ) { measurables, constraints ->
      val coreTextFieldMeasurable = measurables.single()

      val leftPaddingPx = contentPadding.calculateLeftPadding(layoutDirection).roundToPx()
      val horizontalPaddingPx = leftPaddingPx + rightPaddingPx
      val verticalPaddingPx = topPaddingPx + bottomPaddingPx

      val minWidth = constraints.minWidth.let { minWidth ->
        if (minWidth == 0) DefaultMinWidth.roundToPx()
        else minWidth
      }

      val coreTextFieldConstraints =
        constraints
          .copy(minWidth = minWidth, minHeight = 0)
          .offset(horizontal = -horizontalPaddingPx, vertical = -verticalPaddingPx)
      val coreTextFieldPlaceable = coreTextFieldMeasurable.measure(coreTextFieldConstraints)
      val coreTextFieldWidth = max(coreTextFieldPlaceable.width, minWidth)

      if (lazyCoreTextFieldWidth.value == null) {
        lazyCoreTextFieldWidth.value = coreTextFieldWidth
        currentRecomposeScope.invalidate()
      }

      val width = constraints.constrainWidth(coreTextFieldWidth + horizontalPaddingPx)
      val height = run {
        val currentHeight =
          buildInt {
            plus(coreTextFieldPlaceable.height)
            plus(verticalPaddingPx)
            if (counterMaxLength != null) plus(counterContentGap!!.roundToPx())
          }
        val polishHeight = max(currentHeight, minHeight.roundToPx())

        constraints.constrainHeight(polishHeight)
      }

      layout(width = width, height = height) {
        coreTextFieldPlaceable.place(x = leftPaddingPx, y = topPaddingPx)
      }
    }
  }
}

private fun assertTextAreaValidState(
  counterBaseColor: QuackColor?,
  counterHighlightColor: QuackColor?,
  counterTypography: QuackTypography?,
  counterBaseAndHighlightGap: Dp?,
  counterMaxLength: Int?,
  contentGap: Dp?,
) {
  if (counterMaxLength != null) {
    requireNotNull(counterBaseColor)
    requireNotNull(counterHighlightColor)
    requireNotNull(counterTypography)
    requireNotNull(counterBaseAndHighlightGap)
    requireNotNull(contentGap)
  }
}
