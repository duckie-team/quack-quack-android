/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalTextApi::class)

package team.duckie.quackquack.ui

import android.annotation.SuppressLint
import androidx.annotation.IntRange
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach
import team.duckie.quackquack.aide.annotation.DecorateModifier
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackSurface
import team.duckie.quackquack.material.theme.LocalQuackTextFieldTheme
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.runtime.quackMaterializeOf
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.IconRole
import team.duckie.quackquack.ui.token.VerticalDirection
import team.duckie.quackquack.ui.util.ExperimentalQuackQuackApi
import team.duckie.quackquack.ui.util.QuackDsl
import team.duckie.quackquack.ui.util.buildInt
import team.duckie.quackquack.ui.util.reflectivelyFillMaxSizeOperationHashCode
import team.duckie.quackquack.ui.util.wrappedDebugInspectable
import team.duckie.quackquack.util.MustBeTested
import team.duckie.quackquack.util.applyIf
import team.duckie.quackquack.util.cast
import team.duckie.quackquack.util.fastFilterIsInstanceOrNull
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull
import team.duckie.quackquack.util.requireNull

@Immutable
public sealed class TextFieldValidationState {
  public sealed interface WithLabel {
    public val label: String?
  }

  public class Error(override val label: String? = null) : TextFieldValidationState(), WithLabel
  public class Success(override val label: String? = null) : TextFieldValidationState(), WithLabel

  public object Default : TextFieldValidationState()
}

@Immutable
public enum class TextFieldPlaceholderStrategy {
  Always, Hidable,
}

@Immutable
public enum class TextFieldValidationLabelVisibilityStrategy {
  Invisible, Gone,
}

@Stable
private data class TextFieldIconData(
  val icon: QuackIcon,
  val iconSize: Dp,
  val tint: QuackColor?,
  val tintGetter: ((text: String, validationState: TextFieldValidationState) -> QuackColor)?,
  val role: IconRole,
  val direction: HorizontalDirection,
  val contentDescription: String?,
  val onClick: ((text: String) -> Unit)?,
) : QuackDataModifierModel {
  init {
    if (isButtonRole) {
      requireNotNull(onClick, lazyMessage = TextFieldErrors::ButtonIconRuleButNoOnClick)
    }
  }
}

@Stable
private val TextFieldIconData.isButtonRole: Boolean
  inline get() = role == IconRole.Button

@Stable
private data class TextFieldIndicatorData(
  val thickness: Dp,
  val color: QuackColor?,
  val colorGetter: ((text: String, validationState: TextFieldValidationState) -> QuackColor)?,
  val direction: VerticalDirection,
) : QuackDataModifierModel {
  init {
    require(
      color != null || colorGetter != null,
      lazyMessage = TextFieldErrors::IndicatorRequestedButNoColor,
    )
  }
}

@Stable
private data class TextFieldCounterData(
  val baseColor: QuackColor,
  val highlightColor: QuackColor,
  val typography: QuackTypography,
  val baseAndHighlightGap: Dp,
  val maxLength: Int,
) : QuackDataModifierModel

@QuackDsl
@Immutable
public interface TextFieldStyleMarker

@Immutable
public interface TextFieldColorMarker

public interface QuackDefaultTextFieldStyle : TextFieldStyleMarker {
  public data class TextFieldColors internal constructor(
    internal val backgroundColor: QuackColor,
    internal val contentColor: QuackColor,
    internal val placeholderColor: QuackColor,
    internal val errorColor: QuackColor,
    internal val successColor: QuackColor,
  ) : TextFieldColorMarker

  public val validationTypography: QuackTypography

  @Stable
  public fun textFieldColors(
    backgroundColor: QuackColor,
    contentColor: QuackColor,
    placeholderColor: QuackColor,
    errorColor: QuackColor,
    successColor: QuackColor,
  ): TextFieldColors =
    TextFieldColors(
      backgroundColor = backgroundColor,
      contentColor = contentColor,
      placeholderColor = placeholderColor,
      errorColor = errorColor,
      successColor = successColor,
    )
}

