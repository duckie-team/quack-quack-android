/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.runIf

// Caveat: ModalDrawer 는 개인이 구현하기엔 꽤 복잡하고, edge case 가 많을 것으로 예상되어
//         Material 를 참조합니다.

/**
 * QuackModelDrawer 에 사용되는 리소스들을 정의합니다.
 */
private object QuackModalDrawerDefaults {
    val VelocityThreshold = 400.dp
    const val ThresholdFraction = 0.5f

    /**
     * |DrawerContent|Content|
     *
     * DrawerContent 와 Content 간의 사이 간격을 의미합니다.
     */
    val EndDrawerPadding = 76.dp

    val DrawerBackgroundColor = QuackColor.White
    val ScrimBackgroundColor = QuackColor.Black.change(
        alpha = 0.6f,
    )

    const val InfiniteWidthExceptionMessage = "Drawer shouldn't have infinite width"
}

/**
 * ModelDrawer 를 구현합니다.
 *
 * 드래그를 통해서 Drawer 를 활성화 할 수 있으며,
 * [drawerState] 의 [QuackDrawerState.open] 을 통해서 활성화 할 수 있습니다.
 *
 * QuackModalDrawer 가 open 상태가 아닐 때 원래 content 를
 * 표시하기 위해 [content] 를 인수로 받습니다.
 *
 * @param drawerState Drawer 의 상태
 * @param drawerContent Drawer 가 [QuackDrawerValue.Open] 상태일 때 Drawer 내부에 표시할 content
 * @param content Drawer 가 [QuackDrawerValue.Closed] 상태일 때 표시할 content
 */
@Composable
public fun QuackModalDrawer(
    drawerState: QuackDrawerState,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
): Unit = QuackModalDrawerImpl(
    drawerState = drawerState,
    drawerContent = drawerContent,
    content = content,
)

/**
 * QuackModalDrawer 의 구현체 입니다.
 *
 * QuackModalDrawer 가 open 상태가 아닐 때 원래 content 를
 * 인식하기 위해 [content] 를 인수로 받습니다.
 *
 * @param drawerState Drawer 의 상태 [QuackDrawerState]
 * @param gesturesEnabled drag gesture 의 허용 여부
 * @param drawerContent Drawer 내부에 들어갈 content
 * @param content Drawer 가 Close 일 때 표시 할 컴포저블
 */
@Composable
private fun QuackModalDrawerImpl(
    drawerState: QuackDrawerState,
    gesturesEnabled: Boolean = true,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) = with(
    receiver = QuackModalDrawerDefaults,
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val modalDrawerConstraints = constraints
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException(InfiniteWidthExceptionMessage)
        }

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
                thresholds = { _, _ ->
                    FractionalThreshold(
                        fraction = ThresholdFraction,
                    )
                },
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled,
                velocityThreshold = VelocityThreshold,
                resistance = null,
            ),
        ) {
            Box(
                modifier = Modifier.wrapContentSize(),
            ) {
                content()
            }
            Scrim(
                open = drawerState.isOpen,
                onClose = {
                    if (
                        gesturesEnabled &&
                        drawerState.confirmStateChange(
                            /* drawerValue = */
                            QuackDrawerValue.Closed,
                        )
                    ) {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                },
                fraction = {
                    calculateFraction(
                        a = minValue,
                        b = maxValue,
                        pos = drawerState.offset.value,
                    )
                },
                color = ScrimBackgroundColor,
            )
            QuackSurface(
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
                            end = EndDrawerPadding,
                        )
                        // prevent click ripple
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = {},
                        )
                },
                backgroundColor = DrawerBackgroundColor,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    content = drawerContent,
                )
            }
        }
    }
}

/**
 * QuackModalDrawer 외부에 표시 될 배경 오버레이
 *
 * @param open Drawer 가 open 되었는지 여부
 * @param onClose Drawer 외부를 터치 했을 때의 닫힘 이벤트
 * @param fraction 배경에 적용 될 명암 정도
 * @param color 배경에 적용 될 색상
 */
