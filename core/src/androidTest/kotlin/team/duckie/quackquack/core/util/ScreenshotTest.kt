/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.util

import android.graphics.Bitmap
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileOutputStream
import strikt.api.expectThat
import strikt.assertions.isTrue
import team.duckie.quackquack.test.screenshot.matcher.BitmapMatcher
import team.duckie.quackquack.test.screenshot.matcher.MSSIMMatcher

fun screenshotTest(name: String, test: Bitmap, golden: Bitmap) {
    val matcher: BitmapMatcher = MSSIMMatcher()
    val result = matcher.compareBitmaps(
        expected = golden.toIntArray(),
        given = test.toIntArray(),
        width = test.width,
        height = test.height,
    )

    val dir = File(
        InstrumentationRegistry.getInstrumentation().targetContext.cacheDir,
        name,
    ).also(File::mkdir)

    val testFile = File(dir, "test.png").also(File::createNewFile)
    FileOutputStream(testFile).use { out ->
        test.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    val goldenFile = File(dir, "golden.png").also(File::createNewFile)
    FileOutputStream(goldenFile).use { out ->
        golden.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    val diffFile = File(dir, "diff.png").also(File::createNewFile)
    FileOutputStream(diffFile).use { out ->
        result.diff?.compress(Bitmap.CompressFormat.PNG, 100, out)
    }

    expectThat(result.matches).isTrue()
}

private fun Bitmap.toIntArray(): IntArray {
    val bitmapArray = IntArray(width * height)
    getPixels(bitmapArray, 0, width, 0, 0, width, height)
    return bitmapArray
}
