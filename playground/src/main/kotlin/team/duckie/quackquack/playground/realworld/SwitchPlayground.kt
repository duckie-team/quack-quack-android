/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.ui.component.QuackSwitch

class SwitchPlayground : PlaygroundActivity(name = "Switch") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackSwitchDemo.name to { QuackSwitchDemo() },
    )
}

@Composable
fun QuackSwitchDemo() {
    var state by remember { mutableStateOf(false) }

    QuackSwitch(
        checked = state,
        onCheckedChange = { state = it },
    )
}
