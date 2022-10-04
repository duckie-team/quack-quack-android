/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "TestFunctionName")

package team.duckie.quackquack.lint.quack

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. "Button" to "QuackLargeButton"
 * 2. 컴포저블 함수 안에서 실행되야 함
 * 3. Quack 디자인 컴포넌트로 QuickFix 가 가능해야 함
 */
class QuackLargeButtonTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Composable function and QuickFixable`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun Button() {}
                        @Composable
                        fun Main() {
                            Button()
                        }
                        """
                    ),
                ),
                issues = listOf(
                    DesignSystemIssue,
                ),
                expectedCount = 1,
                fixedFile = composableTestFile(
                    """
                    @Composable
                    fun Button() {}
                    @Composable
                    fun Main() {
                        QuackLargeButton()
                    }
                    """
                )
            )
    }

    @Test
    fun `Normal function`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        fun Button() {}
                        fun Main() {
                            Button()
                        }
                    """
                    ),
                ),
                issues = listOf(
                    DesignSystemIssue,
                ),
                expectedCount = 0,
            )
    }
}
