/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 덕키에서 사용할 색상을 정의합니다.
 *
 * 색상 정의는 코드 스타일 통일을 위해 ARGB 형식이 아닌 Hex 형식으로 해야 합니다.
 *
 * @param value 색상 값. 이 인자로 색상을 사용하기 위해 컴포즈의 [Color]로 받습니다.
 */
@Immutable
@JvmInline
public value class QuackColor(public val value: Color) : ReadOnlyProperty<Any, Color> {
  /** [QuackColor]를 [Brush]로 변환합니다. */
  @Stable
  public fun toBrush(): SolidColor = SolidColor(value)

  /**
   * 정해진 [QuackColor]에서 일부 값만 변경이 필요할 때가 있습니다. 이를 대응하기 위해
   * 현재 [QuackColor]에서 변경을 허용하는 필드만 변경하여 새로운 [QuackColor]를 반환하는
   * API를 제공합니다.
   *
   *
   * @param alpha 변경할 투명도. 투명도는 고정된 디자인의 목적을 해치지 않을 것으로 예상하여
   * 변경을 허용합니다.
   *
   * @return [alpha] 값이 변경된 [QuackColor]
   */
  @Stable
  public fun change(alpha: Float): QuackColor {
    return if (alpha == value.alpha) {
      this
    } else {
      QuackColor(value.copy(alpha = alpha))
    }
  }

  public companion object {
    @Stable
    public val Unspecified: QuackColor = QuackColor(Color.Unspecified)

    @Stable
    public val DuckieOrange: QuackColor = QuackColor(Color(0xFFFF8300))

    @Stable
    public val Alert: QuackColor = QuackColor(Color(0xFFFF2929))

    @Stable
    public val Dimmed: QuackColor = QuackColor(Color(0x99000000)) // 0.6 alpha

    @Stable
    public val Gray1: QuackColor = QuackColor(Color(0xFF666666))

    @Stable
    public val Gray2: QuackColor = QuackColor(Color(0xFFA8A8A8))

    @Stable
    public val Gray3: QuackColor = QuackColor(Color(0xFFEFEFEF))

    @Stable
    public val Gray4: QuackColor = QuackColor(Color(0xFFF6F6F6))

    @Stable
    public val Black: QuackColor = QuackColor(Color(0xFF222222))

    @Stable
    public val White: QuackColor = QuackColor(Color(0xFFFFFFFF))
  }

  @Stable
  override fun getValue(thisRef: Any, property: KProperty<*>): Color = value
}
