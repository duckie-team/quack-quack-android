/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("TrailingCommaOnCallSite")

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import io.kotest.core.spec.style.FreeSpec

class ModifierInformationalTest : FreeSpec({
    "허용되지 않은 데코레이터를 사용했을 때 informational issue가 발생함" - {
        "Modifier.Companion" {
            lintTest(
                kotlin(
                    "main.kt",
                    """
                    fun main() {
                        QuackText(Modifier.span("", 0).onClick {}.span("", 0).onClick {}.onClick {})
                        QuackText(modifier = Modifier.span("", 0).onClick {}.span("", 0).onClick {}.onClick {})
                    }
                    """,
                ),
            )
                .expectClean()
        }

        "Modifier".config(enabled = false) {
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

        "별도의 변수로 분리된 Modifier".config(enabled = false) {
            lintTest(
                kotlin(
                    "main.kt",
                    """
                    fun main() {
                        val modifier = Modifier.span("", 1).onClick {}.span("", 2)
                        QuackText(modifier)
                        QuackText(modifier = modifier)

                        val modifier2 = Modifier
                            .span("", 1)
                            .longParameters(
                                a = 1,
                                b = 2,
                                c = 3,
                                lambda = {},
                            )
                            .onClick {}
                            .span("", 2)
                        QuackText(modifier)
                        QuackText(modifier = modifier)
                    }
                    """.trimIndent()
                )
            )
        }
    }

    "informational issue가 발생했을 때 유효한 QuickFix가 제공됨".config(enabled = false) - {
    }
})
