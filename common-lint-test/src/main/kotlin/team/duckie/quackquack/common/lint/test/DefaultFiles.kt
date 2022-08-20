/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [DefaultFiles.kt] created by ricky_0_k on 22. 8, 21. 오전 2:44
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

/**
 * [TestFile] 로 사용할 일반 더미 파일
 */
val DefaultFile: TestFile = kotlin(
    """
    package land.sungbin.dummy

    """.trimIndent()
)

/**
 * @Composable 을 사용하지 않는 [TestFile] 을 만들어 줍니다.
 *
 * @param source 일반 코틀린 코드
 *
 * @return [source] 코드에서 테스트를 위한 준비를 마친 [TestFile]
 */
fun defaultTestFile(@Language("kotlin") source: String): TestFile = kotlin(
    """
    package land.sungbin.dummy

    ${source.trimIndent()}
    """.trimIndent()
)
