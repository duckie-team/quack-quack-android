/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [PreferredImmutableCollectionsDetector.kt] created by Ji Sungbin on 22. 8. 19. 오전 9:13
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
import team.duckie.quackquack.common.lint.isMutableCollection

private const val BriefDescription = "MutableCollections 사용 감지됨"
private const val Explanation = "Skippable 을 위해 ImmutableCollections 사용을 지향해야 합니다."

val PreferredImmutableCollectionsIssue = Issue.create(
    id = "PreferredImmutableCollections",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        PreferredImmutableCollectionsDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

/**
 * QuackQuack 린트의 PreferredImmutableCollections 규칙을 구현합니다.
 *
 * 다음과 같은 조건에서 린트 검사를 진행합니다.
 *
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블을 방출하는 역할이여야 함
 *
 * 다음과 같은 조건에서 린트 에러가 발생합니다.
 *
 * 1. 인자의 타입 이름에 "List", "Map", "Set" 이 포함됨 (MutableCollections)
 * 2. 인자의 타입 이름에 "Immutable", "Persistent" 이 포함되지 않음 (ImmutableCollections)
 *
 * 현재 이 규칙은 인자 타입의 이름만을 이용하여 검사하도록 구현됐습니다.
 * 따라서 아래와 같이 ImmutableCollections 의 네이밍을 MutableCollections 으로 지정하여
 * 사용하면 린트 에러가 발생합니다.
 *
 * ```
 * typealias MyList = ImmutableList<Any>
 *
 * @Composable
 * fun MyComposable(list: MyList) {}
 * //               ~~~~ <- MutableCollections 사용 감지됨
 * // list 인자의 타입명인 MyList 에서 MutableCollections 의 네이밍인 List 가
 * // 포함돼 있고, ImmutableCollections 의 네이밍이 포함돼 있지 않아
 * // MutableCollections 사용으로 판단하고 린트 에러가 발생함
 * ```
 *
 * 인자 타입의 패키지를 이용하여 검사하는 것으로 구현이 개선돼야 합니다.
 */
class PreferredImmutableCollectionsDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(UMethod::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable || !node.isReturnsUnit) return

            for (parameter in node.uastParameters) {
                val ktParameter = parameter.sourcePsi as? KtParameter ?: continue
                val parameterType = ktParameter.typeReference ?: continue
                if (parameterType.isMutableCollection) {
                    context.report(
                        issue = PreferredImmutableCollectionsIssue,
                        scope = parameterType,
                        location = context.getNameLocation(parameterType),
                        message = Explanation,
                    )
                }
            }
        }
    }
}
