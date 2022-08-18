/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [LintTestRule.kt] created by Ji Sungbin on 22. 8. 19. 오전 7:31
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "FunctionName",
    "UnstableApiUsage",
)

package team.duckie.quackquack.common.lint.test

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Issue
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

fun LintTestRule(): LintTestRule = LintTestRuleImpl()

interface LintTestRule : TestRule {
    fun assertErrorCount(
        files: List<TestFile>,
        issues: List<Issue>,
        expectedCount: Int,
    )
}

private class LintTestRuleImpl : TestWatcher(), LintTestRule {
    private lateinit var lint: TestLintTask

    override fun starting(description: Description) {
        lint = TestLintTask.lint().allowMissingSdk()
    }

    override fun assertErrorCount(
        files: List<TestFile>,
        issues: List<Issue>,
        expectedCount: Int,
    ) {
        lint
            .files(*files.addComposableAnnotation())
            .issues(*issues.toTypedArray())
            .run()
            .expectErrorCount(expectedCount)
    }

    private fun List<TestFile>.addComposableAnnotation() = ArrayList(this).apply {
        add(ComposableAnnotationFile)
    }.toTypedArray()
}
