/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [SpecifyLambdaParamsNameTest.kt] created by ricky_0_k on 22. 9, 11. 오후 3:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.core

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 람다 내에 무조건 params 명이 명시되어야 함 (it 일 경우 에러)
 */
class SpecifyLambdaParamsNameTest {
    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `specify lambda params name`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        var list = arrayListOf(Pair(0, 1), Pair(0, 0))
                        var newList = list.sortedWith(compareBy({ it.first }, { it.second }))

                        fun function(){
                            val success = listOf(1, 2, 3).forEach { variableName ->

                            }

                            val sum: (Int, Int) -> Int = { x: Int, it: Int -> x + it }

                            listOf(1, 2, 3).forEach {
                                it.toDouble()
                            }

                            listOf(1, 2, 3).filter { item ->
                                item % 2 == 0
                            }.map { even ->
                                even.toString()
                            }
                        }
                    """
                ),
            ),
            issues = listOf(
                SpecifyLambdaParamsNameIssue,
            ),
            expectedCount = 4,
        )
    }
}
