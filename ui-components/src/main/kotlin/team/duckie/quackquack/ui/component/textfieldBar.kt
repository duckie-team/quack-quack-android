/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [textfieldbar.kt] created by EvergreenTree97 on 22. 9. 1. 오후 3:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.constant.QuackTextFieldDefaults
import team.duckie.quackquack.ui.icon.QuackIcon

/**
 * 컨테이너의 양 쪽 끝에 아이콘을 삽입할 수 있고,
 * 가운데 입력 가능한 TextField를 제공하는 Bar 입니다.
 *
 * @param text TextField 에 표시할 input [String]
 * @param onTextChanged Text가 업데이트 될 때 마다 트리거 되는 콜백,
 * 업데이트 된 텍스트가 콜백의 매개변수로 들어옴
 * @param leadingIcon Bar의 앞 부분에 표시할 아이콘
 * @param trailingIcon Bar의 끝에 표시할 아이콘
 * @param onLeadingClick [leadingIcon]을 클릭했을 때 발생하는 이벤트
 * @param onTrailingClick [onTrailingClick]을 클릭했을 때 발생하는 이벤트
 */
@Composable
fun DoubleIconTextFieldBar(
    text: String,
    onTextChanged: (text: String) -> Unit,
    leadingIcon: QuackIcon,
    trailingIcon: QuackIcon,
    onLeadingClick: () -> Unit,
    onTrailingClick: () -> Unit,
) {
    QuackBasicTextField(
        text = text,
        onTextChanged = onTextChanged,
        leading = {
            QuackSimpleIcon(
                icon = leadingIcon,
                onClick = onLeadingClick,
            )
        },
        trailing = {
            QuackSimpleIcon(
                icon = trailingIcon,
                tint = QuackTextFieldDefaults.textFieldColors().trailingIconColor(
                    isFocused = text.isNotEmpty(),
                ),
                onClick = onTrailingClick,
            )
        },
        margin = PaddingValues(
            horizontal = QuackTextFieldDefaults.iconHorizontalPadding,
            vertical = QuackTextFieldDefaults.iconVerticalPadding,
        ),
    )
}
