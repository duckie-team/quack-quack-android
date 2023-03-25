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
        .issues(CoreAideDecorateModifierDetector.ISSUE)
        .run()
}
