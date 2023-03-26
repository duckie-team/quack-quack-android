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
                    """
                    fun main() {
                        QuackText(
                            Modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )

                        QuackText(
                            modifier = Modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )
                    }
                    """,
                ),
            )
                .expect(
                    """
src/test.kt:6: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .onClick {}
                                ~~~~~~~~~~~
src/test.kt:7: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .longParameters(
                                ^
src/test.kt:18: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .onClick {}
                                ~~~~~~~~~~~
src/test.kt:19: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .longParameters(
                                ^
0 errors, 0 warnings
                    """,
                )
        }

        "Modifier" {
            lintTest(
                kotlin(
                    """
                    fun main() {
                        val modifier = Modifier.span("", -1)

                        QuackText(
                            modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )

                        QuackText(
                            modifier = modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )
                    }
                    """,
                ),
            )
                .expect(
                    """
src/test.kt:8: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .onClick {}
                                ~~~~~~~~~~~
src/test.kt:9: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .longParameters(
                                ^
src/test.kt:20: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .onClick {}
                                ~~~~~~~~~~~
src/test.kt:21: Information: 올바르지 않은 DecorateModifier의 사용이 감지되었습니다. [WrongDecorateModifier]
                                .longParameters(
                                ^
0 errors, 0 warnings
                    """,
                )
        }

        "변수로 분리된 Modifier".config(enabled = false) {
            TODO()
        }
    }

    "informational issue가 발생했을 때 유효한 QuickFix가 제공됨" - {
        "Modifier.Companion" {
            lintTest(
                kotlin(
                    """
                    fun main() {
                        QuackText(
                            Modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )

                        QuackText(
                            modifier = Modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )
                    }
                    """,
                ),
            )
                .expectFixDiffs(
                    """
Autofix for src/test.kt line 6: Remove onClick modifier:
@@ -6 +6
-                                 .onClick {}
Autofix for src/test.kt line 7: Remove longParameters modifier:
@@ -7 +7
-                                 .longParameters(
-                                     a = 1,
-                                     b = 2,
-                                     c = 3,
-                                     d = { println("hi") },
-                                 ),
+                                 ,
Autofix for src/test.kt line 18: Remove onClick modifier:
@@ -18 +18
-                                 .onClick {}
Autofix for src/test.kt line 19: Remove longParameters modifier:
@@ -19 +19
-                                 .longParameters(
-                                     a = 1,
-                                     b = 2,
-                                     c = 3,
-                                     d = { println("hi") },
-                                 ),
+                                 ,
                    """,
                )
        }

        "Modifier" {
            lintTest(
                kotlin(
                    """
                    fun main() {
                        val modifier = Modifier.span("", -1)

                        QuackText(
                            modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )

                        QuackText(
                            modifier = modifier
                                .span("", 0)
                                .onClick {}
                                .longParameters(
                                    a = 1,
                                    b = 2,
                                    c = 3,
                                    d = { println("hi") },
                                ),
                        )
                    }
                    """,
                ),
            )
                .expectFixDiffs(
                    """
Autofix for src/test.kt line 8: Remove onClick modifier:
@@ -8 +8
-                                 .onClick {}
Autofix for src/test.kt line 9: Remove longParameters modifier:
@@ -9 +9
-                                 .longParameters(
-                                     a = 1,
-                                     b = 2,
-                                     c = 3,
-                                     d = { println("hi") },
-                                 ),
+                                 ,
Autofix for src/test.kt line 20: Remove onClick modifier:
@@ -20 +20
-                                 .onClick {}
Autofix for src/test.kt line 21: Remove longParameters modifier:
@@ -21 +21
-                                 .longParameters(
-                                     a = 1,
-                                     b = 2,
-                                     c = 3,
-                                     d = { println("hi") },
-                                 ),
+                                 ,
                    """,
                )
        }
    }
})
