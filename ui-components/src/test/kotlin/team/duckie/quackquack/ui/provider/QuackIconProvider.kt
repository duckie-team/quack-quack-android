/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [LeadingIconProvider.kt] created by Ji Sungbin on 22. 9. 2. 오전 8:51
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.provider

import com.google.testing.junit.testparameterinjector.TestParameter
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.wrapper.QuackIconWrapper

class QuackIconProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues() = listOf(
        QuackIconWrapper(
            value = null,
            name = "none",
        ),
        QuackIconWrapper(
            value = QuackIcon.Close,
            name = "close",
        ),
    )
}
