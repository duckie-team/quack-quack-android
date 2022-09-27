/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.animation.AnimatedContentTransform
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.constant.NoPadding
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.applyQuackSize
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.textstyle.animateQuackTextStyleAsState

@Stable
private val QuackLargeButtonShape = RoundedCornerShape(
    size = 8.dp,
)

@Stable
private val QuackMediumButtonShape = RoundedCornerShape(
    size = 12.dp,
)

// 2개 스팩이 동일함, 하지만 도메인적 의미를 위해 분리
@Stable
private val QuackSmallButtonShape = QuackLargeButtonShape

@Stable
private val QuackChipShape = RoundedCornerShape(
    size = 18.dp,
)

@Stable
private val QuackLargeButtonTextPadding = PaddingValues(
    vertical = 13.dp,
)

@Stable
private val QuackLargeButton40TextPadding = PaddingValues(
    vertical = 11.dp,
)

@Stable
private val QuackMediumButtonTextPadding = PaddingValues(
    horizontal = 62.dp,
    vertical = 11.dp,
)

@Stable
private val QuackSmallButtonTextPadding = PaddingValues(
    horizontal = 12.dp,
    vertical = 8.dp,
)

@Stable
private val QuackChipTextPadding = PaddingValues(
    horizontal = 8.dp,
    vertical = 4.dp,
)

/**
 * 조건에 맞는 QuackButton 의 배경 색을 계산합니다.
 *
 * @param enabled 현재 버튼이 활성화 상태인지 여부
 * @return 만약 버튼이 활성된 상태라면 [QuackColor.DuckieOrange] 를 반환하고, 그렇지 않다면
 * [QuackColor.Gray2] 를 반환한다.
 */
@Stable
private fun quackButtonStandardBackgroundColorFor(enabled: Boolean) = when (enabled) {
    true -> QuackColor.DuckieOrange
    else -> QuackColor.Gray2
}

/**
 * 덕키의 메인 버튼인 QuackLargeButton 을 구현합니다.
 * QuackLargeButton 은 활성 상태에 따라 다른 배경 색상을
 * 가집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param active 버튼 활성화 여부. 배경 색상에
 * 영향을 미칩니다.
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
@NonRestartableComposable
fun QuackLargeButton(
    text: String,
    active: Boolean = true,
    // TODO: imeAnimation: Boolean = false,
    onClick: () -> Unit,
) = QuackBasicButton(
    // TODO: imeAnimation; modifier = Modifier,
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    text = text,
    textPadding = QuackLargeButtonTextPadding,
    textStyle = QuackTextStyle.Subtitle.change(
        color = QuackColor.White,
    ),
    backgroundColor = quackButtonStandardBackgroundColorFor(
        enabled = active,
    ),
    onClick = onClick,
)

/**
 * QuackLargeButton 의 변형을 구현합니다.
 * [QuackLargeButton] 과 형태는 동일하지만, 차이점은
 * 배경 색상이 흰색으로 고정돼 있습니다. 또한 아이콘을
 * 추가로 설정할 수 있습니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param leadingIcon 버튼 텍스트 왼쪽에 표시될 아이콘.
 * 만약 null 이 들어온 경우 아이콘을 표시하지 않으며,
 * 기본값은 null 입니다.
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
@NonRestartableComposable
fun QuackLargeWhiteButton(
    text: String,
    leadingIcon: QuackIcon? = null,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    leadingIcon = leadingIcon,
    text = text,
    textPadding = QuackLargeButtonTextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

/**
 * QuackLargeButton 의 변형을 구현합니다.
 * [QuackLargeWhiteButton] 과 형태는 동일하지만, 차이점은
 * [QuackLargeWhiteButton] 보다 세로 길이가 약간 작습니다.
 * 또한 아이콘을 설정할 수 없습니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
@NonRestartableComposable
fun QuackLarge40WhiteButton(
    text: String,
    onClick: () -> Unit,
) = QuackBasicButton(
    width = QuackWidth.Fill,
    shape = QuackLargeButtonShape,
    text = text,
    textPadding = QuackLargeButton40TextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = QuackColor.Gray3,
    ),
    onClick = onClick,
)

/**
 * QuackMediumButton 의 변형을 구현합니다.
 * 버튼이 테두리로 강조되고, 토글 형식으로 디자인 됐으므로
 * 컴포넌트 이름에 Border 와 Toggle 을 추가로 붙여 강조했습니다.
 *
 * 버튼의 형태는 QuackMediumButton 와 동일하지만, 이름에서 부터
 * 알 수 있듯이 선택 여부에 따라 테두리 색상과 텍스트 스타일이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param selected 버튼이 선택되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
fun QuackMediumBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    shape = QuackMediumButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = when (selected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Black
        },
        weight = when (selected) {
            true -> FontWeight.Bold
            else -> FontWeight.Normal
        },
    ),
    textPadding = QuackMediumButtonTextPadding,
    backgroundColor = QuackColor.White,
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.DuckieOrange
            else -> QuackColor.Gray3
        },
    ),
    onClick = onClick,
)

/**
 * QuackSmallButton 을 구현합니다.
 * [QuackLargeButton] 과 형태는 동일하며, 유일한 차이점은
 * 사이즈가 아주 작습니다. 역시 활성 상태에 따라 배경 색상이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param enabled 버튼이 활성화 되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
fun QuackSmallButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = QuackColor.White,
    ),
    textPadding = QuackSmallButtonTextPadding,
    backgroundColor = quackButtonStandardBackgroundColorFor(
        enabled = enabled,
    ),
    onClick = onClick,
)

/**
 * QuackSmallButton 의 변형을 구현합니다.
 * [QuackSmallButton] 과 형태는 동일하지만, [QuackMediumBorderToggleButton] 과
 * 마찬가지로 버튼이 테두리로 강조되고, 토글 형식으로 디자인 됐으므로
 * 컴포넌트 이름에 Border 와 Toggle 을 추가로 붙여 강조했습니다.
 *
 * 이 컴포넌트 역시 이름에서 부터 알 수 있듯이
 * 선택 여부에 따라 테두리 색상과 텍스트 스타일이 달라집니다.
 *
 * @param text 버튼에 표시될 텍스트
 * @param selected 버튼이 선택되었는지 여부
 * @param onClick 버튼 클릭 시 호출될 콜백
 */
