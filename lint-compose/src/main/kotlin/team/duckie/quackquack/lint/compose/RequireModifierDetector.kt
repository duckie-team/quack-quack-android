/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [RequireModifierDetector.kt] created by limsaehyun on 22. 9. 2. 오후 1:36
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
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.kotlin.name.SpecialNames
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.uast.UMethod
import team.duckie.quackquack.common.lint.compose.isComposable

private const val BriefDescription = "Modifier Type 필수 사용"
private const val Explanation = "컴포저블의 재사용성을 위해 Modifier Type은 필수적으로 사용해야 합니다."

/**
 * QuackQuack 린트의 [RequireModifierDetector] 규칙을 정의합니다.
 *
 * 다음과 같은 조건에서 린트를 검사합니다.
 *
 * 1. 컴포저블 함수여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. parameter에 Modifier Type 이 없는 경우
 */
val RequireModifierIssue = Issue.create(
    id = "RequireModifier",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.CORRECTNESS,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        RequireModifierDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

class RequireModifierDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {

        override fun visitMethod(node: UMethod) {
            if (!node.isComposable) return

            val modifierParameterExists = node.uastParameters.any { parameter ->
                (parameter.sourcePsi as? KtParameter)?.typeReference?.text == "Modifier"
            }

            val parameterPsi = node.javaPsi.parameterList

            if (!modifierParameterExists) {
                context.report(
                    issue = RequireModifierIssue,
                    scope = parameterPsi,
                    location = context.getLocation(parameterPsi),
                    message = Explanation,
                )
            }
        }
    }
}
