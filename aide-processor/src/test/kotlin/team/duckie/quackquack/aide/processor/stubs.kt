/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.aide.processor

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import team.duckie.quackquack.util.backend.test.stub.AideStub
import team.duckie.quackquack.util.backend.test.stub.ComposeStub

val stubs = listOf(
    kotlin("Modifier.kt", ComposeStub.Modifier),
    kotlin("Composable.kt", ComposeStub.Composable),
    kotlin("DecorateModifier.kt", AideStub.Annotation),
)
