/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.layoutId
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
import team.duckie.quackquack.material.QuackIcon
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
import team.duckie.quackquack.util.MustBeTested
import team.duckie.quackquack.util.fastFirstIsInstanceOrNull

@QuackDsl
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public interface ButtonStyleMarker

@Immutable
public interface QuackButtonStyle<T : ButtonStyleMarker> {
    public val colors: QuackButtonColors

    public val radius: Dp

    public val contentPadding: QuackPadding
    public val iconSpacedBy: Dp

    public val borderThickness: Dp

    public val typography: QuackTypography
    public val disabledTypography: QuackTypography

    @Stable
    public operator fun invoke(styleBuilder: T.() -> Unit): T

    public companion object {
        @Stable
        public val Large: QuackButtonStyle<QuackLargeButtonDefaults> get() = QuackLargeButtonDefaults()

        @Stable
        public val Medium: QuackButtonStyle<QuackMediumButtonDefaults> get() = QuackMediumButtonDefaults()

        // TODO(3): 데코레이터 모디파이어 사용 금지 린트 제공
        @Stable
        public val Small: QuackButtonStyle<QuackSmallButtonDefaults> get() = QuackSmallButtonDefaults()
    }
}

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
    @Stable
    internal fun copy(
        backgroundColor: QuackColor = this.backgroundColor,
        disabledBackgroundColor: QuackColor = this.disabledBackgroundColor,
        contentColor: QuackColor = this.contentColor,
        disabledContentColor: QuackColor = this.disabledContentColor,
        borderColor: QuackColor = this.borderColor,
        disabledBorderColor: QuackColor = this.disabledBorderColor,
        iconColor: QuackColor = this.iconColor,
        rippleColor: QuackColor = this.rippleColor,
    ): QuackButtonColors {
        return QuackButtonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            borderColor = borderColor,
            disabledBorderColor = disabledBorderColor,
            iconColor = iconColor,
            rippleColor = rippleColor,
        )
    }

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
}

@Immutable
public class QuackLargeButtonDefaults internal constructor() :
    QuackButtonStyle<QuackLargeButtonDefaults>, ButtonStyleMarker {
    public override var colors: QuackButtonColors = buttonColors()

    public override val radius: Dp = 8.dp

    public override val contentPadding: QuackPadding = QuackPadding(
        horizontal = 112.dp,
        vertical = 12.dp,
    )
    public override val iconSpacedBy: Dp = 4.dp

    public override val borderThickness: Dp = 1.dp

    public override val typography: QuackTypography = QuackTypography.Subtitle
    public override val disabledTypography: QuackTypography = typography

    @Stable
    public fun buttonColors(
        backgroundColor: QuackColor = QuackColor.DuckieOrange,
        disabledBackgroundColor: QuackColor = QuackColor.Gray2,
        contentColor: QuackColor = QuackColor.White,
        disabledContentColor: QuackColor = QuackColor.White,
        borderColor: QuackColor = backgroundColor,
        disabledBorderColor: QuackColor = disabledBackgroundColor,
        iconColor: QuackColor = contentColor,
    ): QuackButtonColors {
        return QuackButtonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            borderColor = borderColor,
            disabledBorderColor = disabledBorderColor,
            rippleColor = QuackColor.Unspecified,
            iconColor = iconColor,
        )
    }

    @Stable
    override fun invoke(styleBuilder: QuackLargeButtonDefaults.() -> Unit): QuackLargeButtonDefaults {
        return apply(styleBuilder)
    }
}

@Immutable
public class QuackMediumButtonDefaults internal constructor() :
    QuackButtonStyle<QuackMediumButtonDefaults>, ButtonStyleMarker {
    public override var colors: QuackButtonColors = QuackButtonColors(
        backgroundColor = QuackColor.White,
        disabledBackgroundColor = QuackColor.White,
        contentColor = QuackColor.Black,
        disabledContentColor = QuackColor.DuckieOrange,
        borderColor = QuackColor.DuckieOrange,
        disabledBorderColor = QuackColor.Gray3,
        rippleColor = QuackColor.Unspecified,
        iconColor = QuackColor.Black,
    )

    public override val radius: Dp = 8.dp

    public override val contentPadding: QuackPadding = QuackPadding(
        horizontal = 58.dp,
        vertical = 10.dp,
    )
    public override val iconSpacedBy: Dp = 4.dp

    public override val borderThickness: Dp = 1.dp

    public override val typography: QuackTypography = QuackTypography.Body1
    public override val disabledTypography: QuackTypography = QuackTypography.Title2

    @Stable
    public fun buttonColors(iconColor: QuackColor = colors.iconColor): QuackButtonColors {
        return colors.copy(iconColor = iconColor)
    }

    @Stable
    override fun invoke(styleBuilder: QuackMediumButtonDefaults.() -> Unit): QuackMediumButtonDefaults {
        return apply(styleBuilder)
    }
}

