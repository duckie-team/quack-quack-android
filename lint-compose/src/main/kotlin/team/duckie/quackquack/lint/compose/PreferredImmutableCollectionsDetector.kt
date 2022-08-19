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

val PreferredImmutableCollectionsIssue = Issue.create(
    id = "PreferredImmutableCollections",
    briefDescription = "MutableCollections 사용 감지됨",
    explanation = "Skippable 을 위해 ImmutableCollections 사용을 지향해야 합니다.",
    category = Category.PERFORMANCE,
    priority = 7,
    severity = Severity.ERROR,
    implementation = Implementation(
        PreferredImmutableCollectionsDetector::class.java,
        Scope.JAVA_FILE_SCOPE
    )
)

private val CollectionNames = listOf(
    "List",
    "Map",
    "Set",
)

private val ImmutableNames = listOf(
    "Immutable",
    "Persistent",
)

class PreferredImmutableCollectionsDetector : Detector(), SourceCodeScanner {
    override fun getApplicableUastTypes() = listOf(UMethod::class.java)

    override fun createUastHandler(context: JavaContext) = object : UElementHandler() {
        override fun visitMethod(node: UMethod) {
            if (!node.isComposable || !node.isReturnsUnit) return

            for (parameter in node.uastParameters) {
                val ktParameter = parameter.sourcePsi as? KtParameter ?: continue
                val parameterType = ktParameter.typeReference ?: continue
                val parameterTypeName = parameterType.text
                val isCollection = CollectionNames.any { collectionName ->
                    parameterTypeName.contains(collectionName)
                }
                val isImmutable = ImmutableNames.any { immutableName ->
                    parameterTypeName.contains(immutableName)
                }
                if (isCollection && !isImmutable) {
                    context.report(
                        issue = PreferredImmutableCollectionsIssue,
                        scope = parameterType,
                        location = context.getNameLocation(parameterType),
                        message = "MutableCollection 사용 감지됨"
                    )
                }
            }
        }
    }
}
