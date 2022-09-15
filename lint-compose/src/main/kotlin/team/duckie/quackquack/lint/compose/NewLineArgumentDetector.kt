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
import com.intellij.psi.impl.source.tree.CompositeElement
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.kotlin.KotlinUFunctionCallExpression
import org.jetbrains.uast.kotlin.KotlinULambdaExpression
import team.duckie.quackquack.common.lint.compose.isComposable
import team.duckie.quackquack.common.lint.compose.isInvokedWithinComposable
import team.duckie.quackquack.common.lint.util.fastForEach
import team.duckie.quackquack.common.lint.util.fastForEachIndexed

private const val BriefDescription = "인자들 각각 new-line 에 배치"
private const val Explanation = "인자들을 쉽게 구분하기 위해 각각 new-line 에 배치해야 합니다."

val NewLineArgumentIssue = Issue.create(
    id = "NewLineArgument",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        NewLineArgumentDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * QuackQuack 린트의 [NewLineArgumentDetector] 규칙을 정의합니다.
 *
 * 다음과 같은 조건에서 린트를 검사합니다.
 *
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블 안에서 invoke 돼야 함
 * 3. 코틀린 함수여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. parameter 가 전부 new-line 에 배치되여야 함
 * 2. argument 가 전부 new-line 에 배치되어야 함
 *
 * 예외적으로, 이러한 경우에는 에러가 발생하지 않습니다.
 *
 * 1. 마지막 argument 가 LAMBDA_EXPRESSION 인 경우
 * 2. 레퍼런스를 참조하는 경우 (REFERENCES_EXPRESSION)
 *
 * ```kotlin
 * @Composable
 * fun MyComposable() {
 *     // 레퍼런스를 참조하는 경우이므로 예외적으로 허용 됨
 *     (1..10).map(Int::toString)
 *
 *     Button() {
 *         // 마지막 argument 의 LAMBDA_EXPRESSION 이므로 예외적으로 허용 됨
 *     }
 * }
 * ```
 */
class NewLineArgumentDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java,
        UExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        // Parameter
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable) return

            node.uastParameters.fastForEach { uParameter ->
                val parameterSourcePsi = uParameter.sourcePsi ?: return
                val parameterPrevSibling = (parameterSourcePsi.node as CompositeElement).treePrev

                if (!parameterPrevSibling.text.isNewLine()) {
                    return context.report(
                        issue = NewLineArgumentIssue,
                        scope = parameterSourcePsi.node.psi,
                        location = context.getLocation(parameterSourcePsi.node.psi),
                        message = Explanation,
                    )
                }
            }
        }

        // Argument
        override fun visitExpression(node: UExpression) {
            if (!node.isInvokedWithinComposable() || node !is KotlinUFunctionCallExpression) return

            val lastArgumentIndex = node.valueArguments.lastIndex
            node.valueArguments.fastForEachIndexed { index, argument ->
                if (index == lastArgumentIndex && argument is KotlinULambdaExpression) return

                val argumentSourcePsi = argument.sourcePsi ?: return
                if (argumentSourcePsi is KtCallableReferenceExpression) return

                val argumentParentPrevSibling =
                    (argumentSourcePsi.node as CompositeElement).treeParent.treePrev ?: return
                val argumentPrevParentPrevSibling = argumentParentPrevSibling.treeParent.treePrev

                if (!(argumentParentPrevSibling.text.isNewLine() || argumentPrevParentPrevSibling.text.isNewLine())) {
                    return context.report(
                        issue = NewLineArgumentIssue,
                        scope = argument,
                        location = context.getLocation(argument),
                        message = Explanation,
                    )
                }
            }
        }
    }

    private fun String.isNewLine() = startsWith("\n")
}
