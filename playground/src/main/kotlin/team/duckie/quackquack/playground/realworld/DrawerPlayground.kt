/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDivider
import team.duckie.quackquack.ui.component.QuackLarge40WhiteButton
import team.duckie.quackquack.ui.component.QuackModalDrawer
import team.duckie.quackquack.ui.component.rememberQuackDrawerState

class DrawerPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackModalDrawerDemo" to { QuackModalDrawerDemo() },

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "ModalDrawer",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackModalDrawerDemo() {
    val drawerState = rememberQuackDrawerState()
    val coroutineScope = rememberCoroutineScope()
    QuackLarge40WhiteButton(
        text = "실행",
        onClick = {
            coroutineScope.launch {
                drawerState.open()
            }
        },
    )
    QuackModalDrawer(
        drawerState = drawerState,
    ){
        QuackBody1(
            text = "안녕하세요 감사합니다"
        )
        QuackDivider()
        QuackBody1(
            text = "네 들어가세요"
        )
    }
}