public interface QuackFilledTextFieldStyle : TextFieldStyleMarker {
  public data class TextFieldColors internal constructor(
    internal val backgroundColor: QuackColor?,
    internal val backgroundColorGetter: ((focusInteraction: FocusInteraction, text: String) -> QuackColor)?,
    internal val contentColor: QuackColor,
    internal val placeholderColor: QuackColor,
  ) : TextFieldColorMarker

  public val radius: Dp

  @Stable
  public fun textFieldColors(
    backgroundColor: QuackColor?,
    backgroundColorGetter: ((focusInteraction: FocusInteraction, text: String) -> QuackColor)?,
    contentColor: QuackColor,
    placeholderColor: QuackColor,
  ): TextFieldColors =
    TextFieldColors(
      backgroundColor = backgroundColor,
      backgroundColorGetter = backgroundColorGetter,
      contentColor = contentColor,
      placeholderColor = placeholderColor,
    )
}

// TODO: 이 컴포넌트가 사용되는 디자인은 아직 개발 범위가 아니므로 TODO 처리
public interface QuackOutlinedTextFieldStyle : TextFieldStyleMarker

@ExperimentalDesignToken
@Immutable
public interface QuackTextFieldStyle<Style : TextFieldStyleMarker, Color : TextFieldColorMarker> {
  /** 사용할 색상들 */
  public val colors: Color

  /** 컨텐츠 주변에 들어갈 패딩 */
  public val contentPadding: PaddingValues

  /** 배치되는 아이콘과 텍스트 사이의 간격 */
  public val contentSpacedBy: Dp

  /** 활성화 상태에서 표시될 텍스트의 타이포그래피 */
  public val typography: QuackTypography

  /** 디자인 스펙을 변경하는 람다 */
  @Stable
  public operator fun invoke(styleBuilder: Style.() -> Unit): Style

  @Stable
  public fun Modifier.wrappedDebugInspectable(): Modifier

  @Immutable
  public companion object {
    public val Default: QuackTextFieldStyle<QuackDefaultTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>
      get() = QuackDefaultTextFieldDefaults()

    public val DefaultLarge: QuackTextFieldStyle<QuackDefaultLargeTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>
      get() = QuackDefaultLargeTextFieldDefaults()

    // public val FilledLarge: QuackTextFieldStyle<QuackFilledLargeTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>
    //   get() = QuackFilledLargeTextFieldDefaults()

    // public val FilledFlat: QuackTextFieldStyle<QuackFilledFlatTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>
    //   get() = QuackFilledFlatTextFieldDefaults()
  }
}

@ExperimentalDesignToken
public class QuackDefaultTextFieldDefaults internal constructor() :
  QuackTextFieldStyle<QuackDefaultTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>,
  QuackDefaultTextFieldStyle {

  override var colors: QuackDefaultTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = QuackColor.White,
      contentColor = QuackColor.Black,
      placeholderColor = QuackColor.Gray2,
      errorColor = QuackColor.Alert,
      successColor = QuackColor.Success,
    )

  override var contentPadding: PaddingValues =
    PaddingValues(
      top = 16.dp,
      bottom = 8.dp,
    )

  override var contentSpacedBy: Dp = 8.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override val validationTypography: QuackTypography = QuackTypography.Body1

  override fun invoke(styleBuilder: QuackDefaultTextFieldDefaults.() -> Unit): QuackDefaultTextFieldDefaults =
    apply(styleBuilder)

  override fun Modifier.wrappedDebugInspectable(): Modifier =
    wrappedDebugInspectable {
      name = toString()
      properties["colors"] = colors
      properties["contentPadding"] = contentPadding
      properties["contentSpacedBy"] = contentSpacedBy
      properties["typography"] = typography
    }

  override fun toString(): String = this::class.java.simpleName
}

