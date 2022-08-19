/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ComposeFiles.kt] created by Ji Sungbin on 22. 8. 19. 오전 7:43
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "unused",
    "UnstableApiUsage",
    "SameParameterValue",
)

package team.duckie.quackquack.common.lint.test

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestFile
import org.intellij.lang.annotations.Language

val ComposableAnnotationFile: TestFile = kotlin(
    """
    package androidx.compose.runtime

    @MustBeDocumented
    @Retention(AnnotationRetention.BINARY)
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.TYPE,
        AnnotationTarget.TYPE_PARAMETER,
        AnnotationTarget.PROPERTY_GETTER,
    )
    annotation class Composable
    """
)

fun composableTestFile(@Language("kotlin") source: String): TestFile = kotlin(
    """
    package land.sungbin.dummy

    import androidx.compose.runtime.Composable

    $source
    """
)
