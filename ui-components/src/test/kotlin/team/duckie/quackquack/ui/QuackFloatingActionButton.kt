/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackFloatingActionButton.kt] created by Ji Sungbin on 22. 9. 4. 오전 2:23
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("TestFunctionName", "SpellCheckingInspection")

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.textstyle.QuackFontScale
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackFloatingActionButton {
    @get:Rule
    val paparazzi = buildPaparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackFloatingActionButton(
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]",
        ) {
            QuackFontScale = fontScale
            team.duckie.quackquack.ui.component.FloatingActionButton(
                onClick = {},
            )
        }
    }
}
