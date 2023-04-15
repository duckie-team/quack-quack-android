/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag

fun SemanticsNodeInteractionsProvider.onTest(): SemanticsNodeInteraction {
    // `Composer.quackMaterializeOf`에 의해 materializing이 2번 돼서 태그가 2개 생김
    return onAllNodesWithTag("test")[1]
}

fun SemanticsNodeInteractionsProvider.onGolden(): SemanticsNodeInteraction {
    return onNodeWithTag("golden")
}