@ExperimentalDesignToken
public class QuackDefaultLargeTextFieldDefaults :
  QuackTextFieldStyle<QuackDefaultLargeTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>,
  QuackDefaultTextFieldStyle {

  override var colors: QuackDefaultTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = QuackColor.White,
      contentColor = QuackColor.Black,
      placeholderColor = QuackColor.Gray2,
      errorColor = QuackColor.Alert,
      successColor = QuackColor.Success,
    )

  override var contentPadding: PaddingValues = PaddingValues(all = 16.dp)

  override var contentSpacedBy: Dp = 8.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override val validationTypography: QuackTypography = QuackTypography.Body1

  override fun invoke(styleBuilder: QuackDefaultLargeTextFieldDefaults.() -> Unit): QuackDefaultLargeTextFieldDefaults =
    apply(styleBuilder)

  override fun Modifier.wrappedDebugInspectable(): Modifier =
    wrappedDebugInspectable {
      name = toString()
      properties["colors"] = colors
      properties["contentPadding"] = contentPadding
      properties["contentSpacedBy"] = contentSpacedBy
      properties["typography"] = typography
    }

  override fun toString(): String = this::class.java.simpleName
}

@ExperimentalDesignToken
public class QuackFilledLargeTextFieldDefaults :
  QuackTextFieldStyle<QuackFilledLargeTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>,
  QuackFilledTextFieldStyle {

  override var radius: Dp = 8.dp

  override var colors: QuackFilledTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = QuackColor.Gray4,
      backgroundColorGetter = null,
      contentColor = QuackColor.Black,
      placeholderColor = QuackColor.Gray2,
    )

  override var contentPadding: PaddingValues =
    PaddingValues(
      horizontal = 20.dp,
      vertical = 17.dp,
    )

  override var contentSpacedBy: Dp = 10.dp

  // TODO(3): 폰트 확인
  override var typography: QuackTypography = QuackTypography.Subtitle

  override fun invoke(styleBuilder: QuackFilledLargeTextFieldDefaults.() -> Unit): QuackFilledLargeTextFieldDefaults =
    apply(styleBuilder)

  override fun Modifier.wrappedDebugInspectable(): Modifier =
    wrappedDebugInspectable {
      name = toString()
      properties["radius"] = radius
      properties["colors"] = colors
      properties["contentPadding"] = contentPadding
      properties["contentSpacedBy"] = contentSpacedBy
      properties["typography"] = typography
    }

  override fun toString(): String = this::class.java.simpleName
}

@ExperimentalDesignToken
public class QuackFilledFlatTextFieldDefaults :
  QuackTextFieldStyle<QuackFilledFlatTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>,
  QuackFilledTextFieldStyle {

  override var radius: Dp = 8.dp

  override var colors: QuackFilledTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = null,
      backgroundColorGetter = { focusInteraction, text ->
        if (text.isNotEmpty()) QuackColor.White
        else when (focusInteraction) {
          is FocusInteraction.Focus -> QuackColor.White
          is FocusInteraction.Unfocus -> QuackColor.Gray4
          else -> error(TextFieldErrors.unhandledFocusInteraction(focusInteraction))
        }
      },
      contentColor = QuackColor.Black,
      placeholderColor = QuackColor.Gray2,
    )

  override var contentPadding: PaddingValues =
    PaddingValues(
      horizontal = 12.dp,
      vertical = 8.dp,
    )

  override var contentSpacedBy: Dp = 10.dp

  override var typography: QuackTypography = QuackTypography.Body1

  override fun invoke(styleBuilder: QuackFilledFlatTextFieldDefaults.() -> Unit): QuackFilledFlatTextFieldDefaults =
    apply(styleBuilder)

  override fun Modifier.wrappedDebugInspectable(): Modifier =
    wrappedDebugInspectable {
      name = toString()
      properties["radius"] = radius
      properties["colors"] = colors
      properties["contentPadding"] = contentPadding
      properties["contentSpacedBy"] = contentSpacedBy
      properties["typography"] = typography
    }

  override fun toString(): String = this::class.java.simpleName
}

@VisibleForTesting
internal object TextFieldErrors {
  fun sameDirectionIcon(direction: String) = "The icon was provided more than once in the same direction. " +
      "Only one icon can be displayed per direction. (Direction offered twice: $direction)"

  fun unhandledFocusInteraction(interaction: FocusInteraction) = "An unhandled focus interaction was provided. " +
      "($interaction)"

