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
import org.jetbrains.uast.UVariable
import org.jetbrains.uast.kotlin.KotlinUField

private const val BriefDescription = "List 네이밍 끝에는 s를 사용해야 합니다."
private const val Explanation = "네이밍 스타일을 통일하기 위해 List 네이밍 끝에는 s를 사용해야 합니다."

// List Type의 QualifiedName
private const val ListQualifiedName = "java.util.List"

val ListNamingIssue = Issue.create(
    id = "ListNaming",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        ListNamingDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * QuackQuack 린트의 [ListNamingDetector] 규칙을 정의합니다.
 *
 * 다음과 같은 조건에서 린트를 검사합니다.
 * 1. Variable의 Type이 List여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 * 1. Naming 끝에 's'가 명시되지 않은 경우
 *
 * ```kotlin
 * // failed: Naming 끝에 's'가 명시되지 않음
 * val peopleList: List<String> = emptyList<String>()
 *
 * // success
 * val peoples = emptyList<String>()
 * ```
 */
class ListNamingDetector : Detector(), SourceCodeScanner {

    override fun getApplicableUastTypes() = listOf(
        UVariable::class.java
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitVariable(node: UVariable) {
            if (node !is KotlinUField) return

            val typeQualifiedName = node.typeReference?.getQualifiedName() ?: return

            if (typeQualifiedName == ListQualifiedName) {
                if (!node.name.endsWith('s')) {
                    context.report(
                        issue = ListNamingIssue,
                        scope = node.sourcePsi,
                        location = context.getLocation(node.sourcePsi),
                        message = Explanation,
                    )
                }
            }
        }
    }
}
