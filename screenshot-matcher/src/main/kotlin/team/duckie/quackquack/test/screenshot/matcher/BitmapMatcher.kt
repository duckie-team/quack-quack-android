/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.test.screenshot.matcher

import android.graphics.Bitmap

/**
 * Interface to implement to provide custom bitmap matchers.
 */
interface BitmapMatcher {
    /**
     * Compares the given bitmaps and returns result of the operation.
     *
     * The images need to have same size.
     *
     * @param expected The reference / golden image.
     * @param given The image taken during the test.
     * @param width Width of both of the images.
     * @param height Height of both of the images.
     */
    fun compareBitmaps(
        expected: IntArray,
        given: IntArray,
        width: Int,
        height: Int,
    ): MatchResult
}

/**
 * Result of the matching performed by [BitmapMatcher].
 *
 * @param matches True if bitmaps match.
 * @param comparisonStatistics Matching statistics provided by this matcher that performed the
 * comparison.
 * @param diff Diff bitmap that highlights the differences between the images. Can be null if match
 * was found.
 */
data class MatchResult(
    val matches: Boolean,
    val comparisonStatistics: String,
    val diff: Bitmap?,
)
