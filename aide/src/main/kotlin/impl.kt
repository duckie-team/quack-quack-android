/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.asCall
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.daemon.common.findWithTransform
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.getQualifiedChain

/**
 * visitMethodCallImpl 구현을 분리한 이유
 * =====================================
 *
 * [ModifierInformationalTest]에서 테스트를 항상 보장되는 결과로 진행하기 위해
 * [rule]의 fixture를 제공해야 합니다. 이를 위해 setUp, tearDown으로 접근하고
 * 각각 단계에서 [rule] 파일을 교체하도록 하였지만 테스트가 실행되면
 * 이미 프로덕션 [rule]이 적용된 상태로 로드됐기에 효과가 없었습니다.
 *
 * 따라서 setUp, tearDown으로 fixture를 설정하는 건 어렵겠다고 판단하였고,
 * [rule]을 하드 코딩하는 게 아닌 직접 제공할 수 있는 [AideDecorateModifierDetector]를
 * 만드는 것으로 해결하였습니다. [AideDecorateModifierDetector]를 [프로덕션][AideDecorateModifierDetector]과 [테스트][CoreAideDecorateModifierTestDetector] 버전으로
 * 나눈 다음에, 공통되는 린트 로직을 갖을 수 있도록 해당 린트의 핵심 구현인 `visitMethodCall`을
 * 별도 함수로 분리하였습니다. 분리된 [visitMethodCallImpl] 함수에 [rule] fixture를 제공함으로써
 * testable한 [AideDecorateModifierDetector]를 만들 수 있습니다.
 */
internal fun visitMethodCallImpl(
    context: JavaContext,
    method: PsiMethod,
    node: UCallExpression,
    modifierFqn: String,
    quackComponents: Map<String, String>,
    aideModifiers: Map<String, List<String>>,
    issue: Issue,
    incidentMessage: String,
) {
    val domain = quackComponents[method.name]!!
    val acceptableModifiers = aideModifiers[domain].orEmpty()

    val modifiers = node.valueArguments.findWithTransform { argument ->
        val chains = argument.getQualifiedChain()
        val isModifier = chains.firstOrNull()?.let { chain ->
            val type = chain.getExpressionType()?.canonicalText
            type?.startsWith(modifierFqn) == true // `Modifier` or `Modifier.Companion` type
        } == true
        isModifier to chains.drop(1) // without `modifier` or `Modifier` chain
    } ?: return

    val decorateModifiers = modifiers.filter { modifier ->
        val identifier = modifier.asCall()?.methodIdentifier ?: return@filter false
        aideModifiers["_${identifier.name}"] != null
    }

    decorateModifiers.forEach { modifier ->
        context.reportWrongModifierIfNeeded(
            acceptableModifiers = acceptableModifiers,
            modifier = modifier,
            issue = issue,
            incidentMessage = incidentMessage,
        )
    }
}

private fun JavaContext.reportWrongModifierIfNeeded(
    acceptableModifiers: List<String>,
    modifier: UExpression,
    issue: Issue,
    incidentMessage: String,
) {
    val sourcePsi = modifier.sourcePsi ?: return
    val identifier = modifier.asCall()?.methodIdentifier ?: return

    val startPsi = sourcePsi.prevSibling // PsiElement (DOT) "."
    val endPsi = sourcePsi.lastChild?.lastChild // PsiElement (RPAR) ")"

    if (!acceptableModifiers.contains(identifier.name)) {
        val removalFix = LintFix.create()
            .name("Remove ${identifier.name} modifier")
            .replace()
            .all()
            .with("")
            .reformat(true)
            .autoFix()
            .build()
        val incident = Incident(context = this, issue = issue)
            .message(incidentMessage)
            .fix(removalFix)
        val baseLocation = incident.parseLocation(sourcePsi)

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

private fun Incident.parseLocation(source: Any): Location {
    val prevLocate = location
    val newLocate = at(source).location
    location = prevLocate
    return newLocate
}

private fun Incident.updateLocationAndScope(source: Location) = apply {
    location = source
    scope = source
}
