/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackIconTextToggle
import team.duckie.quackquack.ui.component.QuackIconToggle
import team.duckie.quackquack.ui.component.QuackRoundCheckBox
import team.duckie.quackquack.ui.component.QuackSquareCheckBox
import team.duckie.quackquack.ui.icon.QuackIcon

class TogglePlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackRoundCheck" to { QuackRoundCheckDemo() },
        "QuackSquareCheck" to { QuackSquareCheckDemo() },
        "QuackToggle" to { QuackIconToggleDemo() },
        "QuackIconTextToggle" to { QuackIconTextToggleDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "QuackRoundCheck",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackRoundCheckDemo() {
    var checked by remember {
        mutableStateOf(true)
    }
    QuackRoundCheckBox(
        checked = checked,
        onToggle = {
            checked = !checked
        },
    )
}

@Composable
fun QuackSquareCheckDemo() {
    var checked by remember {
        mutableStateOf(true)
    }
    QuackSquareCheckBox(
        checked = checked,
        onToggle = {
            checked = !checked
        },
    )
}

@Composable
fun QuackIconToggleDemo() {
    var checked by remember {
        mutableStateOf(true)
    }
    QuackIconToggle(
        checkedIcon = QuackIcon.Heart,
        unCheckedIcon = QuackIcon.FilledHeart,
        checked = checked,
        onToggle = {
            checked = !checked
        },
    )
}

@Composable
fun QuackIconTextToggleDemo() {
    var checked by remember {
        mutableStateOf(true)
    }
    var checked2 by remember {
        mutableStateOf(true)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp,
        ),
    ) {
        QuackIconTextToggle(
            checkedIcon = null,
            unCheckedIcon = QuackIcon.Comment,
            checked = checked,
            text = "10.2k",
            onToggle = {
                checked = !checked
            },
        )
        QuackIconTextToggle(
            checkedIcon = QuackIcon.FilledHeart,
            unCheckedIcon = QuackIcon.Heart,
            checked = checked2,
            text = "10.2k",
            onToggle = {
                checked2 = !checked2
            },
        )
    }
}