  const val IndicatorRequestedButNoColor = "Show indicator was requested, but no indicator color was provided. " +
      "Please provide a non-null value for one of the color or colorGetter fields."

  const val ButtonIconRuleButNoOnClick = "The icon's rule was provided as Button, but no onClick event was provided. " +
      "Please set the icon's rule to Icon or provide an onClick event."

  const val ValidationLabelProvidedButNoDownDirectionIndicator =
    "A label was provided as a TextFieldValidationState, " +
        "but the indicator must have a direction of VerticalDirection.Down in order to display the label. " +
        "The current direction is VerticalDirection.Top."
}

private val DefaultIconSize = 16.dp

@DecorateModifier
@Stable
public fun Modifier.icon(
  icon: QuackIcon,
  iconSize: Dp = DefaultIconSize,
  tint: QuackColor? = QuackColor.Gray2,
  tintGetter: ((text: String, validationState: TextFieldValidationState) -> QuackColor)? = null,
  role: IconRole = if (iconSize == DefaultIconSize) IconRole.Icon else IconRole.Button,
  direction: HorizontalDirection = if (iconSize == DefaultIconSize) HorizontalDirection.Left else HorizontalDirection.Right,
  contentDescription: String? = null,
  onClick: ((text: String) -> Unit)? = null,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "icon"
      properties["icon"] = icon
      properties["iconSize"] = iconSize
      properties["tint"] = tint
      properties["tintGetter"] = tintGetter
      properties["role"] = role
      properties["direction"] = direction
      properties["contentDescription"] = contentDescription
      properties["onClick"] = onClick
    },
  ) {
    TextFieldIconData(
      icon = icon,
      iconSize = iconSize,
      tint = tint,
      tintGetter = tintGetter,
      role = role,
      direction = direction,
      contentDescription = contentDescription,
      onClick = onClick,
    )
  }

@DecorateModifier
@Stable
public fun Modifier.indicator(
  direction: VerticalDirection = VerticalDirection.Down,
  thickness: Dp = 1.dp,
  color: QuackColor? = QuackColor.Gray3,
  colorGetter: ((text: String, validationState: TextFieldValidationState) -> QuackColor)? = null,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "indicator"
      properties["thickness"] = thickness
      properties["color"] = color
      properties["colorGetter"] = colorGetter
      properties["direction"] = direction
    },
  ) {
    TextFieldIndicatorData(
      thickness = thickness,
      color = color,
      colorGetter = colorGetter,
      direction = direction,
    )
  }

@DecorateModifier
@Stable
public fun Modifier.counter(
  @IntRange(from = 1) maxLength: Int,
  baseColor: QuackColor = QuackColor.Gray2,
  highlightColor: QuackColor = QuackColor.Black,
  typography: QuackTypography = QuackTypography.Body1,
  baseAndHighlightGap: Dp = 1.dp,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "counter"
      properties["baseColor"] = baseColor
      properties["highlightColor"] = highlightColor
      properties["typography"] = typography
      properties["baseAndHighlightGap"] = baseAndHighlightGap
      properties["maxLength"] = maxLength
    },
  ) {
    TextFieldCounterData(
      baseColor = baseColor,
      highlightColor = highlightColor,
      typography = typography,
      baseAndHighlightGap = baseAndHighlightGap,
      maxLength = maxLength,
    )
  }

@Stable
private fun Modifier.drawPlaceholder(
  text: String,
  placeholderText: String,
  placeholderStrategy: TextFieldPlaceholderStrategy,
  typography: QuackTypography,
  textMeasurer: TextMeasurer,
  topLeftOffset: Offset,
): Modifier =
  drawWithCache {
    val density = object : Density {
      override val density = this@drawWithCache.density
      override val fontScale = this@drawWithCache.fontScale
    }

    val textMeasurerResult =
      textMeasurer.measure(
        text = placeholderText,
        style = typography.asComposeStyle(),
        overflow = TextOverflow.Ellipsis,
        layoutDirection = layoutDirection,
        density = density,
      )

    fun DrawScope.drawPlaceholder() {
      drawText(
        textLayoutResult = textMeasurerResult,
        topLeft = topLeftOffset,
      )
    }

    onDrawBehind {
      if (placeholderStrategy == TextFieldPlaceholderStrategy.Always) drawPlaceholder()
      else if (text.isEmpty()) drawPlaceholder()
    }
  }

