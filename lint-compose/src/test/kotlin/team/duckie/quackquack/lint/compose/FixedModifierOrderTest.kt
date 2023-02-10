/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.lint.compose

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블을 방출하는 역할이여야 함
 * 3. Composable 함수는 첫 번째 매개변수로 Modifier 만 사용해야 함 (Modifier 타입 변수가 인자로 없는 경우는 고려하지 않음)
 * 4. Composable 함수의 첫 번째 매개변수가 Modifier 가 아닌 경우 오류가 발생함
 */
class FixedModifierOrderTest {
    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Must Composable function`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun modifier(modifier: Modifier) {}
                        """
                    ),
                ),
                issues = listOf(
                    FixedModifierOrderIssue,
                ),
                expectedCount = 0,
            )
    }

    @Test
    fun `Composable function but not emitting composable`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        fun modifier(modifier: Modifier, any: Any) = modifier
                        """
                    ),
                ),
                issues = listOf(
                    FixedModifierOrderIssue,
                ),
                expectedCount = 0,
            )
    }

    @Test
    fun `The Composable function takes only Modifier as the first parameter`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun firstParameterIsModifier(modifier: Modifier, list: MutableList<Any>)
                        """
                    ),
                ),
                issues = listOf(
                    FixedModifierOrderIssue,
                ),
                expectedCount = 0,
            )
    }

    @Test
    fun `If the first parameter of the Composable function is not a Modifier, throws an error`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun secondParameterIsModifier(list: MutableList<Any>, modifier: Modifier)
                        """
                    ),
                ),
                issues = listOf(
                    FixedModifierOrderIssue,
                ),
                expectedCount = 1,
            )
    }
}
