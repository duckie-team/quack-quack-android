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

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.provider.QuackDeviceConfig
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.paparazzi

@RunWith(TestParameterInjector::class)
class QuackToggle {
    @get:Rule
    val paparazzi = paparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackRoundCheckBox(
        @TestParameter checked: Boolean,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]",
            deviceConfig = deviceConfig,
            screenWidth = 200,
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
            name = "[checked:$checked]",
            deviceConfig = deviceConfig,
            screenWidth = 200,
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
            name = "[checked:$checked]",
            deviceConfig = deviceConfig,
            screenWidth = 200,
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
            name = "[checked:$checked]-[fontScale:$fontScale]",
            fontScale = fontScale,
            deviceConfig = deviceConfig,
            screenWidth = 400,
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
