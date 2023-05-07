/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onNodeWithTag

fun SemanticsNodeInteractionsProvider.onTest(): SemanticsNodeInteraction {
  return onNodeWithTag("test")
}

fun SemanticsNodeInteractionsProvider.onGolden(): SemanticsNodeInteraction {
  return onNodeWithTag("golden")
}
