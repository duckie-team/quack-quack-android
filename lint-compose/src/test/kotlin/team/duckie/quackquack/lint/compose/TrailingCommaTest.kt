/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TrailingCommaTest.kt] created by limsaehyun on 22. 8. 29. 오전 8:21
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "TestFunctionName")

package team.duckie.quackquack.lint.compose

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. Composable 함수여야 함
 * 2. 매개변수에서 후행 ','를 사용해야 함
 * 3. 인자에서 후행 ','를 사용해야 함
 * 4. 인자가 [LimitParameterNumber] [LimitArgumentNumber] 보다 더 적게 있을 경우 린트가 작동하지 않음
 */

class TrailingCommaTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Comma NOT behind parameter`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        @Composable
                        fun success1(a: String,) {}

                        @Composable
                        fun success2(a: String) {}

                        @Composable
                        fun success3(
                            a: String,
                            b: String,
                        ) {}

                        @Composable
                        fun failed1(
                            a: String,
                            b: String
                        ) {}

                        @Composable
                        fun failed2(
                            a: String,
                            b: String,
                            c: String
                        )
                    """.trimIndent()
                )
            ),
            issues = listOf(
                TrailingCommaIssue
            ),
            expectedCount = 2
        )
    }

    @Test
    fun `Comma NOT behind argument`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                       private fun test(a: Int = 0, b: Int = 0) {}

                        @Composable
                        fun success1() {
                           test(0,)
                        }

                        @Composable
                        fun success2() {
                           test(0)
                        }

                        @Composable
                        fun success3() {
                           test(0, 0,)
                        }


                        @Composable
                        fun failed1() {
                            test(0, 0)
                        }
                    """.trimIndent()
                )
            ),
            issues = listOf(
                TrailingCommaIssue
            ),
            expectedCount = 1
        )
    }
}
