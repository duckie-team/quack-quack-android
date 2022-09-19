/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "TestFunctionName",
    "SpellCheckingInspection",
)

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackToggle {
    @get:Rule
    val paparazzi = buildPaparazzi {
        // QuackToggle 은 정사각형으로 테스트해도 무방함
        screenWidth = screenHeight + 200
    }

    @get:Rule
    val animationTest = AnimationTestRule()

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
                unCheckedIcon = QuackIcon.Heart,
                checked = checked,
                onToggle = {},
            )
        }
    }

    @Test
    fun QuackIconTextToggle(
        @TestParameter checked: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[checked:$checked]-[fontScale:$fontScale]",
        ) {
            team.duckie.quackquack.ui.component.QuackIconTextToggle(
                checkedIcon = QuackIcon.FilledHeart,
                unCheckedIcon = QuackIcon.Heart,
                checked = checked,
                text = "QuackIconTextToggle",
                onToggle = {},
            )
        }
    }
}