@Immutable
public class QuackSmallButtonDefaults internal constructor() :
    QuackButtonStyle<QuackSmallButtonDefaults>, ButtonStyleMarker {
    public override var colors: QuackButtonColors = buttonColors()

    public override var radius: Dp = 8.dp

    public override var contentPadding: QuackPadding = QuackPadding(
        horizontal = 12.dp,
        vertical = 8.dp,
    )
    public override val iconSpacedBy: Dp = 4.dp

    public override val borderThickness: Dp = 1.dp

    public override var typography: QuackTypography = QuackTypography.Body1
    public override var disabledTypography: QuackTypography = QuackTypography.Body1

    @Stable
    public fun buttonColors(
        backgroundColor: QuackColor = QuackColor.DuckieOrange,
        disabledBackgroundColor: QuackColor = QuackColor.Gray2,
        contentColor: QuackColor = QuackColor.White,
        disabledContentColor: QuackColor = QuackColor.White,
        borderColor: QuackColor = backgroundColor,
        disabledBorderColor: QuackColor = disabledBackgroundColor,
    ): QuackButtonColors {
        return QuackButtonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            borderColor = borderColor,
            disabledBorderColor = disabledBorderColor,
            rippleColor = QuackColor.Unspecified,
            iconColor = QuackColor.Unspecified,
        )
    }

    @Stable
    override fun invoke(styleBuilder: QuackSmallButtonDefaults.() -> Unit): QuackSmallButtonDefaults {
        return apply(styleBuilder)
    }
}

@Stable
private data class ButtonIconData(
    val leadingIcon: QuackIcon?,
    val trailingIcon: QuackIcon?,
) : QuackDataModifierModel

@DecorateModifier
@Stable
public fun Modifier.icons(
    leadingIcon: QuackIcon? = null,
    trailingIcon: QuackIcon? = null,
): Modifier {
    return then(
        ButtonIconData(
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        ),
    )
}

// TODO(2): 빌트인 애니메이션 지원
@MustBeTested(passed = false) // TODO: Testing (Modifier.inspectable)
@Composable
@NonRestartableComposable
@ExperimentalQuackQuackApi
@NoSugar // onClick param is functional type (currently not supported)
public fun <T : ButtonStyleMarker> QuackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @SugarToken
    @CasaValue("QuackButtonStyle.Large")
    style: QuackButtonStyle<T>,
    @CasaValue("\"QuackButton is experimental\"") text: String,
    rippleEnabled: Boolean = true,
    @CasaValue("{}") onClick: () -> Unit,
) {
    val isSmallButton = style is QuackSmallButtonDefaults
    // TODO: 다른 경우로 사이즈를 지정하는 방법이 있을까?
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

    QuackBaseButton(
        modifier = composeModifier,
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
    leadingIcon: QuackIcon?,
    trailingIcon: QuackIcon?,
    onClick: (() -> Unit)?,
) {
    val currentColorFilter = remember(iconColor) {
        ColorFilter.tint(iconColor.value)
    }

    Layout(
        modifier = modifier.quackSurface(
            shape = shape,
            backgroundColor = backgroundColor,
            border = border,
            rippleEnabled = rippleEnabled,
            rippleColor = rippleColor,
            onClick = onClick,
        ),
        content = {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier
                        .layoutId(LeadingIconLayoutId)
                        .fontScaleAwareIconSize()
                        .paint(
                            painter = leadingIcon.asPainter(),
                            colorFilter = currentColorFilter,
                            contentScale = ContentScale.Fit,
                        ),
                )
            }
            QuackText(
                modifier = Modifier.layoutId(TextLayoutId),
                text = text,
                typography = typography,
                singleLine = true,
                softWrap = false,
            )
            if (trailingIcon != null) {
                Box(
                    modifier = Modifier
                        .layoutId(TrailingIconLayoutId)
                        .fontScaleAwareIconSize()
                        .paint(
                            painter = trailingIcon.asPainter(),
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
