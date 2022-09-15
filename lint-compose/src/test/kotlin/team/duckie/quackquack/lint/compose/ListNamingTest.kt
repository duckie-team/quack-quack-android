/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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
 * 1. Variable의 Type이 List여야 함
 * 2. 네이밍 끝에 's'가 명시되어야 함
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
                        fun List<Any>.test() {}

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
