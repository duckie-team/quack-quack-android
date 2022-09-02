/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [RequireModifierTest.kt] created by limsaehyun on 22. 9. 2. 오후 12:33
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "TestFunctionName"
)

package team.duckie.quackquack.lint.compose

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. Composable 함수여야 함
 * 2. parameter 타입으로 Modifier가 없을 경우 에러 방출
 */
class RequireModifierTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Modifier does NOT exist in parameter`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        @Composable
                        fun success1(
                            modifier: Modifier,
                            a: String,
                        ) {}

                        @Composable
                        fun success2(
                            testModifier: Modifier,
                        ) {}

                        @Composable
                        fun failed1() {}

                        @Composable
                        fun failed2(
                            modifier: String,
                        ) {}
                """.trimIndent())
            ),
            issues = listOf(
                RequireModifierIssue,
            ),
            expectedCount = 2,
        )
    }
}
