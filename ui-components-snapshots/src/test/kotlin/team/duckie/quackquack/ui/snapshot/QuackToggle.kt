/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "JUnitMalformedDeclaration",
    "TestFunctionName",
)

package team.duckie.quackquack.ui.snapshot

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.provider.QuackDeviceConfig
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.DeviceOption
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.paparazzi

@RunWith(TestParameterInjector::class)
class QuackToggle {
    private val deviceConfigId = "QuackToggle"

    @get:Rule
    val paparazzi = paparazzi(
        deviceOption = DeviceOption(
            screenWidth = 200,
            matchFullWidth = false,
        ),
        configId = deviceConfigId,
    )

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackRoundCheckBox(
        @TestParameter checked: Boolean,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            parameterNames = listOf(
                "checked",
                "deviceConfig",
            ),
            configId = deviceConfigId,
            deviceConfig = deviceConfig,
        ) {
            team.duckie.quackquack.ui.component.QuackRoundCheckBox(
                checked = checked,
                onToggle = {},
            )
        }
    }

    @Test
    fun QuackSquareCheckBox(
        @TestParameter checked: Boolean,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            parameterNames = listOf(
                "checked",
                "deviceConfig",
            ),
            configId = deviceConfigId,
            deviceConfig = deviceConfig,
        ) {
            team.duckie.quackquack.ui.component.QuackSquareCheckBox(
                checked = checked,
                onToggle = {},
            )
        }
    }

    @Test
    fun QuackIconToggle(
        @TestParameter checked: Boolean,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            parameterNames = listOf(
                "checked",
                "deviceConfig",
            ),
            configId = deviceConfigId,
            deviceConfig = deviceConfig,
        ) {
            team.duckie.quackquack.ui.component.QuackIconToggle(
                checkedIcon = QuackIcon.FilledHeart,
                uncheckedIcon = QuackIcon.Heart,
                checked = checked,
                onToggle = {},
            )
        }
    }

    @Test
    fun QuackIconTextToggle(
        @TestParameter checked: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            parameterNames = listOf(
                "checked",
                "fontScale",
                "deviceConfig",
            ),
            fontScale = fontScale,
            configId = deviceConfigId,
            deviceConfig = deviceConfig,
            overrideScreenWidth = 400,
        ) {
            team.duckie.quackquack.ui.component.QuackIconTextToggle(
                checkedIcon = QuackIcon.FilledHeart,
                uncheckedIcon = QuackIcon.Heart,
                checked = checked,
                text = "QuackIconTextToggle",
                onToggle = {},
            )
        }
    }
}
