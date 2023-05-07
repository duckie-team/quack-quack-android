/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.processor

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import team.duckie.quackquack.util.backend.test.stub.ComposeStub
import team.duckie.quackquack.util.backend.test.stub.SugarStub

val stubs = listOf(
    kotlin("Modifier.kt", ComposeStub.Modifier),
    kotlin("Composable.kt", ComposeStub.Composable),
    kotlin("annotations.kt", SugarStub.Annotations),
    kotlin("typer.kt", SugarStub.Typer),
    kotlin(
        "AwesomeTypes.kt",
        """
@JvmInline
value class AwesomeType(val index: Int) {
    companion object {
        val One = AwesomeType(1)
    }
}

@JvmInline
value class AwesomeType2(val index: Int) {
    companion object {
        val One = AwesomeType2(1)
        val Two = AwesomeType2(2)
        val Three = AwesomeType2(3)
    }
}

@JvmInline
value class AwesomeType3(val index: Int)
        """,
    ),
)
