/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.wrapper

class NamedValue<T>(
    val value: T,
    private val name: String,
) {
    override fun toString() = name
}
