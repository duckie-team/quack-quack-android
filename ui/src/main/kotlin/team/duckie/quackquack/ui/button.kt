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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.aide.annotation.DecorateModifier
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackPadding
import team.duckie.quackquack.material.QuackTypography
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
import team.duckie.quackquack.util.MustBeTested
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull

/**
 * 버튼의 디자인 스펙을 나타냅니다.
 *
 * @see QuackButtonStyle
 */
@QuackDsl
public interface ButtonStyleMarker

/** Large Button 디자인 스펙을 나타냅니다.  */
public interface QuackLargeButtonStyle : ButtonStyleMarker

/** Medium Button 디자인 스펙을 나타냅니다.  */
public interface QuackMediumButtonStyle : ButtonStyleMarker

// TODO(3): 데코레이터 사용 불가능 린트 제공
/** Small Button 디자인 스펙을 나타냅니다.  */
public interface QuackSmallButtonStyle : ButtonStyleMarker

/** 기본으로 제공되는 [ButtonStyleMarker]의 스펙들에서 공통되는 필드를 나타냅니다. */
@Immutable
public interface QuackButtonStyle<T : ButtonStyleMarker> {
  /** 사용할 색상들 */
  public val colors: QuackButtonColors

  /** 모서리의 둥글기 정도 */
  public val radius: Dp

  /** 컨텐츠 주변에 들어갈 패딩 */
  public val contentPadding: QuackPadding

  /** 배치되는 아이콘과 텍스트 사이의 간격 */
  public val iconSpacedBy: Dp

  /** 테두리의 굵기 */
  public val borderThickness: Dp

  /** 활성화 상태에서 표시될 텍스트의 타이포그래피 */
  public val typography: QuackTypography

  /** 비활성화 상태에서 표시될 텍스트의 타이포그래피 */
  public val disabledTypography: QuackTypography

  /** 디자인 스펙을 변경하는 람다 */
  @Stable
  public operator fun invoke(styleBuilder: T.() -> Unit): T

  // TODO: 이 부분까지 자동화는 무리일까?
  // TODO: 더 효율적인 토큰화 방식 고안...
  //       지금은 중복되는 코드가 많이 나온다. 얘도 Modifier로 처리할까? 근데 어떤 스타일로?
  public companion object {
    /** 버튼 디자인 가이드의 `LargeButtons # primary` 디자인 스펙을 가져옵니다. */
    @Stable
    public val PrimaryLarge: QuackButtonStyle<QuackPrimaryLargeButtonDefaults>
      get() = QuackPrimaryLargeButtonDefaults()

    /** 버튼 디자인 가이드의 `LargeButtons # secondary` 디자인 스펙을 가져옵니다. */
    @Stable
    public val SecondaryLarge: QuackButtonStyle<QuackSecondaryLargeButtonDefaults>
      get() = QuackSecondaryLargeButtonDefaults()

    /** 버튼 디자인 가이드의 `MediumButton` 디자인 스펙을 가져옵니다. */
    @Stable
    public val Medium: QuackButtonStyle<QuackMediumButtonDefaults>
      get() = QuackMediumButtonDefaults()

    /** 버튼 디자인 가이드의 `SmallButtons # primary, filled` 디자인 스펙을 가져옵니다. */
    @Stable
    public val PrimaryFilledSmall: QuackButtonStyle<QuackPrimaryFilledSmallButtonDefaults>
      get() = QuackPrimaryFilledSmallButtonDefaults()

    /** 버튼 디자인 가이드의 `SmallButtons # primary, outlined` 디자인 스펙을 가져옵니다. */
    @Stable
    public val PrimaryOutlinedSmall: QuackButtonStyle<QuackPrimaryOutlinedSmallButtonDefaults>
      get() = QuackPrimaryOutlinedSmallButtonDefaults()

    /** 버튼 디자인 가이드의 `SmallButtons # primary, outlined, round` 디자인 스펙을 가져옵니다. */
    @Stable
    public val PrimaryOutlinedRoundSmall: QuackButtonStyle<QuackPrimaryOutlinedRoundSmallButtonDefaults>
      get() = QuackPrimaryOutlinedRoundSmallButtonDefaults()

    /** 버튼 디자인 가이드의 `SmallButtons # secondary` 디자인 스펙을 가져옵니다. */
    @Stable
    public val SecondarySmall: QuackButtonStyle<QuackSecondarySmallButtonDefaults>
      get() = QuackSecondarySmallButtonDefaults()

    /** 버튼 디자인 가이드의 `SmallButtons # secondary, round` 디자인 스펙을 가져옵니다. */
    @Stable
    public val SecondaryRoundSmall: QuackButtonStyle<QuackSecondaryRoundSmallButtonDefaults>
      get() = QuackSecondaryRoundSmallButtonDefaults()
  }
}

