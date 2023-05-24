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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import androidx.compose.ui.util.fastForEach
import team.duckie.quackquack.aide.annotation.DecorateModifier
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
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
import team.duckie.quackquack.ui.util.asLoose
import team.duckie.quackquack.ui.util.buildFloat
import team.duckie.quackquack.ui.util.buildInt
import team.duckie.quackquack.ui.util.currentFontScale
import team.duckie.quackquack.ui.util.minus
import team.duckie.quackquack.ui.util.plus
import team.duckie.quackquack.ui.util.reflectivelyFillMaxSizeOperationHashCode
import team.duckie.quackquack.ui.util.wrappedDebugInspectable
import team.duckie.quackquack.util.MustBeTested
import team.duckie.quackquack.util.applyIf
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
public sealed class TextFieldValidationLabelVisibilityStrategy {
  public class Invisible(
    internal val baselineLabel: String,
    internal val baselineTypography: QuackTypography? = null,
  ) : TextFieldValidationLabelVisibilityStrategy()

  public object Gone : TextFieldValidationLabelVisibilityStrategy()
}

@Stable
private data class TextFieldIconData<ColorSet : TextFieldColorMarker>(
  val icon: QuackIcon,
  val iconSize: Dp,
  val tint: QuackColor?,
  val tintGetter: ((text: String, validationState: TextFieldValidationState, colorSet: ColorSet) -> QuackColor)?,
  val role: IconRole,
  val direction: HorizontalDirection,
  val contentScale: ContentScale,
  val contentDescription: String?,
  val onClick: (() -> Unit)?,
) : QuackDataModifierModel {
  init {
    if (role == IconRole.Button) {
      requireNotNull(onClick, lazyMessage = TextFieldErrors::ButtonIconRuleButNoOnClick)
    }
  }
}

@Stable
private data class TextFieldIndicatorData<ColorSet : TextFieldColorMarker>(
  val thickness: Dp,
  val color: QuackColor?,
  val colorGetter: ((text: String, validationState: TextFieldValidationState, colorSet: ColorSet) -> QuackColor)?,
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
    public val backgroundColor: QuackColor,
    public val contentColor: QuackColor,
    public val placeholderColor: QuackColor,
    public val errorColor: QuackColor,
    public val successColor: QuackColor,
  ) : TextFieldColorMarker

  public val validationLabelAndIndicatorSpacedBy: Dp
  public val validationLabelTypography: QuackTypography

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
    public val backgroundColor: QuackColor?,
    public val backgroundColorGetter: ((focusInteraction: FocusInteraction, text: String) -> QuackColor)?,
    public val contentColor: QuackColor,
    public val placeholderColor: QuackColor,
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
  override var validationLabelAndIndicatorSpacedBy: Dp = 4.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override val validationLabelTypography: QuackTypography = QuackTypography.Body1

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
  override var validationLabelAndIndicatorSpacedBy: Dp = 4.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override val validationLabelTypography: QuackTypography = QuackTypography.Body1

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
private val DefaultButtonIconSize = 24.dp

@DecorateModifier
@Stable
public fun Modifier.defaultTextFieldIcon(
  icon: QuackIcon,
  role: IconRole = IconRole.Icon,
  iconSize: Dp = if (role == IconRole.Icon) DefaultIconSize else DefaultButtonIconSize,
  tint: QuackColor? = QuackColor.Gray2,
  tintGetter: ((
    text: String,
    validationState: TextFieldValidationState,
    colorSet: QuackDefaultTextFieldStyle.TextFieldColors,
  ) -> QuackColor)? = null,
  direction: HorizontalDirection = if (role == IconRole.Icon) HorizontalDirection.Left else HorizontalDirection.Right,
  contentScale: ContentScale = ContentScale.Fit,
  contentDescription: String? = null,
  onClick: (() -> Unit)? = null,
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
      properties["contentScale"] = contentScale
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
      contentScale = contentScale,
      contentDescription = contentDescription,
      onClick = onClick,
    )
  }

