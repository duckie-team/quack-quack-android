/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "TestFunctionName",
    "JUnitMalformedDeclaration",
)

package team.duckie.quackquack.ui

import androidx.compose.runtime.Composable
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.provider.DecorationContentProvider
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi
import team.duckie.quackquack.ui.wrapper.NamedValue

@RunWith(TestParameterInjector::class)
class QuackTextField {
    @get:Rule
    val paparazzi = buildPaparazzi {
        screenHeight *= 2
    }

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackBasicTextField(
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
        @TestParameter(valuesProvider = DecorationContentProvider::class)
        decorationContent: NamedValue<(@Composable () -> Unit)?>,
        @TestParameter isError: Boolean,
        @TestParameter("duckie", "") text: String,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]-[decorationContent:$decorationContent]-" +
                    "[isError:$isError]-[text:$text]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackTextField(
                text = text,
                onTextChanged = {},
                placeholderText = "placeholder message",
                isError = isError,
                errorText = "error message",
                leadingContent = decorationContent.value,
                trailingContent = decorationContent.value,
            )
        }
    }
}