/** [QuackButtonStyle]의 필드들을 [InspectorInfo]로 기록합니다. */
@SuppressLint("ModifierFactoryExtensionFunction")
@Stable
public fun QuackButtonStyle<*>.wrappedDebugInspectable(baseline: Modifier): Modifier =
  baseline.wrappedDebugInspectable {
    name = toString()
    properties["colors"] = colors
    properties["radius"] = radius
    properties["contentPadding"] = contentPadding
    properties["iconSpacedBy"] = iconSpacedBy
    properties["borderThickness"] = borderThickness
    properties["typography"] = typography
    properties["disabledTypography"] = disabledTypography
  }

/**
 * 버튼에서 사용할 색상들을 정의합니다.
 *
 * @param backgroundColor 활성화 상태의 배경 색상
 * @param disabledBackgroundColor 비활성화 상태의 배경 색상
 * @param contentColor 활성화 상태의 컨텐츠 색상 (아이콘 색상은 [iconColor]로 관리되며,
 * 컨텐츠라 하면 대부분 버튼의 텍스트를 의미합니다.)
 * @param disabledBorderColor 비활성화 상태의 컨텐츠 색상 (아이콘 색상은 [iconColor]로
 * 관리되며, 컨텐츠라 하면 대부분 버튼의 텍스트를 의미합니다.)
 * @param iconColor 활성화 상태에 관계 없이 항상 사용할 아이콘 색상
 * @param rippleColor 활성화 상태에 관계 없이 항상 사용할 리플 색상
 */
@Immutable
public class QuackButtonColors internal constructor(
  internal val backgroundColor: QuackColor,
  internal val disabledBackgroundColor: QuackColor,
  internal val contentColor: QuackColor,
  internal val disabledContentColor: QuackColor,
  internal val borderColor: QuackColor,
  internal val disabledBorderColor: QuackColor,
  internal val iconColor: QuackColor,
  internal val rippleColor: QuackColor,
) {
  /** 기존 색상에서 일부 값만 변경하여 새로운 인스턴스를 반환합니다. */
  @Stable
  public fun copy(
    backgroundColor: QuackColor = this.backgroundColor,
    disabledBackgroundColor: QuackColor = this.disabledBackgroundColor,
    contentColor: QuackColor = this.contentColor,
    disabledContentColor: QuackColor = this.disabledContentColor,
    borderColor: QuackColor = this.borderColor,
    disabledBorderColor: QuackColor = this.disabledBorderColor,
    iconColor: QuackColor = this.iconColor,
    rippleColor: QuackColor = this.rippleColor,
  ): QuackButtonColors =
    QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      iconColor = iconColor,
      rippleColor = rippleColor,
    )

  @Suppress("RedundantIf")
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is QuackButtonColors) return false

    if (backgroundColor != other.backgroundColor) return false
    if (disabledBackgroundColor != other.disabledBackgroundColor) return false
    if (contentColor != other.contentColor) return false
    if (disabledContentColor != other.disabledContentColor) return false
    if (borderColor != other.borderColor) return false
    if (disabledBorderColor != other.disabledBorderColor) return false
    if (iconColor != other.iconColor) return false
    if (rippleColor != other.rippleColor) return false

    return true
  }

  override fun hashCode(): Int {
    var result = backgroundColor.hashCode()
    result = 31 * result + disabledBackgroundColor.hashCode()
    result = 31 * result + contentColor.hashCode()
    result = 31 * result + disabledContentColor.hashCode()
    result = 31 * result + borderColor.hashCode()
    result = 31 * result + disabledBorderColor.hashCode()
    result = 31 * result + iconColor.hashCode()
    result = 31 * result + rippleColor.hashCode()
    return result
  }

  override fun toString(): String {
    return "QuackButtonColors(" +
      "backgroundColor=$backgroundColor, " +
      "disabledBackgroundColor=$disabledBackgroundColor, " +
      "contentColor=$contentColor, " +
      "disabledContentColor=$disabledContentColor, " +
      "borderColor=$borderColor, " +
      "disabledBorderColor=$disabledBorderColor, " +
      "iconColor=$iconColor, " +
      "rippleColor=$rippleColor" +
      ")"
  }
}

