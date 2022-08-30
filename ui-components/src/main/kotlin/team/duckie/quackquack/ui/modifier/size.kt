/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [size.kt] created by Ji Sungbin on 22. 8. 15. 오전 1:19
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.modifier

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth

@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER", "UNUSED_VALUE")
@Stable
internal fun Modifier.applyQuackSize(
    width: QuackWidth,
    height: QuackHeight,
): Modifier {
    var modifier = this
    modifier = when (width) {
        QuackWidth.Fill -> fillMaxWidth()
        QuackWidth.Wrap -> wrapContentWidth()
        is QuackWidth.Custom -> width(
            width = width.width,
        )
    }
    modifier.then(
        when (height) {
            QuackHeight.Fill -> fillMaxHeight()
            QuackHeight.Wrap -> wrapContentHeight()
            is QuackHeight.Custom -> height(
                height = height.height,
            )
        }
    )
    return modifier
}
