/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "TestFunctionName",
    "SpellCheckingInspection",
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
import team.duckie.quackquack.ui.component.QuackDialogMenuItem
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.rule.AnimationTestRule
import team.duckie.quackquack.ui.util.boxSnapshot
import team.duckie.quackquack.ui.util.buildPaparazzi

@Ignore(
    value = """
        Cannot read field "metaData" because "appInfo" is null
    """,
)
@RunWith(TestParameterInjector::class)
public class QuackFab {
    @get:Rule
    public val paparazzi: Paparazzi = buildPaparazzi {
        screenHeight = 500
    }

    @get:Rule
    public val animationTest: TestWatcher = AnimationTestRule()

    @Test
    public fun QuackFloatingActionButton(
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackFloatingActionButton(
                icon = QuackIcon.Dm,
                onClick = {},
            )
        }
    }

    @Test
    public fun QuackMenuFloatingActionButton(
        @TestParameter expanded: Boolean,
        @TestParameter("0.5", "1.0", "1.5", "2.0") fontScale: Double,
    ) {
        paparazzi.boxSnapshot(
            name = "[expanded:$expanded]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackMenuFloatingActionButton(
                expanded = expanded,
                onClickButton = {},
                onDismissRequest = {},
                menuItems = persistentListOf(
                    QuackDialogMenuItem(
                        icon = QuackIcon.Feed,
                        text = "피드",
                    ),
                    QuackDialogMenuItem(
                        icon = QuackIcon.Buy,
                        text = "덕딜",
                    ),
                ),
                onClickMenuItem = {},
            )
        }
    }
}
