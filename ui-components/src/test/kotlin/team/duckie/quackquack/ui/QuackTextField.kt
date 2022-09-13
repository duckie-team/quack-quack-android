/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("TestFunctionName")

package team.duckie.quackquack.ui

import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.textstyle.QuackFontScale
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackTextField {
    @get:Rule
    val paparazzi = buildPaparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackBasicTextField(
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
        @TestParameter("0", "100", "200", "300") height: Int,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]-[height:$height]",
        ) {
            QuackFontScale = fontScale
            team.duckie.quackquack.ui.component.QuackBasicTextField(
                text = "QuackBasicTextField",
                onTextChanged = {},
                height = QuackHeight.Custom(
                    height = height.dp,
                ),
                leadingContent = {
                    QuackImage(
                        icon = QuackIcon.ImageEditBg,
                    )
                },
                trailingContent = {
                    QuackImage(
                        icon = QuackIcon.Area,
                    )
                },
            )
        }
    }
}
