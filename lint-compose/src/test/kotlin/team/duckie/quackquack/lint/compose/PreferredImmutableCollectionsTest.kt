/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "TestFunctionName")

package team.duckie.quackquack.lint.compose

import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.common.lint.test.LintTestRule
import team.duckie.quackquack.common.lint.test.composableTestFile

/**
 * 테스트 성공 조건
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블을 방출하는 역할이여야 함
 * 3. "List", "Map", "Set" 타입이 포함된 인자를 대상으로 경고해야 함 (MutableCollections)
 * 4. "Immutable", "Persistent" 타입이 포함된 인자는 경고하지 말아야 함 (ImmutableCollections)
 */
class PreferredImmutableCollectionsTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun `Composable function`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun list(list: MutableList<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    PreferredImmutableCollectionsIssue,
                ),
                expectedCount = 1,
            )
    }

    @Test
    fun `Composable function but not emitting composable`() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun list(list: MutableList<Any>) = list
                        """
                    ),
                ),
                issues = listOf(
                    PreferredImmutableCollectionsIssue,
                ),
                expectedCount = 0,
            )
    }

    @Test
    fun `Warning towards MutableCollections`() {
        // MutableCollections: "List", "Map", "Set"
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun mutableList(list: MutableList<Any>) {}
                        @Composable
                        fun mutableMap(map: MutableMap<Any, Any>) {}
                        @Composable
                        fun mutableSet(set: MutableSet<Any>) {}
                        @Composable
                        fun list(list: List<Any>) {}
                        @Composable
                        fun map(map: Map<Any, Any>) {}
                        @Composable
                        fun set(set: Set<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    PreferredImmutableCollectionsIssue,
                ),
                expectedCount = 6,
            )
    }

    @Test
    fun `Do NOT warning towards ImmutableCollections`() {
        // MutableCollections: "List", "Map", "Set"
        // ImmutableCollections: "Immutable", "Persistent"
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun mutableList(list: ImmutableList<Any>) {}
                        @Composable
                        fun mutableMap(map: ImmutableMap<Any, Any>) {}
                        @Composable
                        fun mutableSet(set: ImmutableSet<Any>) {}
                        @Composable
                        fun persistentList(list: PersistentList<Any>) {}
                        @Composable
                        fun persistentMap(map: PersistentMap<Any, Any>) {}
                        @Composable
                        fun persistentSet(set: PersistentSet<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    PreferredImmutableCollectionsIssue,
                ),
                expectedCount = 0,
            )
    }
}
