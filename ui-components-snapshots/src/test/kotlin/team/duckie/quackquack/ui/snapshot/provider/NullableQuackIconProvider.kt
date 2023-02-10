/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.provider

import com.google.testing.junit.testparameterinjector.TestParameter
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.wrapper.NamedValue

class NullableQuackIconProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues(): List<NamedValue<QuackIcon?>> = listOf(
        NamedValue(
            value = null,
            name = "none",
        ),
        NamedValue(
            value = QuackIcon.Heart,
            name = "Heart",
        ),
    )
}
