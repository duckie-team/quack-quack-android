/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import android.util.Log
import androidx.annotation.IntRange
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackIcon
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.ui.optin.ExperimentalDesignToken
import team.duckie.quackquack.ui.token.HorizontalDirection
import team.duckie.quackquack.ui.token.IconRole
import team.duckie.quackquack.ui.token.VerticalDirection
import team.duckie.quackquack.ui.util.QuackDsl

@Immutable
public enum class TextFieldState {
  Error, Success, Default,
}

@Immutable
public enum class PlaceholderStrategy {
  Always, Hidable,
}

@Stable
private data class TextFieldIconData(
  val icon: QuackIcon,
  val iconSize: Dp,
  val tint: QuackColor?,
  val tintGetter: ((text: String) -> QuackColor)?,
  val role: IconRole,
  val direction: HorizontalDirection,
  val onClick: ((text: String) -> Unit)?,
) : QuackDataModifierModel

@Stable
private data class TextFieldIndicatorData(
  val thickness: Dp,
  val color: QuackColor?,
  val colorGetter: ((text: String) -> QuackColor)?,
  val direction: VerticalDirection,
) : QuackDataModifierModel

@Stable
private data class TextFieldCountableData(
  val baseColor: QuackColor,
  val highlightColor: QuackColor,
  val typography: QuackTypography,
  @IntRange(from = 1) val maxCount: Int,
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
    internal val backgroundColorGetter: (() -> QuackColor)?,
    internal val contentColor: QuackColor,
    internal val placeholderColor: QuackColor,
  ) : TextFieldColorMarker

  public val radius: Dp

  @Stable
  public fun textFieldColors(
    backgroundColor: QuackColor?,
    backgroundColorGetter: (() -> QuackColor)?,
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

// TODO(1): 이 컴포넌트가 사용되는 디자인은 아직 개발 범위가 아니므로 TODO 처리
public interface QuackOutlinedTextFieldStyle : TextFieldStyleMarker

@ExperimentalDesignToken
@Immutable
public interface QuackTextFieldStyle<
  StyleMarker : TextFieldStyleMarker, ColorMarker : TextFieldColorMarker,
  > {
  /** 사용할 색상들 */
  public val colors: ColorMarker

  /** 컨텐츠 주변에 들어갈 패딩 */
  public val contentPadding: PaddingValues

  /** 배치되는 아이콘과 텍스트 사이의 간격 */
  public val contentSpacedBy: Dp

  /** 활성화 상태에서 표시될 텍스트의 타이포그래피 */
  public val typography: QuackTypography

  /** 디자인 스펙을 변경하는 람다 */
  @Stable
  public operator fun invoke(styleBuilder: StyleMarker.() -> Unit): StyleMarker

  // TODO(3): 싱글톤으로 관리해도 되지 않을까?
  public companion object {
    @Stable
    public val Default: QuackTextFieldStyle<
      QuackDefaultTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors,
      >
      get() = QuackDefaultTextFieldDefaults()

    @Stable
    public val DefaultLarge: QuackTextFieldStyle<
      QuackDefaultLargeTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors,
      >
      get() = QuackDefaultLargeTextFieldDefaults()

    @Stable
    public val FilledLarge: QuackTextFieldStyle<
      QuackFilledLargeTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors,
      >
      get() = QuackFilledLargeTextFieldDefaults()

    @Stable
    public val FilledFlat: QuackTextFieldStyle<
      QuackFilledFlatTextFieldDefaults, QuackFilledTextFieldStyle.TextFieldColors,
      >
      get() = QuackFilledFlatTextFieldDefaults()
  }
}

@ExperimentalDesignToken
public class QuackDefaultTextFieldDefaults internal constructor() :
  QuackTextFieldStyle<
    QuackDefaultTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors,
    >, QuackDefaultTextFieldStyle {

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

  override fun invoke(styleBuilder: QuackDefaultTextFieldDefaults.() -> Unit): QuackDefaultTextFieldDefaults =
    apply(styleBuilder)
}

@ExperimentalDesignToken
public class QuackDefaultLargeTextFieldDefaults :
  QuackTextFieldStyle<
    QuackDefaultLargeTextFieldDefaults, QuackDefaultTextFieldStyle.TextFieldColors,
    >, QuackDefaultTextFieldStyle {

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

  override fun invoke(styleBuilder: QuackDefaultLargeTextFieldDefaults.() -> Unit): QuackDefaultLargeTextFieldDefaults =
    apply(styleBuilder)
}

@ExperimentalDesignToken
public class QuackFilledLargeTextFieldDefaults :
  QuackTextFieldStyle<
    QuackFilledLargeTextFieldDefaults,
    QuackFilledTextFieldStyle.TextFieldColors,
    >,
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
}

@ExperimentalDesignToken
public class QuackFilledFlatTextFieldDefaults :
  QuackTextFieldStyle<
    QuackFilledFlatTextFieldDefaults,
    QuackFilledTextFieldStyle.TextFieldColors,
    >,
  QuackFilledTextFieldStyle {

  private val ColorNoSettingWarnMessage =
    "The `QuackFilledFlatTextFieldDefaults` is used, but the `colors#backgroundColorGetter` is not set. " +
      "According to the design guidelines, the background color of a `QuackFilledFlatTextField` can change " +
      "depending on whether it is focused or not."

  override var radius: Dp = 8.dp

  override var colors: QuackFilledTextFieldStyle.TextFieldColors =
    textFieldColors(
      backgroundColor = null,
      backgroundColorGetter = {
        Log.w(this::class.simpleName!!, ColorNoSettingWarnMessage)
        QuackColor.White
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
}
