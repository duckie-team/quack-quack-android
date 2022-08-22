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
        lintTestRule.assertErrorCount(
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
                            private val mutableListClassPrivate: MutableList<Any> = mutableListOf()
                            protected val mutableMapClassProtected: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSetClassPrivate: MutableSet<Any> = mutableSetOf()
                            internal val mutableListClassInternal: MutableList<Any> = mutableListOf()
                            val mutableMapClassPublic: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSetClassPublic: MutableSet<Any> = mutableSetOf()
                        }

                        object DummyObject {
                            private val mutableListObjectPrivate: MutableList<Any> = mutableListOf()
                            protected val mutableMapObjectProtected: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSetObjectPrivate: MutableSet<Any> = mutableSetOf()
                            internal val mutableListObjectInternal: MutableList<Any> = mutableListOf()
                            val mutableMapObjectPublic: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSetObjectPublic: MutableSet<Any> = mutableSetOf()
                        }

                        interface DummyInterface {
                            private val mutableListInterfacePrivate: MutableList<Any> = mutableListOf()
                            protected val mutableMapInterfaceProtected: MutableMap<Any, Any> = mutableMapOf()
                            private val mutableSetInterfacePrivate: MutableSet<Any> = mutableSetOf()
                            internal val mutableListInterfaceInternal: MutableList<Any> = mutableListOf()
                            val mutableMapInterfacePublic: MutableMap<Any, Any> = mutableMapOf()
                            val mutableSetInterfacePublic: MutableSet<Any> = mutableSetOf()
                        }
                        """
                ),
            ),
            issues = listOf(
                MutableCollectionPublicIssue,
            ),
            expectedCount = 9,
        )
    }

    @Test
    fun `Can use Public at Minor ImmutableCollection`() {
        lintTestRule.assertErrorCount(
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
        lintTestRule.assertErrorCount(
            files = listOf(
                composableTestFile(
                    """
                        private val mutableList1: MutableList<Any> = mutableListOf()
                        private val mutableMap1: MutableMap<Any, Any> = mutableMapOf()
                        private val mutableSet1: MutableSet<Any> = mutableSetOf()
                        protected val mutableList2: MutableList<Any> = mutableListOf()
                        protected val mutableMap2: MutableMap<Any, Any> = mutableMapOf()
                        protected val mutableSet2: MutableSet<Any> = mutableSetOf()
                        internal val mutableList3: MutableList<Any> = mutableListOf()
                        internal val mutableMap3: MutableMap<Any, Any> = mutableMapOf()
                        internal val mutableSet3: MutableSet<Any> = mutableSetOf()
                        """
                ),
            ),
            issues = listOf(
                MutableCollectionPublicIssue,
            ),
            expectedCount = 0,
        )
    }
}
