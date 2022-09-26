/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.provider

import androidx.compose.runtime.Composable
import com.google.testing.junit.testparameterinjector.TestParameter
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.wrapper.NamedValue

class DecorationContentProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues() = listOf<NamedValue<(@Composable () -> Unit)?>>(
        NamedValue(
            value = null,
            name = "none",
        ),
        NamedValue(
            value = {
                QuackImage(
                    icon = QuackIcon.FilledHeart,
                )
            },
            name = "icon",
        ),
        NamedValue(
            value = {
                QuackBody1(
                    text = "LongDecorationContent",
                )
            },
            name = "long-text",
        ),
    )
}

