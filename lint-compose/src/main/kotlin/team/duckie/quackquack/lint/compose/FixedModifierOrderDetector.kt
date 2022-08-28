/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [FixedModifierOrderDetector.kt] created by riflockle7 on 22. 8. 28. 오후 11:09
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
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.uast.UMethod
import team.duckie.quackquack.common.lint.compose.isComposable
import team.duckie.quackquack.common.lint.compose.isReturnsUnit

private const val BriefDescription = "Modifier 인자를 첫 번째 위치로 고정해야 함"
private const val Explanation = "Modifier 은 컴포저블의 필수 인자이므로, " +
        "매개변수 첫 번째 위치에 고정하여 도메인적 의미를 강조해야 합니다.\n" +
        "(가장 많이 찾는 인자가 될 가능성이 높으니 첫 번째로 위치 고정을 함으로써 그 의미를 강화합니다.)"

val FixedModifierOrderIssue = Issue.create(
    id = "FixedModifierOrder",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        FixedModifierOrderDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

/**
 * QuackQuack 린트의 FixedModifierOrder 규칙을 구현합니다.
 *
 * 다음과 같은 조건에서 린트 검사를 진행합니다.
 *
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블을 방출하는 역할이여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. 컴포저블 함수에서 첫 번째 인자가 Modifier 타입 변수가 아닐 경우 (Modifier 타입 변수가 인자로 없는 경우는 고려하지 않음)
 *
 * 현재 이 규칙은 인자 타입의 이름만을 이용하여 검사하도록 구현됐습니다.
 * 따라서 아래와 같이 첫 번째 인자의 타입 네임을 체크하여
 * 조건에 부합되지 않을 경우 린트 에러가 발생합니다.
 *
 * ```
 * @Composable
 * fun MyComposable(params: not Modifier, modifier: Modifier) {}
 * //                       ~~~~~~~~~~~~ <- 첫 번째 인자 타입이 Modifier 가 아님
 * // 첫 번째 인자 타입이 Modifier 가 아님을 인지하고 린트 에러가 발생함
 * ```
 *
 * 인자 타입의 패키지를 이용하여 검사하는 것으로 구현이 개선돼야 합니다.
 */
class FixedModifierOrderDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(UMethod::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable || !node.isReturnsUnit) return

            val firstParameter =
                node.uastParameters.firstOrNull()?.sourcePsi as? KtParameter ?: return
            val firstParameterType = firstParameter.typeReference ?: return
            val firstParameterTypeName = firstParameterType.text ?: return

            if (!firstParameterTypeName.lowercase().contains("modifier")) {
                return context.report(
                    issue = FixedModifierOrderIssue,
                    scope = firstParameterType,
                    location = context.getNameLocation(firstParameterType),
                    message = Explanation,
                )
            }
        }
    }
}
