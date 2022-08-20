/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MutableCollectionPublicDetector.kt] created by ricky_0_k on 22. 8. 21. 오전 12:43
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
import com.android.tools.lint.detector.api.*
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UastVisibility

private const val BriefDescription = "MutableCollection public 노출 금지"
private const val Explanation = "캡슐화를 위해 MutableCollection public 노출은 금지됩니다."

val MutableCollectionPublicIssue = Issue.create(
    id = "MutableCollectionPublic",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        MutableCollectionPublicDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

/**
 * MutableCollections 접미사
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
 * 1. Mutable Collection 에서 public 을 사용할 시
 *
 * 현재 이 규칙은 Mutable Collection 앞에 public 이 붙었는지 검사하도록 구현됐습니다.
 * 따라서 아래와 같이 Mutable Collection 앞에 public 을 사용할 시
 * 린트 에러가 발생합니다.
 *
 * ```
 * public MutableCollection<Any> <- MutableCollection 의 접근 제어가 public 으로 감지됨
 *
 * // MutableCollection 접근제어 범위가 public 이어서 린트 에러가 발생함
 * ```
 *
 * 2. 함수 내 파라미터에 public 을 사용할 시
 *
 * 함수 내 파라미터 접근제어자 범위는 기본적으로 packageLocal 이므로 문제가 없습니다.
 */
class MutableCollectionPublicDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java,
        UDeclaration::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitDeclaration(node: UDeclaration) {
            val property = node.sourcePsi as? KtProperty
            val propertyType = property?.typeReference ?: return
            val propertyTypeName = propertyType.text
            val isImmutable = MutableNames.any { immutableName ->
                propertyTypeName.startsWith(immutableName)
            }

            // mutable Collection 인 경우 && PUBLIC 인 경우
            if(isImmutable && node.visibility == UastVisibility.PUBLIC) {
                context.report(
                    issue = MutableCollectionPublicIssue,
                    scope = propertyType,
                    location = context.getNameLocation(propertyType),
                    message = Explanation,
                )
            }
        }

        override fun visitMethod(node: UMethod) {
            for (parameter in node.uastParameters) {
                val ktParameter = parameter.sourcePsi as? KtParameter ?: continue
                val parameterType = ktParameter.typeReference ?: continue
                val parameterTypeName = parameterType.text
                val isImmutable = MutableNames.any { immutableName ->
                    parameterTypeName.startsWith(immutableName)
                }
                if(isImmutable && parameter.visibility == UastVisibility.PUBLIC) {
                    context.report(
                        issue = MutableCollectionPublicIssue,
                        scope = parameterType,
                        location = context.getNameLocation(parameterType),
                        message = Explanation,
                    )
                }
            }
        }
    }
}
