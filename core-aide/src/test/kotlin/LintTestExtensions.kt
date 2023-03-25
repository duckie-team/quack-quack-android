/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.intellij.psi.PsiMethod
import java.util.EnumSet
import org.jetbrains.uast.UCallExpression

@Suppress("unused")
private typealias RealDetector = CoreAideDecorateModifierDetector

class CoreAideDecorateModifierTestDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames() = testQuackComponents

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        visitMethodCallImpl(
            context = context,
            node = node,
            issue = ISSUE,
            incidentMessage = RealDetector.IssueMessage,
            modifierFqn = ModifierSn,
            aideModifiers = testAideModifiers,
        )
    }

    companion object {
        private val Implementation = Implementation(
            CoreAideDecorateModifierTestDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE),
            Scope.JAVA_FILE_SCOPE,
        )

        val ISSUE = RealDetector.ISSUE.apply {
            implementation = Implementation
        }
    }
}

private val material = kotlin(
    "material.kt",
    """
    interface Modifier { companion object : Modifier }

    fun Modifier.span(text: String, color: Int) = this
    fun Modifier.onClick(lambda: () -> Unit) = this
    fun Modifier.longParameters(a: Int, b: Int, c: Int, lambda: () -> Unit) = this

    fun QuackText(modifier: Modifier) {}
    """,
)

fun lintTest(vararg sources: TestFile): TestLintResult {
    return TestLintTask.lint()
        .allowMissingSdk()
        .allowDuplicates()
        .files(*(sources.asList() + material).map { it.within("src") }.toTypedArray())
        .issues(CoreAideDecorateModifierTestDetector.ISSUE)
        .run()
}
