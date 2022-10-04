/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("KDocFields", "OPT_IN_MARKER_ON_WRONG_TARGET")
@file:OptIn(
    ExperimentalMaterialApi::class,
)

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import kotlin.math.roundToInt

private val QuackDrawerVelocityThreshold = 400.dp

private val QuackEndDrawerPadding = 76.dp

/*
열 때
* 1. 버튼을 눌렀을 때 expand 되어야함
* 2. 오른쪽으로 드래그 할 때 expand 되어야함

닫을 때
* 1. 외부 터치하면 다시 닫힘
* 2. 드래그로 포커스 조절 가능
* */
@OptIn(ExperimentalMaterialApi::class)
@Composable
public fun QuackModalDrawer(
    drawerState: QuackDrawerState,
    content: @Composable () -> Unit,
): Unit {
    val scope = rememberCoroutineScope()
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),// drawer 크기
    ) {
        val modalDrawerConstraints = constraints

        val minValue = -modalDrawerConstraints.maxWidth.toFloat()
        val maxValue = 0f

        val anchors = mapOf(
            minValue to QuackDrawerValue.Closed,
            maxValue to QuackDrawerValue.Open,
        )

        Box(
            modifier = Modifier.swipeable(
                state = drawerState.swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = true,
                velocityThreshold = QuackDrawerVelocityThreshold,
                resistance = null,
            ),
        ) {
            Scrim(
                open = drawerState.isOpen,
                onClose = {
                    scope.launch { drawerState.close() }
                },
                fraction = {
                    calculateFraction(minValue, maxValue, drawerState.offset.value)
                },
                color = QuackColor.BlackOpacity60.composeColor,
            )
            Surface(
                modifier = with(
                    receiver = LocalDensity.current,
                ) {
                    Modifier
                        .sizeIn(
                            minWidth = modalDrawerConstraints.minWidth.toDp(),
                            minHeight = modalDrawerConstraints.minHeight.toDp(),
                            maxWidth = modalDrawerConstraints.maxWidth.toDp(),
                            maxHeight = modalDrawerConstraints.maxHeight.toDp()
                        )
                        .offset {
                            IntOffset(
                                x = drawerState.offset.value.roundToInt(),
                                y = 0,
                            )
                        }
                        .padding(
                            end = QuackEndDrawerPadding
                        )
                },
                color = QuackColor.White.composeColor,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        content()
                    },
                )
            }
        }
    }
}

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color,
) {
    val dismissDrawer = if (open) {
        Modifier
            .pointerInput(
                key1 = onClose,
            ) { detectTapGestures { onClose() } }
    } else {
        Modifier
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .then(
                other = dismissDrawer,
            ),
    ) {
        drawRect(color, alpha = fraction())
    }
}

private fun calculateFraction(
    a: Float,
    b: Float,
    pos: Float,
) = ((pos - a) / (b - a)).coerceIn(
    minimumValue = 0f,
    maximumValue = 1f,
)


@Composable
public fun rememberQuackDrawerState(
    initialValue: QuackDrawerValue = QuackDrawerValue.Closed,
    confirmStateChange: (
        QuackDrawerValue: QuackDrawerValue,
    ) -> Boolean = { true },
): QuackDrawerState {
    return rememberSaveable(
        saver = QuackDrawerState.saver(
            confirmStateChange = confirmStateChange,
        ),
    ) {
        QuackDrawerState(
            initialValue = initialValue,
            confirmStateChange = confirmStateChange,
        )
    }
}

@Stable
public class QuackDrawerState(
    initialValue: QuackDrawerValue,
    confirmStateChange: (QuackDrawerValue) -> Boolean = { true },
) {
    internal val swipeableState: SwipeableState<QuackDrawerValue> = SwipeableState(
        initialValue = initialValue,
        animationSpec = quackAnimationSpec(),
        confirmStateChange = confirmStateChange
    )

    /**
     * Whether the drawer is open.
     */
    public val isOpen: Boolean
        get() = currentValue == QuackDrawerValue.Open

    /**
     * Whether the drawer is closed.
     */
    public val isClosed: Boolean
        get() = currentValue == QuackDrawerValue.Closed

    /**
     * The current value of the state.
     *
     * If no swipe or animation is in progress, this corresponds to the start the drawer
     * currently in. If a swipe or an animation is in progress, this corresponds the state drawer
     * was in before the swipe or animation started.
     */

    public val currentValue: QuackDrawerValue
        get() {
            return swipeableState.currentValue
        }

    /**
     * Whether the state is currently animating.
     */
    public val isAnimationRunning: Boolean
        get() {
            return swipeableState.isAnimationRunning
        }

    /**
     * Open the drawer with animation and suspend until it if fully opened or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the open animation ended
     */
    public suspend fun open(): Unit = animateTo(QuackDrawerValue.Open, quackAnimationSpec())

    /**
     * Close the drawer with animation and suspend until it if fully closed or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the close animation ended
     */
    public suspend fun close(): Unit = animateTo(QuackDrawerValue.Closed, quackAnimationSpec())

    /**
     * Set the state of the drawer with specific animation
     *
     * @param targetValue The new value to animate to.
     * @param anim The animation that will be used to animate to the new value.
     */
    public suspend fun animateTo(targetValue: QuackDrawerValue, anim: AnimationSpec<Float>) {
        swipeableState.animateTo(targetValue, anim)
    }

    /**
     * Set the state without any animation and suspend until it's set
     *
     * @param targetValue The new target value
     */
    public suspend fun snapTo(targetValue: QuackDrawerValue) {
        swipeableState.snapTo(targetValue)
    }

    /**
     * 드로어 상태의 대상 값 입니다.
     *
     * Swipe 가 진행 중인 경우 Drawer 에서 Animation 으로 표시 하는 값 입니다.
     * Swipe 마감 Animation 이 실행 중인 경우 이 값이 해당 Animation 의 대상 값 입니다.
     * Swipe 또는 Animation 이 진행 되지 않으면 [currentValue] 와 동일합 니다.
     */
    public val targetValue: QuackDrawerValue
        get() = swipeableState.targetValue

    /**
     * The current position (in pixels) of the drawer sheet.
     */
    public val offset: State<Float>
        get() = swipeableState.offset

    public companion object {
        /**
         * The default [Saver] implementation for [QuackDrawerState].
         */
        public fun saver(confirmStateChange: (QuackDrawerValue) -> Boolean): Saver<QuackDrawerState, QuackDrawerValue> =
            Saver(
                save = { drawerState ->
                    drawerState.currentValue
                },
                restore = { QuackDrawerValue ->
                    QuackDrawerState(
                        initialValue = QuackDrawerValue,
                        confirmStateChange = confirmStateChange,
                    )
                }
            )
    }
}

public enum class QuackDrawerValue {
    /**
     * The state of the drawer when it is closed.
     */
    Closed,

    /**
     * The state of the drawer when it is open.
     */
    Open
}


@Preview
@Composable
public fun `프리뷰`(): Unit {
    val drawerState = rememberQuackDrawerState()
    val coroutineScope = rememberCoroutineScope()
    QuackLarge40WhiteButton(
        text = "실행",
        onClick = {
            coroutineScope.launch {
                drawerState.open()
            }
        },
    )
    QuackModalDrawer(
        drawerState = drawerState,
    ) {
        QuackBody1(
            text = "22"
        )
        QuackDivider()
        QuackBody1(
            text = "22"
        )
    }

}
