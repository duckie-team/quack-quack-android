/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Ignore
import org.junit.Test

@Ignore("미완성 테스트")
class ModifierIssueTest {
    @Test
    fun debug() {
        lint()
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    fun test() {}
                    """,
                ).indented(),
            )
            .issues(CoreAideModifierDetector.ISSUE)
            .run()
            .expectClean()
    }
}