/** 버튼 디자인 가이드의 `LargeButtons # primary` 디자인 스펙을 정의합니다. */
@Stable
public class QuackPrimaryLargeButtonDefaults internal constructor() :
  QuackButtonStyle<QuackPrimaryLargeButtonDefaults>, QuackLargeButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 112.dp,
    vertical = 12.dp,
  )
  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Subtitle
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.DuckieOrange,
    disabledBackgroundColor: QuackColor = QuackColor.Gray2,
    contentColor: QuackColor = QuackColor.White,
    disabledContentColor: QuackColor = QuackColor.White,
    borderColor: QuackColor = backgroundColor,
    disabledBorderColor: QuackColor = disabledBackgroundColor,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = contentColor,
  ): QuackButtonColors =
    QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )

  override fun invoke(styleBuilder: QuackPrimaryLargeButtonDefaults.() -> Unit): QuackPrimaryLargeButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/** 버튼 디자인 가이드의 `LargeButtons # secondary` 디자인 스펙을 정의합니다. */
@Stable
public class QuackSecondaryLargeButtonDefaults internal constructor() :
  QuackButtonStyle<QuackSecondaryLargeButtonDefaults>, QuackLargeButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 112.dp,
    vertical = 12.dp,
  )
  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Subtitle
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.White,
    disabledBackgroundColor: QuackColor = backgroundColor,
    contentColor: QuackColor = QuackColor.Black,
    disabledContentColor: QuackColor = contentColor,
    borderColor: QuackColor = QuackColor.Gray3,
    disabledBorderColor: QuackColor = borderColor,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = contentColor,
  ): QuackButtonColors =
    QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )

  override fun invoke(styleBuilder: QuackSecondaryLargeButtonDefaults.() -> Unit): QuackSecondaryLargeButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/** 버튼 디자인 가이드의 `MediumButton` 디자인 스펙을 정의합니다. */
@Stable
public class QuackMediumButtonDefaults internal constructor() :
  QuackButtonStyle<QuackMediumButtonDefaults>, QuackMediumButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 58.dp,
    vertical = 10.dp,
  )
  override var iconSpacedBy: Dp = 4.dp

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override var disabledTypography: QuackTypography = QuackTypography.Title2

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.White,
    disabledBackgroundColor: QuackColor = QuackColor.Unspecified,
    contentColor: QuackColor = QuackColor.Black,
    disabledContentColor: QuackColor = QuackColor.Unspecified,
    borderColor: QuackColor = QuackColor.DuckieOrange,
    disabledBorderColor: QuackColor = QuackColor.Unspecified,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = contentColor,
  ): QuackButtonColors =
    QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )

  override fun invoke(styleBuilder: QuackMediumButtonDefaults.() -> Unit): QuackMediumButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼 디자인 가이드의 `SmallButtons # primary, filled` 디자인 스펙을 정의합니다.
 *
 * `SmallButton`은 기본적으로 너비가 좁기에 데코레이터를 허용하지 않습니다.
 */
@Stable
public class QuackPrimaryFilledSmallButtonDefaults internal constructor() :
  QuackButtonStyle<QuackPrimaryFilledSmallButtonDefaults>, QuackSmallButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 12.dp,
    vertical = 8.dp,
  )
  override var iconSpacedBy: Dp = Dp.Unspecified

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.DuckieOrange,
    disabledBackgroundColor: QuackColor = QuackColor.Gray2,
    contentColor: QuackColor = QuackColor.White,
    disabledContentColor: QuackColor = QuackColor.White,
    borderColor: QuackColor = backgroundColor,
    disabledBorderColor: QuackColor = disabledBackgroundColor,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
  ): QuackButtonColors {
    return QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )
  }

  override fun invoke(styleBuilder: QuackPrimaryFilledSmallButtonDefaults.() -> Unit): QuackPrimaryFilledSmallButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼 디자인 가이드의 `SmallButtons # primary, outlined` 디자인 스펙을 정의합니다.
 *
 * `SmallButton`은 기본적으로 너비가 좁기에 데코레이터를 허용하지 않습니다.
 */
