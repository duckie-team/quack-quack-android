/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import team.duckie.quackquack.core.theme.QuackTheme

fun ComposeContentTestRule.setQuackContent(content: @Composable () -> Unit) {
    setContent {
        QuackTheme(content = content)
    }
}
