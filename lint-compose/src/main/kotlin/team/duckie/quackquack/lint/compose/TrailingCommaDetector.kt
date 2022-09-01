/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [TrailingCommaDetector.kt] created by limsaehyun on 22. 8. 29. 오전 8:20
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

package team.duckie.quackquack.lint.compose

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.kotlin.KotlinUFunctionCallExpression
import team.duckie.quackquack.common.lint.compose.isComposable
import team.duckie.quackquack.common.lint.compose.isInvokedWithinComposable

private const val BriefDescription = "후행 ',' 필수 사용"
private const val Explanation = "후행 ',' 는 필수적으로 사용해야합니다"

/**
 * [TrailingCommaDetector]가 적용되지 않는 최대 Argument, Parameter 개수를 정의합니다.
 */
private const val LIMIT_ARGUMENT_NUMBER = 2
private const val LIMIT_PARAMETER_NUMBER = 2

/**
 * QuackQuack 린트의 [TrailingCommaDetector] 규칙을 정의합니다.
 *
 * 다음과 같은 조건에서 린트를 검사합니다.
 *
 * 1. 컴포저플 함수여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. 매개변수 뒤에 ','가 오지 않는 경우
 * 2. 인자 뒤에 ','가 오지 않는 경우
 *
 * 예외적으로, 인자와 파라미터 개수가 최대 [LIMIT_ARGUMENT_NUMBER] [LIMIT_PARAMETER_NUMBER]
 * 일 경우에는 예외를 발생시키지 않습니다.
 */
val TrailingCommaIssue = Issue.create(
    id = "TrailingComma",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.CORRECTNESS,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        TrailingCommaDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

class TrailingCommaDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java,
        UExpression::class.java
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        // Argument 검사
        override fun visitExpression(node: UExpression) {
            if (!node.isInvokedWithinComposable() || node !is KotlinUFunctionCallExpression) return

            val valueArgumentSize = node.valueArgumentCount
            val valueArgumentList = node.valueArguments

            if (valueArgumentSize < LIMIT_ARGUMENT_NUMBER) return

            valueArgumentList.map { argument ->
                val argumentSourcePsi = argument.sourcePsi
                val argumentNode = argumentSourcePsi?.node
                val argumentTreeParent = argumentNode?.treeParent
                val argumentNextSibling = argumentTreeParent?.psi?.nextSibling ?: return

                if (!argumentNextSibling.textContains(',')) {
                    context.report(
                        issue = TrailingCommaIssue,
                        scope = argumentNode.psi,
                        location = context.getLocation(argumentNode.psi),
                        message = Explanation,
                    )
                }
            }
        }

        // Parameter 검사
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable) return

            val parameterSize = node.uastParameters.size

            if (parameterSize < LIMIT_PARAMETER_NUMBER) return

            val lastParameter = node.uastParameters.lastOrNull()
            val lastParameterSourcePsi = lastParameter?.sourcePsi
            val lastParameterNextSibling = lastParameterSourcePsi?.node?.psi?.nextSibling ?: return

            if (!(lastParameterNextSibling.textContains(','))) {
                context.report(
                    issue = TrailingCommaIssue,
                    scope = lastParameterSourcePsi,
                    location = context.getLocation(lastParameterSourcePsi),
                    message = Explanation,
                )
            }
        }
    }
}
