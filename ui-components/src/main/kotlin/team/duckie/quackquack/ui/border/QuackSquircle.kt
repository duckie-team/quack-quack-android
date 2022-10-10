/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.border

import android.graphics.Path
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.scaleMatrix
import androidx.core.graphics.translationMatrix
import kotlin.math.abs
import kotlin.math.pow

public class QuackSquircle : Shape {

    public override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline.Generic = Outline.Generic(
        path = createSquirclePath(size, SMOOTHING)
    )

    private fun createSquirclePath(size: Size, smoothing: Double): androidx.compose.ui.graphics.Path {
        return Path().apply {
            val oversize = size.width * OVERSAMPLING_MULTIPLIER
            val squircleRadius = (oversize / 2F).toInt()

            val poweredRadius = squircleRadius
                .toDouble()
                .pow(smoothing)

            val yCoordinates = (-squircleRadius..squircleRadius).map { x ->
                x.toFloat() to evalSquircleFun(x, poweredRadius, smoothing)
            }

            val yMirroredCoordinates = yCoordinates.map { (x, y) -> Pair(x, -y) }

            var currentX = 0F
            var currentY = 0F

            (yCoordinates + yMirroredCoordinates).forEach { (x, y) ->
                quadTo(currentX, currentY, x, y)
                currentX = x
                currentY = y
            }

            close()

            transform(
                scaleMatrix(
                    sx = 1 / OVERSAMPLING_MULTIPLIER,
                    sy = 1 / OVERSAMPLING_MULTIPLIER
                )
            )

            transform(
                translationMatrix(
                    tx = size.width / 2,
                    ty = size.height / 2
                )
            )
        }.asComposePath()
    }

    private fun evalSquircleFun(x: Int, poweredRadius: Double, smoothing: Double) =
        (poweredRadius - abs(x.toDouble().pow(smoothing))).pow(1 / smoothing).toFloat()

    public companion object {
        private const val SMOOTHING = 3.0
        private const val OVERSAMPLING_MULTIPLIER = 4F
    }
}