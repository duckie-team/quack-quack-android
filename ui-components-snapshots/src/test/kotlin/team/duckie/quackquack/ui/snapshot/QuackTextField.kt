/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "JUnitMalformedDeclaration",
    "TestFunctionName",
)

package team.duckie.quackquack.ui.snapshot

import androidx.compose.runtime.Composable
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.snapshot.provider.DecorationContentProvider
import team.duckie.quackquack.ui.snapshot.provider.QuackDeviceConfig
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.DeviceOption
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.paparazzi
import team.duckie.quackquack.ui.snapshot.wrapper.NamedValue

@RunWith(TestParameterInjector::class)
class QuackTextField {
    private val deviceConfigId = "QuackTextField"

    @get:Rule
    val paparazzi = paparazzi(
        deviceOption = DeviceOption(
            screenHeight = 350,
        ),
        configId = deviceConfigId,
    )

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackBasicTextField(
        @TestParameter("1.0", "1.5") fontScale: Float,
        @TestParameter deviceConfig: QuackDeviceConfig,
        @TestParameter(valuesProvider = DecorationContentProvider::class)
        decorationContent: NamedValue<(@Composable () -> Unit)?>,
        @TestParameter isError: Boolean,
        @TestParameter("duckie", "") text: String,
    ) {
        paparazzi.boxSnapshot(
            parameterNames = listOf(
                "fontScale",
                "deviceConfig",
                "decorationContent",
                "isError",
                "text",
            ),
            fontScale = fontScale,
            configId = deviceConfigId,
            deviceConfig = deviceConfig,
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
