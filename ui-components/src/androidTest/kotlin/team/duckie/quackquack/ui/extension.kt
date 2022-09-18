/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.onLast
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.unit.width
import team.duckie.quackquack.ui.color.QuackColor

// 필요 할까?

/**
 * TODO: 좀 더 효율적인 Background Test 필요함
 */
fun SemanticsNodeInteraction.assertBackgroundColor(tint: QuackColor): SemanticsNodeInteraction {
    val imageBitmap = captureToImage()
    val buffer = IntArray(imageBitmap.width * imageBitmap.height)
    imageBitmap.readPixels(buffer, 0, 0, imageBitmap.width - 1, imageBitmap.height - 1)
    val pixelColors = PixelMap(buffer, 0, 0, imageBitmap.width - 1, imageBitmap.height - 1)

    (0 until imageBitmap.width).forEach { x ->
        (0 until imageBitmap.height).forEach { y ->
            if (pixelColors[x, y] == tint.value) return this
        }
    }
    throw AssertionError("Assert failed: The component does not contain the color")
}

/**
 * For Text Color Assert
 */
fun SemanticsNodeInteraction.assertTextColor(
    expectedColor: QuackColor,
): SemanticsNodeInteraction = assert(isOfColor(expectedColor.value))

private fun isOfColor(color: Color): SemanticsMatcher = SemanticsMatcher(
    "${SemanticsProperties.Text.name} is of color '$color'"
) {
    val textLayoutResults = mutableListOf<TextLayoutResult>()
    it.config.getOrNull(SemanticsActions.GetTextLayoutResult)
        ?.action
        ?.invoke(textLayoutResults)
    return@SemanticsMatcher if (textLayoutResults.isEmpty()) {
        false
    } else {
        textLayoutResults.first().layoutInput.style.color == color
    }
}

/**
 * For Bound Assert
 */

fun SemanticsNodeInteraction.assertRightPositionInRootIsEqualTo(expectedRight: Dp): SemanticsNodeInteraction {
    val screenWidth = onAncestors().onLast().getUnclippedBoundsInRoot().width
    return withUnclippedBoundsInRoot {
        it.right.assertIsEqualTo(screenWidth - expectedRight, "right")
    }
}

fun SemanticsNodeInteraction.assertBottomPositionInRootIsEqualTo(expectedBottom: Dp): SemanticsNodeInteraction {
    val screenHeight = onAncestors().onLast().getUnclippedBoundsInRoot().height
    return withUnclippedBoundsInRoot {
        it.bottom.assertIsEqualTo(screenHeight - expectedBottom, "bottom")
    }
}

fun SemanticsNodeInteraction.assertVerticallyCenterInRoot(): SemanticsNodeInteraction {
    val screenHeight = onAncestors().onLast().getUnclippedBoundsInRoot().height
    return withUnclippedBoundsInRoot {
        assert(abs(screenHeight - it.bottom - it.top) < 0.5.dp)
    }
}

fun SemanticsNodeInteraction.assertHorizontallyCenterInRoot(): SemanticsNodeInteraction {
    val screenWidth = onAncestors().onLast().getUnclippedBoundsInRoot().width
    return withUnclippedBoundsInRoot {
        assert(abs(screenWidth - it.right - it.left) < 0.5.dp)
    }
}

fun abs(dp: Dp): Dp {
    return if (dp > 0.dp) dp
    else -dp
}

private fun SemanticsNodeInteraction.withUnclippedBoundsInRoot(
    assertion: (DpRect) -> Unit,
): SemanticsNodeInteraction {
    val node = fetchSemanticsNode("Failed to retrieve bounds of the node.")
    val bounds = with(node.layoutInfo.density) {
        node.unclippedBoundsInRoot.let {
            DpRect(it.left.toDp(), it.top.toDp(), it.right.toDp(), it.bottom.toDp())
        }
    }
    assertion.invoke(bounds)
    return this
}

val SemanticsNode.unclippedBoundsInRoot: Rect
    get() {
        return if (layoutInfo.isPlaced) {
            Rect(positionInRoot, size.toSize())
        } else {
            Dp.Unspecified.value.let { Rect(it, it, it, it) }
        }
    }
