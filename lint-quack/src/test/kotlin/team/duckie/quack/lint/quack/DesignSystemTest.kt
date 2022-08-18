/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DesignSystemTest.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "TestFunctionName")

package team.duckie.quack.lint.quack

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class DesignSystemTest {
    @Test
    fun designSystem() {
        lint()
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                    fun Button() {}
                    fun test() {
                        Button()
                    }
                    """
                )
            )
            .issues(DesignSystemDetector.ISSUE)
            .run()
            .expectErrorCount(
                expectedCount = 1,
            )
    }
}
