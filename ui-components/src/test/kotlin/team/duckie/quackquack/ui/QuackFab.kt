/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackFab.kt] created by Ji Sungbin on 22. 9. 4. 오전 2:23
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("TestFunctionName", "SpellCheckingInspection")

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.QuackPopUpMenuItem
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.textstyle.QuackFontScale
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackFab {
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
            team.duckie.quackquack.ui.component.QuackFloatingActionButton(
                icon = QuackIcon.DMNew,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackMenuFloatingActionButton(
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]",
        ) {
            QuackFontScale = fontScale
            team.duckie.quackquack.ui.component.QuackMenuFloatingActionButton(
                items = persistentListOf(
                    QuackPopUpMenuItem(
                        quackIcon = QuackIcon.WriteFeed,
                        text = "피드",
                        onClick = {},
                    ),
                    QuackPopUpMenuItem(
                        quackIcon = QuackIcon.DrawerBuy,
                        text = "덕딜",
                        onClick = {},
                    ),
                ),
            )
        }
    }
}
