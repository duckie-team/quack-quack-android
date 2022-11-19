/*
 * Designed and developed by Duckie Team, 2022
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
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackBasic2TextField
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackPriceTextField
import team.duckie.quackquack.ui.component.QuackProfileTextField
import team.duckie.quackquack.ui.icon.QuackIcon

class TextFieldPlayground : PlaygroundActivity(
    name = "TextField",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackBasicTextFieldDemo.name to { QuackBasicTextFieldDemo() },
        ::QuackPriceTextFieldDemo.name to { QuackPriceTextFieldDemo() },
        ::QuackBasic2TextFieldDemo.name to { QuackBasic2TextFieldDemo() },
        ::QuackProfileTextFieldDemo.name to { QuackProfileTextFieldDemo() },
    )
}

@Composable
fun QuackBasicTextFieldDemo() {
    val toast = rememberToast()
    val (text, setText) = remember { mutableStateOf("") }

    QuackBasicTextField(
        text = text,
        onTextChanged = setText,
        placeholderText = "BasicTextFieldPlaceholder",
        leadingIcon = QuackIcon.FilledHeart,
        trailingText = "99+",
        trailingTextOnClick = { toast("99+ clicked") },
    )
}

@Composable
fun QuackPriceTextFieldDemo() {
    val (text, setText) = remember { mutableStateOf("") }

    QuackPriceTextField(
        priceText = text,
        onPriceTextChanged = setText,
        placeholderText = "PriceTextFieldPlaceholder",
    )
}

@Composable
fun QuackBasic2TextFieldDemo() {
    val toast = rememberToast()
    val (text, setText) = remember { mutableStateOf("") }

    QuackBasic2TextField(
        text = text,
        onTextChanged = setText,
        placeholderText = "Basic2TextFieldPlaceholder",
        leadingIcon = QuackIcon.Heart,
        leadingIconOnClick = { toast("Heart clicked") },
        trailingIcon = QuackIcon.FilledHeart,
        trailingIconOnClick = { toast("FilledHeart clicked") },
    )
}

@Composable
fun QuackProfileTextFieldDemo() {
    val (text, setText) = remember { mutableStateOf("") }

    QuackProfileTextField(
        text = text,
        onTextChanged = setText,
        placeholderText = "MaxLength: 5",
        maxLength = 5,
        errorText = "ErrorText",
        onCleared = { setText("") },
    )
}
