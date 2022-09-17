/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackTopAppBar {

    @get:Rule
    val paparazzi = buildPaparazzi()

    @Test
    fun QuackSimpleTopAppBar(
    ){
        paparazzi.boxSnapshot(
            name = "SimpleTopAppBar"
        ){
            team.duckie.quackquack.ui.component.QuackTopAppBar(
                leadingIcon = QuackIcon.ArrowBack,
                onClickLeadingIcon = {},
            )
        }

    }
}
