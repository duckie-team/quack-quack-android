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

@Suppress("unused") // why "unused" needed?
private typealias RealDetector = CoreAideDecorateModifierDetector
private typealias TestDetector = CoreAideDecorateModifierTestDetector

class CoreAideDecorateModifierTestDetector : Detector(), SourceCodeScanner {
    override fun getApplicableMethodNames() = testQuackComponents.keys.toList()

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        visitMethodCallImpl(
            context = context,
            method = method,
            node = node,
            modifierFqn = ModifierSn,
            quackComponents = testQuackComponents,
            aideModifiers = testAideModifiers,
            issue = ISSUE,
            incidentMessage = RealDetector.IncidentMessage,
        )
    }

    companion object {
        private val Implementation = Implementation(
            TestDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE),
            Scope.JAVA_FILE_SCOPE,
        )

        val ISSUE = RealDetector.ISSUE.apply {
            implementation = Implementation
        }
    }
}

private val material = kotlin(
    """
    interface Modifier { companion object : Modifier }

    fun Modifier.span(text: String, color: Int) = this
    fun Modifier.onClick(lambda: () -> Unit) = this
    fun Modifier.longParameters(a: Int, b: Int, c: Int, d: () -> Unit) = this

    fun QuackText(modifier: Modifier) {}
    """,
)

fun lintTest(vararg sources: TestFile): TestLintResult {
    return TestLintTask.lint()
        .allowMissingSdk()
        .allowDuplicates()
        .allowSystemErrors(false) // default is true
        .files(*(sources.asList() + material).map { it.within("src") }.toTypedArray())
        .issues(TestDetector.ISSUE)
        .run()
}
