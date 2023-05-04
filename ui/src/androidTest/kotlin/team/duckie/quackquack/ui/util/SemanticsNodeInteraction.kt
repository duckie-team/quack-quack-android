/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should

@Suppress("NewApi")
fun SemanticsNodeInteraction.captureToBitmap(): Bitmap {
    return captureToImage().asAndroidBitmap()
}

fun screenshotEqualMatcher(
    name: String,
    golden: SemanticsNodeInteraction,
): Matcher<SemanticsNodeInteraction> = Matcher { given ->
    val givenBitmap = given.captureToBitmap()
    val goldenBitmap = golden.captureToBitmap()
    val result = screenshotMatcher(
        name = name,
        given = givenBitmap,
        golden = goldenBitmap,
    )

    MatcherResult(
        passed = result.matches,
        failureMessageFn = { result.comparisonStatistics },
        negatedFailureMessageFn = { error("The shouldNot operation is not allowed.") },
    )
}

fun SemanticsNodeInteraction.shouldScreenshotEqual(
    name: String,
    golden: SemanticsNodeInteraction,
) {
    this should screenshotEqualMatcher(name = name, golden = golden)
}
