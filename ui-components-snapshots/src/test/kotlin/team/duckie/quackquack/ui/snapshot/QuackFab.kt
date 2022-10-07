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

import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Ignore
import org.junit.Rule
import org.junit.runner.RunWith
import team.duckie.quackquack.ui.snapshot.rule.AnimationTestRule
import team.duckie.quackquack.ui.snapshot.util.buildPaparazzi

@Ignore(
    value = "TODO",
)
@RunWith(TestParameterInjector::class)
class QuackFab {
    @get:Rule
    val paparazzi = buildPaparazzi {
        screenHeight = 500
    }

    @get:Rule
    val animationTest = AnimationTestRule()
}
