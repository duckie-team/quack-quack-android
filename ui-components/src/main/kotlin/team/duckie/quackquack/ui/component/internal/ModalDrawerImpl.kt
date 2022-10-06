/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "SameParameterValue",
)
@file:OptIn(
    ExperimentalMaterialApi::class,
)

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
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

@Composable
internal fun QuackModalDrawerImpl(
    drawerState: QuackDrawerState,
    gesturesEnabled: Boolean = true,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
): Unit {
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),// drawer 크기
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
                color = QuackColor.BlackOpacity60.composeColor,
            )
            val absoluteElevation = LocalAbsoluteElevation.current + 0.dp
            CompositionLocalProvider(
                LocalAbsoluteElevation provides absoluteElevation,
            ) {

            }
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

private fun calculateFraction(
    a: Float,
    b: Float,
    pos: Float,
) = ((pos - a) / (b - a)).coerceIn(
    minimumValue = 0f,
    maximumValue = 1f,
)