@Stable
public class QuackPrimaryOutlinedSmallButtonDefaults internal constructor() :
  QuackButtonStyle<QuackPrimaryOutlinedSmallButtonDefaults>, QuackSmallButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 12.dp,
    vertical = 8.dp,
  )
  override var iconSpacedBy: Dp = Dp.Unspecified

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.Gray3,
    disabledBackgroundColor: QuackColor = QuackColor.White,
    contentColor: QuackColor = QuackColor.Gray1,
    disabledContentColor: QuackColor = QuackColor.DuckieOrange,
    borderColor: QuackColor = backgroundColor,
    disabledBorderColor: QuackColor = disabledContentColor,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
  ): QuackButtonColors {
    return QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )
  }

  override fun invoke(styleBuilder: QuackPrimaryOutlinedSmallButtonDefaults.() -> Unit): QuackPrimaryOutlinedSmallButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼 디자인 가이드의 `SmallButtons # primary, outlined, round` 디자인 스펙을 정의합니다.
 *
 * `SmallButton`은 기본적으로 너비가 좁기에 데코레이터를 허용하지 않습니다.
 */
@Stable
public class QuackPrimaryOutlinedRoundSmallButtonDefaults internal constructor() :
  QuackButtonStyle<QuackPrimaryOutlinedRoundSmallButtonDefaults>, QuackSmallButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 8.dp,
    vertical = 4.dp,
  )
  override var iconSpacedBy: Dp = Dp.Unspecified

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Subtitle2
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.Gray3,
    disabledBackgroundColor: QuackColor = QuackColor.White,
    contentColor: QuackColor = QuackColor.Gray1,
    disabledContentColor: QuackColor = QuackColor.DuckieOrange,
    borderColor: QuackColor = backgroundColor,
    disabledBorderColor: QuackColor = disabledContentColor,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
  ): QuackButtonColors {
    return QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )
  }

  override fun invoke(styleBuilder: QuackPrimaryOutlinedRoundSmallButtonDefaults.() -> Unit): QuackPrimaryOutlinedRoundSmallButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼 디자인 가이드의 `SmallButtons # secondary` 디자인 스펙을 정의합니다.
 *
 * `SmallButton`은 기본적으로 너비가 좁기에 데코레이터를 허용하지 않습니다.
 */
@Stable
public class QuackSecondarySmallButtonDefaults internal constructor() :
  QuackButtonStyle<QuackSecondarySmallButtonDefaults>, QuackSmallButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 8.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 12.dp,
    vertical = 7.dp,
  )
  override var iconSpacedBy: Dp = Dp.Unspecified

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Body1
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.White,
    disabledBackgroundColor: QuackColor = QuackColor.Unspecified,
    contentColor: QuackColor = QuackColor.Gray1,
    disabledContentColor: QuackColor = QuackColor.Unspecified,
    borderColor: QuackColor = QuackColor.Gray3,
    disabledBorderColor: QuackColor = QuackColor.Unspecified,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
  ): QuackButtonColors {
    return QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )
  }

  override fun invoke(styleBuilder: QuackSecondarySmallButtonDefaults.() -> Unit): QuackSecondarySmallButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼 디자인 가이드의 `SmallButtons # secondary, round` 디자인 스펙을 정의합니다.
 *
 * `SmallButton`은 기본적으로 너비가 좁기에 데코레이터를 허용하지 않습니다.
 */
