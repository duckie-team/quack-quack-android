/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalTextApi::class)

package team.duckie.quackquack.ui

import android.view.View
import androidx.annotation.IntRange
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.input.ImeAction
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
import androidx.compose.ui.unit.sp
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

/** 텍스트 필드 검증 결과를 나타냅니다. */
@Immutable
public sealed class TextFieldValidationState {
  /** 검증 결과를 문구로 나타냅니다. */
  public sealed interface WithLabel {
    /**
     * 검증 결과를 설명하는 문구.
     * 값이 null이 아닐 때만 시각적으로 표시합니다.
     */
    public val label: String?
  }

  /**
   * 검증에 성공한 상태를 정의합니다.
   *
   * @param label 성공을 설명하는 문구
   */
  public class Success(override val label: String? = null) : TextFieldValidationState(), WithLabel

  /**
   * 검증에 실패한 상태를 정의합니다.
   *
   * @param label 실패를 설명하는 문구
   */
  public class Error(override val label: String? = null) : TextFieldValidationState(), WithLabel

  /** 검증이 진행되지 않은 기본 상태를 정의합니다. */
  public object Default : TextFieldValidationState()
}

/** 텍스트 필드에 보여지는 placeholder의 시각적 정책을 정의합니다. */
@Immutable
public enum class TextFieldPlaceholderStrategy {
  /**
   * 텍스트 입력 여부와 관계 없이 항상 placeholder를 표시합니다.
   * 텍스트가 입력됐다면 placeholder 위에 겹쳐서 보여집니다.
   */
  Always,

  /** 텍스트가 입력되지 않았을 때만 placeholder를 표시합니다. */
  Hidable,
}

/**
 * [텍스트 필드 검증 결과 문구][TextFieldValidationState.WithLabel]의 시각적 정책을 정의합니다.
 */
@Immutable
public sealed class TextFieldValidationLabelVisibilityStrategy {
  /**
   * [검증 결과 문구][TextFieldValidationState.WithLabel.label]가 표시되지 않을 때도 레이아웃에 문구만큼의 공간을 차지합니다.
   * 이는 [View.INVISIBLE]과 동일합니다.
   *
   * @param baselineLabel 검증 결과 문구가 표시되지 않을 때 차지할 공간을 계산할 기준점이 되는 문구
   * @param baselineTypography 검증 결과 문구가 표시되지 않을 때 차지할 공간을 계산할 기준점이 되는 타이포그래피.
   * 만약 null이 제공되면 검증 결과 문구를 그릴 때 사용하는 타이포그래피를 대신 사용합니다.
   */
  public class Invisible(
    internal val baselineLabel: String,
    internal val baselineTypography: QuackTypography? = null,
  ) : TextFieldValidationLabelVisibilityStrategy()

  /**
   * [검증 결과 문구][TextFieldValidationState.WithLabel.label]가 표시될 때만 레이아웃에 문구만큼의 공간을 차지합니다.
   * 이는 [View.GONE]과 동일합니다.
   */
  public object Gone : TextFieldValidationLabelVisibilityStrategy()
}

/**
 * 텍스트 필드에 표시할 아이콘 정보를 정의합니다.
 *
 * @see Modifier.defaultTextFieldIcon
 * @see Modifier.filledTextFieldIcon
 */
@Stable
private data class TextFieldIconData<ColorSet : TextFieldColorMarker>(
  val icon: QuackIcon,
  val iconSize: Dp,
  val tint: QuackColor?,
  val tintGetter: ((text: String, validationState: TextFieldValidationState, colorSet: ColorSet) -> QuackColor)?,
  val direction: HorizontalDirection,
  val contentScale: ContentScale,
  val contentDescription: String?,
  val onClick: (() -> Unit)?,
) : QuackDataModifierModel

/**
 * 텍스트 필드에 그릴 인디케이터 정보를 정의합니다.
 *
 * @see Modifier.defaultTextFieldIndicator
 */
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

/**
 * 텍스트 필드에 표시할 counter 정보를 정의합니다.
 *
 * @see Modifier.counter
 */
@Stable
private data class TextFieldCounterData(
  val baseColor: QuackColor,
  val highlightColor: QuackColor,
  val typography: QuackTypography,
  val baseAndHighlightGap: Dp,
  val maxLength: Int,
) : QuackDataModifierModel

/** 텍스트 필드의 스타일을 나타냅니다. */
@QuackDsl
@Immutable
public interface TextFieldStyleMarker

/** 텍스트 필드의 색상을 나타냅니다. */
@Immutable
public interface TextFieldColorMarker

/** Default 텍스트 필드의 스타일을 나타냅니다. */
public interface QuackDefaultTextFieldStyle : TextFieldStyleMarker {
  /**
   * Default 텍스트 필드에서 사용할 색상을 정의합니다.
   *
   * @param backgroundColor 배경 색상
   * @param contentColor 메인 문구 색상
   * @param placeholderColor placeholder 문구 색상
   * @param errorColor 에러 상태의 문구 색상
   * @param successColor 성공 상태의 문구 색상
   */
  public data class TextFieldColors internal constructor(
    public val backgroundColor: QuackColor,
    public val contentColor: QuackColor,
    public val placeholderColor: QuackColor,
    public val errorColor: QuackColor,
    public val successColor: QuackColor,
  ) : TextFieldColorMarker

