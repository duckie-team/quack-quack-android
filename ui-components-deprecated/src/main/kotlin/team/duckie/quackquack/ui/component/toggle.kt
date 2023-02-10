/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import team.duckie.quackquack.ui.animation.QuackAnimatedContent
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackToggleIconSize.Compact
import team.duckie.quackquack.ui.component.QuackToggleIconSize.Normal
import team.duckie.quackquack.ui.component.QuackToggleIconSize.Small
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize

/**
 * [QuackToggleButton] 에서 표시할 아이콘의 사이즈를 정의합니다.
 *
 * @property Normal 보통 사이즈로 표시합니다. (24 dp)
 * @property Small 약간 축소된 사이즈로 표시합니다. (18 dp)
 * @property Compact 많이 축소된 사이즈로 표시합니다. (14 dp)
 */
public enum class QuackToggleIconSize(
    public val size: DpSize,
) {
    Normal(
        size = DpSize(
            all = 24.dp,
        ),
    ),
    Small(
        size = DpSize(
            all = 18.dp,
        ),
    ),
    Compact(
        size = DpSize(
            all = 14.dp,
        ),
    )
}

/**
 * QuackToggle 을 그리는데 필요한 리소스를 구성합니다.
 */
private object QuackToggleDefaults {
    // Copied from AOSP
    object DrawConstaints {
        const val TransitionLabel = "CheckTransition"
        const val BoxOutDuration = 100
        const val StopLocation = 0.5f

        val StrokeWidth = 1.5.dp
        const val CheckCrossX = 0.4f
        const val CheckCrossY = 0.7f
        const val LeftX = 0.25f
        const val LeftY = 0.55f
        const val RightX = 0.75f
        const val RightY = 0.35f
    }

    object RoundCheck {
        /**
         * 주어진 상황에 맞는 테두리를 계산합니다.
         *
         * @param isChecked 현재 선택된 상태인지 여부
         *
         * @return [isChecked] 여부에 따른 [QuackBorder]
         */
        @Stable
        fun borderFor(
            isChecked: Boolean,
        ) = QuackBorder(
            color = when (isChecked) {
                true -> QuackColor.DuckieOrange
                else -> QuackColor.White
            },
        )

        /**
         * 주어진 상황에 맞는 배경 색상을 계산합니다.
         *
         * @param isChecked 현재 선택된 상태인지 여부
         *
         * @return [isChecked] 여부에 따른 [QuackColor]
         */
        @Stable
        fun backgroundColorFor(
            isChecked: Boolean,
        ) = when (isChecked) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Black.change(
                alpha = 0.2f,
            )
        }

        val ContainerSize = DpSize(
            all = 24.dp,
        )
        val ContainerShape = CircleShape

        val CheckColor = QuackColor.White
        val CheckSize = DpSize(
            all = 18.dp,
        )
    }

    object SquareCheck {
        /**
         * 주어진 상황에 맞는 배경 색상을 계산합니다.
         *
         * @param isChecked 현재 선택된 상태인지 여부
         *
         * @return [isChecked] 여부에 따른 [QuackColor]
         */
        @Stable
        fun backgroundColorFor(
            isChecked: Boolean,
        ) = when (isChecked) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Gray3
        }

        val ContainerSize = DpSize(
            all = 24.dp,
        )
        val ContainerShape = RoundedCornerShape(
            size = 4.dp,
        )

        val CheckColor = QuackColor.White
        val CheckSize = DpSize(
            all = 18.dp,
        )
    }

    object ToggleButton {
        val IconSize = Small
        val Typography = QuackTextStyle.Body2.change(
            color = QuackColor.Gray1
        )

        /**
         * ```
         * [Icon][Typography]
         * ```
         *
         * 에서 `Icon` 과 `Typography` 간의 사이 간격을 나타냅니다.
         */
        val ItemSpacedBy = 4.dp
    }
}

/**
 * 덕키의 원형 CheckBox 를 구현합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param checked 체크되었는지 여부
 * @param onClick 체크시 호출되는 콜백
 */
@Composable
public fun QuackRoundCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackToggleDefaults.RoundCheck,
) {
    QuackSurface(
        modifier = modifier.size(
            size = ContainerSize,
        ),
        shape = ContainerShape,
        backgroundColor = backgroundColorFor(
            isChecked = checked,
        ),
        border = borderFor(
            isChecked = checked,
        ),
        onClick = onClick,
    ) {
        Check(
            value = ToggleableState(
                value = checked,
            ),
            checkColor = CheckColor,
            size = CheckSize,
        )
    }
}

