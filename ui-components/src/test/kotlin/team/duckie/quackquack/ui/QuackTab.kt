/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackTab.kt] created by Ji Sungbin on 22. 8. 31. 오후 5:34
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("TestFunctionName")

package team.duckie.quackquack.ui

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.typography.LocalQuackFontScale

@RunWith(TestParameterInjector::class)
class QuackTab {
    @get:Rule
    val paparazzi = paparazzi()

    // Needs Paparazzi#gif: https://github.com/cashapp/paparazzi/issues/496
    @Test
    fun QuackMainTab(
        @TestParameter("0", "1", "2") selectedTabIndex: Int,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.snapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
        ) {
            CompositionLocalProvider(
                LocalQuackFontScale provides fontScale.toLong(),
            ) {
                team.duckie.quackquack.ui.component.QuackMainTab(
                    titles = listOf(
                        "판매중",
                        "거래완료",
                        "숨김",
                    ),
                    tabStartHorizontalPadding = 16.dp,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = {},
                )
            }
        }
    }

    @Test
    fun QuackMainTabOverflow(
        @TestParameter("0", "1", "2", "3", "4") selectedTabIndex: Int,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.snapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
        ) {
            CompositionLocalProvider(
                LocalQuackFontScale provides fontScale.toLong(),
            ) {
                team.duckie.quackquack.ui.component.QuackMainTab(
                    titles = listOf(
                        "판매중",
                        "거래완료",
                        "숨김",
                        "더보기",
                        "내정보",
                    ),
                    tabStartHorizontalPadding = 16.dp,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = {},
                )
            }
        }
    }
}
