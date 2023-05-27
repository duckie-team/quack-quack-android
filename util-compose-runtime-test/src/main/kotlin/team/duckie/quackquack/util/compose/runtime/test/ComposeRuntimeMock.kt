/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)

package team.duckie.quackquack.util.compose.runtime.test

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.withRunningRecomposer
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withContext

@Suppress("TYPE_MISMATCH")
public suspend fun composed(
  coroutineContext: CoroutineContext = TestMonotonicFrameClock(),
  withRecomposer: suspend (recomposer: Recomposer) -> Unit = {},
  withinCompositionContent: @Composable (composition: Composition) -> Unit,
) {
  contract {
    callsInPlace(withRecomposer, InvocationKind.EXACTLY_ONCE)
    callsInPlace(withinCompositionContent, InvocationKind.EXACTLY_ONCE)
  }
  withContext(coroutineContext) {
    withRunningRecomposer { recomposer ->
      val compositon = Composition(applier = EmptyApplier, parent = recomposer)
      compositon.setContent { withinCompositionContent(compositon) }
      withRecomposer(recomposer)
    }
  }
}

@Suppress("EmptyFunctionBlock")
public object EmptyApplier : Applier<Unit> {
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

public class TestMonotonicFrameClock : MonotonicFrameClock {
  private val frameChannel = Channel<Long>()

  public suspend fun advance(frameTimeNanos: Long) {
    frameChannel.send(frameTimeNanos)
  }

  override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R =
    onFrame(frameChannel.receive())
}
