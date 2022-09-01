/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ButtonPlayground.kt] created by Ji Sungbin on 22. 9. 2. 오전 1:02
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackLarge40WhiteButton
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeWhiteButton
import team.duckie.quackquack.ui.component.QuackMediumBorderToggleButton
import team.duckie.quackquack.ui.component.QuackSmallBorderToggleButton
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.icon.QuackIcon

class ButtonPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackLargeButton" to { QuackLargeButtonDemo() },
        "QuackLargeWhiteButton" to { QuackLargeWhiteButtonDemo() },
        "QuackLarge40WhiteButton" to { QuackLarge40WhiteButtonDemo() },
        "QuackMediumBorderToggleButton" to { QuackMediumBorderToggleButtonDemo() },
        "QuackSmallButton" to { QuackSmallButtonDemo() },
        "QuackSmallBorderToggleButton" to { QuackSmallBorderToggleButtonDemo() },
        "QuackToggleChip" to { QuackToggleChipDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Button",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackLargeButtonDemo() {
    var active by remember {
        mutableStateOf(true)
    }

    QuackLargeButton(
        text = "active: $active",
        active = active,
        onClick = { active = !active },
    )
}

@Composable
fun QuackLargeWhiteButtonDemo() {
    var leadingIcon by remember {
        mutableStateOf<QuackIcon?>(QuackIcon.Close)
    }

    QuackLargeWhiteButton(
        text = "leadingIcon: ${leadingIcon != null}",
        leadingIcon = leadingIcon,
        onClick = {
            leadingIcon = when (leadingIcon != null) {
                true -> null
                else -> QuackIcon.Close
            }
        },
    )
}

@Composable
fun QuackLarge40WhiteButtonDemo() {
    QuackLarge40WhiteButton(
        text = "QuackLarge40WhiteButton",
        onClick = { },
    )
}

@Composable
fun QuackMediumBorderToggleButtonDemo() {
    var selected by remember {
        mutableStateOf(true)
    }

    QuackMediumBorderToggleButton(
        text = "selected: $selected",
        selected = selected,
        onClick = { selected = !selected },
    )
}

@Composable
fun QuackSmallButtonDemo() {
    var enabled by remember {
        mutableStateOf(true)
    }

    QuackSmallButton(
        text = "enabled: $enabled",
        enabled = enabled,
        onClick = { enabled = !enabled },
    )
}

@Composable
fun QuackSmallBorderToggleButtonDemo() {
    var selected by remember {
        mutableStateOf(true)
    }

    QuackSmallBorderToggleButton(
        text = "selected: $selected",
        selected = selected,
        onClick = { selected = !selected },
    )
}

@Composable
fun QuackToggleChipDemo() {
    var selected by remember {
        mutableStateOf(true)
    }

    QuackToggleChip(
        text = "selected: $selected",
        selected = selected,
        onClick = { selected = !selected },
    )
}
