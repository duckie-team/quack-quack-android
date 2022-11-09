/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackFab
import team.duckie.quackquack.ui.component.QuackMenuFab
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.icon.QuackIcon

class FabPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackFabDemo" to { QuackFabDemo() },
        "QuackMenuFabDemo" to { QuackMenuFabDemo() },
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
fun QuackFabDemo() {
    QuackFab(
        icon = QuackIcon.Heart,
        onClick = {},
    )
}

@Composable
fun QuackMenuFabDemo() {
    Box(
        modifier = Modifier
            .size(
                size = 250.dp,
            )
            .background(
                color = Color.LightGray.copy(
                    alpha = 0.5f,
                )
            ),
        contentAlignment = Alignment.BottomEnd,
    ) {
        val toast = rememberToast()
        var state by remember {
            mutableStateOf(
                value = false,
            )
        }
        QuackMenuFab(
            items = persistentListOf(
                QuackMenuFabItem(
                    icon = QuackIcon.FilledHeart,
                    text = "FilledHeart",
                ),
                QuackMenuFabItem(
                    icon = QuackIcon.Heart,
                    text = "Heart",
                ),
                QuackMenuFabItem(
                    icon = QuackIcon.WhiteHeart,
                    text = "WhiteHeart",
                ),
            ),
            expanded = state,
            onFabClick = { state = !state },
            onItemClick = { _, item ->
                toast(
                    message = item,
                )
            },
        )
    }
}
