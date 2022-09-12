/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
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

/**
 * [LintTestRule] 인터페이스를 반환합니다.
 *
 * @return [LintTestRule] 구현체
 */
fun LintTestRule(): LintTestRule = LintTestRuleImpl()

/**
 * 린트 테스트를 쉽게 하기 위한 커스텀 테스트 룰
 */
interface LintTestRule : TestRule {
    /**
     * 린트 에러 개수를 검증합니다.
     *
     * @param files 테스트할 [TestFile] 목록
     * @param issues 검증할 [Issue] 목록
     * @param expectedCount 발생해야 하는 린트 에러 개수
     * @param fixedFile QuickFix 가 가능하다면 QuickFix 가 적용된 후 [TestFile]
     * 만약 QuickFix 가 불가능 하다면 null 입력, 기본값은 null
     */
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

    /**
     * [TestFile] 목록에 [ComposableAnnotationFile] 를 추가합니다.
     *
     * @receiver [ComposableAnnotationFile] 이 추가되기 전 [TestFile] 목록
     *
     * @return receiver 로 받은 [TestFile] 목록에
     * [ComposableAnnotationFile] 이 추가된 새로운 [TestFile] 목록
     */
    private fun List<TestFile>.toComposableTestableFiles() = ArrayList(this).apply {
        add(ComposableAnnotationFile)
    }.toTypedArray()
}
