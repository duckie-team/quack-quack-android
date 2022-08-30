/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [icon.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:46
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable

@Composable
internal fun QuackSimpleIcon(
    onClick: () -> Unit = {},
    icon: QuackIcon,
    tint: QuackColor = QuackColor.Black,
) {
    Image(
        modifier = Modifier.quackClickable(
            onClick = onClick,
        ),
        painter = painterResource(icon.drawableId),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint.value),
    )
}
