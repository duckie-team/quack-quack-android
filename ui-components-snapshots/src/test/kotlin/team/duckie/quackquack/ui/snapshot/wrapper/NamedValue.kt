/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.wrapper

class NamedValue<T>(
    val value: T,
    private val name: String,
) {
    override fun toString() = name
}