@Composable
fun QuackSmallBorderToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    shape = QuackSmallButtonShape,
    text = text,
    textStyle = QuackTextStyle.Body1.change(
        color = when (selected) {
            true -> QuackColor.Gray2
            else -> QuackColor.DuckieOrange
        },
    ),
    textPadding = QuackSmallButtonTextPadding,
    backgroundColor = when (selected) {
        true -> QuackColor.Gray3
        else -> QuackColor.White
    },
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.Gray3
            else -> QuackColor.DuckieOrange
        },
    ),
    onClick = onClick,
)

/**
 * QuackToggleChip 을 구현합니다.
 * Button 의 역할보다는 Chip 의 역할이 더 크기에
 * Button 대신 Chip 네이밍을 사용했습니다.
 *
 * 선택 여부에 따라 텍스트 스타일과 배경 색상 그리고
 * 테두리 색상이 달라집니다.
 *
 * @param text chip 에 표시될 텍스트
 * @param selected chip 이 선택되었는지 여부
 * @param onClick chip 클릭 시 호출될 콜백
 */
@Composable
fun QuackToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) = QuackBasicButton(
    shape = QuackChipShape,
    text = text,
    textStyle = QuackTextStyle.Body2.change(
        color = when (selected) {
            true -> QuackColor.Gray2
            else -> QuackColor.DuckieOrange
        },
        weight = FontWeight.Medium,
    ),
    textPadding = QuackChipTextPadding,
    backgroundColor = when (selected) {
        true -> QuackColor.Gray3
        else -> QuackColor.White
    },
    border = QuackBorder(
        color = when (selected) {
            true -> QuackColor.Gray3
            else -> QuackColor.DuckieOrange
        },
    ),
    onClick = onClick,
)

