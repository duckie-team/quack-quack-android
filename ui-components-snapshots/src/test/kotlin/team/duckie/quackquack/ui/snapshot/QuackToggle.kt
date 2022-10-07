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

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackToggle {
    @get:Rule
    val paparazzi: Paparazzi = buildPaparazzi {
        screenWidth *= 2
    }

    @get:Rule
    val animationTest: TestWatcher = AnimationTestRule()

    @Test
    fun QuackRoundCheckBox(
        @TestParameter checked: Boolean,
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]",
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
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]",
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
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]",
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
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]-[fontScale:$fontScale]",
            fontScale = fontScale,
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
