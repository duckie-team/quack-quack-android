/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.asCall
import com.intellij.psi.PsiMethod
import java.util.EnumSet
import org.jetbrains.kotlin.daemon.common.findWithTransform
import org.jetbrains.uast.UCallExpression
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
 * 자동 생성됩니다. 해당 파일들은 rule 패키지에 저장되며, `aideModifiers`와 `aideComponents`로
 * 조회할 수 있습니다.
 */
class CoreAideModifierDetector : Detector(), SourceCodeScanner {
    private val quackComponents = aideComponents.values.flatten()

    override fun getApplicableMethodNames() = quackComponents

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        val domain = context.file.nameWithoutExtension
        val acceptableModifiers = aideModifiers[domain] ?: return

        val modifiers = node.valueArguments.findWithTransform { argument ->
            val chains = argument.getQualifiedChain()
            val isModifier = chains.indexOfFirst { chain ->
                val type = chain.getExpressionType()?.canonicalText
                type?.startsWith("androidx.compose.ui.Modifier") == true // .Companion
            } == 0
            isModifier to chains.drop(1) // without `modifier` or `Modifier` chain
        } ?: return

        val quackModifiers = modifiers.mapNotNull { modifierExpression ->
            val modifierIdentifier = modifierExpression.asCall()?.methodIdentifier
            modifierIdentifier?.takeIf { modifier ->
                aideModifiers[modifier.name] != null
            }
        }

        quackModifiers.forEach { modifier ->
            if (!acceptableModifiers.contains(modifier.name)) {
                val incident = Incident(context, ISSUE)
                    .message("Wrong QuackModifier usage")
                    .at(modifier)
                context.report(incident)
            }
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "TypedModifier",
            briefDescription = "올바르지 않은 Modifier의 사용입니다.",
            explanation = "TODO",
            category = Category.USABILITY,
            priority = 5,
            severity = Severity.INFORMATIONAL,
            implementation = Implementation(
                CoreAideModifierDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE),
                Scope.JAVA_FILE_SCOPE,
            )
        )
    }
}
