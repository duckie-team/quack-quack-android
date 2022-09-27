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
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTextField
import team.duckie.quackquack.ui.icon.QuackIcon

class TextFieldPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackTextFieldWithNoDecoration" to { QuackTextFieldWithNoDecorationDemo() },
        "QuackTextFieldWithLeadingDecoration" to { QuackTextFieldWithLeadingDecorationDemo() },
        "QuackTextFieldWithTrailingDecoration" to { QuackTextFieldWithTrailingDecorationDemo() },
        "QuackTextFieldWithAllDecoration" to { QuackTextFieldWithAllDecorationDemo() },
        "QuackTextFieldErrorState" to { QuackTextFieldErrorStateDemo() },
        "QuackTextFieldErrorState - Live" to { QuackTextFieldErrorStateLiveDemo() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "TextField",
                    items = items,
                )
            }
        }
    }
}

private const val Placeholder = "placeholder text"

@Composable
fun QuackTextFieldWithNoDecorationDemo() {
    // empty test
    var fieldState by remember {
        mutableStateOf(
            value = "",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        placeholderText = Placeholder,
    )
}

@Composable
fun QuackTextFieldWithLeadingDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackTextFieldWithLeadingDecorationDemo",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        leadingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
    )
}

@Composable
fun QuackTextFieldWithTrailingDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackTextFieldWithTrailingDecorationDemo",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
    )
}

@Composable
fun QuackTextFieldWithAllDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackTextFieldWithAllDecorationDemo",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        leadingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
    )
}

@Composable
fun QuackTextFieldErrorStateDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "wow!",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        isError = true,
        errorText = "QuackTextFieldErrorStateDemo",
        leadingContent = {
            QuackImage(
                icon = QuackIcon.Heart,
            )
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.Heart,
            )
        },
    )
}

@Composable
fun QuackTextFieldErrorStateLiveDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "1",
        )
    }
    QuackTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        isError = fieldState.length > 1,
        errorText = "한 글자만 가능!",
        placeholderText = Placeholder,
        leadingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.FilledHeart,
            )
        },
    )
}
