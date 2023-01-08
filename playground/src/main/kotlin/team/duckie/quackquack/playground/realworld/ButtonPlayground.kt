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
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeButtonType
import team.duckie.quackquack.ui.component.QuackMediumToggleButton
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSmallButtonType
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.icon.QuackIcon

// TODO: IME 애니메이션 플레이그라운드 -> common 로직 변경하는 대공사 필요

class ButtonPlayground : PlaygroundActivity(name = "Button") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackLargeButtonTypeFillDemo.name to { QuackLargeButtonTypeFillDemo() },
        ::QuackLargeButtonTypeFillLoadingDemo.name to { QuackLargeButtonTypeFillLoadingDemo() },
        ::QuackLargeButtonTypeBorderDemo.name to { QuackLargeButtonTypeBorderDemo() },
        ::QuackLargeButtonTypeCompactDemo.name to { QuackLargeButtonTypeCompactDemo() },
        ::QuackMediumToggleButtonDemo.name to { QuackMediumToggleButtonDemo() },
        ::QuackSmallButtonBorderDemo.name to { QuackSmallButtonBorderDemo() },
        ::QuackSmallButtonFillDemo.name to { QuackSmallButtonFillDemo() },
        ::QuackToggleChipDemo.name to { QuackToggleChipDemo() },
    )
}

@Composable
fun QuackLargeButtonTypeFillDemo() {
    var enabled by remember { mutableStateOf(false) }

    QuackLargeButton(
        type = QuackLargeButtonType.Fill,
        text = "LargeButton: Fill",
        enabled = enabled,
        onClick = { enabled = !enabled },
    )
}

@Composable
fun QuackLargeButtonTypeFillLoadingDemo() {
    var enabled by remember { mutableStateOf(false) }

    QuackLargeButton(
        type = QuackLargeButtonType.Fill,
        text = "LargeButton: Fill",
        enabled = enabled,
        isLoading = true,
        onClick = { enabled = !enabled },
    )
}

@Composable
fun QuackLargeButtonTypeBorderDemo() {
    val toast = rememberToast()

    QuackLargeButton(
        type = QuackLargeButtonType.Border,
        text = "LargeButton: Border",
        leadingIcon = QuackIcon.Heart,
        onClick = { toast("LargeButtonTypeBorder") },
    )
}

@Composable
fun QuackLargeButtonTypeCompactDemo() {
    val toast = rememberToast()

    QuackLargeButton(
        type = QuackLargeButtonType.Compact,
        text = "LargeButton: Compact",
        onClick = { toast("LargeButtonTypeCompact") },
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
