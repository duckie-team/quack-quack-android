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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.snapshot.provider.NullableQuackIconProvider
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.boxSnapshot
import team.duckie.quackquack.ui.snapshot.util.buildPaparazzi
import team.duckie.quackquack.ui.snapshot.wrapper.NamedValue

@RunWith(TestParameterInjector::class)
class QuackButton {
    @get:Rule
    val paparazzi = buildPaparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()

    @Test
    fun QuackLargeButton(
        @TestParameter active: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[active:$active]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackLargeButton(
                text = "QuackLargeButton",
                active = active,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackLargeWhiteButton(
        @TestParameter(valuesProvider = NullableQuackIconProvider::class) leadingIcon: NamedValue<QuackIcon?>,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[quackicon:$leadingIcon]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackLargeWhiteButton(
                text = "QuackLargeWhiteButton",
                leadingIcon = leadingIcon.value,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackLarge40WhiteButton(
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackLarge40WhiteButton(
                text = "QuackLarge40WhiteButton",
                onClick = {},
            )
        }
    }

    @Test
    fun QuackMediumBorderToggleButton(
        @TestParameter selected: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[selected:$selected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackMediumBorderToggleButton(
                text = "QuackMediumBorderToggleButton",
                selected = selected,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackSmallButton(
        @TestParameter enabled: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[enabled:$enabled]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackSmallButton(
                text = "QuackSmallButton",
                enabled = enabled,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackSmallBorderToggleButton(
        @TestParameter selected: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[selected:$selected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackSmallBorderToggleButton(
                text = "QuackSmallBorderToggleButton",
                selected = selected,
                onClick = {},
            )
        }
    }

    @Test
    fun QuackToggleChip(
        @TestParameter selected: Boolean,
        @TestParameter("1.0", "1.5") fontScale: Float,
    ) {
        paparazzi.boxSnapshot(
            name = "[selected:$selected]-[fontScale:$fontScale]",
            fontScale = fontScale,
        ) {
            team.duckie.quackquack.ui.component.QuackToggleChip(
                text = "QuackToggleChip",
                selected = selected,
                onClick = {},
            )
        }
    }
}
