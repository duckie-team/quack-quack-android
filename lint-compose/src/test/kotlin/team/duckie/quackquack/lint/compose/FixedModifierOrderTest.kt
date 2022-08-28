/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [FixedModifierOrderTest.kt] created by riflockle7 on 22. 8. 28. 오후 11:18
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
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
 * 3. 컴포저블 함수에서 1번째 매개변수는 Modifier 타입 변수여야 함 (Modifier 타입 변수가 인자로 없는 경우는 고려하지 않음)
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
                        fun list(modifier: Modifier) {}
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
                        @Composable
                        fun list(modifier: Modifier, list: MutableList<Any>) = list
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
    fun `Composable function's First Parameter type is Modifier`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun firstParameterIsModifier(modifier: Modifier, list: MutableList<Any>)

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
