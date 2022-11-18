/*
 * Designed and developed by Duckie Team, 2022
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
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.component.QuackSubTab

class TabPlayground : PlaygroundActivity(
    name = "Tab",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackMainTabDemo.name to { QuackMainTabDemo() },
        ::QuackSubTabDemo.name to { QuackSubTabDemo() },
    )
}

@Composable
fun QuackMainTabDemo() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val titles = remember {
        persistentListOf("세글자", "다서엇글자", "이일고오옵글자")
    }

    QuackMainTab(
        titles = titles,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
        },
    )
}

@Composable
fun QuackSubTabDemo() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val titles = remember {
        persistentListOf("세글자", "다서엇글자", "이일고오옵글자")
    }

    QuackSubTab(
        titles = titles,
        selectedTabIndex = selectedTabIndex,
        onTabSelected = { index ->
            selectedTabIndex = index
        },
    )
}
