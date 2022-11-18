/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.ui.component.QuackDivider

class DividerPlayground : PlaygroundActivity(
    name = "Divider",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackDividerDemo.name to { QuackDividerDemo() },
    )
}

@Composable
fun QuackDividerDemo() {
    QuackDivider()
}