// TODO: 인자로 이동?
private val DefaultIndicatorAndLabelGap = 4.dp

@Stable
private fun Modifier.drawIndicator(
  thickness: Dp,
  color: QuackColor,
  direction: VerticalDirection,
  validationState: TextFieldValidationState,
  errorTypography: QuackTypography,
  successTypography: QuackTypography,
  textMeasurer: TextMeasurer,
): Modifier =
  this
    .also {
      if (
        validationState is TextFieldValidationState.WithLabel &&
        validationState.label != null
      ) {
        require(
          direction == VerticalDirection.Down,
          lazyMessage = TextFieldErrors::ValidationLabelProvidedButNoDownDirectionIndicator,
        )
      }
    }
    .drawWithCache {
      val borderColor = color.value
      val yOffset = size.height - thickness.toPx()
      val indicatorAndLabelGap = DefaultIndicatorAndLabelGap.toPx()

      val startOffset = when (direction) {
        VerticalDirection.Top -> Offset(x = 0f, y = 0f)
        VerticalDirection.Down -> Offset(x = 0f, y = yOffset)
      }
      val endOffset = when (direction) {
        VerticalDirection.Top -> Offset(x = size.width, y = 0f)
        VerticalDirection.Down -> Offset(x = size.width, y = yOffset)
      }

      val density = object : Density {
        override val density = this@drawWithCache.density
        override val fontScale = this@drawWithCache.fontScale
      }

      val textMeasurerResult =
        if (validationState is TextFieldValidationState.WithLabel && validationState.label != null) {
          val typography =
            if (validationState is TextFieldValidationState.Success) successTypography
            else errorTypography

          textMeasurer.measure(
            // [SMARTCAST_IMPOSSIBLE] Smart cast to 'String' is impossible, because a property that has open or custom getter
            text = validationState.label!!,
            style = typography.asComposeStyle(),
            overflow = TextOverflow.Visible,
            layoutDirection = layoutDirection,
            density = density,
          )
        } else {
          null
        }

      onDrawBehind {
        drawLine(
          color = borderColor,
          start = startOffset,
          end = endOffset,
          strokeWidth = thickness.toPx(),
        )
        if (textMeasurerResult != null) {
          drawText(
            textLayoutResult = textMeasurerResult,
            topLeft = Offset(
              x = 0f,
              y = size.height + indicatorAndLabelGap,
            ),
          )
        }
      }
    }

// TODO(impl): 텍스트를 그릴 X 좌표를 구할 수 있어야 하고,
//             BaseTextField의 입장에선 텍스트가 그려진 X 좌표를 알아야 함.
@Suppress("UNUSED_PARAMETER", "UNREACHABLE_CODE", "unused", "ModifierFactoryExtensionFunction")
@Stable
private fun TextFieldCounterData.toDrawModifier(
  @IntRange(from = 1) currentLength: Int,
  contentPadding: PaddingValues,
): Modifier =
  composed { // TODO(perf): nested-composed 제거 (Modifier.drawWithCache is ComposedModifier)
    throw NotImplementedError()

    val textMeasurer = rememberTextMeasurer(cacheSize = 5 + 2) // model datas + params

    Modifier.drawWithCache {
      // TODO(impl): baseAndHighlightGap
      val textMeasurerResult = textMeasurer.measure(
        text = buildAnnotatedString {
          withStyle(SpanStyle(color = highlightColor.value)) {
            append(currentLength.toString())
          }
          withStyle(SpanStyle(color = baseColor.value)) {
            append("/$maxLength")
          }
        },
        style = typography.asComposeStyle(),
        overflow = TextOverflow.Visible,
        softWrap = false,
        maxLines = 1,
        layoutDirection = layoutDirection,
        density = object : Density {
          override val density = this@drawWithCache.density
          override val fontScale = this@drawWithCache.fontScale
        },
      )

      // TODO(3): VerticalCenter 배치
      onDrawBehind {
        drawText(
          textLayoutResult = textMeasurerResult,
          topLeft = Offset(
            x = 0f, // TODO: how to get?
            y = contentPadding.calculateTopPadding().toPx(),
          ),
        )
      }
    }
  }

