/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.util

import androidx.compose.ui.util.fastFirstOrNull

internal inline fun <reified T> List<Any>.fastFirstInstanceOrNull(): T? {
    return fastFirstOrNull { it is T } as? T
}
