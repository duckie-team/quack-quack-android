/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.floor
import team.duckie.quackquack.ui.animation.quackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon

private const val TransitionLabel = "CheckTransition"

private const val BoxOutDuration = 100

private val RoundCheckboxSize = 28.dp
private val SquareCheckboxSize = 24.dp
private val SmallIconSize = 18.dp
private val StrokeWidth = 2.dp

private val QuackIconTextToggleSpacing = 4.dp

private const val CheckCrossX = 0.4f
private const val CheckCrossY = 0.7f
private const val LeftX = 0.25f
private const val LeftY = 0.55f
private const val RightX = 0.75f
private const val RightY = 0.35f
private const val StopLocation = 0.5f

private const val RoundCheckBoxAlpha = 0.2f
private val CheckColor = QuackColor.White

private val QuackRoundCheckShape = CircleShape
private val QuackRectangleCheckShape = RoundedCornerShape(
    size = 4.dp,
)

/**
 * 덕키의 원형 CheckBox 입니다.
 *
 * @param checked 체크되었는지 여부
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
public fun QuackRoundCheckBox(
    checked: Boolean,
    onToggle: () -> Unit,
): Unit = QuackSurface(
    modifier = Modifier.size(
        size = RoundCheckboxSize,
    ),
    shape = QuackRoundCheckShape,
    backgroundColor = getCheckBoxBackgroundColor(
        isChecked = checked,
        uncheckedColor = QuackColor.Black.changeAlpha(
            alpha = RoundCheckBoxAlpha,
        ),
    ),
    onClick = onToggle,
    rippleEnabled = false,
) {
    Check(
        value = ToggleableState(
            value = checked,
        ),
    )
}

/**
 * 덕키의 모서리가 둥근 사각형 CheckBox 입니다.
 *
 * @param checked 체크되었는지 여부
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
public fun QuackSquareCheckBox(
    checked: Boolean,
    onToggle: () -> Unit,
): Unit = QuackSurface(
    modifier = Modifier.size(
        size = SquareCheckboxSize,
    ),
    shape = QuackRectangleCheckShape,
    backgroundColor = getCheckBoxBackgroundColor(
        isChecked = checked,
        uncheckedColor = QuackColor.Gray3,
    ),
    onClick = onToggle,
    rippleEnabled = false,
) {
    Check(
        value = ToggleableState(
            value = checked,
        ),
    )
}

/**
 * 덕키의 IconTextToggle 입니다.
 *
 * [checked] 에 따라 보여지는 아이콘이 달라집니다.
 * [checkedIcon] 이 null 이면 [uncheckedIcon] 으로만 적용됩니다.
 *
 * @param checkedIcon 체크되었을 때 보여지는 [QuackIcon],
 * @param uncheckedIcon 체크가 해제되었을 때 보여지는 [QuackIcon]
 * @param checked 체크되었는지 여부
 * @param text 아이콘 오른쪽에 표시될 Text
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
public fun QuackIconTextToggle(
    checkedIcon: QuackIcon?,
    uncheckedIcon: QuackIcon,
    checked: Boolean,
    text: String,
    onToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = QuackIconTextToggleSpacing,
        ),
    ) {
        QuackBasicIconToggle(
            checkedIcon = checkedIcon,
            uncheckedIcon = uncheckedIcon,
            checked = checked,
            onToggle = onToggle,
        )
        QuackBody2(
            text = text,
        )
    }
}

/**
 * 덕키의 ToggleButton 입니다.
 *
 * [checked] 에 따라 보여지는 아이콘이 달라집니다.
 *
 * @param checkedIcon 체크되었을 때 보여지는 [QuackIcon], null 일 경우 [unCheckedIcon] 으로만 적용
 * @param unCheckedIcon 체크가 해제되었을 때 보여지는 [QuackIcon]
 * @param checked 체크되었는지 여부
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
public fun QuackIconToggle(
    checkedIcon: QuackIcon?,
    unCheckedIcon: QuackIcon,
    checked: Boolean,
    onToggle: () -> Unit,
): Unit = QuackBasicIconToggle(
    checkedIcon = checkedIcon,
    uncheckedIcon = unCheckedIcon,
    checked = checked,
    onToggle = onToggle,
)

/**
 * 덕키의 Basic ToggleButton 입니다.
 *
 * [checked] 에 따라 보여지는 아이콘이 달라집니다.
 *
 * @param checkedIcon 체크되었을 때 보여지는 [QuackIcon] , null 일 경우 [uncheckedIcon] 으로만 적용
 * @param uncheckedIcon 체크가 해제되었을 때 보여지는 [QuackIcon]
 * @param checked 체크되었는지 여부
 * @param onToggle 체크시 호출되는 콜백
 */