/**
 * QuackButton 컴포넌트를 구성하는데 사용되는 Button 의 최하위 컴포넌트 입니다
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param width 버튼의 가로 길이. [Modifier] 을 그대로 받는게 아닌
 * 필요한 사이즈 정보만 받기 위해 [QuackWidth] 를 통해 값을 받습니다.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackWidth.Wrap] 입니다.
 * @param height 버튼의 세로 길이. [Modifier] 을 그대로 받는게 아닌
 * 필요한 사이즈 정보만 받기 위해 [QuackHeight] 를 통해 값을 받습니다.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackHeight.Wrap] 입니다.
 * @param elevation 버튼의 그림자 강도. 기본 값은 0 입니다.
 * 즉, 그림자가 표시되지 않습니다.
 * @param shape 버튼의 모양. 기본값은 [RectangleShape] 입니다.
 * @param leadingIcon 버튼의 텍스트 왼쪽에 표시할 아이콘.
 * null 이 들어오면 아이콘을 표시하지 않으며, 기본값은 null 입니다.
 * @param text 버튼에 표시될 텍스트
 * @param textStyle 버튼의 텍스트 스타일.
 * 덕키 디자인 시스템의 버튼 컴포넌트 경우는 기본값이 [QuackTextStyle.Subtitle] 입니다.
 * @param textPadding 버튼의 텍스트에 적용될 [PaddingValues].
 * 덕키 디자인 시스템의 경우 컴포넌트의 사이즈를 정적으로 고정시키지 않고,
 * 컴포넌트 안에 들어가는 요소의 사이즈에 따라 동적으로 사이즈를 결정합니다.
 * QuackButton 의 경우에는 안에 들어가는 요소가 텍스트가 됩니다.
 * 즉, 이 패딩 값에 따라 QuackButton 의 텍스트 컴포넌트 사이즈가 조정되므로
 * QuackButton 의 사이즈를 결정짓는 중요한 인자가 됩니다.
 * 기본값은 [NoPadding] 입니다. 즉, 텍스트 컴포넌트의 사이즈를 늘리지 않습니다.
 * @param backgroundColor 버튼의 배경 색상
 * @param border 버튼에 적용할 테두리 옵션.
 * null 이 들어오면 테두리를 표시하지 않으며, 기본값은 null 입니다.
 * @param rippleEnabled 버튼을 눌렀을 때, 버튼의 배경에 리플 효과를 표시할지 여부.
 * 기본값은 true 입니다.
 * @param rippleColor 버튼이 눌렸을 때, 버튼의 배경에 표시할 리플 효과의 색상.
 * [rippleEnabled] 가 true 일 때만 작용합니다. 기본값은 색상을 지정하지 않은걸 뜻하는
 * [QuackColor.Unspecified] 입니다.
 * @param onClick 버튼을 눌렀을 때 호출될 콜백 함수
 *
 * @see Modifier.applyQuackSize
 */
@Composable
private fun QuackBasicButton(
    modifier: Modifier = Modifier,
    width: QuackWidth = QuackWidth.Wrap,
    height: QuackHeight = QuackHeight.Wrap,
    elevation: Dp = 0.dp,
    shape: Shape = RectangleShape,
    leadingIcon: QuackIcon? = null,
    text: String,
    textStyle: QuackTextStyle = QuackTextStyle.Subtitle,
    textPadding: PaddingValues = NoPadding,
    backgroundColor: QuackColor,
    border: QuackBorder? = null,
    rippleEnabled: Boolean = true,
    rippleColor: QuackColor = QuackColor.Unspecified,
    onClick: () -> Unit,
) {
    val textStyleAnimation by animateQuackTextStyleAsState(
        targetValue = textStyle,
    )
    QuackSurface(
        modifier = Modifier
            .applyQuackSize(
                width = width,
                height = height,
            )
            .then(
                other = modifier,
            ),
        backgroundColor = backgroundColor,
        border = border,
        elevation = elevation,
        shape = shape,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AnimatedContentTransform(
                // animated visibility 대응
                targetState = leadingIcon,
            ) { animatedLeadingIcon ->
                InternalQuackImage(
                    icon = animatedLeadingIcon,
                    tint = QuackColor.Gray1,
                )
            }
            // 아이콘 - 텍스트 사이 간격 없음
            QuackText(
                modifier = Modifier.padding(
                    paddingValues = textPadding,
                ),
                text = text,
                style = textStyleAnimation,
            )
        }
    }
}
