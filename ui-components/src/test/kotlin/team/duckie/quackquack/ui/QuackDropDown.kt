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
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackDropDown {

    @get:Rule
    val paparazzi = buildPaparazzi()
    @Test
    fun QuackSimpleDropDown(
        @TestParameter("완료","거래중") title: String,
    ) {
        paparazzi.boxSnapshot(
            name = "[title:$title]",
        ) {
            team.duckie.quackquack.ui.component.QuackDropDown(
                title = title,
                onClick = {}
            )
        }
    }
}
