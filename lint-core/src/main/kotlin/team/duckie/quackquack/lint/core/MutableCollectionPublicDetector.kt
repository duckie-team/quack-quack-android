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
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.isPublic
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.kotlin.KotlinUField
import team.duckie.quackquack.common.lint.util.fastAny

private const val BriefDescription = "MutableCollections public 노출 금지"
private const val Explanation = "캡슐화를 위해 MutableCollections public 노출은 금지됩니다."

val MutableCollectionPublicIssue = Issue.create(
    id = "MutableCollectionPublic",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        MutableCollectionPublicDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * MutableCollections 접두사
 *
 * kotlinx.collections 패키지에 있는 Collections 들 입니다.
 *
 * @see [https://github.com/Kotlin/kotlinx.collections.immutable]
 */
private val MutableNames = listOf(
    "Mutable",
    "PersistentCollection.Builder",
)

/**
 * QuackQuack 린트의 MutableCollectionPublic 규칙을 구현합니다.
 *
 * 다음과 같은 조건에서 린트 검사를 진행합니다.
 *
 * 1. MutableCollections 에서 public 을 사용할 시
 *
 * 현재 이 규칙은 MutableCollections 변수의 접근 제어 범위(visibility) 가 public 인지 검사하도록 구현됐습니다.
 * 따라서 아래와 같이 MutableCollections 접근 제어 범위가 public 일 시
 * 린트 에러가 발생합니다.
 *
 * ```
 * val variableName: MutableCollection<Any> = emptyList()
//     ~~~~~~~~~~~~ <- MutableCollections 의 접근 제어가 public 으로 감지됨
 * ```
 * TODO internal val variableName: MutableCollection<Any> <- internal 은 private 으로 감지됨
 *      internal 에 대해서는 모니터링 필요
 */
class MutableCollectionPublicDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UDeclaration::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitDeclaration(node: UDeclaration) {
            // Kotlin 특성 상 field 이외의 것 (ex. getter, setter 등) 도 체크하므로 해당 내용만 체크하도록 처리
            if (node !is KotlinUField) return

            val property = node.sourcePsi as? KtProperty
            val propertyType = property?.typeReference ?: return
            val propertyTypeName = propertyType.text
            val isMutable = MutableNames.fastAny { immutableName ->
                propertyTypeName.startsWith(immutableName)
            }

            // mutable Collection 인 경우 && PUBLIC 인 경우
            if (isMutable && property.isPublic) {
                context.report(
                    issue = MutableCollectionPublicIssue,
                    scope = propertyType,
                    location = context.getNameLocation(propertyType),
                    message = Explanation,
                )
            }
        }
    }
}
