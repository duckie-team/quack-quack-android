/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.ui.component.QuackBasicTextArea
import team.duckie.quackquack.ui.component.QuackGoodsDetailTextArea
import team.duckie.quackquack.ui.component.QuackReviewTextArea

class TextAreaPlayground : PlaygroundActivity(name = "TextArea") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackBasicTextAreaDemo.name to { QuackBasicTextAreaDemo() },
        ::QuackReviewTextAreaDemo.name to { QuackReviewTextAreaDemo() },
        ::QuackGoodsDetailTextAreaDemo.name to { QuackGoodsDetailTextAreaDemo() },
    )
}

@Composable
fun QuackBasicTextAreaDemo() {
    val (text, setText) = remember { mutableStateOf("") }

    QuackBasicTextArea(
        text = text,
        onTextChanged = setText,
        placeholderText = "BasicTextAreaPlayholder",
    )
}

@Composable
fun QuackReviewTextAreaDemo() {
    val (text, setText) = remember { mutableStateOf("") }

    QuackReviewTextArea(
        text = text,
        onTextChanged = setText,
        placeholderText = "ReviewTextAreaPlaceholder",
        focused = true,
    )
}

@Composable
fun QuackGoodsDetailTextAreaDemo() {
    val (text, setText) = remember { mutableStateOf("") }

    QuackGoodsDetailTextArea(
        text = text,
        onTextChanged = setText,
        placeholderText = "ReviewTextAreaPlaceholder",
    )
}
