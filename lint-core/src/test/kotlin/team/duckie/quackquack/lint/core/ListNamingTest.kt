/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "TestFunctionName",
)

package team.duckie.quackquack.lint.core

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 변수의 타입이 Collection 이여야 함
 * 2. 네이밍 접미사로 s 가 사용되야 함
 */
class ListNamingTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Do NOT specify 's' at the end of the list naming`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        val peopleList: List<String> = emptyList<String>()
                        val manyPeople = emptyList<String>()

                        val peoples = emptyList<String>()
                        val people = String()
                    """.trimIndent()
                )
            ),
            expectedCount = 2,
            issues = listOf(
                ListNamingIssue,
            ),
        )
    }
}
