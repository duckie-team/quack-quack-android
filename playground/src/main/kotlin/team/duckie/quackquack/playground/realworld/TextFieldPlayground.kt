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
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon

class TextFieldPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackBasicTextFieldWithNoDecoration" to { QuackBasicTextFieldWithNoDecorationDemo() },
        "QuackBasicTextFieldWithLeadingDecoration" to { QuackBasicTextFieldWithLeadingDecorationDemo() },
        "QuackBasicTextFieldWithTrailingDecoration" to { QuackBasicTextFieldWithTrailingDecorationDemo() },
        "QuackBasicTextFieldWithAllDecoration" to { QuackBasicTextFieldWithAllDecorationDemo() },
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

@Composable
fun QuackBasicTextFieldWithNoDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackBasicTextFieldDemo",
        )
    }
    QuackBasicTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
    )
}

@Composable
fun QuackBasicTextFieldWithLeadingDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackBasicTextFieldDemo",
        )
    }
    QuackBasicTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        leadingContent = {
            QuackImage(
                icon = QuackIcon.MarketPrice,
            )
        },
    )
}

@Composable
fun QuackBasicTextFieldWithTrailingDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "QuackBasicTextFieldDemo",
        )
    }
    QuackBasicTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.Badge,
            )
        },
    )
}

@Composable
fun QuackBasicTextFieldWithAllDecorationDemo() {
    var fieldState by remember {
        mutableStateOf(
            value = "",
        )
    }
    QuackBasicTextField(
        text = fieldState,
        onTextChanged = { newText ->
            fieldState = newText
        },
        leadingContent = {
            QuackImage(
                icon = QuackIcon.ImageEditBg,
            )
        },
        trailingContent = {
            QuackImage(
                icon = QuackIcon.Area,
            )
        },
    )
}