@Stable
public class QuackSecondaryRoundSmallButtonDefaults internal constructor() :
  QuackButtonStyle<QuackSecondaryRoundSmallButtonDefaults>, QuackSmallButtonStyle {

  override var colors: QuackButtonColors = buttonColors()

  override var radius: Dp = 30.dp

  override var contentPadding: QuackPadding = QuackPadding(
    horizontal = 8.dp,
    vertical = 4.dp,
  )
  override var iconSpacedBy: Dp = Dp.Unspecified

  override var borderThickness: Dp = 1.dp

  override var typography: QuackTypography = QuackTypography.Subtitle2
  override var disabledTypography: QuackTypography = typography

  @Stable
  public fun buttonColors(
    backgroundColor: QuackColor = QuackColor.White,
    disabledBackgroundColor: QuackColor = QuackColor.Unspecified,
    contentColor: QuackColor = QuackColor.Gray1,
    disabledContentColor: QuackColor = QuackColor.Unspecified,
    borderColor: QuackColor = QuackColor.Gray3,
    disabledBorderColor: QuackColor = QuackColor.Unspecified,
    rippleColor: QuackColor = QuackColor.Unspecified,
    iconColor: QuackColor = QuackColor.Unspecified,
  ): QuackButtonColors {
    return QuackButtonColors(
      backgroundColor = backgroundColor,
      disabledBackgroundColor = disabledBackgroundColor,
      contentColor = contentColor,
      disabledContentColor = disabledContentColor,
      borderColor = borderColor,
      disabledBorderColor = disabledBorderColor,
      rippleColor = rippleColor,
      iconColor = iconColor,
    )
  }

  override fun invoke(styleBuilder: QuackSecondaryRoundSmallButtonDefaults.() -> Unit): QuackSecondaryRoundSmallButtonDefaults =
    apply(styleBuilder)

  override fun toString(): String = this::class.simpleName!!
}

/**
 * 버튼의 아이콘 데코레이터 데이터를 저장합니다.
 *
 * @param leadingIcon 왼쪽에 배치될 아이콘 데이터
 * @param trailingIcon 오른쪽에 배치될 아이콘 데이터
 */
@Stable
private data class ButtonIconData(
  val leadingIcon: ImageVector?,
  val trailingIcon: ImageVector?,
) : QuackDataModifierModel

/**
 * 버튼에 아이콘을 추가합니다. 모든 아이콘은 버튼 텍스트를 기준으로 양옆에 배치됩니다.
 *
 * @param leadingIcon 왼쪽에 배치될 아이콘
 * @param trailingIcon 오른쪽에 배치될 아이콘
 */
@DecorateModifier
@Stable
public fun Modifier.icons(
  leadingIcon: ImageVector? = null,
  trailingIcon: ImageVector? = null,
): Modifier = inspectable(
  inspectorInfo = debugInspectorInfo {
    name = "icons"
    properties["leadingIcon"] = leadingIcon
    properties["trailingIcon"] = trailingIcon
  },
) {
  ButtonIconData(
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
  )
}