@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: QuackColor,
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .runIf(
                condition = open,
            ) {
                pointerInput(
                    key1 = onClose,
                ) {
                    detectTapGestures {
                        onClose()
                    }
                }
            },
    ) {
        drawRect(
            color = color.composeColor,
            alpha = fraction(),
        )
    }
}

/**
 * 화면의 어느 포인트에 위치하고 있는지에 대한 Fraction.
 * Drawer 가 열려 있는 정도에 따라 큰 값을 반환합니다.
 *
 * @param a 최소 값
 * @param b 최대 값
 * @param pos 현재 Drawer 가 위치한 offset
 * @return Drawer 가 열려있는 정도
 */
@Suppress("SameParameterValue")
private fun calculateFraction(
    a: Float,
    b: Float,
    pos: Float,
) = ((pos - a) / (b - a)).coerceIn(
    minimumValue = 0f,
    maximumValue = 1f,
)

/**
 * [QuackDrawerState] 을 생성하고, remember 합니다.
 *
 * @param initialValue state 의 초기값
 * @param confirmStateChange 상태 변경을 확인하기(confirm) 위한 콜백.
 *
 * @return QuackModalDrawer 의 상태를 관리하는 객체
 */
@Composable
public fun rememberQuackDrawerState(
    initialValue: QuackDrawerValue = QuackDrawerValue.Closed,
    confirmStateChange: (drawerValue: QuackDrawerValue) -> Boolean = { true },
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

/**
 * QuackModalDrawer 의 상태를 관리합니다.
 *
 * @param initialValue state 의 초기값
 * @param confirmStateChange 상태 변경을 확인하기(confirm) 위한 콜백
 */
@Stable
public class QuackDrawerState(
    initialValue: QuackDrawerValue,
    internal val confirmStateChange: (
        drawerValue: QuackDrawerValue,
    ) -> Boolean = { true },
) {
    internal val swipeableState = SwipeableState(
        initialValue = initialValue,
        animationSpec = QuackAnimationSpec(),
        confirmStateChange = confirmStateChange,
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
        get() = swipeableState.currentValue

    /**
     * Whether the state is currently animating.
     */
    public val isAnimationRunning: Boolean
        get() = swipeableState.isAnimationRunning

    /**
     * Open the drawer with animation and suspend until it if fully opened or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the open animation ended
     */
    public suspend fun open(): Unit = animateTo(
        targetValue = QuackDrawerValue.Open,
        anim = QuackAnimationSpec(),
    )

    /**
     * Close the drawer with animation and suspend until it if fully closed or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the close animation ended
     */
    public suspend fun close(): Unit = animateTo(
        targetValue = QuackDrawerValue.Closed,
        anim = QuackAnimationSpec(),
    )

    /**
     * Set the state of the drawer with specific animation
     *
     * @param targetValue The new value to animate to.
     * @param anim The animation that will be used to animate to the new value.
     * @return Unit
     */
    public suspend fun animateTo(
        targetValue: QuackDrawerValue,
        anim: AnimationSpec<Float>,
    ) {
        swipeableState.animateTo(
            targetValue = targetValue,
            anim = anim,
        )
    }

    /**
     * Set the state without any animation and suspend until it's set
     *
     * @param targetValue The new target value
     * @return Unit
     */
    public suspend fun snapTo(
        targetValue: QuackDrawerValue,
    ) {
        swipeableState.snapTo(
            targetValue = targetValue,
        )
    }

    /**
     * 드로어 상태의 대상 값 입니다.
     *
     * Swipe 가 진행 중인 경우 Drawer 에서 Animation 으로 표시 하는 값 입니다.
     * Swipe 마감 Animation 이 실행 중인 경우 이 값이 해당 Animation 의 대상 값 입니다.
     * Swipe 또는 Animation 이 진행 되지 않으면 [currentValue] 와 동일합니다.
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
         *
         * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
         * @return [Saver]
         */
        internal fun saver(
            confirmStateChange: (drawerValue: QuackDrawerValue) -> Boolean,
        ): Saver<QuackDrawerState, QuackDrawerValue> = Saver(
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
    Open,
}
