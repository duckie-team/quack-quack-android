/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.runtime

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.withRunningRecomposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.materialize
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.Enabled
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

private object QuackElement : QuackDataModifierModel
private object StdlibElement : Modifier.Element

class MaterializingTest : StringSpec() {
    init {
        coroutineTestScope = true

        "Modifier.Element 분리" {
            val elements = listOf(StdlibElement, QuackElement)
            var modifier: Modifier = Modifier

            elements.forEach { element ->
                modifier = modifier.then(element)
            }

            modifier.splitToList() shouldContainExactly elements
        }

        "stdlib-Modifier와 quack-Modifier가 분리돼야 함" {
            lateinit var stdlibModifiers: List<Modifier>
            lateinit var quackModifiers: List<Modifier>

            val modifier = Modifier
                .then(StdlibElement)
                .then(QuackElement)

            composed {
                val (stdlibModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
                stdlibModifiers = stdlibModifier.splitToList()
                quackModifiers = quackDataModels
            }

            expectThat(stdlibModifiers).containsExactly(StdlibElement)
            expectThat(quackModifiers).containsExactly(QuackElement)
        }

        "stdlib-ComposedModifier와 quack-ComposedModifier가 분리돼야 함" {
            lateinit var stdlibModifiers: List<Modifier>
            lateinit var quackModifiers: List<Modifier>

            @Suppress("UnnecessaryComposedModifier")
            val modifier = Modifier
                .composed { StdlibElement }
                .quackComposed { QuackElement }

            composed {
                val (stdlibModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
                stdlibModifiers = stdlibModifier.splitToList()
                quackModifiers = quackDataModels
            }

            expectThat(stdlibModifiers).hasSize(1) // stdlib ComposedModifier is private
            expectThat(quackModifiers).containsExactly(QuackElement)
        }

        "nonquack-ComposedModifier은 stdlib-materialize에 그대로 들어가야 함" {
            lateinit var stdlibModifiers: List<Modifier>
            lateinit var quackModifiers: List<Modifier>

            @Suppress("UnnecessaryComposedModifier")
            val modifier = Modifier
                .composed { StdlibElement }
                .quackComposed(quackDataModelProducer = false) { StdlibElement }

            composed {
                val (stdlibModifier, quackDataModels) = currentComposer.quackMaterializeOf(modifier)
                stdlibModifiers = stdlibModifier.splitToList()
                quackModifiers = quackDataModels
            }

            stdlibModifiers shouldHaveSize 2 // androidx.compose.ui.ComposedModifier is private
            quackModifiers shouldHaveSize 0
        }

        "quack-ComposedModifier가 nonquack한 값을 반환하면 ISE를 던짐" {
            val modifier = Modifier.quackComposed { this }

            expectThrows<IllegalStateException> {
                composed {
                    currentComposer.quackMaterializeOf(modifier)
                }
            }
                .get { message }
                .isEqualTo(QuackMaterializingErrors.QuackDataModelProducerButNot)
        }

        "quack-ComposedModifier는 first-composition에 한 번만 실행돼야 함" {
            val invoker: () -> Unit = mock()
            val modifier = Modifier.quackComposed {
                invoker.invoke()
                QuackElement
            }

            composed {
                currentComposer.quackMaterializeOf(modifier)
            }

            verify(invoker, times(1)).invoke()
        }

        "quack-ComposedModifier는 re-composition에 한 번만 재실행돼야 함" {
            lateinit var recomposeScope: RecomposeScope
            val frameClock = TestMonotonicFrameClock()

            val invoker: () -> Unit = mock()
            val modifier = Modifier.quackComposed {
                invoker.invoke()
                QuackElement
            }

            composed(
                coroutineContext = frameClock,
                withRecomposer = {
                    recomposeScope.invalidate()
                    frameClock.advance(0L)
                    verify(invoker, times(2)).invoke()
                },
            ) {
                currentComposer.quackMaterializeOf(modifier)
                recomposeScope = currentRecomposeScope
            }
        }

        "quack-KeyedComposedModifier는 안정성을 준수하며 re-composition에 한 번만 재실행돼야 함".config(
            enabledOrReasonIf = {
                Enabled.disabled(
                    reason = """
                    - asop에 있는 recomposingKeyedComposedModifierSkips 테스트도 로컬로 돌려보면 실패함
                    [https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui/src/test/kotlin/androidx/compose/ui/ComposedModifierTest.kt;l=288-320;drc=5729d22fd521d7e83ec4eb8dedd34a0c2f491738]
                    
                    - 안정성 테스트 코드를 어떻개 작성해야 할지 모르곘음
                    """.trimIndent(),
                )
            },
        ) {
            lateinit var recomposeScope: RecomposeScope
            val frameClock = TestMonotonicFrameClock()

            val invoker: () -> Unit = mock()
            var key = 0
            val modifier = Modifier.quackComposed(
                fullyQualifiedName = "test",
                key1 = key,
            ) {
                invoker.invoke()
                QuackElement
            }

            composed(
                coroutineContext = frameClock,
                withRecomposer = {
                    recomposeScope.invalidate()
                    frameClock.advance(0L)

                    key = 1
                    recomposeScope.invalidate()
                    frameClock.advance(0L)

                    verify(invoker, times(2)).invoke()
                },
            ) {
                currentComposer.quackMaterializeOf(modifier)
                recomposeScope = currentRecomposeScope
            }
        }

        "nonquack-ComposedModifier는 first-composition에 한 번만 실행돼야 함" {
            val invoker: () -> Unit = mock()
            val modifier = Modifier.quackComposed(quackDataModelProducer = false) {
                invoker.invoke()
                this
            }

            composed {
                val (stdlibModifier, _) = currentComposer.quackMaterializeOf(modifier)
                currentComposer.materialize(stdlibModifier)
            }

            verify(invoker, times(1)).invoke()
        }

        "nonquack-ComposedModifier는 re-composition에 한 번만 재실행돼야 함" {
            lateinit var recomposeScope: RecomposeScope
            val frameClock = TestMonotonicFrameClock()

            val invoker: () -> Unit = mock()
            val modifier = Modifier.quackComposed(quackDataModelProducer = false) {
                invoker.invoke()
                this
            }

            composed(
                coroutineContext = frameClock,
                withRecomposer = {
                    recomposeScope.invalidate()
                    frameClock.advance(0L)
                    verify(invoker, times(2)).invoke()
                },
            ) {
                val (stdlibModifier, _) = currentComposer.quackMaterializeOf(modifier)
                currentComposer.materialize(stdlibModifier)
                recomposeScope = currentRecomposeScope
            }
        }
    }
}

private fun Modifier.splitToList(): List<Modifier> {
    return foldIn(mutableListOf()) { acc, element ->
        acc.apply { add(element) }
    }
}

private suspend fun composed(
    coroutineContext: CoroutineContext = TestMonotonicFrameClock(),
    withRecomposer: suspend (recomposer: Recomposer) -> Unit = {},
    withinCompositionContent: @Composable (composition: Composition) -> Unit,
) {
    withContext(coroutineContext) {
        withRunningRecomposer { recomposer ->
            val compositon = Composition(
                applier = EmptyApplier,
                parent = recomposer,
            )
            compositon.setContent {
                withinCompositionContent(compositon)
            }
            withRecomposer(recomposer)
        }
    }
}

@Suppress("EmptyFunctionBlock")
private object EmptyApplier : Applier<Unit> {
    override val current: Unit = Unit
    override fun down(node: Unit) {}
    override fun up() {}
    override fun clear() {}

    override fun insertTopDown(index: Int, instance: Unit) {
        error("Unexpected")
    }

    override fun insertBottomUp(index: Int, instance: Unit) {
        error("Unexpected")
    }

    override fun remove(index: Int, count: Int) {
        error("Unexpected")
    }

    override fun move(from: Int, to: Int, count: Int) {
        error("Unexpected")
    }
}

private class TestMonotonicFrameClock : MonotonicFrameClock {
    private val frameChannel = Channel<Long>()

    suspend fun advance(frameTimeNanos: Long) {
        frameChannel.send(frameTimeNanos)
    }

    override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R {
        return onFrame(frameChannel.receive())
    }
}