/**
 * 버튼을 그립니다.
 *
 * - 이 컴포넌트는 자체의 패딩 정책을 구현합니다.
 * - 이 컴포넌트는 자체의 배치 정책을 구현합니다.
 * - [스타일][style]별로 사용 가능한 데코레이터가 달라집니다.
 *
 * ### 패딩 정책
 *
 * 1. [버튼의 스타일][QuackButtonStyle]에서 [contentPadding][QuackButtonStyle.contentPadding] 옵션을
 * 별도로 제공하고 있습니다. 이는 [Modifier.padding]과 다른 패딩 정책을 사용합니다. [Modifier.padding]은
 * 버튼의 루트 레이아웃을 기준으로 패딩이 적용되지만, [QuackButtonStyle.contentPadding]은 버튼의
 * 텍스트를 기준으로 패딩이 적용됩니다. 이 부분의 자세한 내용은 배치 정책 세션을 참고하세요.
 * 2. [LayoutModifier]를 사용하여 컴포넌트의 사이즈가 명시됐다면 [QuackButtonStyle.contentPadding]
 * 옵션은 무시됩니다. [contentPadding][QuackButtonStyle.contentPadding]은 컴포넌트 사이즈 하드코딩을
 * 대체하는 용도로 제공됩니다. 하지만 컴포넌트 사이즈가 하드코딩됐다면 [contentPadding][QuackButtonStyle.contentPadding]을
 * 제공하는 의미가 없어집니다. 따라서 컴포넌트의 사이즈가 하드코딩됐다면 개발자의 의도를 존중한다는 원칙하에
 * 컴포넌트의 사이즈가 중첩으로 확장되는 일을 예방하고자 [contentPadding][QuackButtonStyle.contentPadding]
 * 옵션을 무시합니다. 예를 들어 `Modifier.height(10.dp)`로 컴포넌트 높이를 명시했고, [contentPadding][QuackButtonStyle.contentPadding]으로
 * `QuackPadding(vertical=10.dp)`을 제공했다고 해봅시다. 이런 경우에는 [contentPadding][QuackButtonStyle.contentPadding]이
 * 무시되고 버튼의 높이가 10dp로 적용됩니다. 컴포넌트 사이즈를 명시하면서 패딩을 적용하고 싶다면
 * [contentPadding][QuackButtonStyle.contentPadding] 대신에 [Modifier.padding]을 사용하세요.
 * [LayoutModifier]를 사용하는 흔한 [Modifier]로는 [Modifier.size], [Modifier.height], [Modifier.width] 등이
 * 있습니다. [LayoutModifierNode]를 사용하는 [Modifier]는 [contentPadding][QuackButtonStyle.contentPadding] 무시
 * 옵션이 아직 지원되지 않습니다. ([#636](https://github.com/duckie-team/quack-quack-android/issues/636))
 *
 * ### 배치 정책
 *
 * [style.contentPadding][QuackButtonStyle.contentPadding]은 항상 버튼의 텍스트를 기준으로
 * 적용됩니다. 예를 들어 버튼의 아이콘을 leading과 trailing을 모두 제공했고, [contentPadding][QuackButtonStyle.contentPadding]으로
 * `QuackPadding(horizontal=10.dp)`를 제공했다면 양끝의 horizontal 패딩이 각각 아이콘을 기준으로
 * 적용되는 게 아닌 버튼의 텍스트를 기준으로 적용됩니다. 따라서 개발자는 [contentPadding][QuackButtonStyle.contentPadding]의 값을
 * 제공할 때 양끝 아이콘을 기준으로 제공하는 게 아닌 가운데 텍스트를 기준으로 제공해야 합니다.
 * 이 정책은 양끝 아이콘이 동적으로 적용될 때 의도하지 않는 버튼 사이즈 변경을 예방하기 위해
 * 고안됐습니다. 예를 들어 `contentPadding: QuackPadding(horizontal=10.dp)`을 양끝 아이콘 기준으로
 * 적용했다고 해봅시다. 처음에는 양끝에 아이콘이 없어서 가운데 텍스트를 기준으로 패딩이 적용됩니다.
 * 이 시점에는 버튼의 너비가 25dp입니다. (왼쪽 패딩 10dp, 텍스트 5dp, 오른쪽 패딩 10dp) 사용자
 * 요청에 의해 양쪽 모두에 5dp의 너비를 갖는 아이콘이 추가되었습니다. 이 시점에서는 양쪽 아이콘이
 * 존재하므로 [contentPadding][QuackButtonStyle.contentPadding]이 양쪽 아이콘을 기준으로 적용되어
 * 버튼의 너비가 35dp입니다. (왼쪽 패딩 10dp, 왼쪽 아이콘 5dp, 텍스트 5dp, 오른쪽 아이콘 5dp,
 * 오른쪽 패딩 10dp) 즉, 의도하지 않게 버튼의 너비가 10dp 증가하였습니다. 이러한 상황을 예방하기
 * 위해 이 정책이 사용됩니다.
 *
 * ### 사용 가능 데코레이터
 *
 * |                 style             | [icons][Modifier.icons] |                       description                       |
 * | :-------------------------------: | :---------------------: | :-----------------------------------------------------: |
 * |  [Large][QuackLargeButtonStyle]  |            ⭕           |                                                          |
 * | [Medium][QuackMediumButtonStyle] |            ⭕           |                                                          |
 * |  [Small][QuackSmallButtonStyle]  |            ❌           | 버튼의 너비가 좁기에 아이콘 데코레이터를 사용할 수 없습니다. |
 *
 * @param enabled 활성화 상태 여부
 * @param style 적용할 스타일. 사전 정의 스타일은 [QuackButtonStyle.Companion] 필드를 참고하세요.
 * @param text 중앙에 표시할 텍스트
 * @param rippleEnabled 클릭했을 때 리플 애니메이션을 적용할지 여부
 * @param onClick 클릭했을 때 실행할 람다식. [enabled]이 true일 때만 작동합니다.
 */
