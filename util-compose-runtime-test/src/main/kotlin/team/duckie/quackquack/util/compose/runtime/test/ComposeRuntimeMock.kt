/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)

package team.duckie.quackquack.util.compose.runtime.test

import android.view.Choreographer
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

/**
 * [Recomposer]를 임의로 실행하여 Compose runtime 환경을 시뮬레이션합니다.
 *
 * @param coroutineContext [Recomposer]를 실행할 [CoroutineContext]
 * @param withRecomposer [Recomposer]를 기반으로 실행할 람다
 * @param withinCompositionContent [Composition.setContent] 안에서 실행할 람다
 */
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

/**
 * 아무 동작도 하지 않는 [Applier]를 구현합니다.
 * 만약 트리 순회가 요청되면 [IllegalStateException]이 발생합니다.
 */
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

/** [Choreographer]와 Latch 개념이 없는 순수한 [MonotonicFrameClock]을 구현합니다. */
public class TestMonotonicFrameClock : MonotonicFrameClock {
  private val frameChannel = Channel<Long>()

  public suspend fun advance(frameTimeNanos: Long) {
    frameChannel.send(frameTimeNanos)
  }

  override suspend fun <R> withFrameNanos(onFrame: (frameTimeNanos: Long) -> R): R =
    onFrame(frameChannel.receive())
}
