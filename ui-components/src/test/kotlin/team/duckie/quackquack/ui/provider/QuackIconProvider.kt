/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.provider

import com.google.testing.junit.testparameterinjector.TestParameter
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.wrapper.NamedValue

public class QuackIconProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues(): List<NamedValue<QuackIcon>> = listOf(
        NamedValue(
            value = QuackIcon.Heart,
            name = "Heart",
        ),
        NamedValue(
            value = QuackIcon.FilledHeart,
            name = "FilledHeart",
        ),
    )
}
