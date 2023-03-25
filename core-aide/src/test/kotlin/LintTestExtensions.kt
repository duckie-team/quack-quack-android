/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestLintTask

fun lintTest(vararg sources: TestFile): TestLintTask {
    return TestLintTask.lint()
        .allowMissingSdk()
        .allowDuplicates()
        .files(*stubs, *sources)
}
