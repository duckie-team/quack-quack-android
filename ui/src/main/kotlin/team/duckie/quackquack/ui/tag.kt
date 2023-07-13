/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
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
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.material.QuackBorder
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.QuackPadding
import team.duckie.quackquack.material.QuackTypography
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.material.quackSurface
import team.duckie.quackquack.runtime.QuackDataModifierModel
import team.duckie.quackquack.runtime.quackMaterializeOf
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable
import team.duckie.quackquack.ui.plugin.interceptor.rememberInterceptedStyleSafely
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
  val icon: ImageVector,
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
@Stable
public fun Modifier.trailingIcon(
  icon: ImageVector,
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

@VisibleForTesting
internal object QuackTagErrors {
  const val GrayscaleFlatStyleUnselectedState = "Per design guidelines, " +
    "the GrayscaleFlat design specification does not allow an unselected state."
}

/**
 * 태그를 그립니다.
 *
 * - 이 컴포넌트는 자체의 패딩 정책을 구현합니다.
 * - [스타일][style]별로 사용 가능한 데코레이터가 달라집니다.
 *
 * ### 패딩 정책
 *
 * 1. [태그의 스타일][QuackTagStyle]에서 [contentPadding][QuackTagStyle.contentPadding] 옵션을
 * 별도로 제공하고 있습니다. 이는 [Modifier.padding]과 다른 패딩 정책을 사용합니다. [Modifier.padding]은
 * 태그의 루트 레이아웃을 기준으로 패딩이 적용되지만, [QuackTagStyle.contentPadding]은 태그의
 * 텍스트와 후행 아이콘을 기준으로 적용됩니다. 태그 컴포넌트는 [trailingIcon][Modifier.trailingIcon] 데코레이터로
 * 후행 아이콘을 추가할 수 있고, 후행 아이콘 여부에 따라 패딩 정책이 결정됩니다. 후행 아이콘이 있다면 세로와
 * 가로에 따라 패딩을 적용하는 방식이 달라집니다. 세로의 경우는 태그 텍스트를 기준으로 적용되고, 가로의 경우는
 * 후행 아이콘의 터치 영역을 증가시키는 식으로 적용됩니다. 기본적으로 후행 아이콘은 16px의 사이즈를 갖습니다.
 * 유저 입장에서 16px의 터치 영역은 좋은 경험을 제공하지 못할 것으로 예상하여, [전체 가로 패딩][QuackPadding.horizontal]의
 * 오른쪽 영역을 후행 아이콘의 오른쪽 패딩으로 적용합니다. 이때, [전체 가로 패딩][QuackPadding.horizontal]의 오른쪽
 * 영역을 그대로 적용하는 게 아니라 해당 값에서 [텍스트와 후행 아이콘 사이 공간][QuackTagStyle.iconSpacedBy]을 뺀
 * 값을 적용합니다. 이는 디자인 가이드라인에 의거합니다. 그리고 [텍스트와 후행 아이콘 사이 공간][QuackTagStyle.iconSpacedBy]의
 * 반을 후행 아이콘의 왼쪽 패딩으로 적용합니다. [텍스트와 후행 아이콘 사이 공간][QuackTagStyle.iconSpacedBy] 반의
 * 나머지 부분은 태그 텍스트의 오른쪽 패딩으로 적용됩니다. 후행 아이콘이 없다면 단순히 태그 텍스트를 기준으로 패딩이
 * 적용됩니다.
 * 2. [LayoutModifier]를 사용하여 컴포넌트의 사이즈가 명시됐다면 [QuackTagStyle.contentPadding]
 * 옵션은 무시됩니다. [contentPadding][QuackTagStyle.contentPadding]은 컴포넌트 사이즈 하드코딩을
 * 대체하는 용도로 제공됩니다. 하지만 컴포넌트 사이즈가 하드코딩됐다면 [contentPadding][QuackTagStyle.contentPadding]을
 * 제공하는 의미가 없어집니다. 따라서 컴포넌트의 사이즈가 하드코딩됐다면 개발자의 의도를 존중한다는 원칙하에
 * 컴포넌트의 사이즈가 중복으로 확장되는 일을 예방하고자 [contentPadding][QuackTagStyle.contentPadding]
 * 옵션을 무시합니다. 예를 들어 `Modifier.height(10.dp)`로 컴포넌트 높이를 명시했고, [contentPadding][QuackTagStyle.contentPadding]으로
 * `QuackPadding(vertical=10.dp)`을 제공했다고 해봅시다. 이런 경우에는 [contentPadding][QuackTagStyle.contentPadding]이
 * 무시되고 태그의 높이가 10dp로 적용됩니다. 컴포넌트 사이즈를 명시하면서 패딩을 적용하고 싶다면
 * [contentPadding][QuackTagStyle.contentPadding] 대신에 [Modifier.padding]을 사용하세요.
 * [LayoutModifier]를 사용하는 흔한 [Modifier]로는 [Modifier.size], [Modifier.height], [Modifier.width] 등이
 * 있습니다. [LayoutModifierNode]를 사용하는 [Modifier]는 [contentPadding][QuackTagStyle.contentPadding] 무시
 * 옵션이 아직 지원되지 않습니다. ([#636](https://github.com/duckie-team/quack-quack-android/issues/636))
 *
 * ### 사용 가능 데코레이터
 *
 * |                         style                          | [trailingIcon][Modifier.trailingIcon] |            description             |
 * |:------------------------------------------------------:|:-------------------------------------:|:----------------------------------:|
 * |          [Outlined][QuackOutlinedTagDefaults]          |                   ⭕                   |                                    |
 * |            [Filled][QuackFilledTagDefaults]            |                   ⭕                   |                                    |
 * |     [GrayscaleFlat][QuackGrayscaleFlatTagDefaults]     |                   ❌                   | 태그의 너비가 좁기에 아이콘 데코레이터를 사용할 수 없습니다. |
 * | [GrayscaleOutlined][QuackGrayscaleOutlinedTagDefaults] |                   ⭕                   |                                    |
 *
 *
 * @param text 중앙에 표시할 텍스트
 * @param style 적용할 스타일. 사전 정의 스타일은 [QuackTagStyle.Companion] 필드를 참고하세요.
 * @param selected 선택 상태 여부
 * @param rippleEnabled 클릭했을 때 리플 애니메이션을 적용할지 여부
 * @param onClick 클릭했을 때 실행할 람다식. 태그는 토글이 자유로워야 하므로 [selected]와 관계 없이
 * 항상 클릭 가능합니다.
 */
@Sugarable
@ExperimentalQuackQuackApi
@NonRestartableComposable
@Composable
public fun <T : TagStyleMarker> QuackTag(
  @CasaValue("\"QuackTagPreview\"") text: String,
  @SugarToken @CasaValue("QuackTagStyle.Outlined") style: QuackTagStyle<T>,
  modifier: Modifier = Modifier,
  selected: Boolean = true,
  rippleEnabled: Boolean = true,
  @CasaValue("{}") onClick: () -> Unit,
) {
  @Suppress("NAME_SHADOWING")
  val style = rememberInterceptedStyleSafely<QuackTagStyle<T>>(style = style, modifier = modifier)

  val isGrayscaleFlat = style is QuackGrayscaleFlatTagDefaults
  if (isGrayscaleFlat) {
    check(selected) { QuackTagErrors.GrayscaleFlatStyleUnselectedState }
  }

  var isSizeSpecified = false
  val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier) { currentModifier ->
    if (!isSizeSpecified) {
      isSizeSpecified = currentModifier is LayoutModifier
    }
  }
  val trailingIconData = remember(quackDataModels) {
    quackDataModels.fastFirstIsInstanceOrNull<TagTrailingIconData>().takeUnless { isGrayscaleFlat }
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
  val currentBorder =
    remember(selected, borderThickness, borderColor, unselectedBorderColor) {
      QuackBorder(
        thickness = borderThickness,
        color = if (selected) borderColor else unselectedBorderColor,
      )
    }

  val rippleColor = style.colors.rippleColor
  val currentRippleEnabled = if (selected) rippleEnabled else false

  val radius = style.radius

  val shape = remember(radius) { RoundedCornerShape(size = radius) }

  val contentPadding = style.contentPadding
  val currentContentPadding = if (isSizeSpecified) null else contentPadding

  val iconSpacedBy = style.iconSpacedBy

  val typography = style.typography
  val unselectedTypography = style.unselectedTypography
  val currentTypography =
    remember(selected, typography, unselectedTypography, currentContentColor) {
      (if (selected) typography else unselectedTypography).change(color = currentContentColor)
    }

  val iconColor = style.colors.iconColor
  val unselectedIconColor = style.colors.unselectedIconColor
  val currentIconColor = if (selected) iconColor else unselectedIconColor

  val trailingIcon = trailingIconData?.icon
  val trailingIconSize = trailingIconData?.size
  val trailingIconOnClick = trailingIconData?.onClick

  val inspectableModifier = style.wrappedDebugInspectable(composeModifier)

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
  trailingIcon: ImageVector?,
  trailingIconSize: Dp?,
  trailingIconOnClick: (() -> Unit)?,
  onClick: (() -> Unit)?,
) {
  if (trailingIcon != null) requireNotNull(trailingIconSize)

  val currentIconColorFilter = remember(iconColor) {
    (iconColor ?: QuackColor.Unspecified).toColorFilterOrNull()
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
                  painter = rememberVectorPainter(trailingIcon),
                  colorFilter = currentIconColorFilter,
                  contentScale = ContentScale.Fit,
                ),
            )
          },
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
              ),
            )
          }
        }
        Box(
          Modifier
            .layoutId(FakeTrailingIconLayoutId)
            .fontScaleAwareIconSize(baseline = trailingIconSize!!),
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
        .plus(trailingIconContainerConstraints?.minWidth ?: trailingIconAwareEndHorizontalPadding),
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
