/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
import team.duckie.quackquack.ui.component.QuackBorderTextArea
import team.duckie.quackquack.ui.component.QuackTextArea

class TextAreaPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackBorderTextAreaDemo" to { QuackBorderTextAreaDemo() },
        "QuackTextAreaDemo" to { QuackTextAreaDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "TextArea",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackBorderTextAreaDemo() {
    var text by remember {
        mutableStateOf(
            value = "",
        )
    }
    QuackBorderTextArea(
        text = text,
        onTextChanged = { inputText ->
            text = inputText
        },
        placeholderText = "placeholder",
    )
}

@Composable
fun QuackTextAreaDemo() {
    var text by remember {
        mutableStateOf(
            value = "",
        )
    }
    QuackTextArea(
        text = text,
        onTextChanged = { inputText ->
            text = inputText
        },
        placeholderText = "placeholder",
    )
}
