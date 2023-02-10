/*
 * Designed and developed by Duckie Team, 2022
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
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.kotlin.KotlinUFunctionCallExpression
import team.duckie.quackquack.common.lint.util.fastForEach

private const val BriefDescription = "quackAnimationSpec 를 사용해주세요."
private const val Explanation = "animationSpec 에는 quackAnimationSpec 를 사용해야 합니다."

val SpecifyAnimationSpecIssue = Issue.create(
    id = "SpecifyAnimationSpec",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        SpecifyAnimationSpecDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * animate*StateOf functions list
 */
private val AnimateStateOfList = listOf(
    "animateFloatAsState",
    "animateColorAsState",
    "animateDpAsState",
    "animateSizeAsState",
    "animateOffsetAsState",
    "animateRectAsState",
    "animateIntAsState",
    "animateIntOffsetAsState",
    "animateSizeAsState",
    "animateValueAsState",
)

/**
 * QuackQuack 린트의 [SpecifyAnimationSpecDetector] 를 구현합니다.
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 * 1. 코틀린 함수여야 함
 * 2. [AnimateStateOfList] 를 호출해야 함
 * 3. animationSpec 에 quackAnimationSpec 를 사용하지 않은 경우
 * 4. default argument 를 사용한 경우
 */
class SpecifyAnimationSpecDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitExpression(node: UExpression) {
            if (node !is KotlinUFunctionCallExpression) return

            val referencedName = node.sourcePsi.node.firstChildNode.firstChildNode.text

            if (referencedName in AnimateStateOfList) {
                node.valueArguments.fastForEach { argument ->
                    val argumentSourcePsi = argument.sourcePsi ?: return
                    val argumentTreeParent = argumentSourcePsi.node.treeParent ?: return

                    val argumentName =
                        argumentTreeParent.firstChildNode.firstChildNode.firstChildNode.text

                    if (argumentName == "animationSpec") {
                        val argumentValue =
                            argumentTreeParent.lastChildNode.firstChildNode.firstChildNode.text

                        if (argumentValue != "QuackAnimationSpec") {
                            sendErrorReport(
                                psi = argumentSourcePsi,
                            )
                        }

                        return
                    }
                }

                sendErrorReport(
                    psi = node.sourcePsi,
                )
            }
        }

        private fun sendErrorReport(
            psi: PsiElement,
        ) {
            context.report(
                issue = SpecifyAnimationSpecIssue,
                scope = psi,
                location = context.getNameLocation(
                    element = psi,
                ),
                message = Explanation,
            )
        }
    }
}
