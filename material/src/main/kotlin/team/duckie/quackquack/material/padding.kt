/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packFloats
import androidx.compose.ui.util.unpackFloat1
import androidx.compose.ui.util.unpackFloat2

/**
 * 꽥꽥에서 사용할 패딩을 나타냅니다.
 *
 * 꽥꽥 컴포넌트에는 보통 가로, 세로 동일한 간격의 패딩이 적용됩니다. 따라서 이 클래스를
 * 이용한 패딩은 [가로][horizontal]와 [세로][vertical] 값만 보유합니다.
 *
 * 이 클래스의 인스턴스를 만들려면 [QuackPadding] 함수를 사용하세요.
 */
@Immutable
@JvmInline
public value class QuackPadding internal constructor(@PublishedApi internal val packedValue: Long) {
  /** 왼쪽과 오른쪽 가장자리에 적용될 패딩 값 */
  public val horizontal: Dp
    inline get() = unpackFloat1(packedValue).dp

  /** 위쪽과 아래쪽 가장자리에 적용될 패딩 값 */
  public val vertical: Dp
    inline get() = unpackFloat2(packedValue).dp

  /** [QuackPadding] 값을 [PaddingValues]로 변환합니다. */
  @Stable
  public fun asPaddingValues(): PaddingValues =
    PaddingValues(horizontal = horizontal, vertical = vertical)

  /** [QuackPadding]의 일부 값을 변경하여 반환합니다. */
  @Stable
  public fun copy(
    horizontal: Dp = this.horizontal,
    vertical: Dp = this.vertical,
  ): QuackPadding =
    QuackPadding(horizontal = horizontal, vertical = vertical)
}

/**
 * [QuackPadding] 클래스의 인스턴스를 만듭니다.
 *
 * @param horizontal 왼쪽과 오른쪽 가장자리에 적용될 패딩 값
 * @param vertical 위쪽과 아래쪽 가장자리에 적용될 패딩 값
 */
@Stable
public fun QuackPadding(horizontal: Dp = 0.dp, vertical: Dp = 0.dp): QuackPadding =
  QuackPadding(packFloats(horizontal.value, vertical.value))
