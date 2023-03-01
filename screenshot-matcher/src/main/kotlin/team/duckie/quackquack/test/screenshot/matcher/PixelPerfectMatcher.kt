/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.test.screenshot.matcher

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Bitmap matching that does an exact comparison of pixels between bitmaps.
 */
class PixelPerfectMatcher : BitmapMatcher {
    override fun compareBitmaps(
        expected: IntArray,
        given: IntArray,
        width: Int,
        height: Int,
    ): MatchResult {
        check(expected.size == given.size) {
            "expected and given must have the same size."
        }

        var different = 0
        var same = 0

        val diffArray = IntArray(width * height)

        for (x in 0 until width) {
            for (y in 0 until height) {
                val index = x + y * width
                val referenceColor = expected[index]
                val testColor = given[index]
                if (referenceColor == testColor) {
                    ++same
                } else {
                    ++different
                }
                diffArray[index] = diffColor(referenceColor = referenceColor, testColor = testColor)
            }
        }

        if (different > 0) {
            val diff = Bitmap.createBitmap(diffArray, width, height, Bitmap.Config.ARGB_8888)
            val stats = "[PixelPerfect] Same pixels: $same, " +
                    "Different pixels: $different"
            return MatchResult(matches = false, diff = diff, comparisonStatistics = stats)
        }

        val stats = "[PixelPerfect]"
        return MatchResult(matches = true, diff = null, comparisonStatistics = stats)
    }

    private fun diffColor(referenceColor: Int, testColor: Int): Int {
        return if (referenceColor != testColor) {
            Color.MAGENTA
        } else {
            // Quack-changed: Color.TRANSPARENT -> referenceColor
            referenceColor
        }
    }
}
