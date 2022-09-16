/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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
 * 2. 컴포저블 안에서 invoke 돼야 함
 * 3. 코틀린 함수여야 함
 * 4. 모든 인자에 Named Argument가 있어야 함
 */

class NamedArgumentTest {
    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `NOT using named argument`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                            @Composable
                            fun MyComposable(
                                a: String,
                                b: Int,
                                onClick: () -> Unit = {},
                            ) {}

                            fun testFunction(
                                a: String,
                                b: Int,
                            ) {}

                            @Composable
                            fun success1() {
                                MyComposable(
                                    a = "success",
                                    b = 1,
                                ) {

                                }
                            }

                            @Composable
                            fun success2() {
                                testFunction()(
                                    "success",
                                    123,
                                )
                            }

                            @Composable
                            fun success3() {
                                MyComposable(
                                    a = "success",
                                    b = 123,
                                    onClick = { },
                                )
                            }

                            @Composable
                            fun failed1() {
                                MyComposable(
                                    "success",
                                    123,
                                )
                            }

                            @Composable
                            fun failed2() {
                                MyComposable(
                                    a = "success",
                                    123,
                                )
                            }

                            @Composable
                            fun failed3() {
                                MyComposable(
                                    "success",
                                    b = 123,
                                )
                            }
                        """.trimIndent()
                    ),
                ),
                issues = listOf(
                    NamedArgumentIssue,
                ),
                expectedCount = 3,
            )
    }
}