@DecorateModifier
@Stable
public fun Modifier.filledTextFieldIcon(
  icon: QuackIcon,
  iconSize: Dp = DefaultIconSize,
  tint: QuackColor? = QuackColor.Gray2,
  tintGetter: ((
    text: String,
    validationState: TextFieldValidationState,
    colorSet: QuackFilledTextFieldStyle.TextFieldColors,
  ) -> QuackColor)? = null,
  role: IconRole = if (iconSize == DefaultIconSize) IconRole.Icon else IconRole.Button,
  direction: HorizontalDirection = if (iconSize == DefaultIconSize) HorizontalDirection.Left else HorizontalDirection.Right,
  contentScale: ContentScale = ContentScale.Fit,
  contentDescription: String? = null,
  onClick: (() -> Unit)? = null,
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
      properties["contentScale"] = contentScale
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
      contentScale = contentScale,
      contentDescription = contentDescription,
      onClick = onClick,
    )
  }

@Stable
public val DefaultIndicatorColorGetterForDefaultTextField: (
  text: String,
  validationState: TextFieldValidationState,
  colorSet: QuackDefaultTextFieldStyle.TextFieldColors,
) -> QuackColor =
  { _, validationState, colorSet ->
    val defaultColor = colorSet.contentColor
    val successColor = colorSet.successColor
    val errorColor = colorSet.errorColor

    when (validationState) {
      is TextFieldValidationState.Default -> defaultColor
      is TextFieldValidationState.Success -> successColor
      is TextFieldValidationState.Error -> errorColor
    }
  }

