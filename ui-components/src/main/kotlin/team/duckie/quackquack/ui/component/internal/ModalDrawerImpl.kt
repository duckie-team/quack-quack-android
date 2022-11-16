/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlinx.coroutines.launch
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackDrawerState
import team.duckie.quackquack.ui.component.QuackDrawerValue
import team.duckie.quackquack.ui.component.QuackSurface
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.util.runIf

private val QuackDrawerVelocityThreshold = 400.dp
private val QuackEndDrawerPadding = 76.dp

private const val QuackThresholdFraction = 0.5f

private const val QuackDrawerExceptionMessage = "Drawer shouldn't have infinite width"

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
@ExperimentalMaterialApi
@Composable
internal fun QuackModalDrawerImpl(
    drawerState: QuackDrawerState,
    gesturesEnabled: Boolean = true,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val modalDrawerConstraints = constraints
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException(
                QuackDrawerExceptionMessage
            )
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
                        fraction = QuackThresholdFraction,
                    )
                },
                orientation = Orientation.Horizontal,
                enabled = gesturesEnabled,
                velocityThreshold = QuackDrawerVelocityThreshold,
                resistance = null,
            ),
        ) {
            Box {
                content()
            }
            Scrim(
                open = drawerState.isOpen,
                onClose = {
                    if (
                        gesturesEnabled &&
                        drawerState.confirmStateChange(QuackDrawerValue.Closed)
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
                        pos = drawerState.offset.value
                    )
                },
                color = QuackColor.Black.composeColor,
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
                            end = QuackEndDrawerPadding,
                        )
                        .quackClickable(
                            rippleEnabled = false,
                            onClick = {},
                        )
                },
                backgroundColor = QuackColor.White,
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
    color: Color,
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
            color = color,
            alpha = fraction(),
        )
    }
}

/**
 * 화면의 어느 포인트에 위치하고 있는지에 대한 Fraction
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
