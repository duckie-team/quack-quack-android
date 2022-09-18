/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "SameParameterValue",
    "ObjectPropertyName",
    "NonAsciiCharacters",
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
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection
import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.toUElement
import team.duckie.quackquack.common.lint.extension.content
import team.duckie.quackquack.common.lint.util.fastForEach
import team.duckie.quackquack.common.lint.util.isReturnsUnit

private const val BriefDescription = "함수에 KDoc 및 @param, @return, @throws 및 description 필수"
private const val Explanation = "함수에 KDoc 을 추가해야 합니다.\n" +
        "그리고 누락된 내용(@param, @return, @throws, @description) 이 없는지 체크해야 합니다."
private const val KDoc_명세_필요 = "함수에는 KDoc 이 명시돼야 합니다."
private const val Param_개수와_매개변수_개수_불일치 = "매개 변수의 개수와 @param 명세 개수가 일치해야 합니다."
private const val Return_명세_필요 = "@return 이 반드시 명세돼야 합니다 "
private const val Throws_명세_필요 = "@throws 이 반드시 명세돼야 합니다 "
private const val Param_명세_필요 = "함수 내 모든 매개변수에 대하여 @param 는 반드시 명세되어야 합니다"
private const val KDocTag_에_대응하는_내용_없음 = "KDocTag 에 대응하는 내용이 반드시 있어야 합니다."

val KDocFieldsIssue = Issue.create(
    id = "KDocFields",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        KDocFieldsDetector::class.java,
        Scope.JAVA_FILE_SCOPE,
    ),
)

