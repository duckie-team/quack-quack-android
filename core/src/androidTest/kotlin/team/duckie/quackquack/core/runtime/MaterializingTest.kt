/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.runtime

import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.materialize
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize

private object QuackFirstData : QuackDataModifierModel
private object QuackSecondData : QuackDataModifierModel
private object QuackThirdData : QuackDataModifierModel

private object StdlibFirstData : Modifier.Element
private object StdlibSecondData : Modifier.Element
private object StdlibThirdData : Modifier.Element

/**
 * [quackMaterializeOf]와 [quackComposed]를 테스트합니다.
 *
 * - [QuackDataModifierModel]과 [Modifier]가 분리돼야 함
 * - stdlib의 `ComposedModifier`와 [quackComposed]의 `ComposedModifier`가 분리돼야 함
 * - [quackComposed]의 non-quack한 composed는 리컴포지션될 때 한 번만 재실행돼야 함
 */
class MaterializingTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun Modifier_to_ListModifier() {
        val elements = listOf(
            StdlibFirstData,
            QuackFirstData,
            QuackSecondData,
            StdlibSecondData,
            QuackThirdData,
            StdlibThirdData,
        )
        var modifier: Modifier = Modifier
        elements.forEach { element ->
            modifier = modifier.then(element)
        }

        expectThat(modifier.splitToList()).containsExactly(elements)
    }

    // - [QuackDataModifierModel]과 [Modifier]가 분리돼야 함
    @Test
    fun split_QuackDataModifierModel_and_Modifier() {
        val modifier = Modifier
            .then(StdlibFirstData)
            .then(QuackFirstData)
            .then(QuackSecondData)
            .then(StdlibSecondData)
            .then(QuackThirdData)
            .then(StdlibThirdData)

        rule.setContent {
            val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
            val composeModifiers = composeModifier.splitToList()

            expectThat(quackDataModels).containsExactly(QuackFirstData, QuackSecondData, QuackThirdData)
            expectThat(composeModifiers).containsExactly(StdlibFirstData, StdlibSecondData, StdlibThirdData)
        }
    }

    // - stdlib의 `ComposedModifier`와 [quackComposed]의 `ComposedModifier`가 분리돼야 함
    @Test
    fun split_stdlib_ComposeModifier_and_QuackComposed_ComposedModifier() {
        @Suppress("UnnecessaryComposedModifier")
        val modifier = Modifier
            .composed { StdlibFirstData }
            .composed { StdlibSecondData }
            .composed { StdlibThirdData }
            .quackComposed { QuackFirstData }
            .quackComposed { QuackSecondData }
            .quackComposed { QuackThirdData }

        rule.setContent {
            val (composeModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
            val composeModifiers = composeModifier.splitToList()

            expectThat(quackDataModels).containsExactly(QuackFirstData, QuackSecondData, QuackThirdData)
            expectThat(composeModifiers).hasSize(3) // stdlib ComposedModifier is private
        }
    }

    // - [quackComposed]의 non-quack한 composed는 first-composition시에 최초 한 번만 실행돼야 함
    @Ignore("FIXME(sungbin): 최초 컴포지션시에 stdlib-materializerOf으로 들어가면 re-invoke됨 (동일 프레임에서)")
    @Test
    fun must_OnceWorks_QuackComposeds_NonQuackComposed_with_stdlib_materialierOf() {
        val value = mutableListOf<Int>()
        val quackComposedModifier = Modifier
            .quackComposed { value += 0; this }
            .quackComposed { value += 1; this }
            .quackComposed { value += 2; this }

        rule.setContent {
            val (composedModifier, _) = currentComposer.quackMaterializeOf(quackComposedModifier)
            currentComposer.materialize(composedModifier)

            expectThat(value).containsExactly(0, 1, 2)
        }
    }

    // - [quackComposed]의 non-quack한 composed는 리컴포지션될 때 한 번만 재실행돼야 함
    @Test
    fun must_OnceWorks_QuackComposeds_NonQuackComposed_with_stdlib_materialierOf_in_recomposition() {
        val value = mutableListOf<Int>()
        val quackComposedModifier = Modifier
            .quackComposed { value += 0; this }
            .quackComposed { value += 1; this }
            .quackComposed { value += 2; this }

        rule.setContent {
            val (composedModifier, _) = currentComposer.quackMaterializeOf(quackComposedModifier)

            currentComposer.materialize(composedModifier) // first-composition
            currentComposer.materialize(composedModifier) // re-composition

            expectThat(value).containsExactly(0, 1, 2, 0, 1, 2, 0, 1, 2)
        }
    }
}

private fun Modifier.splitToList(): List<Modifier> {
    val modifiers = mutableListOf<Modifier>()
    foldIn<Modifier>(Modifier) { acc, element ->
        modifiers += element
        acc
    }
    return modifiers
}
