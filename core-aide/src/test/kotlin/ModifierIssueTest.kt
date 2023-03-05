/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Ignore
import org.junit.Test

@Ignore(value = "현재는 구현된 도메인이 하나뿐임")
class ModifierIssueTest {
    @Test
    fun test() {
        lint()
            .allowMissingSdk()
            .allowDuplicates()
            .files(
                kotlin(
                    "androidx/compose/ui/Modifier.kt",
                    """
                    package androidx.compose.ui
                    interface Modifier { companion object : Modifier }
                    """.trimIndent(),
                ).indented().within("src"),
                kotlin(
                    "team/duckie/quackquack/core/component/modifiers.kt",
                    """
                    package team.duckie.quackquack.core.component
                    fun Modifier.span() = this
                    fun Modifier.highlight() = this
                    fun Modifier.chain() = this    
                    fun Modifier.chain2() = this    
                    """,
                ).indented().within("src"),
                kotlin(
                    "text.kt",
                    """
                    import androidx.compose.ui.Modifier
                    import team.duckie.quackquack.core.component.span
                    import team.duckie.quackquack.core.component.highlight
                    import team.duckie.quackquack.core.component.chain
                    import team.duckie.quackquack.core.component.chain2

                    fun main() {
                        QuackText(modifier = Modifier.highlight().chain().span().chain2())
                    }

                    fun QuackText(modifier: Modifier) { }
                    """,
                ).indented().within("src"),
            )
            .issues(CoreAideModifierDetector.ISSUE)
            .run()
            .expectClean()
    }
}