/**
 * QuackQuack 린트의 함수에 KDoc 스타일 규칙을 구현합니다.
 *
 * 다음과 같은 조건에서 린트 검사를 진행합니다.
 *
 * 1. 함수를 사용할 시
 *
 * 해당 검사는 함수에 KDoc 스타일 형태 주석 작성을 권장하도록 하는 린트 검사입니다.
 * 그러므로 함수인 경우에 KDoc 스타일로 작성이 됐는지 검사합니다.
 *
 * ```
 * /**
 *  * 모든 KDocTag 가 존재하는 함수 예제
 *  *
 *  * @param ex1 테스트 용 문자열
 *  * @param efg 테스트 용 숫자
 *  * @return 무조건 0 입니다.
 *  * @throws IllegalStateException
 *  */
 * fun fullMethod(ex1: String, efg: Int) = when ("".isEmpty()) {
 *     true -> 0
 *     else -> throw IllegalStateException()
 * }
 * ```
 *
 * 다음과 같은 조건에서 에러를 캐치합니다.
 *
 * 1. 함수에 KDoc 이 명시되지 않을 경우
 * 2. @param 명세 개수가 매개 변수 개수와 일치하지 않을 경우
 * 3. 필수 KDocTag 가 명세되지 않았을 경우
 * 4. 필수 KDocTag 에 대응하는 내용이 없을 경우
 *
 * ```
 * //  ~~~~~~~ <- KDoc 주석이 없음
 * fun failed1(ex1: String, efg: Int) { ... }
 *
 * /**
 *  * 실패 케이스입니다.
 *  *
 *  * ~~ <- param 이 명세되지 않음 (= param 어노테이션 개수와 매개변수 개수가 일치하지 않음)
 *  * @return 반환값은 없습니다.
 *  * @throws IllegalStateException
 *  */
 * fun failed2(ex1: String) { ... }
 *
 * /**
 *  * 실패 케이스입니다.
 *  *
 *  * @param ex1 테스트 용 문자열
 *  * @param efg 명세되지 않은 params
 *  *        ~~~ <- 명세되지 않은 params
 *  * @return 반환값은 없습니다.
 *  * @throws IllegalStateException
 *  */
 * fun failed3(ex1: String) { ... }
 *
 * /**
 *  * KDocTag 에 대응되는 내용이 없는 경우입니다.
 *  *
 *  * @param ex1 // ~~ <- 내용이 명세되지 않음
 *  * @return 반환값은 없습니다.
 *  * @throws IllegalStateException
 *  */
 * override fun failed5(ex1: String) {
 *     throw IllegalStateException()
 * }
 * ```
 */
class KDocFieldsDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java,
    )

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            // 오로지 함수만 체크하도록 함 (함수 아닐 경우 그대로 리턴)
            val ktNamedFunction = (node.sourcePsi as? KtNamedFunction) ?: return

            // ktNamedFunction.children 가 Array 여서 random access 가 안됨
            // 그래서 fastFind 가 아닌 일반 find 사용
            val kDocArea = ktNamedFunction.children.find { element ->
                // 함수 영역에서 kDoc 데이터를 뽑아온다.
                element is KDoc
            }

            // override 함수는 kDoc 이 있을 경우 규칙을 지키되, 없을 경우 그냥 넘어간다.
            if (node.modifierList.text == "override" && kDocArea == null) {
                return
            }

            val kDocSections = kDocArea?.children
                ?.filterIsInstance<KDocSection>()
                ?.firstOrNull()?.children

            val bodyArea = node.sourcePsi?.lastChild as? KtBlockExpression
                ?: node.sourcePsi?.lastChild as? KtReferenceExpression

            // kDoc 으로 치환될 수 있는 주석이 없다면 에러를 발생 시킨다.
            if (kDocSections.isNullOrEmpty()) {
                return sendErrorReport(
                    context = context,
                    node = node,
                    message = KDoc_명세_필요,
                )
            }

            // kDocTag 들을 어노테이션 이름 (name) 에 따라 분류
            val kDocTags = kDocSections
                .filterIsInstance<KDocTag>()
                .groupBy { kDocTag ->
                    kDocTag.name
                }
                .toMutableMap()

            // "param" 명세가 필요한 지 체크
            val methodParameterNames = node.uastParameters.mapNotNull { parameter ->
                (parameter.sourcePsi as? KtParameter)?.name
            }.toMutableList()
            val isNoParams = methodParameterNames.isEmpty()

            // "params" 린트 검사
            if (methodParameterNames.size != (kDocTags["param"]?.size ?: 0)) {
                return sendErrorReport(
                    context = context,
                    node = node,
                    message = Param_개수와_매개변수_개수_불일치,
                )
            }

            if (!isNoParams) {
                // 각 "param" KDocTag 내용에 대해 분석한다.
                kDocTags["param"]?.fastForEach { kDocParameterTag ->
                    // 'variable name' 과 kDocParameterTag.getSubjectName() 이 같은지 체크한다.
                    val isDeleted = methodParameterNames.remove(kDocParameterTag.getSubjectName())

                    if (!isDeleted) {
                        return sendErrorReport(
                            context = context,
                            node = node,
                            message = Param_명세_필요,
                        )
                    } else if (kDocParameterTag.getSubjectName().isNullOrBlank()) {
                        return sendErrorReport(
                            context = context,
                            node = node,
                            message = KDocTag_에_대응하는_내용_없음,
                        )
                    }
                }
            }
            kDocTags.remove("param")

            // bodyArea.children 가 Array 여서 random access 가 안됨
            // 그래서 fastAny 가 아닌 일반 any 사용
            val isThrowable = bodyArea?.children?.any { bodyElement ->
                // "throws" 명세가 필요한 지 체크
                bodyElement.node.elementType == KtNodeTypes.THROW
            } ?: false

            // "throws" 린트 검사
            if (isThrowable && kDocTags["throws"].isNullOrEmpty()) {
                return sendErrorReport(
                    context = context,
                    node = node,
                    message = Throws_명세_필요,
                )
            }

            kDocTags["throws"]?.fastForEach { kDocThrowsTag ->
                if (kDocThrowsTag.getSubjectName().isNullOrBlank()) {
                    return sendErrorReport(
                        context = context,
                        node = node,
                        message = KDocTag_에_대응하는_내용_없음,
                    )
                }
            }
            kDocTags.remove("throws")

            // "return" 린트 검사
            if (!node.isReturnsUnit && kDocTags["return"].isNullOrEmpty()) {
                return sendErrorReport(context, node, Return_명세_필요)
            }
            kDocTags["return"]?.fastForEach { kDocReturnTag ->
                if (kDocReturnTag.content.isBlank()) {
                    return sendErrorReport(context, node, KDocTag_에_대응하는_내용_없음)
                }
            }
            kDocTags.remove("return")
        }

        private fun sendErrorReport(
            context: JavaContext,
            node: UMethod,
            message: String,
        ) {
            context.report(
                issue = KDocFieldsIssue,
                scope = node.toUElement(),
                location = context.getNameLocation(node),
                message = message,
            )
        }
    }
}
