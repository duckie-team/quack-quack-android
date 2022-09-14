/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import kotlin.math.floor
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon

private const val TransitionLabel = "checkTransition"

private const val BoxOutDuration = 100
private const val CheckAnimationDuration = 100

private val RoundCheckboxSize = 28.dp
private val SquareCheckboxSize = 24.dp
private val StrokeWidth = 2.dp

const val CheckCrossX = 0.4f
const val CheckCrossY = 0.7f
const val LeftX = 0.25f
const val LeftY = 0.55f
const val RightX = 0.75f
const val RightY = 0.35f
const val StopLocation = 0.5f

private val CheckColor = QuackColor.White

/**
 * 덕키의 Toggle Button 입니다.
 *
 * Check 여부에 따라 보여지는 아이콘이 달라집니다.
 *
 * @param checkedIcon 체크되었을 때 보여지는 [QuackIcon]
 * @param unCheckedIcon 체크가 해제되었을 때 보여지는 [QuackIcon]
 * @param checked 체크되었는지 여부
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
fun QuackIconToggle(
    checkedIcon: QuackIcon,
    unCheckedIcon: QuackIcon,
    checked: Boolean,
    onToggle: () -> Unit,
) {
    QuackImage(
        icon = when (checked) {
            true -> checkedIcon
            else -> unCheckedIcon
        },
        rippleEnabled = false,
        onClick = onToggle,
    )
}

/**
 * [Canvas]에 Check 모양을 그립니다.
 *
 * @param value 현재 토글 상태를 의미하는 [ToggleableState]
 * @param checkColor Check 색상
 */
@Composable
private fun Check(
    value: ToggleableState,
    checkColor: QuackColor = CheckColor,
) {
    val transition = updateTransition(
        targetState = value,
        label = TransitionLabel,
    )
    val checkDrawFraction by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = CheckAnimationDuration
            )
        },
        label = TransitionLabel,
    ) { toggleableState ->
        when (toggleableState) {
            ToggleableState.On -> 1f
            ToggleableState.Off -> 0f
            ToggleableState.Indeterminate -> 1f
        }
    }
    val checkCenterGravitationShiftFraction by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == ToggleableState.Off -> snap()
                targetState == ToggleableState.Off -> snap(
                    delayMillis = BoxOutDuration
                )
                else -> tween(
                    durationMillis = CheckAnimationDuration
                )
            }
        },
        label = TransitionLabel,
    ) { toggleableState ->
        when (toggleableState) {
            ToggleableState.On -> 0f
            ToggleableState.Off -> 0f
            ToggleableState.Indeterminate -> 1f
        }
    }
    val checkCache = remember {
        CheckDrawingCache()
    }
    Canvas(
        modifier = Modifier
            .wrapContentSize(
                align = Alignment.Center,
            )
            .requiredSize(
                size = SquareCheckboxSize,
            ),
    ) {
        val strokeWidthPx = floor(
            x = StrokeWidth.toPx(),
        )
        drawCheck(
            checkColor = checkColor.value,
            checkFraction = checkDrawFraction,
            crossCenterGravitation = checkCenterGravitationShiftFraction,
            strokeWidthPx = strokeWidthPx,
            drawingCache = checkCache
        )
    }
}

@Immutable
private class CheckDrawingCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path(),
)

/**
 * [start], [stop] 사이의 임의의 지점 f(p) 를 반환하는 함수이다.
 *
 * [fraction]은 임의의 점 p 까지의 거리를 의미한다.
 *
 * @param start 시작 지점
 * @param stop 도착 지점
 * @param fraction 임의의 점 p 까지의 거리
 * @return Float
 */
private fun linearInterpolation(
    start: Float,
    stop: Float,
    fraction: Float,
): Float {
    return (1 - fraction) * start + fraction * stop
}

/**
 * 체크 표시를 그리기 위한 메서드
 * [crossCenterGravitation]의 값이 0f -> 1f -> 0f 이므로,
 * 중심지점일수록 그려지는 속도가 빠르다.
 *
 * @param checkColor 체크 표시의 색상
 * @param checkFraction 체크 표시가 끝나는 지점까지의 거리
 * @param crossCenterGravitation 중심지점의 중력
 * @param strokeWidthPx 선의 굵기
 * @param drawingCache 그려질 선의 경로를 저장하는 캐시
 */
@Stable
private fun DrawScope.drawCheck(
    checkColor: Color,
    checkFraction: Float,
    crossCenterGravitation: Float,
    strokeWidthPx: Float,
    drawingCache: CheckDrawingCache,
) {
    val stroke = Stroke(
        width = strokeWidthPx,
        cap = StrokeCap.Square,
    )
    val width = size.width

    val gravitatedCrossX = linearInterpolation(
        start = CheckCrossX,
        stop = StopLocation,
        fraction = crossCenterGravitation,
    )
    val gravitatedCrossY = linearInterpolation(
        start = CheckCrossY,
        stop = StopLocation,
        fraction = crossCenterGravitation
    )

    val gravitatedLeftY = linearInterpolation(
        start = LeftY,
        stop = StopLocation,
        fraction = crossCenterGravitation
    )
    val gravitatedRightY = linearInterpolation(
        start = RightY,
        stop = StopLocation,
        fraction = crossCenterGravitation
    )

    with(drawingCache) {
        checkPath.reset()
        checkPath.moveTo(
            x = width * LeftX,
            y = width * gravitatedLeftY,
        )
        checkPath.lineTo(
            x = width * gravitatedCrossX,
            y = width * gravitatedCrossY,
        )
        checkPath.lineTo(
            x = width * RightX,
            y = width * gravitatedRightY
        )
        pathMeasure.setPath(
            path = checkPath,
            forceClosed = false,
        )
        pathToDraw.reset()
        pathMeasure.getSegment(
            startDistance = 0f,
            stopDistance = pathMeasure.length * checkFraction,
            destination = pathToDraw,
            startWithMoveTo = true,
        )
    }
    drawPath(
        path = drawingCache.pathToDraw,
        color = checkColor,
        style = stroke,
    )
}