@Composable
private fun QuackBasicIconToggle(
    checkedIcon: QuackIcon?,
    uncheckedIcon: QuackIcon,
    checked: Boolean,
    onToggle: () -> Unit,
) = QuackImage(
    overrideSize = DpSize(
        width = SmallIconSize,
        height = SmallIconSize,
    ),
    src = when (checkedIcon != null && checked) {
        true -> checkedIcon
        else -> uncheckedIcon
    },
    rippleEnabled = false,
    onClick = onToggle,
)

/**
 * 체크 여부에 따라 CheckBox 의 배경색을 지정합니다.
 *
 * @param isChecked 체크 여부
 * @param uncheckedColor 체크되지 않았을 경우 배경색
 * @return CheckBox 의 배경색
 */
@Stable
private fun getCheckBoxBackgroundColor(
    isChecked: Boolean,
    uncheckedColor: QuackColor,
) = when (isChecked) {
    true -> QuackColor.DuckieOrange
    else -> uncheckedColor
}

/**
 * [Canvas] 에 Check 모양을 그립니다.
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
            quackAnimationSpec()
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
                    delayMillis = BoxOutDuration,
                )

                else -> quackAnimationSpec()
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
            checkColor = checkColor.composeColor,
            checkFraction = checkDrawFraction,
            crossCenterGravitation = checkCenterGravitationShiftFraction,
            strokeWidthPx = strokeWidthPx,
            drawingCache = checkCache,
        )
    }
}

@Immutable
private class CheckDrawingCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        other as CheckDrawingCache

        if (checkPath != other.checkPath) return false
        if (pathMeasure != other.pathMeasure) return false
        if (pathToDraw != other.pathToDraw) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkPath.hashCode()
        result = 31 * result + pathMeasure.hashCode()
        result = 31 * result + pathToDraw.hashCode()
        return result
    }
}

/**
 * [start], [stop] 사이의 임의의 지점 f(p) 를 반환하는 함수이다.
 *
 * [fraction] 은 임의의 점 p 까지의 거리를 의미한다.
 *
 * @param start 시작 지점
 * @param stop 도착 지점
 * @param fraction 임의의 점 p 까지의 거리
 *
 * @return [start], [stop] 사이의 임의의 지점 f(p)
 */
@Suppress("SameParameterValue")
private fun linearInterpolation(
    start: Float,
    stop: Float,
    fraction: Float,
) = (1 - fraction) * start + fraction * stop

/**
 * 체크 표시를 그리기 위한 메서드
 * [crossCenterGravitation] 의 값이 0f -> 1f -> 0f 이므로,
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
        cap = StrokeCap.Round,
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
        fraction = crossCenterGravitation,
    )

    val gravitatedLeftY = linearInterpolation(
        start = LeftY,
        stop = StopLocation,
        fraction = crossCenterGravitation,
    )
    val gravitatedRightY = linearInterpolation(
        start = RightY,
        stop = StopLocation,
        fraction = crossCenterGravitation,
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
            y = width * gravitatedRightY,
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
