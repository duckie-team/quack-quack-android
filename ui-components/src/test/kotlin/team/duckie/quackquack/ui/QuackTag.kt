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
    "MemberNameEqualsClassName",
)

package team.duckie.quackquack.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlin.random.Random
import kotlinx.collections.immutable.persistentListOf
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.provider.QuackIconProvider
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi
import team.duckie.quackquack.ui.wrapper.NamedValue

@Ignore(
    value = """
        Cannot read field "metaData" because "appInfo" is null
    """,
)
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
            val items = remember {
                persistentListOf(
                    "친절하고 매너가 좋아요",
                    "답장이 빨라요",
                    "입금을 제때 해줘요",
                    "답장이 느려요",
                    "너무 늦은 시간에 연락해요",
                    "무리하게 가격을 깎아요",
                )
            }
            val itemsSelection = remember {
                mutableStateListOf(
                    elements = Array(
                        size = items.size,
                        init = { Random.nextBoolean() },
                    )
                )
            }

            team.duckie.quackquack.ui.component.QuackRowTag(
                title = "이런 점이 최고였어요",
                items = items,
                itemsSelection = itemsSelection,
                onClick = {},
            )
        }
    }
}

