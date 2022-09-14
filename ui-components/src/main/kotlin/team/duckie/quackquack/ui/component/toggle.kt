/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.runtime.Composable
import team.duckie.quackquack.ui.icon.QuackIcon

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
