/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackDialogMenuItem
import team.duckie.quackquack.ui.component.QuackFloatingActionButton
import team.duckie.quackquack.ui.component.QuackMenuFloatingActionButton
import team.duckie.quackquack.ui.icon.QuackIcon

class FabPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackFloatingActionButton" to { QuackFloatingActionButtonDemo() },
        "QuackMenuFloatingActionButton" to { QuackMenuFloatingActionButtonDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Fab",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackFloatingActionButtonDemo() {
    QuackFloatingActionButton(
        icon = QuackIcon.DmNew,
        onClick = {},
    )
}

@Composable
fun QuackMenuFloatingActionButtonDemo() {
    var expanded by remember {
        mutableStateOf(
            value = false,
        )
    }

    QuackMenuFloatingActionButton(
        expanded = expanded,
        onClickButton = {
            expanded = true
        },
        onDismissRequest = {
            expanded = false
        },
        menuItems = persistentListOf(
            QuackDialogMenuItem(
                icon = QuackIcon.WriteFeed,
                text = "피드",
            ),
            QuackDialogMenuItem(
                icon = QuackIcon.DrawerBuy,
                text = "덕딜",
            ),
        ),
        onClickMenuItem = {},
    )
}
