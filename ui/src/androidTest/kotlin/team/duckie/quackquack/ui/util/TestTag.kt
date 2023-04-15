/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

fun Modifier.markTest(): Modifier {
    return testTag("test")
}

fun Modifier.markGolden(): Modifier {
    return testTag("golden")
}
