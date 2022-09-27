/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "NotConstructor",
    "TestFunctionName",
    "SpellCheckingInspection",
    "JUnitMalformedDeclaration",
)

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.QuackTagItem
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.provider.QuackIconProvider
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi
import team.duckie.quackquack.ui.wrapper.NamedValue

@RunWith(TestParameterInjector::class)
class QuackTag {
    @get:Rule
    val paparazzi = buildPaparazzi {
        screenHeight = 500
        screenWidth *= 2
    }

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackTag(
        @TestParameter isSelected: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[isSelected:$isSelected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackTag(
                text = "QuackTag",
                isSelected = isSelected,
            )
        }
    }

    @Test
    fun QuackGrayscaleTag(
        @TestParameter("99+", "") trailingText: String,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[trailingText:$trailingText]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackGrayscaleTag(
                text = "QuackTag",
                trailingText = trailingText,
            )
        }
    }

    @Test
    fun QuackIconTag(
        @TestParameter isSelected: Boolean,
        @TestParameter(valuesProvider = QuackIconProvider::class) icon: NamedValue<QuackIcon>,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[isSelected:$isSelected]-[icon:$icon]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackIconTag(
                text = "QuackTag",
                icon = icon.value,
                isSelected = isSelected,
            )
        }
    }

    @Test
    fun QuackRowTag(
        @TestParameter isSelected: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[isSelected:$isSelected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackRowTag(
                title = "이런 점이 최고였어요",
                items = listOf(
                    QuackTagItem(
                        isSelected = false,
                        text = "친절하고 매너가 좋아요",
                    ),
                    QuackTagItem(
                        isSelected = false,
                        text = "답장이 빨라요",
                    ),
                    QuackTagItem(
                        isSelected = false,
                        text = "입금을 제때 해줘요",
                    ),
                    QuackTagItem(
                        isSelected = false,
                        text = "답장이 느려요",
                    ),
                    QuackTagItem(
                        isSelected = false,
                        text = "너무 늦은 시간에 연락해요",
                    ),
                    QuackTagItem(
                        isSelected = false,
                        text = "무리하게 가격을 깎아요",
                    ),
                ),
                onClick = {},
            )
        }
    }
}

