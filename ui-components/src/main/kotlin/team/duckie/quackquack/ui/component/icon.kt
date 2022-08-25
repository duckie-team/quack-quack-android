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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import team.duckie.quackquack.ui.R
import team.duckie.quackquack.ui.color.QuackColor

@Immutable
@JvmInline
value class QuackIcon private constructor(
    val value: Int,
) {
    companion object {
        @Stable
        val Close = QuackIcon(
            value = R.drawable.ic_close_24
        )

        @Stable
        val Search = QuackIcon(
            value = R.drawable.search_16
        )

        @Stable
        val Won = QuackIcon(
            value = R.drawable.won_16
        )
    }

}

@Composable
internal fun QuackSimpleIconImage(
    modifier: Modifier = Modifier,
    icon: QuackIcon,
    color: QuackColor = QuackColor.Black,
    contentDescription: String,
) {
    Image(
        modifier = modifier,
        painter = painterResource(icon.value),
        contentDescription = contentDescription,
        colorFilter = ColorFilter.tint(color.value),
    )

}
