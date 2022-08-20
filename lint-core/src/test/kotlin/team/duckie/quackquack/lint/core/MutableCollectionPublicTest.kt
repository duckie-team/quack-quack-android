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
import team.duckie.quackquack.common.lint.test.FileType
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.defaultTestFile

/**
 * 테스트 성공 조건
 * 1. Mutable 키워드가 붙은 Collection 에 public, internal 이 붙을 시 경고해야 함
 * 2. Immutable, Persistence 등 Minor 한 Immutable Collection 은 경고하지 않음
 * 3. public, internal 사용하지 않을 경우 경고하지 않음
 * 4. class, object, interface 내 Mutable Collection 변수에 public, internal 이 붙을 시 경고해야 함
 * 5. Function 내의 parameter 의 접근 제어자는 public 이 아님 (package local)
 * 6. Compose Function 내의 parameter 의 접근 제어자는 public 이 아님 (package local)
 */
class MutableCollectionPublicTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Don't use Public at Mutable Collection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
                        """
                        val mutableList: MutableList<Any>
                        val mutableMap: MutableMap<Any, Any>
                        val mutableSet: MutableSet<Any>
                        val list: List<Any>
                        val map: Map<Any, Any>
                        val set: Set<Any>
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 3,
                fileType = FileType.Default,
            )
    }

    @Test
    fun `Can use Public at Minor Immutable Collection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
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
                fileType = FileType.Default,
            )
    }

    @Test
    fun `Dont't use Public, Internal at Mutable Collection`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
                        """
                        private val mutableList: MutableList<Any>
                        private val mutableMap: MutableMap<Any, Any>
                        private val mutableSet: MutableSet<Any>
                        protected val mutableList: MutableList<Any>
                        protected val mutableMap: MutableMap<Any, Any>
                        protected val mutableSet: MutableSet<Any>
                        internal val mutableList: MutableList<Any>
                        internal val mutableMap: MutableMap<Any, Any>
                        internal val mutableSet: MutableSet<Any>
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 3,
                fileType = FileType.Default,
            )
    }

    @Test
    fun `Don't use Public at Mutable Collection in class, object, interface`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
                        """
                        class DummyClass {
                            private val mutableList: MutableList<Any>
                            protected val mutableMap: MutableMap<Any, Any>
                            private val mutableSet: MutableSet<Any>
                            internal val mutableList: MutableList<Any>
                            val mutableMap: MutableMap<Any, Any>
                            val mutableSet: MutableSet<Any>
                        }

                        object DummyObject {
                            private val mutableList: MutableList<Any>
                            protected val mutableMap: MutableMap<Any, Any>
                            private val mutableSet: MutableSet<Any>
                            internal val mutableList: MutableList<Any>
                            val mutableMap: MutableMap<Any, Any>
                            val mutableSet: MutableSet<Any>
                        }

                        interface DummyInterface {
                            private val mutableList: MutableList<Any>
                            protected val mutableMap: MutableMap<Any, Any>
                            private val mutableSet: MutableSet<Any>
                            internal val mutableList: MutableList<Any>
                            val mutableMap: MutableMap<Any, Any>
                            val mutableSet: MutableSet<Any>
                        }
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 9,
                fileType = FileType.Default,
            )
    }

    @Test
    fun `Function parameter is not public`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
                        """
                        fun mutableList(val list: MutableList<Any>) {}
                        fun mutableMap(val map: MutableMap<Any, Any>) {}
                        fun mutableSet(val set: MutableSet<Any>) {}
                        fun mutableList(private val list: MutableList<Any>) {}
                        fun mutableMap(private val map: MutableMap<Any, Any>) {}
                        fun mutableSet(private val set: MutableSet<Any>) {}
                        fun list(val list: MutableList<Any>) {}
                        fun map(val map: MutableMap<Any, Any>) {}
                        fun set(val set: MutableSet<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 0,
                fileType = FileType.Default,
            )
    }

    @Test
    fun `Compose Function parameter is not public`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    defaultTestFile(
                        """
                        @Composable
                        fun mutableList(val list: MutableList<Any>) {}
                        @Composable
                        fun mutableMap(val map: MutableMap<Any, Any>) {}
                        @Composable
                        fun mutableSet(val set: MutableSet<Any>) {}
                        @Composable
                        fun mutableList(private val list: MutableList<Any>) {}
                        @Composable
                        fun mutableMap(private val map: MutableMap<Any, Any>) {}
                        @Composable
                        fun mutableSet(private val set: MutableSet<Any>) {}
                        @Composable
                        fun list(list: MutableList<Any>) {}
                        @Composable
                        fun map(map: MutableMap<Any, Any>) {}
                        @Composable
                        fun set(set: MutableSet<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    MutableCollectionPublicIssue,
                ),
                expectedCount = 0,
                fileType = FileType.ComposableAnnotation,
            )
    }
}