// TODO(casa): support for state parameter. but how?
@ExperimentalDesignToken
@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun <Style : QuackDefaultTextFieldStyle> QuackDefaultTextField(
  @CasaValue("\"QuackDefaultTextFieldPreview\"") value: String,
  @CasaValue("{}") onValueChange: (value: String) -> Unit,
  @SugarToken @CasaValue("QuackTextFieldStyle.Default") style: QuackTextFieldStyle<Style, QuackDefaultTextFieldStyle.TextFieldColors>,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  placeholderText: String? = null,
  placeholderStrategy: TextFieldPlaceholderStrategy = TextFieldPlaceholderStrategy.Hidable,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = false,
  @IntRange(from = 1) minLines: Int = 1,
  @IntRange(from = 1) maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit = {},
  validationState: TextFieldValidationState = TextFieldValidationState.Default,
  validationLabelVisibilityStrategy: TextFieldValidationLabelVisibilityStrategy = TextFieldValidationLabelVisibilityStrategy.Invisible,
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

  QuackDefaultTextField(
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
    placeholderStrategy = placeholderStrategy,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    validationState = validationState,
    validationLabelVisibilityStrategy = validationLabelVisibilityStrategy,
    interactionSource = interactionSource,
  )
}

// TODO(casa): @NoCasa
@ExperimentalDesignToken
@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun <Style : QuackDefaultTextFieldStyle> QuackDefaultTextField(
  value: TextFieldValue,
  onValueChange: (value: TextFieldValue) -> Unit,
  @SugarToken style: QuackTextFieldStyle<Style, QuackDefaultTextFieldStyle.TextFieldColors>,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  readOnly: Boolean = false,
  placeholderText: String? = null,
  placeholderStrategy: TextFieldPlaceholderStrategy = TextFieldPlaceholderStrategy.Hidable,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  singleLine: Boolean = false,
  @IntRange(from = 1) minLines: Int = 1,
  @IntRange(from = 1) maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit = {},
  validationState: TextFieldValidationState = TextFieldValidationState.Default,
  validationLabelVisibilityStrategy: TextFieldValidationLabelVisibilityStrategy = TextFieldValidationLabelVisibilityStrategy.Invisible,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  var isSizeSpecified = false
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier) { currentModifier ->
    if (!isSizeSpecified && currentModifier is LayoutModifier) {
      isSizeSpecified = currentModifier.hashCode() != reflectivelyFillMaxSizeOperationHashCode
    }
  }
  val (leadingIconData, trailingIconData) = remember(quackDataModels) {
    val icons = quackDataModels.fastFilterIsInstanceOrNull<TextFieldIconData>()
    var leadingIconData: TextFieldIconData? = null
    var trailingIconData: TextFieldIconData? = null

    icons?.fastForEach { icon ->
      when (icon.direction) {
        HorizontalDirection.Left -> {
          requireNull(leadingIconData) { TextFieldErrors.sameDirectionIcon("leading") }
          leadingIconData = icon
        }
        HorizontalDirection.Right -> {
          requireNull(trailingIconData) { TextFieldErrors.sameDirectionIcon("trailing") }
          trailingIconData = icon
        }
      }
    }

    leadingIconData to trailingIconData
  }
  val indicatorData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TextFieldIndicatorData>()
  }
  val counterData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TextFieldCounterData>()
  }

  val backgroundColor = style.colors.backgroundColor
  val contentColor = style.colors.contentColor
  val placeholderColor = style.colors.placeholderColor
  val errorColor = style.colors.errorColor
  val successColor = style.colors.successColor

  val contentPadding = style.contentPadding
  val currentContentPadding = if (isSizeSpecified) null else contentPadding

  val contentSpacedBy = style.contentSpacedBy

  val typography = remember(style.typography, contentColor) {
    style.typography.change(color = contentColor)
  }
  val placeholderTypography = remember(style.typography, placeholderColor) {
    style.typography.change(color = placeholderColor)
  }
  // why need casting?
  val validationTypography = style.cast<QuackDefaultTextFieldStyle>().validationTypography
  val errorTypography = remember(validationTypography, errorColor) {
    validationTypography.change(color = errorColor)
  }
  val successTypography = remember(validationTypography, successColor) {
    validationTypography.change(color = successColor)
  }

  val currentLeadingIconTint = leadingIconData?.run {
    checkNotNull(tintGetter?.invoke(value.text, validationState) ?: tint)
  }
  val currentTrailingIconTint = trailingIconData?.run {
    checkNotNull(tintGetter?.invoke(value.text, validationState) ?: tint)
  }

  val currentIndicatorColor = indicatorData?.run {
    checkNotNull(colorGetter?.invoke(value.text, validationState) ?: color)
  }

  val inspectableModifier = with(style) { composeModifier.wrappedDebugInspectable() }

  QuackBaseDefaultTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = inspectableModifier,
    enabled = enabled,
    readOnly = readOnly,
    placeholderText = placeholderText,
    placeholderStrategy = placeholderStrategy,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    validationState = validationState,
    validationLabelVisibilityStrategy = validationLabelVisibilityStrategy,
    interactionSource = interactionSource,
    backgroundColor = backgroundColor,
    contentPadding = currentContentPadding,
    contentSpacedBy = contentSpacedBy,
    typography = typography,
    placeholderTypography = placeholderTypography,
    errorTypography = errorTypography,
    successTypography = successTypography,
    leadingIcon = leadingIconData?.icon,
    leadingIconSize = leadingIconData?.iconSize,
    leadingIconTint = currentLeadingIconTint,
    leadingIconRole = leadingIconData?.role,
    leadingIconContentDescription = leadingIconData?.contentDescription,
    leadingIconOnClick = leadingIconData?.onClick,
    trailingIcon = trailingIconData?.icon,
    trailingIconSize = trailingIconData?.iconSize,
    trailingIconTint = currentTrailingIconTint,
    trailingIconRole = trailingIconData?.role,
    trailingIconContentDescription = trailingIconData?.contentDescription,
    trailingIconOnClick = trailingIconData?.onClick,
    indicatorThickness = indicatorData?.thickness,
    indicatorColor = currentIndicatorColor,
    indicatorDirection = indicatorData?.direction,
    counterBaseColor = counterData?.baseColor,
    counterHighlightColor = counterData?.highlightColor,
    counterTypography = counterData?.typography,
    counterBaseAndHighlightGap = counterData?.baseAndHighlightGap,
    counterMaxLength = counterData?.maxLength,
  )
}

