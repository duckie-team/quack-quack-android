/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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

/**
 * [TestFile] 로 사용할 @Composable 어노테이션 더미 파일
 */
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
    """.trimIndent()
)

/**
 * @Composable 을 사용하기 위한 준비를 마친 [TestFile] 로 만들어 줍니다.
 *
 * @param source @Composable 을 사용할 코틀린 코드
 *
 * @return [source] 코드에서 @Composable 을 사용하기 위한 준비를 마친 [TestFile]
 */
fun composableTestFile(@Language("kotlin") source: String): TestFile = kotlin(
    """
    package land.sungbin.dummy

    import androidx.compose.runtime.Composable

    ${source.trimIndent()}
    """.trimIndent()
)
