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
import team.duckie.quackquack.ui.component.QuackRoundCheckBox
import team.duckie.quackquack.ui.component.QuackSmallRoundCheckBox
import team.duckie.quackquack.ui.component.QuackSquareCheckBox
import team.duckie.quackquack.ui.component.QuackToggleButton
import team.duckie.quackquack.ui.component.QuackToggleIconSize
import team.duckie.quackquack.ui.icon.QuackIcon

class TogglePlayground : PlaygroundActivity(
    name = "Toggle",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackRoundCheckBoxDemo.name to { QuackRoundCheckBoxDemo() },
        ::QuackSmallRoundCheckBoxDemo.name to { QuackSmallRoundCheckBoxDemo() },
        ::QuackSquareCheckBoxDemo.name to { QuackSquareCheckBoxDemo() },
        ::QuackToggleButtonTypeNormalDemo.name to { QuackToggleButtonTypeNormalDemo() },
        ::QuackToggleButtonTypeSmallDemo.name to { QuackToggleButtonTypeSmallDemo() },
        ::QuackToggleButtonTypeCompactDemo.name to { QuackToggleButtonTypeCompactDemo() },
    )
}

@Composable
fun QuackSmallRoundCheckBoxDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackSmallRoundCheckBox(
        checked = checked,
        onClick = { checked = !checked },
    )
}

@Composable
fun QuackRoundCheckBoxDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackRoundCheckBox(
        checked = checked,
        onClick = { checked = !checked },
    )
}

@Composable
fun QuackSquareCheckBoxDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackSquareCheckBox(
        checked = checked,
        onClick = { checked = !checked },
    )
}

@Composable
fun QuackToggleButtonTypeNormalDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackToggleButton(
        checkedIcon = QuackIcon.FilledHeart,
        uncheckedIcon = QuackIcon.WhiteHeart,
        iconSize = QuackToggleIconSize.Normal,
        checked = checked,
        onClick = { checked = !checked },
    )
}

@Composable
fun QuackToggleButtonTypeSmallDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackToggleButton(
        checkedIcon = QuackIcon.FilledHeart,
        uncheckedIcon = QuackIcon.WhiteHeart,
        iconSize = QuackToggleIconSize.Small,
        checked = checked,
        onClick = { checked = !checked },
        trailingText = "Small",
    )
}

@Composable
fun QuackToggleButtonTypeCompactDemo() {
    var checked by remember { mutableStateOf(false) }

    QuackToggleButton(
        checkedIcon = QuackIcon.FilledHeart,
        uncheckedIcon = QuackIcon.WhiteHeart,
        iconSize = QuackToggleIconSize.Compact,
        checked = checked,
        onClick = { checked = !checked },
    )
}