@MustBeTested(passed = true)
@Composable
@NonRestartableComposable
@ExperimentalQuackQuackApi
public fun <T : ButtonStyleMarker> QuackButton(
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  @SugarToken @CasaValue("QuackButtonStyle.Large") style: QuackButtonStyle<T>,
  @CasaValue("\"QuackButton is experimental\"") text: String,
  rippleEnabled: Boolean = true,
  @CasaValue("{}") onClick: () -> Unit,
) {
  val isSmallButton = style is QuackSmallButtonStyle
  // TODO: 다른 경우로 사이즈를 지정하는 방법이 있을까?
  // TODO(3): LayoutModifierNode 지원
  var isSizeSpecified = false
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier) { currentModifier ->
    if (!isSizeSpecified) {
      isSizeSpecified = currentModifier is LayoutModifier
    }
  }
  val iconData = remember(quackDataModels, isSmallButton) {
    quackDataModels.fastFirstIsInstanceOrNull<ButtonIconData>().takeUnless { isSmallButton }
  }

  val backgroundColor = style.colors.backgroundColor
  val disabledBackgroundColor = style.colors.disabledBackgroundColor
  val currentBackgroundColor = if (enabled) backgroundColor else disabledBackgroundColor

  val contentColor = style.colors.contentColor
  val disabledContentColor = style.colors.disabledContentColor
  val currentContentColor = if (enabled) contentColor else disabledContentColor

  val borderThickness = style.borderThickness
  val borderColor = style.colors.borderColor
  val disabledBorderColor = style.colors.disabledBorderColor
  val currentBorder = remember(
    enabled,
    borderThickness,
    borderColor,
    disabledBorderColor,
  ) {
    QuackBorder(
      thickness = borderThickness,
      color = if (enabled) borderColor else disabledBorderColor,
    )
  }

  val iconColor = style.colors.iconColor

  val rippleColor = style.colors.rippleColor

  val currentRippleEnabled = if (enabled) rippleEnabled else false

  val radius = style.radius
  val shape = remember(radius) {
    RoundedCornerShape(size = radius)
  }

  val contentPadding = style.contentPadding
  val currentContentPadding = if (isSizeSpecified) null else contentPadding

  val iconSpacedBy = style.iconSpacedBy

  val typography = style.typography
  val disabledTypography = style.disabledTypography
  val currentTypography = remember(
    enabled,
    typography,
    disabledTypography,
    currentContentColor,
  ) {
    (if (enabled) typography else disabledTypography).change(color = currentContentColor)
  }

  val leadingIcon = iconData?.leadingIcon
  val trailingIcon = iconData?.trailingIcon

  val currentOnClick = onClick.takeIf { enabled }

  val inspectableModifier = style
    .wrappedDebugInspectable(composeModifier)
    .wrappedDebugInspectable {
      name = "QuackButton"
      properties["text"] = text
      properties["backgroundColor"] = currentBackgroundColor
      properties["iconColor"] = iconColor
      properties["rippleColor"] = rippleColor
      properties["rippleEnabled"] = currentRippleEnabled
      properties["shape"] = shape
      properties["border"] = currentBorder
      properties["typography"] = currentTypography
      properties["contentPadding"] = currentContentPadding
      properties["iconSpacedBy"] = iconSpacedBy
      properties["leadingIcon"] = leadingIcon
      properties["trailingIcon"] = trailingIcon
      properties["onClick"] = currentOnClick
    }

  QuackBaseButton(
    modifier = inspectableModifier,
    text = text,
    backgroundColor = currentBackgroundColor,
    iconColor = iconColor,
    rippleColor = rippleColor,
    rippleEnabled = currentRippleEnabled,
    shape = shape,
    border = currentBorder,
    typography = currentTypography,
    contentPadding = currentContentPadding,
    iconSpacedBy = iconSpacedBy,
    leadingIcon = leadingIcon,
    trailingIcon = trailingIcon,
    onClick = currentOnClick,
  )
}

private const val TextLayoutId = "QuackBaseButtonText"
private const val LeadingIconLayoutId = "QuackBaseButtonLeadingIcon"
private const val TrailingIconLayoutId = "QuackBaseButtonTrailingIcon"

