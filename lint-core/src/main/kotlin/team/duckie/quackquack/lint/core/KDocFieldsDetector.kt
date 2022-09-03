/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MutableCollectionPublicDetector.kt] created by ricky_0_k on 22. 9. 2. 오전 1:51
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
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection
import org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.toUElement

private const val BriefDescription = "함수에 KDoc 및 @param, @return, @throws 및 description 필수"
private const val Explanation = "함수에 KDoc (/** and end with */)을 추가해야 합니다.\n" +
        "그리고 누락된 내용(@param, @return, @throws, @description) 이 없는지 체크해야 합니다."
private const val ExplanationKDocNotExist = "함수에는 KDoc 이 명시되어야 합니다."
private const val ExplanationParamCountNotEqual = "@param 명세 개수가, 매개 변수 개수가 일치해야 합니다."
private const val ExplanationWrongParam1 = "@return, @throws, @description 에 오타가 있습니다"
private const val ExplanationParamNotCompleted1 = "@return, @description 은 반드시 명세되어야 합니다 " +
        "(@throws 는 필요에 따라 명세)"
private const val ExplanationParamNotCompleted2 = "함수 내 매개변수가 있을 시, @param 는 반드시 명세되어야 합니다"

val KDocFieldsIssue = Issue.create(
    id = "KDocFields",
    briefDescription = BriefDescription,
    explanation = Explanation,
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        KDocFieldsDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

class KDocFieldsDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(
        UMethod::class.java,
    )

    /**
     * QuackQuack 린트의 함수에 KDoc 스타일 규칙을 구현합니다.
     *
     * 다음과 같은 조건에서 린트 검사를 진행합니다.
     *
     * 1. 함수를 사용할 시
     *
     * 해당 검사는 함수에 KDoc 스타일 형태 주석 작성을 권장하도록 하는 린트 검사입니다.
     * 그러므로 함수인 경우에 KDoc 스타일로 작성이 되었는지 검사합니다.
     *
     * ```
     * import java.lang.Exception
     *
     * /**
     *  * @description 모든 어노테이션이 존재하는 함수 예제
     *  *
     *  * @param ex1 테스트 용 문자열
     *  * @param efg 테스트 용 숫자
     *  * @return 반환값은 없습니다.
     *  * @throws Exception
     *  */
     * fun fullMethod(ex1: String, efg: Int) {
     *     throw Exception()
     * }
     *
     *
     * /**
     *  *
     *  *
     *  *
     *  * @description 모든 어노테이션이 존재하는 함수 예제
     *  *
     *  * @param ex1 테스트 용 문자열
     *  * 가나다라
     *  *
     *  * @param efg 테스트 용 숫자
     *  * abcdefg
     *  * @return 반환값은 없습니다.
     *  * 0123461#@!%@!
     *  * @throws Exception
     *  */
     * fun onlyCheckKDocSection(ex1: String, efg: Int) {
     *     throw Exception()
     * }
     * ```
     *
     * 다음과 같은 조건에서 에러를 캐치합니다.
     *
     * 1. 함수에 KDoc 이 명시되지 않을 경우
     * 2. @param 명세 개수가, 매개 변수 개수가 일치하지 않을 경우
     * 3. @return, @throws, @description 에 오타가 있을 경우
     * 4. @return, @description 이 명세되지 않았을 경우 (@throws 는 필요에 따라 명세)
     * 5. @param 가 명시되어야 할 조건인데, 명세되지 않았을 경우
     *
     * ```
     * import java.lang.Exception
     *
     * ~~~ <- KDoc 주석이 없음
     * fun failed1(ex1: String, efg: Int) { ... }
     *
     *
     * /**
     *  * @description 실패 케이스입니다.
     *  *
     *  * @param ex1 테스트 용 문자열
     *  * @param efg 명세되지 않은 params
     *  //       ~~~ <- 명세되지 않은 params
     *  * @return 반환값은 없습니다.
     *  * @throws Exception
     *  */
     * fun failed2(ex1: String) { ... }
     *
     *
     * /**
     *  * @ddescription 실패 케이스입니다.
     *  //~~~~~~~~~~~~~ <- 잘못 입력된 annotation
     *  *
     *  * @return 반환값은 없습니다.
     *  * @throws Exception
     *  */
     * fun failed3() {
     *     throw Exception();
     * }
     *
     *
     * /**
     *  * @description return 이 없는 경우입니다.
     *  *
     *  * @param ex1 테스트 용 문자열
     *  * @param efg 테스트 용 숫자
     *  // ~~ <- return 이 명세되지 않음
     *  */
     * override fun failed2(ex1: String, efg: Int) {
     * }
     *
     *
     * /**
     *  * @description 일부 param(efg) 명세가 없는 경우입니다.
     *  *
     *  * @param ex1 테스트 용 문자열
     *  // ~~ <- param(efg) 이 명세되지 않음
     *  * @return 반환값은 없습니다.
     *  * @throws Exception
     *  */
     * override fun failed2(ex1: String, efg: Int) {
     *     throw Exception()
     * }
     * ```
     */

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            val kDocArea = node.sourcePsi?.firstChild
            // expression 형태로 처리되는 경우도 있으므로
            val blockArea = node.sourcePsi?.lastChild as? KtBlockExpression
                ?: node.sourcePsi?.lastChild as? KtReferenceExpression
            val isThrowsOptional = blockArea?.children
                ?.none { it.node.elementType == KtNodeTypes.THROW }
                ?: true

            // kDoc 으로 치환될 수 있는 주석이 없다면 에러를 발생 시킨다.

            if (kDocArea != null && kDocArea.children.isNotEmpty()) {
                val kDocTags = kDocArea.children.filterIsInstance<KDocSection>().first().children
                // kDocTag 들을 분류한다.
                // ("param" 을 name 으로 가지지 않는 kDocTags, "param" 을 name 으로 가진 kDocTags)
                val (kDocNotParamTags, kDocParamTags) = kDocTags
                    .mapNotNull {
                        it as? KDocTag
                    }
                    .partition { kDocTag ->
                        kDocTag.name != "param"
                    }

                // 반드시 존재해야 하는 Annotations
                // ("param" 의 경우 따로 분석하며, throws 는 throws 가 있을 경우 추가해준다.)
                val mustExistAnnotations = mutableListOf(
                    "description",
                    "return",
                ).apply { if (!isThrowsOptional) add("throws") }

                // "param" 을 제외한 각 KDocTag 내용에 대해 분석한다.
                kDocNotParamTags.forEach { kDocTag ->
                    val isDeleted = mustExistAnnotations.remove(kDocTag.name)

                    if (!isDeleted)
                        return sendErrorReport(context, node, ExplanationWrongParam1)
                    else if (kDocTag.getContent().isBlank() || kDocTag.getContent().isEmpty()) {
                        // TODO (riflockle7) 어노테이션 명세는 있으나, 내용 명세가 없는 경우에 대한 정책 확인 필요
                        println("어노태이션 대응 내용 중 빈 내용 존재")
                    }
                }

                // "param" 을 제외한 어노테이션이 다 명세되었는지 확인한다. ("throws" 는 조건에 따라 명세)
                // 명세 안된 어노테이션이 있을 경우 에러를 발생 시킨다.
                if (mustExistAnnotations.isNotEmpty())
                    return sendErrorReport(context, node, ExplanationParamNotCompleted1)

                val methodParameterNames = node.uastParameters
                    .mapNotNull { (it.sourcePsi as? KtParameter)?.name }.toMutableList()

                // kDoc 에 명세된 "param" 개수와, 실제 매개변수 개수가 다르면 에러를 발생 시킨다.
                if (kDocParamTags.size != methodParameterNames.size)
                    return sendErrorReport(context, node, ExplanationParamCountNotEqual)

                // 각 "param" KDocTag 내용에 대해 분석한다.
                kDocParamTags.forEach { kDocParameterTag ->
                    // 'variable name' 과 kDocParameterTag.getSubjectName() 이 같은지 체크한다.
                    val isDeleted = methodParameterNames.remove(kDocParameterTag.getSubjectName())

                    if (!isDeleted)
                        return sendErrorReport(context, node, ExplanationParamNotCompleted2)
                    else if (
                        kDocParameterTag.getContent().isBlank()
                        || kDocParameterTag.getContent().isEmpty()
                    ) {
                        // TODO (riflockle7) 어노테이션 명세는 있으나, 내용 명세가 없는 경우에 대한 정책 확인 필요
                        println("어노태이션 대응 내용 중 빈 내용 존재")
                    }
                }

                // 분석 안된 "param" 이 있을 경우 에러를 발생 시킨다.
                if (methodParameterNames.isNotEmpty())
                    return sendErrorReport(context, node, ExplanationParamNotCompleted2)
            } else {
                sendErrorReport(context, node, ExplanationKDocNotExist)
            }
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
