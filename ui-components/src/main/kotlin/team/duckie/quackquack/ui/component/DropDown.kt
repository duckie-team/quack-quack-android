/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.ui.border.QuackBorder
import team.duckie.quackquack.ui.border.applyAnimatedQuackBorder
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle
import team.duckie.quackquack.ui.util.DpSize
import team.duckie.quackquack.ui.util.runIf

/**
 * QuackDropDown 을 그리는데 필요한 리소스를 정의합니다.
 */
private object QuackDropDownDefaults {
    val TextPadding = PaddingValues(
        top = 8.dp,
        bottom = 8.dp,
        start = 12.dp,
        end = 4.dp,
    )
    val IconPadding = PaddingValues(
        end = 8.dp,
    )

    val Border = QuackBorder(
        color = QuackColor.Gray3,
    )
    val Shape = RoundedCornerShape(
        size = 8.dp,
    )
    val BackgroundColor = QuackColor.White
    val Typography = QuackTextStyle.Body1

    val IconSize = DpSize(
        16.dp,
    )
    val IconResource = QuackIcon.ArrowDown
    val IconTint = QuackColor.Gray1
}

/**
 * 덕키에서 Drop Down 을 표시하는 컴포넌트를 구현합니다.
 * [QuackDropDownCard] 는 다음과 같은 특징을 갖습니다.
 *
 * - 항상 trailing content 로 [QuackIcon.ArrowDown] 을 갖습니다.
 * - 단순 rounding 형태의 Card 를 갖기 때문에 이름에 Card 가 추가됐습니다.
 *
 * @param modifier 이 컴포넌트에 적용할 [Modifier]
 * @param text 표시할 텍스트
 * @param showBorder 테두리를 표시할지 여부
 * @param onClick 클릭했을 때 호출될 람다
 */
@Composable
public fun QuackDropDownCard(
    modifier: Modifier = Modifier,
    text: String,
    showBorder: Boolean = true,
    onClick: (() -> Unit)? = null,
): Unit = with(
    receiver = QuackDropDownDefaults,
) {
    Row(
        modifier = modifier
            .clip(
                shape = Shape,
            )
            .quackClickable(
                onClick = onClick,
            )
            .background(
                color = BackgroundColor.composeColor,
                shape = Shape,
            )
            .runIf(showBorder) {
                applyAnimatedQuackBorder(
                    border = Border,
                    shape = Shape,
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        QuackText(
            modifier = Modifier.padding(
                paddingValues = TextPadding,
            ),
            text = text,
            style = Typography,
            singleLine = true,
        )
        QuackImage(
            modifier = Modifier.padding(
                paddingValues = IconPadding,
            ),
            src = IconResource,
            size = IconSize,
            tint = IconTint,
        )
    }
}
