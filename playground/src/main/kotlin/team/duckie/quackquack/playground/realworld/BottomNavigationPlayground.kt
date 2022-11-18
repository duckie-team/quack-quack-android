/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.ui.component.QuackBottomNavigation

class BottomNavigationPlayground : PlaygroundActivity(
    name = "BottomNavigation",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackBottomNavigationDemo.name to { QuackBottomNavigationDemo() },
    )
}

@Composable
fun QuackBottomNavigationDemo() {
    val (index, setIndex) = remember { mutableStateOf(0) }

    QuackBottomNavigation(
        selectedIndex = index,
        onClick = setIndex,
    )
}