private const val DefaultCoreTextFieldLayoutId = "QuackBaseDefaultTextFieldCoreTextFieldLayoutId"
private const val DefaultLeadingIconLayoutId = "QuackBaseDefaultTextFieldLeadingIconLayoutId"
private const val DefaultTrailingContentLayoutId = "QuackBaseDefaultTextFieldTrailingContentLayoutId"

@MustBeTested(passed = false)
@NoSugar
@ExperimentalQuackQuackApi
@Composable
public fun QuackBaseDefaultTextField(
  value: TextFieldValue,
  onValueChange: (value: TextFieldValue) -> Unit,
  modifier: Modifier,
  enabled: Boolean,
  readOnly: Boolean,
  placeholderText: String?,
  placeholderStrategy: TextFieldPlaceholderStrategy,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions,
  singleLine: Boolean,
  minLines: Int,
  maxLines: Int,
  visualTransformation: VisualTransformation,
  onTextLayout: (layoutResult: TextLayoutResult) -> Unit,
  validationState: TextFieldValidationState,
  validationLabelVisibilityStrategy: TextFieldValidationLabelVisibilityStrategy,
  interactionSource: MutableInteractionSource,
  backgroundColor: QuackColor,
  contentPadding: PaddingValues?,
  contentSpacedBy: Dp,
  typography: QuackTypography,
  placeholderTypography: QuackTypography,
  errorTypography: QuackTypography,
  successTypography: QuackTypography,
  // decorators
  leadingIcon: QuackIcon?,
  leadingIconSize: Dp?,
  leadingIconTint: QuackColor?,
  leadingIconRole: IconRole?,
  leadingIconContentDescription: String?,
  leadingIconOnClick: ((text: String) -> Unit)?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconTint: QuackColor?,
  trailingIconRole: IconRole?,
  trailingIconContentDescription: String?,
  trailingIconOnClick: ((text: String) -> Unit)?,
  indicatorThickness: Dp?,
  indicatorColor: QuackColor?,
  indicatorDirection: VerticalDirection?,
  counterBaseColor: QuackColor?,
  counterHighlightColor: QuackColor?,
  counterTypography: QuackTypography?,
  counterBaseAndHighlightGap: Dp?,
  counterMaxLength: Int?,
) {
  val currentCursorColor = LocalQuackTextFieldTheme.current.cursorColor
  val currentCursorBrush = remember(currentCursorColor, calculation = currentCursorColor::toBrush)
  val currentTextStyle = remember(typography, calculation = typography::asComposeStyle)

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = currentTextStyle,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    visualTransformation = visualTransformation,
    onTextLayout = onTextLayout,
    interactionSource = interactionSource,
    cursorBrush = currentCursorBrush,
  ) { coreTextField ->
    Layout(
      modifier = modifier
        .testTag("BaseDefaultTextField")
        .quackSurface(backgroundColor = backgroundColor)
        .applyIf(indicatorThickness != null) {
          drawIndicator(
            thickness = indicatorThickness!!,
            color = indicatorColor!!,
            direction = indicatorDirection!!,
            validationState = validationState,
            errorTypography = errorTypography,
            successTypography = successTypography,
            textMeasurer = rememberTextMeasurer(/*cacheSize = 6*/), // TODO(pref): param size?
          )
        },
      content = {
        Box(
          modifier = Modifier
            .layoutId(DefaultCoreTextFieldLayoutId)
            .applyIf(placeholderText != null) {
              drawPlaceholder(
                text = value.text,
                placeholderText = placeholderText!!,
                placeholderStrategy = placeholderStrategy,
                typography = placeholderTypography,
                textMeasurer = rememberTextMeasurer(/*cacheSize = 5*/), // TODO(pref): param size?
                topLeftOffset = Offset.Zero,
              )
            },
          propagateMinConstraints = true,
        ) {
          coreTextField()
        }
      },
    ) { measurables, constraints ->
      val coreTextFieldMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultCoreTextFieldLayoutId
      }!!
      val leadingIconMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultLeadingIconLayoutId
      }
      val trailingIconMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultTrailingContentLayoutId
      }

      val horizontalPadding = contentPadding?.let { padding ->
        padding.calculateTopPadding()
          .plus(padding.calculateBottomPadding())
          .roundToPx()
      } ?: 0
      val verticalPadding = contentPadding?.let { padding ->
        padding.calculateLeftPadding(layoutDirection)
          .plus(padding.calculateRightPadding(layoutDirection))
          .roundToPx()
      } ?: 0

      val minWidth = constraints.minWidth
      val minHeight = constraints.minHeight

      val contentSpacedByPx = contentSpacedBy.roundToPx()

      val width =
        buildInt {
          plus(minWidth)
          plus(horizontalPadding)
          if (leadingIcon != null) {
            minus(leadingIconSize!!.roundToPx())
            minus(contentSpacedByPx)
          }
          if (trailingIcon != null) {
            minus(trailingIconSize!!.roundToPx())
            minus(contentSpacedByPx)
          }
        }
      val height = minHeight + verticalPadding

      layout(
        width = width,
        height = height,
      ) {

      }
    }
  }
}

@NoSugar
@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun QuackFilledTextField() {
  // LaunchedEffect(interactionSource) {
  //   interactionSource.interactions.collect { interaction ->
  //   }
  // }
}

@SuppressLint("ComposableNaming")
@NoSugar
@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun QuackOutlinedTextField(): Nothing {
  throw NotImplementedError(
    "The design that this component is used for is not in development scope, " +
        "so it this component is not yet developed.",
  )
}
