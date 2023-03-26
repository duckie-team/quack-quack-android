/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.android.tools.lint.detector.api.Incident
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.asCall
import com.intellij.psi.PsiMethod
import org.jetbrains.annotations.VisibleForTesting
import org.jetbrains.kotlin.daemon.common.findWithTransform
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.getQualifiedChain

// TODO: 문서화
@VisibleForTesting
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
    val acceptableModifiers = aideModifiers[domain] ?: return

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
