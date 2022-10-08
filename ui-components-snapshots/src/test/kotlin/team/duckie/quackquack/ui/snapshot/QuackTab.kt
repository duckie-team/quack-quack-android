/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "JUnitMalformedDeclaration",
    "TestFunctionName",
)

package team.duckie.quackquack.ui.snapshot

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.snapshot.provider.QuackDeviceConfig
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.paparazzi

@RunWith(TestParameterInjector::class)
class QuackTab {
    @get:Rule
    val paparazzi = paparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackMainTab(
        @TestParameter("0", "1", "2") selectedTabIndex: Int,
        @TestParameter("1.0", "1.5") fontScale: Float,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
            fontScale = fontScale,
            deviceConfig = deviceConfig,
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
    fun QuackSubTab(
        @TestParameter("0", "1", "2") selectedTabIndex: Int,
        @TestParameter("1.0", "1.5") fontScale: Float,
        @TestParameter deviceConfig: QuackDeviceConfig,
    ) {
        paparazzi.boxSnapshot(
            name = "[selectedTabIndex:$selectedTabIndex]-[fontScale:$fontScale]",
            fontScale = fontScale,
            deviceConfig = deviceConfig,
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
