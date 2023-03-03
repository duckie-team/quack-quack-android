/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.withNotNull

class QuackCoreAideProcessorTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    // 1. TypedModifier 어노테이션이 달린 모든 Modifier가 포함돼야 함
    // 2. TypedModifier 어노테이션이 달린 일반 함수에서는 에러가 발생해야 함
    // 3. 중복되는 값이 없어야 함
    // 4. symbol filter가 작동해야 함
    @Test
    fun `AideModifiers가 정상적으로 생성됨`() {
        val compilation = prepareCompilation(
            kotlin(
                "text.kt",
                """
                import team.duckie.quackquack.aide.annotation.TypedModifier
                import androidx.compose.runtime.Composable
                import androidx.compose.ui.Modifier

                @TypedModifier
                fun Modifier.span(): Modifier = Modifier

                @TypedModifier
                fun Modifier.spans(): Modifier = Modifier

                @TypedModifier
                fun Modifier.spans(): Modifier = Modifier

                @TypedModifier
                fun emptyModifier(): Unit = Unit

                @TypedModifier
                fun Modifier.noReturnModifier(): Unit = Unit

                @TypedModifier
                private fun Modifier.noPublicModifier(): Modifier = Modifier
                """,
            ),
        )
        val result = compilation.compile()

        expectThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)

        val aideModifiersKt = compilation.kspSourcesDir.walkTopDown().firstOrNull { it.name == "AideModifiers.kt" }

        expectThat(aideModifiersKt).withNotNull {
            get(File::readText).isEqualTo(
                """
                
                """.trimIndent(),
            )
        }
    }

    private fun prepareCompilation(vararg sourceFiles: SourceFile): KotlinCompilation {
        return KotlinCompilation().apply {
            sources = sourceFiles.asList()
            workingDir = temporaryFolder.root
            verbose = false
            inheritClassPath = true
            symbolProcessorProviders = listOf(QuackCoreAideSymbolProcessorProvider())
            messageOutputStream = System.out
        }
    }

    private fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
        return prepareCompilation(*sourceFiles).compile()
    }
}
