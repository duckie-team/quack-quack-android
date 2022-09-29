/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.wrapper

public class NamedValue<T>(
    public val value: T,
    private val name: String,
) {
    override fun toString(): String = name
}
