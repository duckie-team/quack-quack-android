/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [runIf.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:00
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.common

inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this

inline fun <T> T.runIfBuilder(condition: (T) -> Boolean, run: T.() -> T) = if (condition(this)) {
    run()
} else this
