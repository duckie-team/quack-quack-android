/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.component.BottomNavigationItem
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@RunWith(TestParameterInjector::class)
class QuackBottomNavigation {

    @get:Rule
    val paparazzi = buildPaparazzi()

    @Test
    fun QuackSimpleBottomNavigation(
        @TestParameter("0","1","2","3") selectedIndex: Int,
    ){

        paparazzi.boxSnapshot(
            name = "[selectedIndex]:$selectedIndex]",
        ){
            team.duckie.quackquack.ui.component.QuackBottomNavigation(
                icons = persistentListOf(
                    BottomNavigationItem(
                        defaultIcon = QuackIcon.ArrowDown,
                        selectedIcon = QuackIcon.ImageEdit,
                    ),
                    BottomNavigationItem(
                        defaultIcon = QuackIcon.Setting,
                        selectedIcon = QuackIcon.Delete,
                    ),
                    BottomNavigationItem(
                        defaultIcon = QuackIcon.ArrowRight,
                        selectedIcon = QuackIcon.Camera,
                    ),
                    BottomNavigationItem(
                        defaultIcon = QuackIcon.Feed,
                        selectedIcon = QuackIcon.MarketPrice,
                    ),
                ),
                onClick = {},
                selectedIndex = selectedIndex,
            )
        }
    }
}