  /** [텍스트 필드 검증 결과 문구][TextFieldValidationState.WithLabel]와 텍스트 필드 인디케이터 사이의 간격  */
  public val validationLabelAndIndicatorSpacedBy: Dp

  /** [텍스트 필드 검증 결과 문구][TextFieldValidationState.WithLabel]의 타이포그래피 */
  public val validationLabelTypography: QuackTypography

  /** [TextFieldColors] 인스턴스를 만듭니다. */
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

/** Filled 텍스트 필드의 스타일을 나타냅니다. */
public interface QuackFilledTextFieldStyle : TextFieldStyleMarker {
  /**
   * Filled 텍스트 필드에서 사용할 색상을 정의합니다.
   *
   * @param backgroundColor 정적 배경 색상
   * @param backgroundColorGetter 동적 배경 색상. 값을 계산하기 위해 [현재 입력된 문구][String],
   * [현재 텍스트 필드의 포커스 인터렉션 상태][FocusInteraction]가 인자로 제공되는 람다가 제공됩니다.
   * @param contentColor 메인 문구 색상
   * @param placeholderColor placeholder 문구 색상
   */
  public data class TextFieldColors internal constructor(
    public val backgroundColor: QuackColor?,
    public val backgroundColorGetter: ((text: String, focusInteraction: FocusInteraction) -> QuackColor)?,
    public val contentColor: QuackColor,
    public val placeholderColor: QuackColor,
  ) : TextFieldColorMarker

  /** 테두리 둥글기 정도 */
  public val radius: Dp

