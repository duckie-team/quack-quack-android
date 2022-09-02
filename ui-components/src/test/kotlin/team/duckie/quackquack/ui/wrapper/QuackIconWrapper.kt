/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackIconWrapper.kt] created by Ji Sungbin on 22. 9. 2. 오전 8:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.wrapper

import team.duckie.quackquack.ui.icon.QuackIcon

class QuackIconWrapper(
    val value: QuackIcon?,
    private val name: String,
) {
    override fun toString() = name
}
