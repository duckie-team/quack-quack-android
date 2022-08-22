/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [color.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:48
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import kotlin.reflect.KProperty

@Immutable
@JvmInline
value class QuackColor private constructor(
    val value: Color,
) {
    companion object {
        @Stable
        val Unspecified = QuackColor(
            value = Color.Unspecified,
        )

        @Stable
        val DuckieOrange = QuackColor(
            value = Color(
                red = 255,
                green = 131,
                blue = 0,
            )
        )

        @Stable
        val Greyish = QuackColor(
            value = Color(
                red = 168,
                green = 168,
                blue = 168,
            )
        )

        @Stable
        val White = QuackColor(
            value = Color.White,
        )

        @Stable
        val Black = QuackColor(
            value = Color.Black,
        )
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
}
