/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import team.duckie.quackquack.common.lint.compose.isInvokedWithinComposable

private const val BriefDescription = "컴포즈 기본 디자인 컴포넌트 사용 감지됨"
private const val Explanation = "Jetpack Compose 의 기본 컴포저블 대신에 " +
        "QuackQuack 디자인 시스템의 컴포저블을 사용해야 합니다."

val DesignSystemIssue = Issue.create(
    id = "DesignSystem",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.CUSTOM_LINT_CHECKS,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        DesignSystemDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * key 에서 value 로 변경돼야 하는 컴포저블명을 매핑합니다.
 *
 * [key - value]
 *
 * 컴포즈 foundation 컴포넌트 - QuackQuack 디자인 시스템의 컴포넌트
 */
private val methods = mapOf(
    "Button" to "QuackLargeButton",
)

/**
 * QuackQuack 린트의 DesignSystem 규칙을 구현합니다.
 *
 * 아래와 같은 조건에서 린트 검사가 진행됩니다.
 *
 * 1. 컴포저블 함수 안에서 사용돼야 함
 *
 * 아래와 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. [methods] 의 key 를 사용중이 감지됨
 *
 * 현재 이 규칙은 함수명을 이용하여 검사하도록 구현됐습니다.
 * 따라서 아래와 같이 일반 함수의 네이밍을 [methods] 의 key 로
 * 하면 린트 에러가 발생합니다.
 *
 * ```
 * fun Button() {}
 * @Composable
 * fun Main() {
 *     Button()
 * //  ~~~~~~~~ <- 컴포즈 기본 디자인 컴포넌트 사용 감지됨
 * // Button 이 컴포저블이 아닌데도 린트 에러가 발생함
 * }
 * ```
 *
 * 해당 함수가 컴포저블인지도 확인하도록 구현이 개선돼야 합니다.
 */
class DesignSystemDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes() = listOf(
        UCallExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitCallExpression(node: UCallExpression) {
            if (!node.isInvokedWithinComposable()) return

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

    private fun reportIssue(
        context: JavaContext,
        node: UElement,
        currentName: String,
        preferredName: String,
    ) {
        val quickfix = LintFix.create()
            .name("$preferredName 로 변경")
            .replace()
            .text(currentName)
            .with(preferredName)
            .autoFix()
            .build()

        context.report(
            issue = DesignSystemIssue,
            scope = node,
            location = context.getLocation(node),
            message = Explanation,
            quickfixData = quickfix,
        )
    }
}
