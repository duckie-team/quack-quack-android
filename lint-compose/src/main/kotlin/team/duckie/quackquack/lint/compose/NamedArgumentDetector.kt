/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "SameParameterValue",
)

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
import com.intellij.lang.ASTNode
import org.jetbrains.kotlin.KtNodeTypes.VALUE_ARGUMENT_NAME
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.getContainingUMethod
import org.jetbrains.uast.kotlin.KotlinUFunctionCallExpression
import org.jetbrains.uast.kotlin.KotlinULambdaExpression
import team.duckie.quackquack.common.fastForEachIndexed
import team.duckie.quackquack.common.lint.compose.isComposable

private const val BriefDescription = "named argument 사용은 필수입니다."
private const val Explanation = "인자들을 쉽게 구별하기 위해 named argument는 필수적으로 사용해야 합니다."

val NamedArgumentIssue = Issue.create(
    id = "FixedModifierOrder",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        NamedArgumentDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * QuackQuack 린트의 [NamedArgumentDetector] 규칙을 정의합니다.
 *
 * 다음과 같은 조건에서 린트를 검사합니다.
 *
 * 2. 컴포저블 안에서 invoke 돼야 함
 * 3. 코틀린 함수여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. named argument를 사용해야 함
 *
 * 다음과 같은 조건에서는 예외적으로 린트 에러가 발생하지 않습니다.
 * 1. function이 [IgnoredFunctions]인 경우우 */

class NamedArgumentDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(
        UExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitExpression(node: UExpression) {
            if (node !is KotlinUFunctionCallExpression) return

            val containingUMethod = node.getContainingUMethod() ?: return
            if (!containingUMethod.isComposable) return

            val lastArgumentIndex = node.valueArguments.lastIndex
            node.valueArguments.fastForEachIndexed { index, argument ->
                if (index == lastArgumentIndex && argument is KotlinULambdaExpression) return

                val expressionSourcePsi = argument.sourcePsi

                val argumentParent = expressionSourcePsi?.node?.treeParent ?: return

                val argumentFirstChildNode = argumentParent.firstChildNode
                val argumentParentFirstChildNode = argumentParent.treeParent.firstChildNode

                if (!(argumentFirstChildNode.isValueArgumentName() || argumentParentFirstChildNode.isValueArgumentName())) {
                    context.report(
                        issue = NamedArgumentIssue,
                        scope = expressionSourcePsi,
                        location = context.getLocation(expressionSourcePsi),
                        message = Explanation,
                    )
                    return
                }
            }
        }
    }

    private fun ASTNode.isValueArgumentName() =
        this.elementType == VALUE_ARGUMENT_NAME
}
