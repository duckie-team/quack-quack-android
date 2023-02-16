/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.TouchInjectionScope

fun TouchInjectionScope.immediateXMoveTo(offset: Float) {
    moveTo(Offset(offset, 0f), 0)
}