/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemDetector.kt] created by Ji Sungbin on 22. 8. 16. 오후 6:33
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.lint.compose

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement

@Suppress("UnstableApiUsage") // Detector
class DesignSystemDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(UCallExpression::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitCallExpression(node: UCallExpression) {
            val name = node.methodName ?: return
            val preferredName = methods[name] ?: return
            reportIssue(
                context = context,
                node = node,
                currentName = name,
                preferredName = preferredName,
            )
        }
    }

    private val methods = mapOf(
        "Button" to "QuackLargeButton",
    )

    private fun reportIssue(
        context: JavaContext,
        node: UElement,
        currentName: String,
        preferredName: String,
    ) {
        val quickfix = LintFix.create()
            .name("Use $preferredName")
            .replace()
            .text(currentName)
            .with(preferredName)
            .robot(true)
            .independent(true)
            .build()

        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "Using $preferredName instead of $currentName",
            quickfixData = quickfix
        )
    }

    companion object {
        @JvmField
        val ISSUE = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "Jetpack Compose 의 foundation 컴포저블 대신에 " +
                "QuackQuack 디자인 시스템의 컴포저블을 사용해야 합니다.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