/**
 * 덕키의 원형 CheckBox 를 구현합니다.
 * [QuackRoundCheckBox] 보다 작습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param checked 체크되었는지 여부
 * @param checkedText 체크시 하단에 표시할 텍스트
 * @param onClick 체크시 호출되는 콜백
 */
@Composable
public fun QuackSmallRoundCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    checkedText: String,
    onClick: (() -> Unit)? = null,
): Unit = with(QuackToggleDefaults.RoundCheck) {
    QuackAnimatedContent(targetState = checked) { showUnderText ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            QuackSurface(
                modifier = modifier.size(DpSize(18.dp)),
                shape = ContainerShape,
                backgroundColor = backgroundColorFor(
                    isChecked = checked,
                ),
                border = borderFor(
                    isChecked = checked,
                ),
                onClick = onClick,
            ) {
                Check(
                    value = ToggleableState(
                        value = checked,
                    ),
                    checkColor = CheckColor,
                    size = DpSize(12.dp),
                )
            }
            if (showUnderText) {
                QuackBody3(
                    modifier = Modifier.padding(top = 2.dp),
                    text = checkedText,
                    color = QuackColor.DuckieOrange,
                )
            }
        }
    }
}

/**
 * 덕키의 사각형 CheckBox 를 구현합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param checked 체크되었는지 여부
 * @param onClick 체크시 호출되는 콜백
 */
@Composable
public fun QuackSquareCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackToggleDefaults.SquareCheck,
) {
    QuackSurface(
        modifier = modifier.size(
            size = ContainerSize,
        ),
        shape = ContainerShape,
        backgroundColor = backgroundColorFor(
            isChecked = checked,
        ),
        onClick = onClick,
    ) {
        Check(
            value = ToggleableState(
                value = checked,
            ),
            checkColor = CheckColor,
            size = CheckSize,
        )
    }
}

/**
 * Checked 여부에 따라 조건에 맞는 아이콘을 표시합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param checkedIcon 체크 상태에서 표시할 아이콘
 * @param uncheckedIcon 체크 상태가 아닐 때 표시할 아이콘
 * @param iconSize 아이콘을 표시할 크기.
 * 기본값은 [QuackToggleIconSize.Normal] 이며, [trailingText] 이 존재할 때는
 * [QuackToggleIconSize.Small] 로 강제됩니다.
 * @param checked 현재 체크 상태에 있는지 여부
 * @param trailingText trailing content 로 배치될 텍스트.
 * 만약 null 이 입력될 시 trailing content 를 배치하지 않습니다.
 * @param onClick 아이콘을 클릭했을 때 실행될 람다
 */
@Composable
public fun QuackToggleButton(
    modifier: Modifier = Modifier,
    checkedIcon: QuackIcon,
    uncheckedIcon: QuackIcon,
    iconSize: QuackToggleIconSize = Normal,
    checked: Boolean,
    trailingText: String? = null,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackToggleDefaults.ToggleButton,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = ItemSpacedBy,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackImage(
            src = when (checked) {
                true -> checkedIcon
                else -> uncheckedIcon
            },
            size = (iconSize.takeIf { trailingText == null } ?: IconSize).size,
            rippleEnabled = false,
            onClick = onClick,
        )
        trailingText?.let {
            QuackText(
                text = trailingText,
                style = Typography,
                singleLine = true,
            )
        }
    }
}

/**
 * [Canvas] 에 Check 모양을 그립니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
 *
 * @param value 현재 토글 상태를 의미하는 [ToggleableState]
 * @param checkColor Check 색상
 * @param size Check 의 크기
 */
@Composable
private fun Check(
    value: ToggleableState,
    checkColor: QuackColor,
    size: DpSize,
): Unit = with(
    receiver = QuackToggleDefaults.DrawConstaints,
) {
    val transition = updateTransition(
        targetState = value,
        label = TransitionLabel,
    )
    val checkDrawFraction by transition.animateFloat(
        transitionSpec = {
            QuackAnimationSpec()
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
                else -> QuackAnimationSpec()
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
                size = size,
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

// Copied from AOSP
// TODO: documentation
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
 * [start], [stop] 사이의 임의의 지점 f(p) 를 반환합니다.
 * [fraction] 은 임의의 점 p 까지의 거리를 의미합니다.
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
 * [DrawScope] 에 체크 표시를 그립니다.
 * [crossCenterGravitation] 의 값이 0f -> 1f -> 0f 이므로,
 * 중심지점일수록 그려지는 속도가 빨라집니다.
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
): Unit = with(
    receiver = QuackToggleDefaults.DrawConstaints,
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

    with(
        receiver = drawingCache,
    ) {
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
