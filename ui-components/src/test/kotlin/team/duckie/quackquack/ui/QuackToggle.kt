/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "TestFunctionName",
    "SpellCheckingInspection",
    "JUnitMalformedDeclaration",
)

package team.duckie.quackquack.ui

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@Ignore(
    value = """
        Cannot read field "metaData" because "appInfo" is null
    """,
)
@RunWith(TestParameterInjector::class)
public class QuackToggle {
    @get:Rule
    public val paparazzi: Paparazzi = buildPaparazzi {
        screenWidth *= 2
    }

    @get:Rule
    public val animationTest: TestWatcher = AnimationTestRule()

    @Test
    public fun QuackRoundCheckBox(
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
    public fun QuackSquareCheckBox(
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
    public fun QuackIconToggle(
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
    public fun QuackIconTextToggle(
        @TestParameter checked: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
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
