/*
 * Designed and developed by Duckie Team, 2022
 *
 * [SpecifyLambdaParamsNameTest.kt] created by ricky_0_k on 22. 9, 11. 오후 3:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("NonAsciiCharacters")

package team.duckie.quackquack.lint.core

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 람다 내에 무조건 params 명이 명시되어야 함 (it 일 경우 에러)
 * 2. 람다 내에 최소 1개 이상의 param 이 있어야 함
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

    @Test
    fun `최소 1개 이상의 param 을 가지고 있을 때만 린트 체크를 진행함`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        private fun emptyLambda(lambda: () -> Unit) {
                            lambda()
                        }
                        fun main() {
                            emptyLambda {
                                println("empty lambda")
                             }
                        }
                    """
                ),
            ),
            issues = listOf(
                SpecifyLambdaParamsNameIssue,
            ),
            expectedCount = 0,
        )
    }

    @Test
    fun `암시적 it 이 있지만 사용되지 않았다면 에러가 발생하지 않음`() {
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                    private fun unusedLambda(lambda: (value: Int) -> Unit) {
                        lambda(11)
                    }
                    fun main() {
                        unusedLambda {
                            println("unused lambda")
                         }
                    }
                """
                ),
            ),
            issues = listOf(
                SpecifyLambdaParamsNameIssue,
            ),
            expectedCount = 0,
        )
    }
}
