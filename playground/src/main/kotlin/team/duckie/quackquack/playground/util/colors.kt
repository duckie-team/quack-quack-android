/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

// Math.floor(Math.random() * 360);
// https://codepen.io/neilorangepeel/pen/GopwNr
val Color.Companion.pastelRandom: Color
    get() {
        return hsl(
            hue = Random.nextInt(until = 360).toFloat(),
            saturation = 1f,
            lightness = 0.8f,
        )
    }
