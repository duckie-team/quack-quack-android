/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MutableCollectionPublicTest.kt] created by ricky_0_k on 22. 8, 21. 오전 1:16
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "TestFunctionName")

package team.duckie.quackquack.lint.core

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. MutableCollection 의 접근 제어 범위가 public 일 시 경고해야 함 (특정 범위 내에서도 마찬가지)
 * 2. Immutable, Persistence 등 Minor 한 ImMutableCollection 은 경고하지 않음
 * 3. 접근 제어 범위가 public 이 아닐경우 경고하지 않음
 */
class MutableCollectionPublicTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Don't use Public at MutableCollection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        val mutableList: MutableList<Any> = mutableListOf()
                        val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                        val mutableSet: MutableSet<Any> = mutableSetOf()
                        val list: List<Any> = listOf()
                        val map: Map<Any, Any> = mapOf()
                        val set: Set<Any> = setOf()

                        class DummyClass {
                            private val mutableList: MutableList<Any> = mutableListOf()
                            protected val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSet: MutableSet<Any> = mutableSetOf()
                            internal val mutableList: MutableList<Any> = mutableListOf()
                            val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSet: MutableSet<Any> = mutableSetOf()
                        }

                        object DummyObject {
                            private val mutableList: MutableList<Any> = mutableListOf()
                            protected val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSet: MutableSet<Any> = mutableSetOf()
                            internal val mutableList: MutableList<Any> = mutableListOf()
                            val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSet: MutableSet<Any> = mutableSetOf()
                        }

                        interface DummyInterface {
                            private val mutableList: MutableList<Any> = mutableListOf()
                            protected val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSet: MutableSet<Any> = mutableSetOf()
                            internal val mutableList: MutableList<Any> = mutableListOf()
                            val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSet: MutableSet<Any> = mutableSetOf()
                        }
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 3,
            )
    }

    @Test
    fun `Can use Public at Minor ImmutableCollection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        val mutableList: ImmutableList<Any>
                        val mutableMap: ImmutableMap<Any, Any>
                        val mutableSet: ImmutableSet<Any>
                        val list: PersistentList<Any>
                        val map: PersistentMap<Any, Any>
                        val set: PersistentSet<Any>
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 0,
            )
    }

    @Test
    fun `Dont't use Public, Internal at MutableCollection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        private val mutableList: MutableList<Any> = mutableListOf()
                        private val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                        private val mutableSet: MutableSet<Any> = mutableSetOf()
                        protected val mutableList: MutableList<Any> = mutableListOf()
                        protected val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                        protected val mutableSet: MutableSet<Any> = mutableSetOf()
                        internal val mutableList: MutableList<Any> = mutableListOf()
                        internal val mutableMap: MutableMap<Any, Any> = mutableMapOf()
                        internal val mutableSet: MutableSet<Any> = mutableSetOf()
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 3,
            )
    }
}
