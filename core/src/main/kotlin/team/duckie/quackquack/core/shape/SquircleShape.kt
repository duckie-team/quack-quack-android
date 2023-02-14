@file:Suppress("SameParameterValue")

package team.duckie.quackquack.core.shape

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

/**
 * Squircle 모양을 구현합니다.
 */
// TAKEN FROM: https://stackoverflow.com/q/73299444/14299073
public object SquircleShape : Shape {
    private const val SMOOTHING = 3.0
    private const val OVERSAMPLING_MULTIPLIER = 4F

    public override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline.Generic = Outline.Generic(
        path = createSquirclePath(
            size = size,
            smoothing = SMOOTHING,
        ),
    )

    private fun createSquirclePath(
        size: Size,
        smoothing: Double,
    ): androidx.compose.ui.graphics.Path {
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
                    sy = 1 / OVERSAMPLING_MULTIPLIER,
                ),
            )

            transform(
                translationMatrix(
                    tx = size.width / 2,
                    ty = size.height / 2,
                ),
            )
        }.asComposePath()
    }

    private fun evalSquircleFun(x: Int, poweredRadius: Double, smoothing: Double) =
        (poweredRadius - abs(x.toDouble().pow(smoothing))).pow(1 / smoothing).toFloat()
}
