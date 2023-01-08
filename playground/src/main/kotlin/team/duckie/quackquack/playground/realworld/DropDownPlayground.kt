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
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackDropDownCard

class DropDownPlayground : PlaygroundActivity(name = "DropDown") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackDropDownCardDemo.name to { QuackDropDownCardDemo() },
        ::QuackDropDownCardNoLineDemo.name to { QuackDropDownCardNoLineDemo() },
    )
}

@Composable
fun QuackDropDownCardDemo() {
    val toast = rememberToast()

    QuackDropDownCard(
        text = "DropDownCard",
        onClick = { toast("DropDownCard") },
    )
}

@Composable
fun QuackDropDownCardNoLineDemo() {
    val toast = rememberToast()

    QuackDropDownCard(
        text = "DropDownCard",
        showBorder = false,
        onClick = { toast("DropDownCard") },
    )
}

