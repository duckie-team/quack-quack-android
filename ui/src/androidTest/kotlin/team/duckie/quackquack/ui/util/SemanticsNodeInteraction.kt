/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import strikt.api.Assertion

@Suppress("NewApi")
fun SemanticsNodeInteraction.captureToBitmap(): Bitmap {
    return captureToImage().asAndroidBitmap()
}

fun Assertion.Builder<SemanticsNodeInteraction>.isScreenshotSame(
    name: String,
    golden: SemanticsNodeInteraction,
): Assertion.Builder<SemanticsNodeInteraction> {
    val givenBitmap = subject.captureToBitmap()
    val goldenBitmap = golden.captureToBitmap()
    val result = screenshotTest(
        name = name,
        given = givenBitmap,
        golden = goldenBitmap,
    )

    return assert("the bitmap of given is the same as the bitmap of golden") {
        if (result.matches) {
            pass(description = result.comparisonStatistics)
        } else {
            fail(description = result.comparisonStatistics)
        }
    }
}
