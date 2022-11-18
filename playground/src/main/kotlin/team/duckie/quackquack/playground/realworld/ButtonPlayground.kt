/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackMediumToggleButton
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.icon.QuackIcon

class ButtonPlayground : PlaygroundActivity(
    name = "Button",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackLargeButtonTypeBorderDemo.name to { QuackLargeButtonTypeBorderDemo() },
        ::QuackLargeButtonTypeCompactDemo.name to { QuackLargeButtonTypeCompactDemo() },
        ::QuackLargeButtonTypeFillDemo.name to { QuackLargeButtonTypeFillDemo() },
        ::QuackMediumToggleButtonDemo.name to { QuackMediumToggleButtonDemo() },
        ::QuackSmallButtonBorderDemo.name to { QuackSmallButtonBorderDemo() },
        ::QuackSmallButtonFillDemo.name to { QuackSmallButtonFillDemo() },
        ::QuackToggleChipDemo.name to { QuackToggleChipDemo() },
    )
}

@Composable
fun QuackLargeButtonTypeBorderDemo() {
    var active by remember { mutableStateOf(false) }

    QuackLargeButton(
        type = QuackLargeButtonType.Border,
        text = "LargeButton: Border",
        active = active,
        leadingIcon = QuackIcon.Heart.takeIf { active },
        onClick = { active = !active },
    )
}

@Composable
fun QuackLargeButtonTypeCompactDemo() {
    var active by remember { mutableStateOf(false) }

    QuackLargeButton(
        type = QuackLargeButtonType.Compact,
        text = "LargeButton: Compact",
        active = active,
        leadingIcon = QuackIcon.Heart.takeIf { active },
        onClick = { active = !active },
    )
}

@Composable
fun QuackLargeButtonTypeFillDemo() {
    var active by remember { mutableStateOf(false) }

    QuackLargeButton(
        type = QuackLargeButtonType.Fill,
        text = "LargeButton: Fill",
        active = active,
        leadingIcon = QuackIcon.Heart.takeIf { active },
        onClick = { active = !active },
    )
}

@Composable
fun QuackMediumToggleButtonDemo() {
    var selected by remember { mutableStateOf(false) }

    QuackMediumToggleButton(
        text = "MediumToggleButton",
        selected = selected,
        onClick = { selected = !selected },
    )
}

@Composable
fun QuackSmallButtonBorderDemo() {
    var enabled by remember { mutableStateOf(false) }

    QuackSmallButton(
        type = QuackSmallButtonType.Border,
        text = "SmallButton: Border",
        enabled = enabled,
        onClick = { enabled = !enabled },
    )
}

@Composable
fun QuackSmallButtonFillDemo() {
    var enabled by remember { mutableStateOf(false) }

    QuackSmallButton(
        type = QuackSmallButtonType.Fill,
        text = "SmallButton: Fill",
        enabled = enabled,
        onClick = { enabled = !enabled },
    )
}

@Composable
fun QuackToggleChipDemo() {
    var selected by remember { mutableStateOf(false) }

    QuackToggleChip(
        text = "ToggleChip",
        selected = selected,
        onClick = { selected = !selected },
    )
}