@DecorateModifier
@Stable
public fun Modifier.defaultTextFieldIndicator(
  direction: VerticalDirection = VerticalDirection.Bottom,
  thickness: Dp = 1.dp,
  color: QuackColor? = null,
  colorGetter: ((
    text: String,
    validationState: TextFieldValidationState,
    colorSet: QuackDefaultTextFieldStyle.TextFieldColors,
  ) -> QuackColor)? = DefaultIndicatorColorGetterForDefaultTextField,
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
  validationLabelVisibilityStrategy: TextFieldValidationLabelVisibilityStrategy = TextFieldValidationLabelVisibilityStrategy.Gone,
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
  validationLabelVisibilityStrategy: TextFieldValidationLabelVisibilityStrategy = TextFieldValidationLabelVisibilityStrategy.Gone,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  require(style is QuackDefaultTextFieldStyle)

  var isSizeSpecified = false
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier) { currentModifier ->
    if (!isSizeSpecified && currentModifier is LayoutModifier) {
      isSizeSpecified = currentModifier.hashCode() != reflectivelyFillMaxSizeOperationHashCode
    }
  }
  val (leadingIconData, trailingIconData) = remember(quackDataModels) {
    val icons =
      quackDataModels.fastFilterIsInstanceOrNull<TextFieldIconData<QuackDefaultTextFieldStyle.TextFieldColors>>()
    var leadingIconData: TextFieldIconData<QuackDefaultTextFieldStyle.TextFieldColors>? = null
    var trailingIconData: TextFieldIconData<QuackDefaultTextFieldStyle.TextFieldColors>? = null

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
    quackDataModels.fastFirstIsInstanceOrNull<TextFieldIndicatorData<QuackDefaultTextFieldStyle.TextFieldColors>>()
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
  val validationLabelAndIndicatorSpacedBy = style.validationLabelAndIndicatorSpacedBy

  val typography = remember(style.typography, contentColor) {
    style.typography.change(color = contentColor)
  }
  val placeholderTypography = remember(style.typography, placeholderColor) {
    style.typography.change(color = placeholderColor)
  }
  val validationLabelTypography = style.validationLabelTypography
  val errorTypography = remember(validationLabelTypography, errorColor) {
    validationLabelTypography.change(color = errorColor)
  }
  val successTypography = remember(validationLabelTypography, successColor) {
    validationLabelTypography.change(color = successColor)
  }

  val currentLeadingIconTint = leadingIconData?.run {
    checkNotNull(tintGetter?.invoke(value.text, validationState, style.colors) ?: tint)
  }
  val currentTrailingIconTint = trailingIconData?.run {
    checkNotNull(tintGetter?.invoke(value.text, validationState, style.colors) ?: tint)
  }

  val currentIndicatorColor = indicatorData?.run {
    checkNotNull(colorGetter?.invoke(value.text, validationState, style.colors) ?: color)
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
    validationLabelAndIndicatorSpacedBy = validationLabelAndIndicatorSpacedBy,
    typography = typography,
    placeholderTypography = placeholderTypography,
    errorTypography = errorTypography,
    successTypography = successTypography,
    leadingIcon = leadingIconData?.icon,
    leadingIconSize = leadingIconData?.iconSize,
    leadingIconTint = currentLeadingIconTint,
    leadingIconRole = leadingIconData?.role,
    leadingIconContentScale = leadingIconData?.contentScale,
    leadingIconContentDescription = leadingIconData?.contentDescription,
    leadingIconOnClick = leadingIconData?.onClick,
    trailingIcon = trailingIconData?.icon,
    trailingIconSize = trailingIconData?.iconSize,
    trailingIconTint = currentTrailingIconTint,
    trailingIconRole = trailingIconData?.role,
    trailingIconContentScale = trailingIconData?.contentScale,
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
private const val DefaultCoreTextFieldContainerLayoutId = "QuackBaseDefaultTextFieldCoreTextFieldContainerLayoutId"
private const val DefaultLeadingIconLayoutId = "QuackBaseDefaultTextFieldLeadingIconLayoutId"
private const val DefaultLeadingIconContainerLayoutId = "QuackBaseDefaultTextFieldLeadingIconContainerLayoutId"
private const val DefaultTrailingIconLayoutId = "QuackBaseDefaultTextFieldTrailingIconLayoutId"
private const val DefaultTrailingIconContainerLayoutId = "QuackBaseDefaultTextFieldTrailingIconContainerLayoutId"

@Stable
private class LazyValue<T>(var value: T? = null)

@Composable
private fun rememberLtrTextMeasurer(cacheSize: Int = /*DefaultCacheSize*/ 8): TextMeasurer {
  val fontFamilyResolver = LocalFontFamilyResolver.current
  val density = LocalDensity.current

  return remember(fontFamilyResolver, density, cacheSize) {
    TextMeasurer(
      fallbackFontFamilyResolver = fontFamilyResolver,
      fallbackDensity = density,
      fallbackLayoutDirection = LayoutDirection.Ltr,
      cacheSize = cacheSize,
    )
  }
}

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
  validationLabelAndIndicatorSpacedBy: Dp,
  typography: QuackTypography,
  placeholderTypography: QuackTypography,
  errorTypography: QuackTypography,
  successTypography: QuackTypography,
  // decorators
  leadingIcon: QuackIcon?,
  leadingIconSize: Dp?,
  leadingIconTint: QuackColor?,
  leadingIconRole: IconRole?,
  leadingIconContentScale: ContentScale?,
  leadingIconContentDescription: String?,
  leadingIconOnClick: (() -> Unit)?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconTint: QuackColor?,
  trailingIconRole: IconRole?,
  trailingIconContentScale: ContentScale?,
  trailingIconContentDescription: String?,
  trailingIconOnClick: (() -> Unit)?,
  indicatorThickness: Dp?,
  indicatorColor: QuackColor?,
  indicatorDirection: VerticalDirection?,
  counterBaseColor: QuackColor?,
  counterHighlightColor: QuackColor?,
  counterTypography: QuackTypography?,
  counterBaseAndHighlightGap: Dp?,
  counterMaxLength: Int?,
) {
  assertDefaultTextFieldValidState(
    validationState = validationState,
    leadingIcon = leadingIcon,
    leadingIconSize = leadingIconSize,
    leadingIconTint = leadingIconTint,
    leadingIconRole = leadingIconRole,
    leadingIconContentScale = leadingIconContentScale,
    trailingIcon = trailingIcon,
    trailingIconSize = trailingIconSize,
    trailingIconTint = trailingIconTint,
    trailingIconRole = trailingIconRole,
    trailingIconContentScale = trailingIconContentScale,
    indicatorThickness = indicatorThickness,
    indicatorColor = indicatorColor,
    indicatorDirection = indicatorDirection,
    counterBaseColor = counterBaseColor,
    counterHighlightColor = counterHighlightColor,
    counterTypography = counterTypography,
    counterBaseAndHighlightGap = counterBaseAndHighlightGap,
    counterMaxLength = counterMaxLength,
  )

  val fontScaleAwareLeadingIconSize: Dp?
  val fontScaleAwareTrailingIconSize: Dp?

  currentFontScale { fontScale ->
    fontScaleAwareLeadingIconSize = leadingIconSize?.times(fontScale)
    fontScaleAwareTrailingIconSize = trailingIconSize?.times(fontScale)
  }

  val currentCursorColor = LocalQuackTextFieldTheme.current.cursorColor
  val currentDensity = LocalDensity.current
  val currentCursorBrush = remember(currentCursorColor, calculation = currentCursorColor::toBrush)
  val currentTextStyle = remember(typography, calculation = typography::asComposeStyle)
  val currentRecomposeScope = currentRecomposeScope

  val topPaddingPx = with(currentDensity) { contentPadding?.calculateTopPadding()?.roundToPx() ?: 0 }
  val bottomPaddingPx = with(currentDensity) { contentPadding?.calculateBottomPadding()?.roundToPx() ?: 0 }

  val lazyCoreTextFieldContainerWidth = remember { LazyValue<Int>() }
  val lazyCoreTextFieldWidth = remember { LazyValue<Int>() }

  val indicatorLabelMeasurer = rememberLtrTextMeasurer(/*cacheSize = 6*/) // TODO(pref): param size?
  val indicatorLabelMeasureResult =
    remember(
      indicatorLabelMeasurer,
      validationState,
      successTypography,
      errorTypography,
      lazyCoreTextFieldContainerWidth.value,
    ) {
      if (validationState is TextFieldValidationState.WithLabel && validationState.label != null) {
        val indicatorTypography =
          if (validationState is TextFieldValidationState.Success) successTypography
          else errorTypography

        indicatorLabelMeasurer.measure(
          // [SMARTCAST_IMPOSSIBLE] Smart cast to 'String' is impossible, because a property that has open or custom getter
          text = validationState.label!!,
          style = indicatorTypography.asComposeStyle(),
          constraints = Constraints().let { constraints ->
            if (lazyCoreTextFieldContainerWidth.value != null) constraints.copy(maxWidth = lazyCoreTextFieldContainerWidth.value!!)
            else constraints
          },
        )
      } else {
        null
      }
    }
  val indicatorLabelBaselineHeightOrNull =
    remember(
      indicatorLabelMeasurer,
      validationLabelVisibilityStrategy,
      successTypography,
      lazyCoreTextFieldContainerWidth.value,
    ) {
      if (validationLabelVisibilityStrategy is TextFieldValidationLabelVisibilityStrategy.Invisible) {
        indicatorLabelMeasurer
          .measure(
            text = validationLabelVisibilityStrategy.baselineLabel,
            style = (validationLabelVisibilityStrategy.baselineTypography ?: successTypography).asComposeStyle(),
            constraints = Constraints().let { constraints ->
              if (lazyCoreTextFieldContainerWidth.value != null) constraints.copy(maxWidth = lazyCoreTextFieldContainerWidth.value!!)
              else constraints
            },
          )
          .size
          .height
      } else {
        null
      }
    }

  val placeholderTextMeasurer = rememberLtrTextMeasurer(/*cacheSize = 5*/) // TODO(pref): param size?
  val placeholderTextMeasureResult =
    remember(
      placeholderTextMeasurer,
      placeholderText,
      placeholderTypography,
      placeholderStrategy,
      lazyCoreTextFieldWidth.value,
    ) {
      if (placeholderText != null) {
        placeholderTextMeasurer.measure(
          text = placeholderText,
          style = placeholderTypography.asComposeStyle(),
          overflow = TextOverflow.Ellipsis,
          maxLines = 1, // force single-line for placeholder
          constraints = Constraints().let { constraints ->
            if (lazyCoreTextFieldWidth.value != null) constraints.copy(maxWidth = lazyCoreTextFieldWidth.value!!)
            else constraints
          },
        )
      } else {
        null
      }
    }

  val counterTextMeasurer = rememberLtrTextMeasurer(/*cacheSize = 7*/) // TODO(pref): param size?
  val counterTextMeasureResult =
    remember(
      currentDensity,
      counterTextMeasurer,
      value.text,
      counterMaxLength,
      counterHighlightColor,
      counterBaseColor,
      counterTypography,
      counterBaseAndHighlightGap,
    ) {
      if (counterMaxLength != null) {
        val currentLenght = value.text.length

        counterTextMeasurer.measure(
          text = buildAnnotatedString {
            withStyle(SpanStyle(color = counterHighlightColor!!.value)) {
              append(currentLenght.toString())
            }
            withStyle(
              SpanStyle(
                fontSize = with(currentDensity) { counterBaseAndHighlightGap!!.toSp() },
                color = Color.Transparent,
              ),
            ) {
              append("_") // TODO(impl): correctly?
            }
            withStyle(SpanStyle(color = counterBaseColor!!.value)) {
              append("/$counterMaxLength")
            }
          },
          // placeholders = listOf(
          //   AnnotatedString.Range(
          //     item = Placeholder(
          //       width = with(currentDensity) { counterBaseAndHighlightGap!!.toSp() },
          //       height = 1.sp,
          //       placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
          //     ),
          //     start = currentLenght,
          //     end = currentLenght + 1,
          //   )
          // ),
          style = counterTypography!!.change(textAlign = TextAlign.End).asComposeStyle(),
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
        .applyIf(indicatorLabelMeasureResult != null) {
          drawBehind {
            drawText(
              textLayoutResult = indicatorLabelMeasureResult!!,
              topLeft = Offset(
                x = 0f,
                y = size.height - indicatorLabelMeasureResult.size.height,
              ),
            )
          }
        },
      content = {
        Box(
          Modifier
            .layoutId(DefaultCoreTextFieldContainerLayoutId)
            .quackSurface(backgroundColor = backgroundColor)
            .applyIf(indicatorThickness != null) {
              drawWithCache {
                val indicatorThicknessPx = indicatorThickness!!.toPx()
                val borderColor = indicatorColor!!.value
                val yOffset = size.height - indicatorThicknessPx

                // TODO: The y offset must be 1 to be visible without being clipped. But why?
                val startOffset = when (indicatorDirection!!) {
                  VerticalDirection.Top -> Offset(x = 0f, y = 1f)
                  VerticalDirection.Bottom -> Offset(x = 0f, y = yOffset)
                }
                val endOffset = when (indicatorDirection) {
                  VerticalDirection.Top -> Offset(x = size.width, y = 1f)
                  VerticalDirection.Bottom -> Offset(x = size.width, y = yOffset)
                }

                onDrawBehind {
                  drawLine(
                    color = borderColor,
                    start = startOffset,
                    end = endOffset,
                    strokeWidth = indicatorThicknessPx,
                  )
                }
              }
            }
            .applyIf(counterTextMeasureResult != null) {
              drawBehind {
                drawText(
                  textLayoutResult = counterTextMeasureResult!!,
                  topLeft = Offset(
                    x = buildFloat {
                      plus(size.width)
                      if (fontScaleAwareLeadingIconSize != null) {
                        minus((fontScaleAwareLeadingIconSize + contentSpacedBy).toPx())
                      }
                      minus(counterTextMeasureResult.size.width)
                    },
                    y = buildFloat {
                      plus(topPaddingPx)
                      val coreTextFieldHeight = size.height - topPaddingPx - bottomPaddingPx
                      plus(coreTextFieldHeight / 2)
                      minus(counterTextMeasureResult.size.height / 2)
                    },
                  ),
                )
              }
            },
        )
        Box(
          modifier = Modifier
            .layoutId(DefaultCoreTextFieldLayoutId)
            .applyIf(placeholderTextMeasureResult != null) {
              drawBehind {
                @Suppress("NAME_SHADOWING")
                val placeholderTextMeasureResult = placeholderTextMeasureResult!!
                if (placeholderStrategy == TextFieldPlaceholderStrategy.Always) drawText(placeholderTextMeasureResult)
                else if (value.text.isEmpty()) drawText(placeholderTextMeasureResult)
              }
            },
          propagateMinConstraints = true,
        ) {
          coreTextField()
        }
        if (leadingIcon != null) {
          Box(
            Modifier
              .layoutId(DefaultLeadingIconLayoutId)
              .size(fontScaleAwareLeadingIconSize!!)
              .paint(
                painter = leadingIcon.asPainter(),
                contentScale = leadingIconContentScale!!,
                colorFilter = remember(leadingIconTint!!) { leadingIconTint.toColorFilterOrNull() },
              )
              .applyIf(leadingIconContentDescription != null) {
                semantics {
                  contentDescription = leadingIconContentDescription!!
                }
              },
          )
        }
        if (trailingIcon != null) {
          Box(
            Modifier
              .layoutId(DefaultTrailingIconLayoutId)
              .size(fontScaleAwareTrailingIconSize!!)
              .paint(
                painter = trailingIcon.asPainter(),
                contentScale = trailingIconContentScale!!,
                colorFilter = remember(trailingIconTint!!) { trailingIconTint.toColorFilterOrNull() },
              )
              .applyIf(trailingIconContentDescription != null) {
                semantics {
                  contentDescription = trailingIconContentDescription!!
                }
              },
          )
        }
        if (leadingIconRole == IconRole.Button) {
          Box(
            Modifier
              .layoutId(DefaultLeadingIconContainerLayoutId)
              .quackClickable(
                role = Role.Button,
                rippleEnabled = false,
                onClick = leadingIconOnClick!!,
              ),
          )
        }
        if (trailingIconRole == IconRole.Button) {
          Box(
            Modifier
              .layoutId(DefaultTrailingIconContainerLayoutId)
              .quackClickable(
                role = Role.Button,
                rippleEnabled = false,
                onClick = trailingIconOnClick!!,
              ),
          )
        }
      },
    ) { measurables, constraints ->
      val coreTextFieldMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultCoreTextFieldLayoutId
      }!!
      val coreTextFieldContainerMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultCoreTextFieldContainerLayoutId
      }!!
      val leadingIconMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultLeadingIconLayoutId
      }
      val leadingIconContainerMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultLeadingIconContainerLayoutId
      }
      val trailingIconMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultTrailingIconLayoutId
      }
      val trailingIconContainerMeasurable = measurables.fastFirstOrNull { measurable ->
        measurable.layoutId == DefaultTrailingIconContainerLayoutId
      }

      val leftPaddingPx = contentPadding?.calculateLeftPadding(layoutDirection)?.roundToPx() ?: 0
      val rightPaddingPx = contentPadding?.calculateRightPadding(layoutDirection)?.roundToPx() ?: 0
      val horizontalPaddingPx = leftPaddingPx + rightPaddingPx
      val verticalPaddingPx = topPaddingPx + bottomPaddingPx

      val contentSpacedByPx = contentSpacedBy.roundToPx()
      val halfContentSpacedByPx = contentSpacedByPx / 2

      val labelAndIndicatorSpacedByPx = validationLabelAndIndicatorSpacedBy.roundToPx()

      val fontScaleAwareLeadingIconSizePx = fontScaleAwareLeadingIconSize?.roundToPx()
      val fontScaleAwareTrailingIconSizePx = fontScaleAwareTrailingIconSize?.roundToPx()

      val minWidth = constraints.minWidth

      val coreTextFieldWidth =
        buildInt {
          plus(minWidth)
          minus(horizontalPaddingPx)
          if (leadingIcon != null) {
            minus(fontScaleAwareLeadingIconSizePx!!)
            minus(contentSpacedByPx)
          }
          if (trailingIcon != null) {
            minus(fontScaleAwareTrailingIconSizePx!!)
            minus(contentSpacedByPx)
          }
          if (counterTextMeasureResult != null) {
            minus(counterTextMeasureResult.size.width)
            minus(contentSpacedByPx)
          }
        }

      val coreTextFieldConstraints =
        constraints.copy(
          minWidth = coreTextFieldWidth,
          maxWidth = coreTextFieldWidth,
          minHeight = 0,
        )
      val coreTextFieldPlaceable = coreTextFieldMeasurable.measure(coreTextFieldConstraints)

      val width = constraints.constrainWidth(minWidth + horizontalPaddingPx)
      var height = constraints.constrainHeight(coreTextFieldPlaceable.height + verticalPaddingPx)

      val extraLooseConstraints = constraints.asLoose(width = true, height = true)
      var leadingIconContainerConstraints: Constraints? = null
      var trailingIconContainerConstraints: Constraints? = null

      if (leadingIconRole == IconRole.Button) {
        leadingIconContainerConstraints =
          Constraints.fixed(
            width = (fontScaleAwareLeadingIconSizePx!! + halfContentSpacedByPx).coerceAtMost(width),
            height = height,
          )
      }
      if (trailingIconRole == IconRole.Button) {
        trailingIconContainerConstraints =
          Constraints.fixed(
            width = (halfContentSpacedByPx + fontScaleAwareTrailingIconSizePx!!).coerceAtMost(width),
            height = height,
          )
      }

      val leadingIconPlaceable = leadingIconMeasurable?.measure(extraLooseConstraints)
      val leadingIconContainerPlaceable = leadingIconContainerMeasurable?.measure(leadingIconContainerConstraints!!)

      val trailingIconPlaceable = trailingIconMeasurable?.measure(extraLooseConstraints)
      val trailingIconContainerPlaceable = trailingIconContainerMeasurable?.measure(trailingIconContainerConstraints!!)

      val coreTextFieldContainerConstraints = Constraints.fixed(width = width, height = height)
      val coreTextFieldContainerPlaceable = coreTextFieldContainerMeasurable.measure(coreTextFieldContainerConstraints)

      height = if (indicatorLabelMeasureResult != null) {
        constraints.constrainHeight(height + labelAndIndicatorSpacedByPx + indicatorLabelMeasureResult.size.height)
      } else if (indicatorLabelBaselineHeightOrNull != null) {
        constraints.constrainHeight(height + labelAndIndicatorSpacedByPx + indicatorLabelBaselineHeightOrNull)
      } else {
        height
      }

      if (lazyCoreTextFieldContainerWidth.value == null || lazyCoreTextFieldWidth.value == null) {
        lazyCoreTextFieldContainerWidth.value = width
        lazyCoreTextFieldWidth.value = coreTextFieldWidth
        currentRecomposeScope.invalidate()
      }

      layout(width = width, height = height) {
        coreTextFieldContainerPlaceable.place(x = 0, y = 0, zIndex = 0f)
        coreTextFieldPlaceable.place(
          x = leftPaddingPx + (fontScaleAwareLeadingIconSizePx?.plus(contentSpacedByPx) ?: 0),
          y = topPaddingPx,
          zIndex = 1f,
        )

        leadingIconPlaceable?.place(
          x = 0,
          y = topPaddingPx + (coreTextFieldPlaceable.height / 2) - (leadingIconPlaceable.height / 2),
          zIndex = 0f,
        )
        leadingIconContainerPlaceable?.place(
          x = 0,
          y = coreTextFieldContainerPlaceable.height - leadingIconContainerPlaceable.height,
          zIndex = 1f,
        )

        trailingIconPlaceable?.place(
          x = width - trailingIconPlaceable.width,
          y = topPaddingPx + (coreTextFieldPlaceable.height / 2) - (trailingIconPlaceable.height / 2),
          zIndex = 0f,
        )
        trailingIconContainerPlaceable?.place(
          x = width - trailingIconContainerPlaceable.width,
          y = coreTextFieldContainerPlaceable.height - trailingIconContainerPlaceable.height,
          zIndex = 1f,
        )
      }
    }
  }
}

