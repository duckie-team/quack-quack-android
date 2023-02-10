/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
import team.duckie.quackquack.ui.snapshot.util.paparazzi

@Ignore(
    value = "TODO",
)
@RunWith(TestParameterInjector::class)
class QuackFab {
    @get:Rule
    val paparazzi = paparazzi()

    @get:Rule
    val animationTest = AnimationTestRule()
}
