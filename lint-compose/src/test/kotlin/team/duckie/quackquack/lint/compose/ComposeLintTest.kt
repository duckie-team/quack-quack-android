/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemTest.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
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

class ComposeLintTest {

    @get:Rule
    val lintTestRule = LintTestRule()

    @Test
    fun preferredImmutableCollections() {
        lintTestRule
            .assertErrorCount(
                files = listOf(
                    composableTestFile(
                        """
                        @Composable
                        fun Test(list: MutableList<Any>) {}
                        """
                    ),
                ),
                issues = listOf(
                    PreferredImmutableCollectionsIssue,
                ),
                expectedCount = 1,
            )
    }
}
