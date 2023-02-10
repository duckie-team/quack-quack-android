// ORIGINAL IMPLEMENTATION COPIED FROM AOSP Switch.kt FILE. (Material-Compose Artifact)

/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:OptIn(ExperimentalMaterialApi::class)

package team.duckie.quackquack.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.animatedQuackBorderAsState
import team.duckie.quackquack.ui.border.applyAnimatedQuackBorder
import team.duckie.quackquack.ui.color.QuackColor

private val TrackWidth = 28.dp
internal val TrackStrokeWidth = 12.dp

private val ThumbDiameter = 16.dp
private val ThumbRippleRadius = 10.dp
private val ThumbPathLength = TrackWidth - ThumbDiameter
private val ThumbDefaultElevation = 1.dp
private val ThumbPressedElevation = 6.dp

private val SwitchWidth = TrackWidth
private val SwitchHeight = ThumbDiameter
private const val SwitchSwipeableThresholds = 0.5f

/**
 * 덕키 디자인의 Switch 를 구현합니다.
 *
 * @param modifier Modifier to be applied to the switch layout
 * @param checked whether or not this component is checked
 * @param onCheckedChange callback to be invoked when Switch is being clicked,
 * therefore the change of checked state is requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the "checked" state.
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this Switch. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this Switch in different [Interaction]s.
 */
@Composable
public fun QuackSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((checked: Boolean) -> Unit)?,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val minBound = 0f
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val swipeableState = rememberSwipeableStateFor(
        value = checked,
        onValueChange = onCheckedChange ?: {},
        animationSpec = QuackAnimationSpec(),
    )
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val toggleableModifier = if (onCheckedChange != null) {
        Modifier.toggleable(
            value = checked,
            onValueChange = onCheckedChange,
            enabled = true,
            role = Role.Switch,
            interactionSource = interactionSource,
            indication = null,
        )
    } else {
        Modifier
    }

    Box(
        modifier
            .then(toggleableModifier)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(minBound to false, maxBound to true),
                thresholds = { _, _ ->
                    FractionalThreshold(fraction = SwitchSwipeableThresholds)
                },
                orientation = Orientation.Horizontal,
                enabled = onCheckedChange != null,
                reverseDirection = isRtl,
                interactionSource = interactionSource,
                resistance = null,
            )
            .wrapContentSize(align = Alignment.Center)
            .requiredSize(
                width = SwitchWidth,
                height = SwitchHeight,
            )
    ) {
        SwitchImpl(
            checked = checked,
            thumbValue = swipeableState.offset,
            interactionSource = interactionSource,
        )
    }
}

@Composable
private fun <T : Any> rememberSwipeableStateFor(
    value: T,
    onValueChange: (T) -> Unit,
    animationSpec: AnimationSpec<Float>,
): SwipeableState<T> {
    val swipeableState = remember {
        SwipeableState(
            initialValue = value,
            animationSpec = animationSpec,
            confirmStateChange = { true },
        )
    }
    val forceAnimationCheck = remember { mutableStateOf(false) }
    LaunchedEffect(value, forceAnimationCheck.value) {
        if (value != swipeableState.currentValue) {
            swipeableState.animateTo(value)
        }
    }
    DisposableEffect(swipeableState.currentValue) {
        if (value != swipeableState.currentValue) {
            onValueChange(swipeableState.currentValue)
            forceAnimationCheck.value = !forceAnimationCheck.value
        }
        onDispose { }
    }
    return swipeableState
}

@Composable
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    thumbValue: State<Float>,
    interactionSource: InteractionSource,
) {
    val interactions = remember { mutableStateListOf<Interaction>() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val hasInteraction = interactions.isNotEmpty()
    val elevation = if (hasInteraction) {
        ThumbPressedElevation
    } else {
        ThumbDefaultElevation
    }
    val trackColor by animateColorAsState(
        targetValue = if (checked) {
            QuackColor.DuckieOrange.composeColor
        } else {
            QuackColor.Gray3.composeColor
        },
        animationSpec = QuackAnimationSpec(),
    )
    Canvas(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxSize()
    ) {
        drawTrack(
            trackColor = trackColor,
            trackWidth = TrackWidth.toPx(),
            strokeWidth = TrackStrokeWidth.toPx(),
        )
    }

    val thumbBorder = animatedQuackBorderAsState(
        if (checked) {
            QuackBorder(color = QuackColor.DuckieOrange)
        } else {
            QuackBorder(color = QuackColor.Gray2)
        }
    )
    Spacer(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .offset {
                IntOffset(
                    x = thumbValue.value.roundToInt(),
                    y = 0,
                )
            }
            .clip(CircleShape)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = ThumbRippleRadius,
                )
            )
            .requiredSize(size = ThumbDiameter)
            .applyAnimatedQuackBorder(
                border = thumbBorder,
                shape = CircleShape,
            )
            .shadow(
                elevation = elevation,
                shape = CircleShape,
                clip = false,
            )
            .background(
                color = QuackColor.White.composeColor,
                shape = CircleShape,
            )
    )
}

private fun DrawScope.drawTrack(
    trackColor: Color,
    trackWidth: Float,
    strokeWidth: Float,
) {
    val strokeRadius = strokeWidth / 2
    drawLine(
        color = trackColor,
        start = Offset(
            x = strokeRadius,
            y = center.y,
        ),
        end = Offset(
            x = trackWidth - strokeRadius,
            y = center.y,
        ),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round,
    )
}
