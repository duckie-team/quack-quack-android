/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
 * 2. parameter가 전부 new line에 배치되여야 함
 * 3. argument가 전부 new line 배치되어야 함
 * 4. last argument가 LAMDA_EXPRESSION일 경우 에러가 발생되지 않아야 함
 * 5. argument type이 REFERENCES_EXPRESSION일 경우 에러가 발생되지 않아야 함
 */
class NewLineArgumentTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Parameter Not placed in new line`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                            @Composable
                            fun success1() {}

                            @Composable
                            fun success2(
                                a: String,
                            ) {}
                            @Composable
                            fun success3(
                                a: String,
                                b: String,
                                c: String,
                            ) {}

                            @Composable
                            fun failed1(a: String,) {}

                            @Composable
                            fun failed2(
                                a: String, b: String,
                                c: String,
                            ) {}

                            @Composable
                            fun failed3(a: String, b: String, c: String,) {}
                    """.trimIndent()
                )
            ),
            expectedCount = 3,
            issues = listOf(
                NewLineArgumentIssue
            ),
        )
    }

    @Test
    fun `Argument Not placed in new line`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                            private fun test(a: String = "", b: Int = 0, func: () -> Unit) {}

                            @Composable
                            fun success1() {
                                (1..10).map(Int::toString)
                            }

                            @Composable
                            fun success2() {
                                test(
                                    a = "example",
                                    b = 1000,
                                ) {
                                    print("example")
                                }
                            }

                            @Composable
                            fun success3() {
                                test(
                                    a = "test",
                                    b = 1000,
                                )
                            }

                            @Composable
                            fun success4() {
                                test(
                                    a = "test",
                                    b = 1000,
                                    c = 1000,
                                )
                            }

                            @Composable
                            fun failed1() {
                                test(a = "test",)
                            }

                            @Composable
                            fun failed2() {
                                test(a = "test", b = 1000,)
                            }

                            @Composable
                            fun failed3() {
                                test(
                                    a = "test", b = 1000,
                                    c = 1000,
                                )
                            }
                    """.trimIndent()
                )
            ),
            expectedCount = 3,
            issues = listOf(
                NewLineArgumentIssue
            ),
        )
    }
}
