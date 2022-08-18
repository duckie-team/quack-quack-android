/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemDetector.kt] created by Ji Sungbin on 22. 8. 16. 오후 6:33
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.quack

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import team.duckie.quackquack.common.lint.compose.isComposable
import team.duckie.quackquack.common.lint.hasOperator
import team.duckie.quackquack.common.lint.isOperator

class DesignSystemDetector : Detector(), SourceCodeScanner {
    private val methods = mapOf(
        "BasicTextField" to "QuackTextField",
    )

    override fun getApplicableUastTypes() = listOf(UMethod::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable) return
            if (context.isOperator(node) || context.hasOperator(node)) return
            methods[node.name]?.let { quackName ->
                reportIssue(
                    context = context,
                    node = node,
                    currentName = node.name,
                    preferredName = quackName
                )
            }
        }
    }

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
            .autoFix()
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
            priority = 10,
            severity = Severity.FATAL,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}