// TODO: Modifier.testTag 별도 모듈로 분리 (아마 util-test)
//       isQuackQuackTestTagEnabled 로컬 변수 boolean 값에 따라 활성화해야 함
//       (클라이언트에서 test tag 중복 방지)
/**
 * 고유한 배치 정책으로 버튼을 그립니다. 배치 정책의 자세한 정보는 [QuackButton] 문서를 참고하세요.
 *
 * 이 컴포넌트는 [QuackButtonStyle]의 필드를 개별 인자로 받습니다.
 */
@NoSugar
@Composable
public fun QuackBaseButton(
  modifier: Modifier,
  text: String,
  backgroundColor: QuackColor,
  iconColor: QuackColor,
  rippleColor: QuackColor,
  rippleEnabled: Boolean,
  shape: Shape,
  border: QuackBorder,
  typography: QuackTypography,
  contentPadding: QuackPadding?,
  iconSpacedBy: Dp,
  leadingIcon: ImageVector?,
  trailingIcon: ImageVector?,
  onClick: (() -> Unit)?,
) {
  val currentColorFilter = remember(iconColor) { iconColor.toColorFilterOrNull() }

  Layout(
    modifier = modifier
      .testTag("button")
      .quackSurface(
        shape = shape,
        backgroundColor = backgroundColor,
        border = border,
        role = Role.Button,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick,
      ),
    content = {
      if (leadingIcon != null) {
        Box(
          modifier = Modifier
            .testTag("leadingIcon")
            .layoutId(LeadingIconLayoutId)
            .fontScaleAwareIconSize()
            .paint(
              painter = rememberVectorPainter(leadingIcon),
              colorFilter = currentColorFilter,
              contentScale = ContentScale.Fit,
            ),
        )
      }
      QuackText(
        modifier = Modifier
          .testTag("buttonText")
          .layoutId(TextLayoutId),
        text = text,
        typography = typography,
        singleLine = true,
        softWrap = false,
      )
      if (trailingIcon != null) {
        Box(
          modifier = Modifier
            .testTag("trailingIcon")
            .layoutId(TrailingIconLayoutId)
            .fontScaleAwareIconSize()
            .paint(
              painter = rememberVectorPainter(trailingIcon),
              colorFilter = currentColorFilter,
              contentScale = ContentScale.Fit,
            ),
        )
      }
    },
  ) { measurables, constraints ->
    val leadingIconMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == LeadingIconLayoutId
    }
    val textMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == TextLayoutId
    }!!
    val trailingIconMeasurable = measurables.fastFirstOrNull { measurable ->
      measurable.layoutId == TrailingIconLayoutId
    }

    val looseConstraints = constraints.asLoose(width = true, height = true)

    val horizontalPadding = contentPadding?.let { padding ->
      padding.horizontal.roundToPx() * 2
    } ?: 0
    val verticalPadding = contentPadding?.let { padding ->
      padding.vertical.roundToPx() * 2
    } ?: 0

    val baselineConstraints = constraints.offset(
      horizontal = -horizontalPadding,
      vertical = -verticalPadding,
    )
    val textPlaceable = textMeasurable.measure(baselineConstraints)

    val width = constraints.constrainWidth(textPlaceable.width + horizontalPadding)
    val height = constraints.constrainHeight(textPlaceable.height + verticalPadding)

    layout(width = width, height = height) {
      val textPlaceableX = Alignment.CenterHorizontally.align(
        size = textPlaceable.width,
        space = width,
        layoutDirection = layoutDirection,
      )
      textPlaceable.place(
        x = textPlaceableX,
        y = Alignment.CenterVertically.align(
          size = textPlaceable.height,
          space = height,
        ),
      )

      if (leadingIconMeasurable != null) {
        val leadingIconPlaceable = leadingIconMeasurable.measure(looseConstraints)
        leadingIconPlaceable.place(
          x = textPlaceableX - iconSpacedBy.roundToPx() - leadingIconPlaceable.width,
          y = Alignment.CenterVertically.align(
            size = leadingIconPlaceable.height,
            space = height,
          ),
        )
      }
      if (trailingIconMeasurable != null) {
        val trailingIconPlaceable = trailingIconMeasurable.measure(looseConstraints)
        trailingIconPlaceable.place(
          x = textPlaceableX + textPlaceable.width + iconSpacedBy.roundToPx(),
          y = Alignment.CenterVertically.align(
            size = trailingIconPlaceable.height,
            space = height,
          ),
        )
      }
    }
  }
}
