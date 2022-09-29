/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "TestFunctionName",
    "JUnitMalformedDeclaration",
)

package team.duckie.quackquack.ui

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.collections.immutable.persistentListOf
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@Ignore(
    value = """
        Cannot invoke "String.toUpperCase(java.util.Locale)" because "buildCodename" is null
    """,
)
@RunWith(TestParameterInjector::class)
public class QuackTab {
    @get:Rule
    public val paparazzi: Paparazzi = buildPaparazzi()

    @get:Rule
    public val animationTest: TestWatcher = AnimationTestRule()

    @Test
    public fun QuackMainTab(
        @TestParameter("0", "1", "2") selectedTabIndex: Int,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackMainTab(
                titles = persistentListOf(
                    "판매중",
                    "거래완료",
                    "숨김",
                ),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = {},
            )
        }
    }

    @Test
    public fun QuackSubTab(
        @TestParameter("0", "1", "2") selectedTabIndex: Int,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackSubTab(
                titles = persistentListOf(
                    "피드",
                    "컬렉션",
                    "좋아요",
                ),
                selectedTabIndex = selectedTabIndex,
                onTabSelected = {},
            )
        }
    }
}
