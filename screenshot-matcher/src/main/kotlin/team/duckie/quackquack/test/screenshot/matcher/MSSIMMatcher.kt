/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused", "LocalVariableName", "SameParameterValue", "FunctionName")

package team.duckie.quackquack.test.screenshot.matcher

import android.graphics.Color
import androidx.annotation.FloatRange
import kotlin.math.pow

/**
 * Image comparison using Structural Similarity Index, developed by Wang, Bovik, Sheikh, and
 * Simoncelli. Details can be read in their paper:
 * https://ece.uwaterloo.ca/~z70wang/publications/ssim.pdf
 */
class MSSIMMatcher(
    // Quack-changed: Increased default threshold from 0.98 to 1.0
    @FloatRange(from = 0.0, to = 1.0) private val threshold: Double = 1.0,
) : BitmapMatcher {
    companion object {
        // These values were taken from the publication
        private const val CONSTANT_L = 254.0
        private const val CONSTANT_K1 = 0.00001
        private const val CONSTANT_K2 = 0.00003
        private val CONSTANT_C1 = (CONSTANT_L * CONSTANT_K1).pow(2.0)
        private val CONSTANT_C2 = (CONSTANT_L * CONSTANT_K2).pow(2.0)
        private const val WINDOW_SIZE = 10
    }

    override fun compareBitmaps(
        expected: IntArray,
        given: IntArray,
        width: Int,
        height: Int,
    ): MatchResult {
        val SSIMTotal = calculateSSIM(
            ideal = expected,
            given = given,
            width = width,
            height = height,
        )

        val stats = "[MSSIM] Required SSIM: $threshold, Actual " +
                "SSIM: " + "%.3f".format(SSIMTotal)

        if (SSIMTotal >= threshold) {
            return MatchResult(
                matches = true,
                diff = null,
                comparisonStatistics = stats,
            )
        }

        // Create diff
        val result = PixelPerfectMatcher().compareBitmaps(
            expected = expected,
            given = given,
            width = width,
            height = height,
        )
        return MatchResult(
            matches = false,
            diff = result.diff,
            comparisonStatistics = stats,
        )
    }

    private fun calculateSSIM(
        ideal: IntArray,
        given: IntArray,
        width: Int,
        height: Int,
    ): Double {
        return calculateSSIM(
            ideal = ideal,
            given = given,
            offset = 0,
            stride = width,
            width = width,
            height = height,
        )
    }

    private fun calculateSSIM(
        ideal: IntArray,
        given: IntArray,
        offset: Int,
        stride: Int,
        width: Int,
        height: Int,
    ): Double {
        var SSIMTotal = 0.0
        var windows = 0
        var currentWindowY = 0
        while (currentWindowY < height) {
            val windowHeight = computeWindowSize(
                coordinateStart = currentWindowY,
                dimension = height,
            )
            var currentWindowX = 0
            while (currentWindowX < width) {
                val windowWidth = computeWindowSize(
                    coordinateStart = currentWindowX,
                    dimension = width,
                )
                val start = indexFromXAndY(
                    x = currentWindowX,
                    y = currentWindowY,
                    stride = stride,
                    offset = offset,
                )
                val isIdealWindowWhite = isWindowWhite(
                    colors = ideal,
                    start = start,
                    stride = stride,
                    windowWidth = windowWidth,
                    windowHeight = windowHeight,
                )
                val isGivenWindowWhite = isWindowWhite(
                    colors = given,
                    start = start,
                    stride = stride,
                    windowWidth = windowWidth,
                    windowHeight = windowHeight,
                )
                if (isIdealWindowWhite && isGivenWindowWhite) {
                    currentWindowX += WINDOW_SIZE
                    continue
                }
                windows++
                val means = getMeans(
                    pixels0 = ideal,
                    pixels1 = given,
                    start = start,
                    stride = stride,
                    windowWidth = windowWidth,
                    windowHeight = windowHeight,
                )
                val meanX = means[0]
                val meanY = means[1]
                val variances = getVariances(
                    pixels0 = ideal,
                    pixels1 = given,
                    mean0 = meanX,
                    mean1 = meanY,
                    start = start,
                    stride = stride,
                    windowWidth = windowWidth,
                    windowHeight = windowHeight,
                )
                val varX = variances[0]
                val varY = variances[1]
                val stdBoth = variances[2]
                val SSIM = SSIM(
                    muX = meanX,
                    muY = meanY,
                    sigX = varX,
                    sigY = varY,
                    sigXY = stdBoth,
                )
                SSIMTotal += SSIM
                currentWindowX += WINDOW_SIZE
            }
            currentWindowY += WINDOW_SIZE
        }
        if (windows == 0) {
            return 1.0
        }
        return SSIMTotal / windows.toDouble()
    }

    /**
     * Compute the size of the window. The window defaults to WINDOW_SIZE, but
     * must be contained within dimension.
     */
    private fun computeWindowSize(coordinateStart: Int, dimension: Int): Int {
        return if (coordinateStart + WINDOW_SIZE <= dimension) {
            WINDOW_SIZE
        } else {
            dimension - coordinateStart
        }
    }

    private fun isWindowWhite(
        colors: IntArray,
        start: Int,
        stride: Int,
        windowWidth: Int,
        windowHeight: Int,
    ): Boolean {
        for (y in 0 until windowHeight) {
            for (x in 0 until windowWidth) {
                if (colors[indexFromXAndY(x, y, stride, start)] != Color.WHITE) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * This calculates the position in an array that would represent a bitmap given the parameters.
     */
    private fun indexFromXAndY(x: Int, y: Int, stride: Int, offset: Int): Int {
        return x + y * stride + offset
    }

    private fun SSIM(muX: Double, muY: Double, sigX: Double, sigY: Double, sigXY: Double): Double {
        var SSIM = (2 * muX * muY + CONSTANT_C1) * (2 * sigXY + CONSTANT_C2)
        val denom = ((muX * muX + muY * muY + CONSTANT_C1) * (sigX + sigY + CONSTANT_C2))
        SSIM /= denom
        return SSIM
    }

    /**
     * This method will find the mean of a window in both sets of pixels. The return is an array
     * where the first double is the mean of the first set and the second double is the mean of the
     * second set.
     */
    private fun getMeans(
        pixels0: IntArray,
        pixels1: IntArray,
        start: Int,
        stride: Int,
        windowWidth: Int,
        windowHeight: Int,
    ): DoubleArray {
        var avg0 = 0.0
        var avg1 = 0.0
        for (y in 0 until windowHeight) {
            for (x in 0 until windowWidth) {
                val index: Int = indexFromXAndY(x, y, stride, start)
                avg0 += getIntensity(pixels0[index])
                avg1 += getIntensity(pixels1[index])
            }
        }
        avg0 /= windowWidth * windowHeight.toDouble()
        avg1 /= windowWidth * windowHeight.toDouble()
        return doubleArrayOf(avg0, avg1)
    }

    /**
     * Finds the variance of the two sets of pixels, as well as the covariance of the windows. The
     * return value is an array of doubles, the first is the variance of the first set of pixels,
     * the second is the variance of the second set of pixels, and the third is the covariance.
     */
    private fun getVariances(
        pixels0: IntArray,
        pixels1: IntArray,
        mean0: Double,
        mean1: Double,
        start: Int,
        stride: Int,
        windowWidth: Int,
        windowHeight: Int,
    ): DoubleArray {
        var var0 = 0.0
        var var1 = 0.0
        var varBoth = 0.0
        for (y in 0 until windowHeight) {
            for (x in 0 until windowWidth) {
                val index: Int = indexFromXAndY(x, y, stride, start)
                val v0 = getIntensity(pixels0[index]) - mean0
                val v1 = getIntensity(pixels1[index]) - mean1
                var0 += v0 * v0
                var1 += v1 * v1
                varBoth += v0 * v1
            }
        }
        var0 /= windowWidth * windowHeight - 1.toDouble()
        var1 /= windowWidth * windowHeight - 1.toDouble()
        varBoth /= windowWidth * windowHeight - 1.toDouble()
        return doubleArrayOf(var0, var1, varBoth)
    }

    /**
     * Gets the intensity of a given pixel in RGB using luminosity formula
     *
     * l = 0.21R' + 0.72G' + 0.07B'
     *
     * The prime symbols dictate a gamma correction of 1.
     */
    private fun getIntensity(pixel: Int): Double {
        val gamma = 1.0
        var l = 0.0
        l += 0.21f * (Color.red(pixel) / 255f.toDouble()).pow(gamma)
        l += 0.72f * (Color.green(pixel) / 255f.toDouble()).pow(gamma)
        l += 0.07f * (Color.blue(pixel) / 255f.toDouble()).pow(gamma)
        return l
    }
}
