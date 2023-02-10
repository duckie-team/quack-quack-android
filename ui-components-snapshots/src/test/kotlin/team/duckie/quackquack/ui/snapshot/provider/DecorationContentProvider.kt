/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.provider

import androidx.compose.runtime.Composable
import com.google.testing.junit.testparameterinjector.TestParameter
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.wrapper.NamedValue

class DecorationContentProvider : TestParameter.TestParameterValuesProvider {
    override fun provideValues(): List<NamedValue<(@Composable () -> Unit)?>> =
        listOf(
            NamedValue(
                value = null,
                name = "none",
            ),
            NamedValue(
                value = {
                    QuackImage(
                        src = QuackIcon.FilledHeart,
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

