/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
)
@file:OptIn(
    ExperimentalMaterialApi::class,
)

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CancellationException
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.component.internal.QuackModalDrawerImpl

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
    internal val confirmStateChange: (QuackDrawerValue) -> Boolean = { true },
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
