/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.uast.UVariable
import org.jetbrains.uast.kotlin.KotlinUField
import team.duckie.quackquack.common.lint.isCollection

private const val BriefDescription = "List 네이밍에는 s 접미사를 사용해야 합니다."
private const val Explanation = "네이밍 스타일을 통일하기 위해 List 네이밍에는 s 접미사를 사용해야 합니다."

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
 * 1. 변수의 타입이 Collection 이여야 함
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

            if (typeQualifiedName.isCollection && !node.name.endsWith('s')) {
                val namingFix = LintFix.create()
                    .replace()
                    .text("List")
                    .with("s")
                    .build()

                context.report(
                    issue = ListNamingIssue,
                    scope = node.sourcePsi,
                    location = context.getLocation(node.sourcePsi),
                    message = Explanation,
                    quickfixData = if (node.name.endsWith("List")) {
                        namingFix
                    } else null
                )
            }
        }
    }
}
