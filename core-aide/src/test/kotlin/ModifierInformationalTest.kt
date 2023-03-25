/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import io.kotest.core.spec.style.StringSpec

class ModifierInformationalTest : StringSpec({
    "허용되지 않은 데코레이터를 사용했을 때 informational issue가 발생함" {
        lintTest(
            kotlin(
                "text.kt",
                """
                fun main() {
                    QuackText(modifier = Modifier.onClick {})
                }
                """,
            ),
        )
            .expectClean()
    }

    "informational issue가 발생했을 때 유효한 QuickFix가 제공됨".config(enabled = false) {
    }
})
