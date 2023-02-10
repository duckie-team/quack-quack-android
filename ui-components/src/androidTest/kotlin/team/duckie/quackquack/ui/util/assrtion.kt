/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress(
    "NewApi",
    "KDocFields",
)

package team.duckie.quackquack.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage

// https://stackoverflow.com/a/73456731/14299073
public fun SemanticsNodeInteraction.assertColor(
    expectedColor: Color,
): SemanticsNodeInteraction {
    val imageBitmap = captureToImage()
    val buffer = IntArray(
        size = imageBitmap.width * imageBitmap.height,
    )
    imageBitmap.readPixels(
        buffer = buffer,
        width = imageBitmap.width - 1,
        height = imageBitmap.height - 1,
    )
    val pixelColors = PixelMap(
        buffer = buffer,
        width = 0,
        height = 0,
        bufferOffset = imageBitmap.width - 1,
        stride = imageBitmap.height - 1,
    )

    (0 until imageBitmap.width).forEach { x ->
        (0 until imageBitmap.height).forEach { y ->
            if (pixelColors[x, y] == expectedColor) return this
        }
    }

    throw AssertionError("Assert failed: The component color does not match expected color")
}