private fun assertDefaultTextFieldValidState(
  validationState: TextFieldValidationState,
  leadingIcon: QuackIcon?,
  leadingIconSize: Dp?,
  leadingIconTint: QuackColor?,
  leadingIconRole: IconRole?,
  leadingIconContentScale: ContentScale?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconTint: QuackColor?,
  trailingIconRole: IconRole?,
  trailingIconContentScale: ContentScale?,
  indicatorThickness: Dp?,
  indicatorColor: QuackColor?,
  indicatorDirection: VerticalDirection?,
  counterBaseColor: QuackColor?,
  counterHighlightColor: QuackColor?,
  counterTypography: QuackTypography?,
  counterBaseAndHighlightGap: Dp?,
  counterMaxLength: Int?,
) {
  if (
    validationState is TextFieldValidationState.WithLabel &&
    validationState.label != null
  ) {
    require(
      indicatorDirection == VerticalDirection.Bottom,
      lazyMessage = TextFieldErrors::ValidationLabelProvidedButNoDownDirectionIndicator,
    )
  }

  if (leadingIcon != null) {
    requireNotNull(leadingIconSize)
    requireNotNull(leadingIconTint)
    requireNotNull(leadingIconRole)
    requireNotNull(leadingIconContentScale)
  }

  if (trailingIcon != null) {
    requireNotNull(trailingIconSize)
    requireNotNull(trailingIconTint)
    requireNotNull(trailingIconRole)
    requireNotNull(trailingIconContentScale)
  }

  if (indicatorThickness != null) {
    requireNotNull(indicatorColor)
    requireNotNull(indicatorDirection)
  }

  if (counterMaxLength != null) {
    requireNotNull(counterBaseColor)
    requireNotNull(counterHighlightColor)
    requireNotNull(counterTypography)
    requireNotNull(counterBaseAndHighlightGap)
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
