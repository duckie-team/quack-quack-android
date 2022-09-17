/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

/**
@RunWith(TestParameterInjector::class)
class QuackSelectableImage {

    @get:Rule
    val paparazzi = buildPaparazzi()

    @Test
    fun QuackSelectableImage(
        @TestParameter("true","false") isSelected: Boolean,
    ) {
        paparazzi.boxSnapshot(
            name = "[isSelected:$isSelected]",
        ) {
            team.duckie.quackquack.ui.component.QuackSelectableImage(
                size = 118.dp,
                image = "https://picsum.photos/id/237/200/300",
                isSelected = isSelected,
                onClick = {},
            )
        }
    }
}
 */
