/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

/**
 * QuackLabel 의 리소스 모음
 */
private object QuackLabelDefaults {
    val Padding = PaddingValues(
        horizontal = 6.dp,
        vertical = 4.dp,
    )

    /**
     * 조건에 맞는 [QuackTextStyle] 을 계산합니다.
     *
     * @param isActive 현재 활성화 됐는지 여부
     *
     * @return [isActive] 여부에 따라 적용할 [QuackTextStyle]
     */
    @Stable
    fun typographyFor(
        isActive: Boolean,
    ) = QuackTextStyle(
        // Body3 에서 FontWeight 만 바뀜
        size = 10.sp,
        weight = FontWeight.Bold,
        letterSpacing = 0.sp,
        lineHeight = 13.sp,
        color = when (isActive) {
            true -> QuackColor.White
            else -> QuackColor.Gray1
        }
    )

    /**
     * 조건에 맞는 배경 색상을 계산합니다.
     *
     * @param isActive 현재 활성화 됐는지 여부
     *
     * @return [isActive] 여부에 따라 적용할 배경 색상
     */
    @Stable
    fun backgroundColorFor(
        isActive: Boolean,
    ) = when (isActive) {
        true -> QuackColor(
            // 디자인 가이드에 없는 색상
            composeColor = Color(
                color = 0xFF6DBBFF,
            ),
        )
        else -> QuackColor.Gray3
    }

    val Shape = RoundedCornerShape(
        size = 11.dp,
    )
}

/**
 * 간단히 레이블을 보여주는 컴포넌트를 구현합니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 레이블에 표시할 텍스트
 * @param active 레이블이 활성화 됐는지 여부
 * @param onClick 레이블이 클릭됐을 때 실행될 람다
 */
// TODO: 애니메이션이 살짝 부자연스러운 느낌?
@Composable
public fun QuackLabel(
    modifier: Modifier = Modifier,
    text: String,
    active: Boolean,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackLabelDefaults,
) {
    QuackSurface(
        modifier = modifier,
        shape = Shape,
        backgroundColor = backgroundColorFor(
            isActive = active,
        ),
        onClick = onClick,
    ) {
        QuackText(
            modifier = Modifier.padding(
                paddingValues = Padding,
            ),
            text = text,
            style = typographyFor(
                isActive = active,
            ),
        )
    }
}
