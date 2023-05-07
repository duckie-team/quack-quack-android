/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import team.duckie.quackquack.util.backend.test.stub.CasaStub
import team.duckie.quackquack.util.backend.test.stub.ComposeStub
import team.duckie.quackquack.util.backend.test.stub.SugarStub

val stubs = listOf(
  kotlin("Composable.kt", ComposeStub.Composable),
  kotlin("CasaAnnotations.kt", CasaStub.Annotations),
  kotlin("SugarAnnotations.kt", SugarStub.Annotations),
)
