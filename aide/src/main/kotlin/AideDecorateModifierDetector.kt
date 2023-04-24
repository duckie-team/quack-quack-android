/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import java.util.EnumSet
import org.jetbrains.uast.UCallExpression

/**
 * 꽥꽥 컴포넌트가 해당 도메인에 맞지 않는 DecorateModifier를 사용하면 경고를 표시합니다.
 *
 * ```kotlin
 * fun main() {
 *   QuackText(
 *     modifier = Modifier.longClick {}, // warning!
 *     text = "Modifier#longClick is Button domain DecorateModifier.",
 *   )
 * }
 * ```
 *
 * 도메인별 꽥꽥 컴포넌트와 도메인별 사용 가능한 DecorateModifier는 ㅂaide-processor에 의해
 * 자동 생성됩니다. 해당 파일들은 rule 패키지에 저장되며, [`aideModifiers`][aideModifiers]와 [`quackComponents`][quackComponents]로
 * 조회할 수 있습니다.
 */
class AideDecorateModifierDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames() = quackComponents.keys.toList()

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        visitMethodCallImpl(
            context = context,
            method = method,
            node = node,
            modifierFqn = ModifierFqn,
            quackComponents = quackComponents,
            aideModifiers = aideModifiers,
            issue = ISSUE,
            incidentMessage = IncidentMessage,
        )
    }

    internal companion object {
        private const val IssueId = "WrongDecorateModifier"
        private const val IssueBrief = "사용하고자 하는 DecorateModifier가 해당 스코프에 선언된 게 맞나요?"
        private val IssueExplanation = """
            꽥꽥은 컴포넌트를 도메인으로 구분하며, 도메인마다 사용 가능한 DecorateModifier가 달라집니다.
            예를 들어 다음과 같은 컴포넌트가 있습니다.
            
            ```
            @Composable
            fun QuackText(modifier: Modifier = Modifier, text: String) {
                BasicText(modifier = modifier, text = text)
            }
            ```
            
            위 `QuackText` 컴포넌트는 텍스트 표시가 주 목적이므로 Text 도메인으로 간주됩니다. 
            모든 DecorateModifier는 해당 도메인에 속하는 컴포넌트에만 사용됨을 기준으로 설계됐기 때문에 
            외부 도메인에서는 올바르게 작동하지 않을 수 있습니다. 따라서 도메인에 맞지 않는 
            DecorateModifier 사용이 감지됐을 때는 `$IssueId` informational을 발생시킵니다.
        """.trimIndent()

        internal const val IncidentMessage = "올바르지 않은 DecorateModifier의 사용이 감지되었습니다."

        internal val ISSUE = Issue.create(
            id = IssueId,
            briefDescription = IssueBrief,
            explanation = IssueExplanation,
            category = Category.USABILITY,
            priority = 5,
            severity = Severity.INFORMATIONAL,
            implementation = Implementation(
                AideDecorateModifierDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
                Scope.JAVA_FILE_SCOPE,
            ),
        )
    }
}