  /** [TextFieldColors]의 인스턴스를 만듭니다. */
  @Stable
  public fun textFieldColors(
    backgroundColor: QuackColor?,
    backgroundColorGetter: ((text: String, focusInteraction: FocusInteraction) -> QuackColor)?,
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

/** Outlined 텍스트 필드의 스타일을 나타냅니다. */
// TODO: 이 컴포넌트가 사용되는 디자인은 아직 개발 범위가 아니므로 TODO 처리
public interface QuackOutlinedTextFieldStyle : TextFieldStyleMarker

/** 모든 TextField 스타일에 공통되는 디자인 스펙을 나타냅니다. */
@ExperimentalDesignToken
@Immutable
public interface QuackTextFieldStyle<Style : TextFieldStyleMarker, Color : TextFieldColorMarker> {
  /** 사용할 색상들 */
  public val colors: Color

  /** 컨텐츠 주변에 들어갈 패딩 */
  public val contentPadding: PaddingValues

  /** 배치되는 컨텐츠(양끝 아이콘, 카운터, 메인 문구.. 등등)들 사이의 간격 */
  public val contentSpacedBy: Dp

  /** 입력된 문구를 표시할 때 사용할 타이포그래피 */
  public val typography: QuackTypography

  /** 디자인 스펙을 변경하는 람다 */
  @Stable
  public operator fun invoke(styleBuilder: Style.() -> Unit): Style

  /** 주어진 디자인 스펙을 [InspectableModifier]로 감싸서 반환합니다. */
  @Stable
  public fun Modifier.wrappedDebugInspectable(): Modifier

  public companion object {
    /** 스타일 가이드에 정의된 Default 텍스트 필드의 기본 스타일을 가져옵니다. */
    public val Default: QuackTextFieldStyle<QuackDefaultTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>
      get() = QuackDefaultTextFieldDefaults()

    /** 스타일 가이드에 정의된 Default 텍스트 필드의 Large 스타일을 가져옵니다. */
    public val DefaultLarge: QuackTextFieldStyle<QuackDefaultLargeTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors>
      get() = QuackDefaultLargeTextFieldDefaults()

    // public val FilledLarge: QuackTextFieldStyle<QuackFilledLargeTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>
    //   get() = QuackFilledLargeTextFieldDefaults()

    // public val FilledFlat: QuackTextFieldStyle<QuackFilledFlatTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>
    //   get() = QuackFilledFlatTextFieldDefaults()
  }
}

/** 스타일 가이드에 맞게 Default 텍스트 필드의 기본 스타일을 정의합니다. */
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

/** 스타일 가이드에 맞게 Default 텍스트 필드의 Large 스타일을 정의합니다. */
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

/** 스타일 가이드에 맞게 Filled 텍스트 필드의 Large 스타일을 정의합니다. */
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

/** 스타일 가이드에 맞게 Filled 텍스트 필드의 Flat 스타일을 정의합니다. */
@ExperimentalDesignToken
public class QuackFilledFlatTextFieldDefaults :
  QuackTextFieldStyle<QuackFilledFlatTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors>,
  QuackFilledTextFieldStyle {

  override var radius: Dp = 8.dp

  override var colors: QuackFilledTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = null,
      backgroundColorGetter = { text, focusInteraction ->
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

/** 텍스트 필드 내부에서 발생할 수 있는 오류를 정의합니다. */
@VisibleForTesting
internal object TextFieldErrors {
  fun sameDirectionIcon(direction: String) = "The icon was provided more than once in the same direction. " +
    "Only one icon can be displayed per direction. (Direction offered twice: $direction)"

  fun unhandledFocusInteraction(interaction: FocusInteraction) = "An unhandled focus interaction was provided. " +
    "($interaction)"

  const val IndicatorRequestedButNoColor = "Show indicator was requested, but no indicator color was provided. " +
    "Please provide a non-null value for one of the color or colorGetter fields."

  const val ValidationLabelProvidedButNoBottomDirectionIndicator =
    "A label was provided as a TextFieldValidationState, " +
      "but the indicator must have a direction of VerticalDirection.Bottom in order to display the label. " +
      "The current direction is VerticalDirection.Top."
}

/** 기본 아이콘 사이즈 */
private val DefaultIconSize = 16.dp

/**
 * 버튼으로 인식될 아이콘의 기본 사이즈.
 * 꽥꽥 텍스트 필드의 기본 구현은 더 확장된 터치 영역을 보장합니다.
 */
@Suppress("unused")
private val DefaultIconButtonSize = 24.dp

/**
 * Default 텍스트 필드에 아이콘을 표시합니다.
 *
 * [tint]와 [tintGetter]가 모두 제공됐다면 [tintGetter]를 사용합니다.
 * 만약 두 개의 값이 모두 제공되지 않았다면 틴트를 적용하지 않습니다.
 *
 * 각각 [direction]간 최대 한 가지의 아이콘만 표시할 수 있습니다.
 *
 * @param icon 표시할 아이콘
 * @param iconSize 표시할 사이즈
 * @param tint [icon]에 적용할 정적 틴트
 * @param tintGetter [icon]에 적용할 동적 틴트. 값을 계산하기 위해 [현재 입력된 문구][String],
 * [현재 텍스트 필드의 검증 상태][TextFieldValidationState], [현재 텍스트 필드의 스타일로 주어진 색상][QuackDefaultTextFieldStyle.TextFieldColors]이 인자로 제공되는
 * 람다가 제공됩니다.
 * @param direction [icon]을 배치할 방향
 * @param contentScale [icon]에 적용할 [ContentScale]
 * @param contentDescription [icon]을 설명하는 문구. 접근성 서비스에 사용됩니다.
 * @param onClick [icon]이 클릭됐을 때 호출할 람다. 값이 제공되면 터치 영역이
 * 넓은 범위로 확장됩니다. 자세한 내용은 텍스트 필드 컴포저블 문서를 참고하세요.
 */
@DecorateModifier
@Stable
public fun Modifier.defaultTextFieldIcon(
  icon: QuackIcon,
  iconSize: Dp = DefaultIconSize,
  tint: QuackColor? = QuackColor.Gray2,
  tintGetter: ((
    text: String,
    validationState: TextFieldValidationState,
    colorSet: QuackDefaultTextFieldStyle.TextFieldColors,
  ) -> QuackColor)? = null,
  direction: HorizontalDirection = HorizontalDirection.Right,
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
      direction = direction,
      contentScale = contentScale,
      contentDescription = contentDescription,
      onClick = onClick,
    )
  }

/**
 * Filled 텍스트 필드에 아이콘을 표시합니다.
 *
 * [tint]와 [tintGetter]가 모두 제공됐다면 [tintGetter]를 사용합니다.
 * 만약 두 개의 값이 모두 제공되지 않았다면 틴트를 적용하지 않습니다.
 *
 * 각각 [direction]간 최대 한 가지의 아이콘만 표시할 수 있습니다.
 *
 * @param icon 표시할 아이콘
 * @param iconSize 표시할 사이즈
 * @param tint [icon]에 적용할 정적 틴트
 * @param tintGetter [icon]에 적용할 동적 틴트. 값을 계산하기 위해 [현재 입력된 문구][String],
 * [현재 텍스트 필드의 검증 상태][TextFieldValidationState], [현재 텍스트 필드의 스타일로 주어진 색상][QuackFilledTextFieldStyle.TextFieldColors]이 인자로 제공되는
 * 람다가 제공됩니다.
 * @param direction [icon]을 배치할 방향
 * @param contentScale [icon]에 적용할 [ContentScale]
 * @param contentDescription [icon]을 설명하는 문구. 접근성 서비스에 사용됩니다.
 * @param onClick [icon]이 클릭됐을 때 호출할 람다. 값이 제공되면 터치 영역이
 * 넓은 범위로 확장됩니다. 자세한 내용은 텍스트 필드 컴포저블 문서를 참고하세요.
 */
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
  direction: HorizontalDirection = HorizontalDirection.Right,
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
      direction = direction,
      contentScale = contentScale,
      contentDescription = contentDescription,
      onClick = onClick,
    )
  }

/**
 * 인디케이터의 동적 색상을 Default 텍스트 필드의 스타일 가이드에 맞게
 * 제공할 수 있도록 사전 정의된 람다.
 *
 * [Modifier.defaultTextFieldIndicator]의 `colorGetter` 인자로 사용할 수 있습니다.
 */
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

/**
 * Default 텍스트 필드에 인티케이터를 그립니다.
 *
 * [color]와 [colorGetter]가 모두 제공됐다면 [colorGetter]를 사용합니다.
 * 둘 중 하나는 무조건 제공해야 합니다.
 *
 * @param direction 인디케이터를 그릴 방향
 * @param thickness 인디케이터의 굵기
 * @param color 인디케이터의 정적 색상
 * @param colorGetter 인디케이터의 동적 색상. 값을 계산하기 위해 [현재 입력된 문구][String],
 * [현재 텍스트 필드의 검증 상태][TextFieldValidationState], [현재 텍스트 필드의 스타일로 주어진 색상][QuackDefaultTextFieldStyle.TextFieldColors]이 인자로 제공되는
 * 람다가 제공됩니다.
 */
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

/**
 * 텍스트 필드에 counter를 표시합니다.
 *
 * counter는 다음과 같은 구조를 갖습니다.
 *
 * ```
 * // 현재 10자가 입력됐고, 최대 20자까지 허용이라면
 *
 * 10/20
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
 */
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
/**
 * Default 텍스트 필드를 그립니다.
 *
 * - 이 컴포넌트는 자체의 패딩 정책을 구현합니다.
 * - 이 컴포넌트는 자체의 터치 영역 정책을 구현합니다.
 *
 * ### 패딩 정책
 *
 * 1. [텍스트 필드 스타일][QuackTextFieldStyle]에서 [QuackTextFieldStyle.contentPadding] 옵션을
 * 별도로 제공하고 있습니다. 이는 [Modifier.padding]과 다른 패딩 정책을 사용합니다. [Modifier.padding]은
 * 텍스트 필드의 루트 레이아웃을 기준으로 패딩이 적용되지만, [contentPadding][QuackTextFieldStyle.contentPadding]은 텍스트 필드의
 * 메인 텍스트를 기준으로 적용됩니다.
 * 2. [LayoutModifier]를 사용하여 컴포넌트의 사이즈가 명시됐다면 [QuackTextFieldStyle.contentPadding]
 * 옵션은 무시됩니다. [contentPadding][QuackTextFieldStyle.contentPadding]은 컴포넌트 사이즈 하드코딩을
 * 대체하는 용도로 제공됩니다. 하지만 컴포넌트 사이즈가 하드코딩됐다면 [contentPadding][QuackTextFieldStyle.contentPadding]을
 * 제공하는 의미가 없어집니다. 따라서 컴포넌트의 사이즈가 하드코딩됐다면 개발자의 의도를 존중한다는 원칙하에
 * 컴포넌트의 사이즈가 중복으로 확장되는 일을 예방하고자 [contentPadding][QuackTextFieldStyle.contentPadding] 옵션을 무시합니다.
 * 예를 들어 `Modifier.height(10.dp)`로 컴포넌트 높이를 명시했고, [contentPadding][QuackTextFieldStyle.contentPadding]으로
 * `QuackPadding(vertical=10.dp)`을 제공했다고 해봅시다. 이런 경우에는 [contentPadding][QuackTextFieldStyle.contentPadding]이
 * 무시되고 태그의 높이가 10dp로 적용됩니다. 컴포넌트 사이즈를 명시하면서 패딩을 적용하고 싶다면
 * [contentPadding][QuackTextFieldStyle.contentPadding] 대신에 [Modifier.padding]을 사용하세요.
 * 단, [Modifier.fillMaxWidth]는 디자인을 지키기 위해 사용되는 경우가 많으므로 이 경우는 예외적으로 [contentPadding][QuackTextFieldStyle.contentPadding]이
 * 보존됩니다. [LayoutModifier]를 사용하는 흔한 [Modifier]로는 [Modifier.size], [Modifier.height], [Modifier.width] 등이
 * 있습니다. [LayoutModifierNode]를 사용하는 [Modifier]는 [contentPadding][QuackTextFieldStyle.contentPadding] 무시
 * 옵션이 아직 지원되지 않습니다. ([#636](https://github.com/duckie-team/quack-quack-android/issues/636))
 *
 * ### 터치 영역
 *
 * 이 컴포넌트에는 [defaultTextFieldIcon][Modifier.defaultTextFieldIcon] 데코레이터로 아이콘을 추가할 수 있습니다.
 * 아이콘에 [onClick][TextFieldIconData.onClick] 이벤트가 제공됐다면 [해당 아이콘의 위치][TextFieldIconData.direction]에 맞는
 * 영역 전체을 터치 영역으로 지정합니다. 예를 들어 [오른쪽][HorizontalDirection.Right]에 클릭 가능한 아이콘이 제공됐다고 해봅시다.
 * 그렇다면 텍스트 필드의 높이 전체와, 아이콘의 너비와 [QuackTextFieldStyle.contentSpacedBy]의 반을 더한 너비를 터치 영역으로 지정합니다.
 *
 * ### 사용 가능한 데코레이터
 *
 * - [Modifier.defaultTextFieldIcon]: 아이콘을 표시합니다.
 * - [Modifier.defaultTextFieldIndicator]: 인티케이터를 그립니다.
 * - [Modifier.counter]: counter를 표시합니다.
 *
 * @param value 텍스트 필드에 입력된 글자
 * @param onValueChange 입력 서비스가 글자를 업데이트할 때 호출되는 콜백입니다. 업데이트된 글자는 콜백의 매개변수로 제공됩니다.
 * @param style 텍스트 필드에 적용할 디자인 스펙. 사전 정의된 디자인 스펙은 [QuackTextFieldStyle.Companion]을 참고하세요.
 * @param enabled 텍스트 필드의 활성화 상태를 제어합니다. false면 텍스트 필드를 편집하거나 포커스를 맞출 수 없으며
 * 텍스트 필드에 글자를 입력할 수 없습니다.
 * @param readOnly 텍스트 필드의 편집 가능한 상태를 제어합니다. true면 텍스트 필드를 수정할 수 없지만 사용자가 포커스를 맞추고
 * 텍스트를 복사할 수 있습니다. 읽기 전용 텍스트 필드는 일반적으로 사용자가 편집할 수 없는 미리 채워진 양식을 표시하는 데 사용됩니다.
 * @param placeholderText 입력된 글자가 없을 때 표시할 대체 글자
 * @param placeholderStrategy [placeholderText]의 시각적 정책. 자세한 정보는 [TextFieldPlaceholderStrategy] 문서를 참고하세요.
 * @param keyboardOptions 키보드 유형 및 [ImeAction]과 같은 구성이 포함된 소프트웨어 키보드 옵션입니다.
 * @param keyboardActions 입력 서비스가 IME 액션을 방출하면 해당 콜백이 호출됩니다. 이 IME 액션은 [KeyboardOptions.imeAction]에
 * 지정한 것과 다를 수 있습니다.
 * @param singleLine true로 설정하면 이 텍스트 필드가 여러 줄로 줄 바꿈되지 않고 가로로 스크롤되는 단일 텍스트 필드가 됩니다.
 * 키보드에 [ImeAction]으로 return 키가 표시되지 않도록 알려줍니다. [maxLines] 및 [minLines]는 모두 자동으로 1로 설정되므로 무시됩니다.
 * @param minLines 텍스트 필드에 표시하기 위한 최소 줄 수입니다. `1 <= minLines <= maxLines`가 요구됩니다. [singleLine]이 true면
 * 이 옵션은 무시됩니다.
 * @param maxLines 텍스트 필드에 표시될 수 있는 최대 줄 수입니다. `1 <= minLines <= maxLines`가 요구됩니다. [singleLine]이 true면
 * 이 옵션은 무시됩니다.
 * @param visualTransformation [value]의 시각적 표현을 변경할 수 있는 필터입니다. 기본적으로 적용되지 않습니다.
 * @param onTextLayout 새 텍스트 레이아웃이 계산될 때 실행되는 콜백입니다. 콜백이 제공하는 [TextLayoutResult] 객체에는 단락 정보,
 * 텍스트의 크기, 기준선 및 기타 세부 정보가 포함됩니다. 콜백은 텍스트에 장식이나 기능을 추가하는 데 사용할 수 있습니다.
 * 예를 들어 텍스트 주위에 커서나 선택 영역을 그리는 데 사용할 수 있습니다.
 * @param validationState 텍스트 필드의 검증 결과. 자세한 정보는 [TextFieldValidationState] 문서를 참고하세요.
 * 만약 [검증 결과 문구][TextFieldValidationState.WithLabel]를 제공하고 [defaultTextFieldIndicator] 데코레이터가 사용됐을 경우,
 * 인디케이터의 방향이 [Bottom][VerticalDirection.Bottom]이여야 합니다.
 * @Param validationLabelVisibilityStrategy [validationState]를 설명할 문구가 제공됐다면 해당 문구의 시각적 정책.
 * 자세한 내용은 [TextFieldValidationLabelVisibilityStrategy] 문서를 참고하세요.
 * @param interactionSource 이 텍스트 필드의 인터랙션 스트림을 나타내는 변경 가능한 인터랙션 소스입니다. 인터랙션을 관찰하고
 * 다른 인터랙션에서 이 텍스트 필드의 모양/동작을 커스터마이징하려면 자신만의 변경 가능한 인터랙션 소스를 생성하여 전달할 수 있습니다.
 */
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

/**
 * Default 텍스트 필드를 그립니다.
 *
 * - 이 컴포넌트는 자체의 패딩 정책을 구현합니다.
 * - 이 컴포넌트는 자체의 터치 영역 정책을 구현합니다.
 *
 * ### 패딩 정책
 *
 * 1. [텍스트 필드 스타일][QuackTextFieldStyle]에서 [QuackTextFieldStyle.contentPadding] 옵션을
 * 별도로 제공하고 있습니다. 이는 [Modifier.padding]과 다른 패딩 정책을 사용합니다. [Modifier.padding]은
 * 텍스트 필드의 루트 레이아웃을 기준으로 패딩이 적용되지만, [contentPadding][QuackTextFieldStyle.contentPadding]은 텍스트 필드의
 * 메인 텍스트를 기준으로 적용됩니다.
 * 2. [LayoutModifier]를 사용하여 컴포넌트의 사이즈가 명시됐다면 [QuackTextFieldStyle.contentPadding]
 * 옵션은 무시됩니다. [contentPadding][QuackTextFieldStyle.contentPadding]은 컴포넌트 사이즈 하드코딩을
 * 대체하는 용도로 제공됩니다. 하지만 컴포넌트 사이즈가 하드코딩됐다면 [contentPadding][QuackTextFieldStyle.contentPadding]을
 * 제공하는 의미가 없어집니다. 따라서 컴포넌트의 사이즈가 하드코딩됐다면 개발자의 의도를 존중한다는 원칙하에
 * 컴포넌트의 사이즈가 중복으로 확장되는 일을 예방하고자 [contentPadding][QuackTextFieldStyle.contentPadding] 옵션을 무시합니다.
 * 예를 들어 `Modifier.height(10.dp)`로 컴포넌트 높이를 명시했고, [contentPadding][QuackTextFieldStyle.contentPadding]으로
 * `QuackPadding(vertical=10.dp)`을 제공했다고 해봅시다. 이런 경우에는 [contentPadding][QuackTextFieldStyle.contentPadding]이
 * 무시되고 태그의 높이가 10dp로 적용됩니다. 컴포넌트 사이즈를 명시하면서 패딩을 적용하고 싶다면
 * [contentPadding][QuackTextFieldStyle.contentPadding] 대신에 [Modifier.padding]을 사용하세요.
 * 단, [Modifier.fillMaxWidth]는 디자인을 지키기 위해 사용되는 경우가 많으므로 이 경우는 예외적으로 [contentPadding][QuackTextFieldStyle.contentPadding]이
 * 보존됩니다. [LayoutModifier]를 사용하는 흔한 [Modifier]로는 [Modifier.size], [Modifier.height], [Modifier.width] 등이
 * 있습니다. [LayoutModifierNode]를 사용하는 [Modifier]는 [contentPadding][QuackTextFieldStyle.contentPadding] 무시
 * 옵션이 아직 지원되지 않습니다. ([#636](https://github.com/duckie-team/quack-quack-android/issues/636))
 *
 * ### 터치 영역
 *
 * 이 컴포넌트에는 [defaultTextFieldIcon][Modifier.defaultTextFieldIcon] 데코레이터로 아이콘을 추가할 수 있습니다.
 * 아이콘에 [onClick][TextFieldIconData.onClick] 이벤트가 제공됐다면 [해당 아이콘의 위치][TextFieldIconData.direction]에 맞는
 * 영역 전체을 터치 영역으로 지정합니다. 예를 들어 [오른쪽][HorizontalDirection.Right]에 클릭 가능한 아이콘이 제공됐다고 해봅시다.
 * 그렇다면 텍스트 필드의 높이 전체와, 아이콘의 너비와 [QuackTextFieldStyle.contentSpacedBy]의 반을 더한 너비를 터치 영역으로 지정합니다.
 *
 * ### 사용 가능한 데코레이터
 *
 * - [Modifier.defaultTextFieldIcon]: 아이콘을 표시합니다.
 * - [Modifier.defaultTextFieldIndicator]: 인티케이터를 그립니다.
 * - [Modifier.counter]: counter를 표시합니다.
 *
 * @param value 텍스트 필드에 입력된 글자
 * @param onValueChange 입력 서비스가 글자를 업데이트할 때 호출되는 콜백입니다. 업데이트된 글자는 콜백의 매개변수로 제공됩니다.
 * @param style 텍스트 필드에 적용할 디자인 스펙. 사전 정의된 디자인 스펙은 [QuackTextFieldStyle.Companion]을 참고하세요.
 * @param enabled 텍스트 필드의 활성화 상태를 제어합니다. false면 텍스트 필드를 편집하거나 포커스를 맞출 수 없으며
 * 텍스트 필드에 글자를 입력할 수 없습니다.
 * @param readOnly 텍스트 필드의 편집 가능한 상태를 제어합니다. true면 텍스트 필드를 수정할 수 없지만 사용자가 포커스를 맞추고
 * 텍스트를 복사할 수 있습니다. 읽기 전용 텍스트 필드는 일반적으로 사용자가 편집할 수 없는 미리 채워진 양식을 표시하는 데 사용됩니다.
 * @param placeholderText 입력된 글자가 없을 때 표시할 대체 글자
 * @param placeholderStrategy [placeholderText]의 시각적 정책. 자세한 정보는 [TextFieldPlaceholderStrategy] 문서를 참고하세요.
 * @param keyboardOptions 키보드 유형 및 [ImeAction]과 같은 구성이 포함된 소프트웨어 키보드 옵션입니다.
 * @param keyboardActions 입력 서비스가 IME 액션을 방출하면 해당 콜백이 호출됩니다. 이 IME 액션은 [KeyboardOptions.imeAction]에
 * 지정한 것과 다를 수 있습니다.
 * @param singleLine true로 설정하면 이 텍스트 필드가 여러 줄로 줄 바꿈되지 않고 가로로 스크롤되는 단일 텍스트 필드가 됩니다.
 * 키보드에 [ImeAction]으로 return 키가 표시되지 않도록 알려줍니다. [maxLines] 및 [minLines]는 모두 자동으로 1로 설정되므로 무시됩니다.
 * @param minLines 텍스트 필드에 표시하기 위한 최소 줄 수입니다. `1 <= minLines <= maxLines`가 요구됩니다. [singleLine]이 true면
 * 이 옵션은 무시됩니다.
 * @param maxLines 텍스트 필드에 표시될 수 있는 최대 줄 수입니다. `1 <= minLines <= maxLines`가 요구됩니다. [singleLine]이 true면
 * 이 옵션은 무시됩니다.
 * @param visualTransformation [value]의 시각적 표현을 변경할 수 있는 필터입니다. 기본적으로 적용되지 않습니다.
 * @param onTextLayout 새 텍스트 레이아웃이 계산될 때 실행되는 콜백입니다. 콜백이 제공하는 [TextLayoutResult] 객체에는 단락 정보,
 * 텍스트의 크기, 기준선 및 기타 세부 정보가 포함됩니다. 콜백은 텍스트에 장식이나 기능을 추가하는 데 사용할 수 있습니다.
 * 예를 들어 텍스트 주위에 커서나 선택 영역을 그리는 데 사용할 수 있습니다.
 * @param validationState 텍스트 필드의 검증 결과. 자세한 정보는 [TextFieldValidationState] 문서를 참고하세요.
 * 만약 [검증 결과 문구][TextFieldValidationState.WithLabel]를 제공하고 [defaultTextFieldIndicator] 데코레이터가 사용됐을 경우,
 * 인디케이터의 방향이 [Bottom][VerticalDirection.Bottom]이여야 합니다.
 * @Param validationLabelVisibilityStrategy [validationState]를 설명할 문구가 제공됐다면 해당 문구의 시각적 정책.
 * 자세한 내용은 [TextFieldValidationLabelVisibilityStrategy] 문서를 참고하세요.
 * @param interactionSource 이 텍스트 필드의 인터랙션 스트림을 나타내는 변경 가능한 인터랙션 소스입니다. 인터랙션을 관찰하고
 * 다른 인터랙션에서 이 텍스트 필드의 모양/동작을 커스터마이징하려면 자신만의 변경 가능한 인터랙션 소스를 생성하여 전달할 수 있습니다.
 */
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
    (tintGetter?.invoke(value.text, validationState, style.colors) ?: tint) ?: QuackColor.Unspecified
  }
  val currentTrailingIconTint = trailingIconData?.run {
    (tintGetter?.invoke(value.text, validationState, style.colors) ?: tint) ?: QuackColor.Unspecified
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
    leadingIconContentScale = leadingIconData?.contentScale,
    leadingIconContentDescription = leadingIconData?.contentDescription,
    leadingIconOnClick = leadingIconData?.onClick,
    trailingIcon = trailingIconData?.icon,
    trailingIconSize = trailingIconData?.iconSize,
    trailingIconTint = currentTrailingIconTint,
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

/** 동적으로 계산되는 값의 인스턴스를 보관하는 래퍼 클래스 */
@Stable
private class LazyValue<T>(var value: T? = null)

/**
 * [fallbackLayoutDirection][TextMeasurer.fallbackLayoutDirection]을 [LayoutDirection.Ltr]으로 고정하며
 * [TextMeasurer] 인스턴스를 생성 및 반환합니다. [LayoutDirection.Rtl]을 고려하지 않아도 되는 상황에서
 * 불필요한 컴포저블 참조를 줄여줍니다.
 *
 * @param cacheSize [TextMeasurer] 생성자에 [cacheSize][TextMeasurer.cacheSize]로 전달할 값
 */
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

/** 텍스트 필드의 너비가 지정되지 않았을 떄 기본으로 사용할 너비 */
private val DefaultMinWidth = 200.dp

/**
 * Default 텍스트 필드를 그립니다.
 *
 * 이 컴포저블은 자체의 배치 정책을 갖습니다. 자세한 내용은 [QuackDefaultTextField] 문서를 참고하세요.
 * 또한 [QuackDefaultTextFieldStyle]의 필드와 이 컴포넌트에 사용 가능한 데코레이터의 정보를 모두
 * 개별적으로 인자로 받습니다.
 */
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
  leadingIconContentScale: ContentScale?,
  leadingIconContentDescription: String?,
  leadingIconOnClick: (() -> Unit)?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconTint: QuackColor?,
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
    leadingIconContentScale = leadingIconContentScale,
    trailingIcon = trailingIcon,
    trailingIconSize = trailingIconSize,
    trailingIconTint = trailingIconTint,
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

  val indicatorSuccessComposeTypography = remember(successTypography, calculation = successTypography::asComposeStyle)
  val indicatorErrorComposeTypography = remember(errorTypography, calculation = errorTypography::asComposeStyle)
  val indicatorLabelConstraints = remember(lazyCoreTextFieldContainerWidth.value) {
    Constraints().let { constraints ->
      if (lazyCoreTextFieldContainerWidth.value != null) constraints.copy(maxWidth = lazyCoreTextFieldContainerWidth.value!!)
      else constraints
    }
  }
  val indicatorLabelMeasurer = rememberLtrTextMeasurer(/*cacheSize = 6*/) // TODO(pref): param size?
  val indicatorLabelMeasureResult =
    remember(
      indicatorSuccessComposeTypography,
      indicatorErrorComposeTypography,
      indicatorLabelConstraints,
      indicatorLabelMeasurer,
      validationState,
    ) {
      if (validationState is TextFieldValidationState.WithLabel && validationState.label != null) {
        val indicatorTypography =
          if (validationState is TextFieldValidationState.Success) indicatorSuccessComposeTypography
          else indicatorErrorComposeTypography

        indicatorLabelMeasurer.measure(
          text = validationState.label!!,
          style = indicatorTypography,
          constraints = indicatorLabelConstraints,
        )
      } else {
        null
      }
    }

  val indicatorLabelBaselineTypography =
    remember(validationLabelVisibilityStrategy, indicatorSuccessComposeTypography) {
      ((validationLabelVisibilityStrategy as? TextFieldValidationLabelVisibilityStrategy.Invisible)
        ?.baselineTypography)
        ?.asComposeStyle()
        ?: indicatorSuccessComposeTypography
    }
  val indicatorLabelBaselineHeightOrNull =
    remember(
      indicatorLabelMeasurer,
      validationLabelVisibilityStrategy,
      indicatorLabelBaselineTypography,
      indicatorLabelConstraints,
    ) {
      if (validationLabelVisibilityStrategy is TextFieldValidationLabelVisibilityStrategy.Invisible) {
        indicatorLabelMeasurer
          .measure(
            text = validationLabelVisibilityStrategy.baselineLabel,
            style = indicatorLabelBaselineTypography,
            constraints = indicatorLabelConstraints,
          )
          .size
          .height
      } else {
        null
      }
    }

  val placeholderComposeTypography =
    remember(placeholderTypography, calculation = placeholderTypography::asComposeStyle)
  val placeholderConstraints = remember(lazyCoreTextFieldWidth.value) {
    Constraints().let { constraints ->
      if (lazyCoreTextFieldWidth.value != null) constraints.copy(maxWidth = lazyCoreTextFieldWidth.value!!)
      else constraints
    }
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

  /*
   * maintainer notes: BasicTextField는 CoreTextField의 레퍼 컴포저블임.
   * CoreTextField는 내부에서 여러 가지 텍스트 필드 Modifier를 SimpleLayout 컴포저블로
   * 구체화하고, 여기에 사용된 SimpleLayout 컴포저블이 decorationBox 인자로 제공됨.
   * decorationBox 컴포저블은 CoreTextFieldRootBox 컴포저블 안에서 실행되는데,
   * CoreTextFieldRootBox 컴포저블은 propagateMinConstraints = true 인 Box로
   * 이어지고, Box의 content가 ContextMenuArea 컴포저블로 실행됨. ContextMenuArea 컴포저블은
   * expect 함수로 desktop과 android 구현이 있고, android 환경에서는 단순히 content() 로 끝남.
   */
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
        if (leadingIconOnClick != null) {
          Box(
            Modifier
              .layoutId(DefaultLeadingIconContainerLayoutId)
              .quackClickable(
                role = Role.Button,
                rippleEnabled = false,
                onClick = leadingIconOnClick,
              ),
          )
        }
        if (trailingIconOnClick != null) {
          Box(
            Modifier
              .layoutId(DefaultTrailingIconContainerLayoutId)
              .quackClickable(
                role = Role.Button,
                rippleEnabled = false,
                onClick = trailingIconOnClick,
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

      val minWidth = constraints.minWidth.let { minWidth ->
        if (minWidth == 0) DefaultMinWidth.roundToPx()
        else minWidth
      }

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
      val coreTextFieldMinHeight =
        if (
          placeholderStrategy == TextFieldPlaceholderStrategy.Always &&
          placeholderTextMeasureResult != null
        ) {
          placeholderTextMeasureResult.size.height
        } else {
          0
        }

      val coreTextFieldConstraints =
        constraints.copy(
          minWidth = coreTextFieldWidth,
          maxWidth = coreTextFieldWidth,
          minHeight = coreTextFieldMinHeight,
        )
      val coreTextFieldPlaceable = coreTextFieldMeasurable.measure(coreTextFieldConstraints)

      val width = constraints.constrainWidth(minWidth + horizontalPaddingPx)
      var height = constraints.constrainHeight(coreTextFieldPlaceable.height + verticalPaddingPx)

      val extraLooseConstraints = constraints.asLoose(width = true, height = true)
      var leadingIconContainerConstraints: Constraints? = null
      var trailingIconContainerConstraints: Constraints? = null

      if (leadingIconOnClick != null) {
        leadingIconContainerConstraints =
          Constraints.fixed(
            width = (fontScaleAwareLeadingIconSizePx!! + halfContentSpacedByPx).coerceAtMost(width),
            height = height,
          )
      }
      if (trailingIconOnClick != null) {
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
        coreTextFieldContainerPlaceable.place(
          x = 0,
          y = 0,
          zIndex = 0f,
        )
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

/** [QuackBaseDefaultTextField]가 올바르게 그려질 수 있는 상태인지 검증합니다. */
private fun assertDefaultTextFieldValidState(
  validationState: TextFieldValidationState,
  leadingIcon: QuackIcon?,
  leadingIconSize: Dp?,
  leadingIconTint: QuackColor?,
  leadingIconContentScale: ContentScale?,
  trailingIcon: QuackIcon?,
  trailingIconSize: Dp?,
  trailingIconTint: QuackColor?,
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
    validationState.label != null &&
    indicatorDirection != null
  ) {
    require(
      indicatorDirection == VerticalDirection.Bottom,
      lazyMessage = TextFieldErrors::ValidationLabelProvidedButNoBottomDirectionIndicator,
    )
  }

  if (leadingIcon != null) {
    requireNotNull(leadingIconSize)
    requireNotNull(leadingIconTint)
    requireNotNull(leadingIconContentScale)
  }

  if (trailingIcon != null) {
    requireNotNull(trailingIconSize)
    requireNotNull(trailingIconTint)
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

@Suppress("ComposableNaming")
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
