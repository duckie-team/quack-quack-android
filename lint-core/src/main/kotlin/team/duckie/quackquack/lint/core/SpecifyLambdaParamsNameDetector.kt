/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [SpecifyLambdaParamsNameDetector.kt] created by ricky_0_k on 22. 9, 11. 오후 3:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "SameParameterValue",
)

package team.duckie.quackquack.lint.core

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.kotlin.psi.KtFunctionLiteral
import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.KtParameterList
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.ULambdaExpression

private const val BriefDescription = "람다 내 Params 이름 명시해야 함"
private const val Explanation = "람다 내 Params 이름을 반드시 명시해야 합니다."
private const val LambdaParameterNameUndefined = "파라미터 이름이 명세하지 않았습니다."
private const val FoundedItParameterName = "람다식 파라미터 내에서 it 명명을 허용하지 않습니다."

val SpecifyLambdaParamsNameIssue = Issue.create(
    id = "SpecifyLambdaParamsName",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        SpecifyLambdaParamsNameDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

/**
 * QuackQuack 린트의 SpecifyLambdaParamsName 규칙을 구현합니다.
 *
 * 다음과 같은 조건에서 린트 검사를 진행합니다.
 *
 * 1. 람다이어야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. 람다 내에 params 명세가 없을 경우
 * ```
 * listOf<Int>(1, 2, 3).forEach {   // <- 별도로 파라미터 명세를 하지 않음
 *     it.toDouble()
 * //  ~~ <- 파라미터 명세가 없으므로 it 를 사용하게 됨 (린트에서 허용하지 않음)
 * }
 *
 * ```
 * 2. 람다 내에 params 명 중 it 를 사용할 경우
 * ```
 * val sum: (Int, Int) -> Int = { it: Int, y: Int -> it + y }
 * //                             ~~ <- it 라는 이름을 사용할 수 없음
 * ```
 */
class SpecifyLambdaParamsNameDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        ULambdaExpression::class.java
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitLambdaExpression(node: ULambdaExpression) {
            print(node)

            // 람다식인지를 먼저 검증한다.
            // TODO (riflockle7) 만약 람다 내에 파라미터가 없는 케이스가 있을지 확인 필요
            val lambdaExpression = (node.sourcePsi as? KtLambdaExpression) ?: return
            val ktFunctionLiteral = lambdaExpression.children
                .filterIsInstance<KtFunctionLiteral>().firstOrNull()
                ?: return

            // 람다식이 맞다면 람다식 내의 파라미터 정보를 찾는다. 파라미터 정보는 반드시 있다고 가정한다.
            val ktParameterLists = ktFunctionLiteral.children.filterIsInstance<KtParameterList>()
            val lambdaParams = ktParameterLists.firstOrNull()?.parameters

            if (lambdaParams.isNullOrEmpty()) {
                // 만약 파라미터 정보가 비어 있거나, 아무 설정도 안되어 있어 null 로 잡히는 경우 에러를 발생시킨다.
                return sendErrorReport(
                    context,
                    node,
                    LambdaParameterNameUndefined,
                )
            } else {
                lambdaParams.forEach { ktParameter ->
                    // 네이밍을 it 로 해주었을 경우를 허용하지 않는다.
                    if (ktParameter.name == "it") {
                        // 에러 발생: it 를 허용하지 않음
                        return sendErrorReport(
                            context, node,
                            FoundedItParameterName,
                        )
                    }
                }
            }
        }
    }

    private fun sendErrorReport(
        context: JavaContext,
        node: UExpression,
        message: String,
    ) {
        context.report(
            issue = SpecifyLambdaParamsNameIssue,
            scope = node,
            location = context.getNameLocation(node),
            message = message,
        )
    }
}
