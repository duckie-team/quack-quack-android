/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.asCall
import com.intellij.psi.PsiMethod
import java.util.EnumSet
import org.jetbrains.annotations.VisibleForTesting
import org.jetbrains.kotlin.daemon.common.findWithTransform
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.getQualifiedChain

/**
 * 꽥꽥 컴포넌트가 해당 도메인에 맞지 않는 Modifier를 사용하면 경고를 표시합니다.
 *
 * ```kotlin
 * fun main() {
 *   QuackText(
 *     modifier = Modifier.longClick {}, // warning!
 *     text = "Modifier#longClick is Button domain modifier.",
 *   )
 * }
 * ```
 *
 * 도메인별 꽥꽥 컴포넌트와 도메인별 사용 가능한 Modifier는 core-aide-processor에 의해
 * 자동 생성됩니다. 해당 파일들은 rule 패키지에 저장되며, [`aideModifiers`][aideModifiers]와 [`aideComponents`][aideComponents]로
 * 조회할 수 있습니다.
 */
class CoreAideDecorateModifierDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames() = quackComponents

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val domain = context.file.nameWithoutExtension
        val acceptableModifiers = aideModifiers[domain] ?: return

        val modifiers = node.valueArguments.findWithTransform { argument ->
            val chains = argument.getQualifiedChain()
            val isModifier = chains.firstOrNull()?.let { chain ->
                val type = chain.getExpressionType()?.canonicalText
                type?.startsWith("androidx.compose.ui.Modifier") == true // `Modifier` or `Modifier.Companion` type
            } == true
            isModifier to chains.drop(1) // without `modifier` or `Modifier` chain
        } ?: return

        val decorateModifiers = modifiers.filter { modifier ->
            val identifier = modifier.asCall()?.methodIdentifier ?: return@filter false
            aideModifiers["_${identifier.name}"] != null
        }

        decorateModifiers.forEach { modifier ->
            context.reportWrongModifierIfNeeded(acceptableModifiers, modifier)
        }
    }

    private fun JavaContext.reportWrongModifierIfNeeded(
        acceptableModifiers: List<String>,
        modifier: UExpression,
    ) {
        val nullSafetySourcePsi = modifier.sourcePsi ?: return
        val identifier = modifier.asCall()?.methodIdentifier ?: return

        val startPsi = nullSafetySourcePsi.prevSibling // PsiElement (DOT) "."
        val endPsi = nullSafetySourcePsi.lastChild?.lastChild // PsiElement (RPAR) ")"

        if (!acceptableModifiers.contains(identifier.name)) {
            val removalFix = LintFix.create()
                .name("Remove ${identifier.name} modifier")
                .replace()
                .all()
                .with("")
                .reformat(true)
                .autoFix()
                .build()
            val incident = Incident(context = this, issue = ISSUE)
                .message(IssueMessage)
                .fix(removalFix)
            val baseLocation = incident.parseLocation(nullSafetySourcePsi)

            if (startPsi != null && endPsi != null) {
                val startPsiLocation = incident.parseLocation(startPsi)
                val endPsiLocation = incident.parseLocation(endPsi)
                val resolvedLocation = if (startPsiLocation.start != null && endPsiLocation.end != null) {
                    Location.create(
                        file = baseLocation.file,
                        start = startPsiLocation.start!!,
                        end = endPsiLocation.end!!,
                    )
                } else {
                    baseLocation
                }
                incident.updateLocationAndScope(resolvedLocation)
            } else {
                incident.updateLocationAndScope(baseLocation)
            }

            report(incident)
        }
    }

    companion object {
        private const val IssueId = "DecorateModifier"
        private const val IssueMessage = "올바르지 않은 Modifier의 사용이 감지되었습니다."
        private const val IssueBrief = "사용하고자 하는 Modifier가 해당 스코프에 선언된 Modifier가 맞나요?"
        private val IssueExplanation = """
            꽥꽥은 컴포넌트를 도메인으로 구분하며, 도메인마다 사용 가능한 Modifier가 달라집니다.
            예를 들어 다음과 같은 컴포넌트가 있습니다.
            
            ```
            @Composable
            fun QuackText(modifier: Modifier = Modifier, text: String) {
                BasicText(modifier = modifier, text = text)
            }
            ```
            
            위 `QuackText` 컴포넌트는 텍스트 표시가 주 목적이므로 Text 도메인으로 간주됩니다. 
            일부 Modifier는 Text 도메인에 속하는 컴포넌트에만 사용됨을 기준으로 설계됐기 때문에 
            외부 도메인에서는 올바르게 작동하지 않을 수 있습니다. 따라서 도메인에 맞지 않는 
            Modifier 사용이 감지됐을 때는 `$IssueId` informational을 발생시킵니다.
            ```
        """.trimIndent()

        val ISSUE = Issue.create(
            id = IssueId,
            briefDescription = IssueBrief,
            explanation = IssueExplanation,
            category = Category.USABILITY,
            priority = 5,
            severity = Severity.INFORMATIONAL,
            implementation = Implementation(
                CoreAideDecorateModifierDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
                Scope.JAVA_FILE_SCOPE,
            ),
        )
    }
}

@VisibleForTesting
internal fun Incident.parseLocation(source: Any): Location {
    val prevLocate = location
    val newLocate = at(source).location
    location = prevLocate
    return newLocate
}

private fun Incident.updateLocationAndScope(source: Location) = apply {
    location = source
    scope = source
}