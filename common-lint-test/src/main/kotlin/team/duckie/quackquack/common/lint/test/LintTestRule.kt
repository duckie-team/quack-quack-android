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
import com.android.tools.lint.checks.infrastructure.TestMode
import com.android.tools.lint.detector.api.Issue
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import team.duckie.quackquack.common.runIf

fun LintTestRule(): LintTestRule = LintTestRuleImpl()

interface LintTestRule : TestRule {
    fun assertErrorCount(
        files: List<TestFile>,
        issues: List<Issue>,
        expectedCount: Int,
        fixedFile: TestFile? = null,
    )
}

private class LintTestRuleImpl : LintTestRule {
    override fun apply(base: Statement, description: Description) = base

    override fun assertErrorCount(
        files: List<TestFile>,
        issues: List<Issue>,
        expectedCount: Int,
        fixedFile: TestFile?,
    ) {
        TestLintTask
            .lint()
            .allowMissingSdk()
            .testModes(TestMode.DEFAULT)
            .files(*files.toComposableTestableFiles())
            .issues(*issues.toTypedArray())
            .run()
            .expectErrorCount(expectedCount)
            .runIf(fixedFile != null) {
                checkFix(
                    fix = null, // 첫 번째 QuickFix 로 자동 선택됨
                    after = fixedFile!!
                )
            }
    }

    private fun List<TestFile>.toComposableTestableFiles() = ArrayList(this).apply {
        add(ComposableAnnotationFile)
    }.toTypedArray()
}
