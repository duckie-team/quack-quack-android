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
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.QuackTagItem
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackRow {
    @get:Rule
    val paparazzi = buildPaparazzi {
        screenHeight = 500
        screenWidth *= 2
    }

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackTagRow(
        @TestParameter isSelected: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[isSelected:$isSelected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackTagRow(
                title = "이런 점이 최고였어요",
                items = persistentListOf(
                    QuackTagItem(
                        isSelected = isSelected,
                        text = "친절하고 매너가 좋아요",
                    ),
                    QuackTagItem(
                        isSelected = isSelected,
                        text = "답장이 빨라요",
                    ),
                    QuackTagItem(
                        isSelected = isSelected,
                        text = "입금을 제때 해줘요",
                    ),
                ),
                onClickItem = {},
            )
        }
    }
}

