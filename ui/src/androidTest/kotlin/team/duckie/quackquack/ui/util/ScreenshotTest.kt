/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import android.graphics.Bitmap
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream
import team.duckie.quackquack.test.screenshot.matcher.BitmapMatcher
import team.duckie.quackquack.test.screenshot.matcher.MSSIMMatcher
import team.duckie.quackquack.test.screenshot.matcher.MatchResult

private val bitmapMatcher: BitmapMatcher = MSSIMMatcher()

fun screenshotTest(name: String, given: Bitmap, golden: Bitmap): MatchResult {
    val result = bitmapMatcher.compareBitmaps(
        expected = golden.toIntArray(),
        given = given.toIntArray(),
        width = given.width,
        height = given.height,
    )

    val dir = File(
        InstrumentationRegistry.getInstrumentation().targetContext.cacheDir,
        name,
    ).also(File::mkdir)

    val givenFile = File(dir, "given.png").also(File::createNewFile)
    FileOutputStream(givenFile).use { out ->
        given.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    val goldenFile = File(dir, "golden.png").also(File::createNewFile)
    FileOutputStream(goldenFile).use { out ->
        golden.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    val diffFile = File(dir, "diff.png").also(File::createNewFile)
    FileOutputStream(diffFile).use { out ->
        result.diff?.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    return result
}

private fun Bitmap.toIntArray(): IntArray {
    val bitmapArray = IntArray(width * height)
    getPixels(bitmapArray, 0, width, 0, 0, width, height)
    return bitmapArray
}
