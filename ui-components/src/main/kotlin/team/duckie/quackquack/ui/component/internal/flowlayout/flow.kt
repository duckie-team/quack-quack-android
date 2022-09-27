/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.component.internal.flowlayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.max

/**
 * A composable that places its children in a horizontal flow. Unlike [Row], if the
 * horizontal space is too small to put all the children in one row, multiple rows may be used.
 *
 * Note that just like [Row], flex values cannot be used with [FlowRow].
 *
 * @param modifier The modifier to be applied to the FlowRow.
 * @param mainAxisSize The size of the layout in the main axis direction.
 * @param mainAxisAlignment The alignment of each row's children in the main axis direction.
 * @param mainAxisSpacing The main axis spacing between the children of each row.
 * @param crossAxisAlignment The alignment of each row's children in the cross axis direction.
 * @param crossAxisSpacing The cross axis spacing between the rows of the layout.
 * @param lastLineMainAxisAlignment Overrides the main axis alignment of the last row.
 * @param content The composable content for display.
 */
@Composable
@NonRestartableComposable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSize: SizeMode = SizeMode.Wrap,
    mainAxisAlignment: FlowMainAxisAlignment = MainAxisAlignment.Start,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisAlignment: FlowCrossAxisAlignment = FlowCrossAxisAlignment.Start,
    crossAxisSpacing: Dp = 0.dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment = mainAxisAlignment,
    content: @Composable () -> Unit,
) = Flow(
    modifier = modifier,
    orientation = LayoutOrientation.Horizontal,
    mainAxisSize = mainAxisSize,
    mainAxisAlignment = mainAxisAlignment,
    mainAxisSpacing = mainAxisSpacing,
    crossAxisAlignment = crossAxisAlignment,
    crossAxisSpacing = crossAxisSpacing,
    lastLineMainAxisAlignment = lastLineMainAxisAlignment,
    content = content,
)

/**
 * A composable that places its children in a vertical flow. Unlike [Column], if the
 * vertical space is too small to put all the children in one column, multiple columns may be used.
 *
 * Note that just like [Column], flex values cannot be used with [FlowColumn].
 *
 * @param modifier The modifier to be applied to the FlowColumn.
 * @param mainAxisSize The size of the layout in the main axis direction.
 * @param mainAxisAlignment The alignment of each column's children in the main axis direction.
 * @param mainAxisSpacing The main axis spacing between the children of each column.
 * @param crossAxisAlignment The alignment of each column's children in the cross axis direction.
 * @param crossAxisSpacing The cross axis spacing between the columns of the layout.
 * @param lastLineMainAxisAlignment Overrides the main axis alignment of the last column.
 * @param content The composable content for display.
 */
@Composable
@NonRestartableComposable
fun FlowColumn(
    modifier: Modifier = Modifier,
    mainAxisSize: SizeMode = SizeMode.Wrap,
    mainAxisAlignment: FlowMainAxisAlignment = MainAxisAlignment.Start,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisAlignment: FlowCrossAxisAlignment = FlowCrossAxisAlignment.Start,
    crossAxisSpacing: Dp = 0.dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment = mainAxisAlignment,
    content: @Composable () -> Unit,
) = Flow(
    modifier = modifier,
    orientation = LayoutOrientation.Vertical,
    mainAxisSize = mainAxisSize,
    mainAxisAlignment = mainAxisAlignment,
    mainAxisSpacing = mainAxisSpacing,
    crossAxisAlignment = crossAxisAlignment,
    crossAxisSpacing = crossAxisSpacing,
    lastLineMainAxisAlignment = lastLineMainAxisAlignment,
    content = content,
)

/**
 * Used to specify the alignment of a layout's children, in cross axis direction.
 */
enum class FlowCrossAxisAlignment {
    /**
     * Place children such that their center is in the middle of the cross axis.
     */
    Center,

    /**
     * Place children such that their start edge is aligned to the start edge of the cross axis.
     */
    Start,

    /**
     * Place children such that their end edge is aligned to the end edge of the cross axis.
     */
    End,
}

private typealias FlowMainAxisAlignment = MainAxisAlignment

/**
 * Layout model that arranges its children in a horizontal or vertical flow.
 *
 * @param modifier The modifier to be applied to the FlowColumn.
 * @param orientation The orientation of the layout.
 * @param mainAxisSize The size of the layout in the main axis direction.
 * @param mainAxisAlignment The alignment of each column's children in the main axis direction.
 * @param mainAxisSpacing The main axis spacing between the children of each column.
 * @param crossAxisAlignment The alignment of each column's children in the cross axis direction.
 * @param crossAxisSpacing The cross axis spacing between the columns of the layout.
 * @param lastLineMainAxisAlignment Overrides the main axis alignment of the last column.
 * @param content The composable content for display.
 */
@Composable
private fun Flow(
    modifier: Modifier,
    orientation: LayoutOrientation,
    mainAxisSize: SizeMode,
    mainAxisAlignment: FlowMainAxisAlignment,
    mainAxisSpacing: Dp,
    crossAxisAlignment: FlowCrossAxisAlignment,
    crossAxisSpacing: Dp,
    lastLineMainAxisAlignment: FlowMainAxisAlignment,
    content: @Composable () -> Unit,
) {
    fun Placeable.mainAxisSize() = when (orientation == LayoutOrientation.Horizontal) {
        true -> width
        else -> height
    }

    fun Placeable.crossAxisSize() = when (orientation == LayoutOrientation.Horizontal) {
        true -> height
        else -> width
    }

    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, outerConstraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        val constraints = OrientationIndependentConstraints(
            constraints = outerConstraints,
            orientation = orientation,
        )

        val childConstraints = if (orientation == LayoutOrientation.Horizontal) {
            Constraints(
                maxWidth = constraints.mainAxisMax,
            )
        } else {
            Constraints(
                maxHeight = constraints.mainAxisMax,
            )
        }

        // Return whether the placeable can be added to the current sequence.
        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() || currentMainAxisSize + mainAxisSpacing.roundToPx() +
                    placeable.mainAxisSize() <= constraints.mainAxisMax

        // Store current sequence information and start a new sequence.
        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += crossAxisSpacing.roundToPx()
            }
            sequences += currentSequence.toList()
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(
                a = mainAxisSpace,
                b = currentMainAxisSize
            )

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        for (measurable in measurables) {
            // Ask the child for its preferred size.
            val placeable = measurable.measure(
                constraints = childConstraints,
            )

            // Start a new sequence if there is not enough space.
            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            // Add the child to the current sequence.
            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += mainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.mainAxisSize()
            currentCrossAxisSize = max(
                a = currentCrossAxisSize,
                b = placeable.crossAxisSize(),
            )
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = if (constraints.mainAxisMax != Constraints.Infinity &&
            mainAxisSize == SizeMode.Expand
        ) {
            constraints.mainAxisMax
        } else {
            max(
                a = mainAxisSpace,
                b = constraints.mainAxisMin,
            )
        }
        val crossAxisLayoutSize = max(
            a = crossAxisSpace,
            b = constraints.crossAxisMin,
        )

        val layoutWidth = if (orientation == LayoutOrientation.Horizontal) {
            mainAxisLayoutSize
        } else {
            crossAxisLayoutSize
        }
        val layoutHeight = if (orientation == LayoutOrientation.Horizontal) {
            crossAxisLayoutSize
        } else {
            mainAxisLayoutSize
        }

        layout(
            width = layoutWidth,
            height = layoutHeight,
        ) {
            sequences.forEachIndexed { sequenceIndex, placeables ->
                val childrenMainAxisSizes = IntArray(
                    size = placeables.size,
                    init = { int ->
                        placeables[int].mainAxisSize() + when (int < placeables.lastIndex) {
                            true -> mainAxisSpacing.roundToPx()
                            else -> 0
                        }
                    }
                )
                val arrangement = if (sequenceIndex < sequences.lastIndex) {
                    mainAxisAlignment.arrangement
                } else {
                    lastLineMainAxisAlignment.arrangement
                }
                // Handle vertical direction
                val mainAxisPositions = IntArray(
                    size = childrenMainAxisSizes.size,
                    init = { 0 },
                )
                with(arrangement) {
                    arrange(
                        totalSize = mainAxisLayoutSize,
                        sizes = childrenMainAxisSizes,
                        outPositions = mainAxisPositions,
                    )
                }
                placeables.forEachIndexed { placeableIndex, placeable ->
                    val crossAxis = when (crossAxisAlignment) {
                        FlowCrossAxisAlignment.Start -> 0
                        FlowCrossAxisAlignment.End -> crossAxisSizes[placeableIndex] - placeable.crossAxisSize()
                        FlowCrossAxisAlignment.Center ->
                            Alignment.Center.align(
                                IntSize.Zero,
                                IntSize(
                                    width = 0,
                                    height = crossAxisSizes[placeableIndex] - placeable.crossAxisSize()
                                ),
                                LayoutDirection.Ltr
                            ).y
                    }
                    if (orientation == LayoutOrientation.Horizontal) {
                        placeable.place(
                            x = mainAxisPositions[placeableIndex],
                            y = crossAxisPositions[placeableIndex] + crossAxis
                        )
                    } else {
                        placeable.place(
                            x = crossAxisPositions[placeableIndex] + crossAxis,
                            y = mainAxisPositions[placeableIndex]
                        )
                    }
                }
            }
        }
    }
}

/**
 * Used to specify how a layout chooses its own size when multiple behaviors are possible.
 */
enum class SizeMode {
    /**
     * Minimize the amount of free space by wrapping the children,
     * subject to the incoming layout constraints.
     */
    Wrap,

    /**
     * Maximize the amount of free space by expanding to fill the available space,
     * subject to the incoming layout constraints.
     */
    Expand,
}

/**
 * Used to specify the alignment of a layout's children, in main axis direction.
 */
enum class MainAxisAlignment(
    internal val arrangement: Arrangement.Vertical,
) {
    // workaround for now - use Arrangement that equals to previous Arrangement
    /**
     * Place children such that they are as close as possible to the middle of the main axis.
     */
    Center(
        arrangement = Arrangement.Center,
    ),

    /**
     * Place children such that they are as close as possible to the start of the main axis.
     */
    Start(
        arrangement = Arrangement.Top,
    ),

    /**
     * Place children such that they are as close as possible to the end of the main axis.
     */
    End(
        arrangement = Arrangement.Bottom,
    ),

    /**
     * Place children such that they are spaced evenly across the main axis, including free
     * space before the first child and after the last child.
     */
    SpaceEvenly(
        arrangement = Arrangement.SpaceEvenly,
    ),

    /**
     * Place children such that they are spaced evenly across the main axis, without free
     * space before the first child or after the last child.
     */
    SpaceBetween(
        arrangement = Arrangement.SpaceBetween,
    ),

    /**
     * Place children such that they are spaced evenly across the main axis, including free
     * space before the first child and after the last child, but half the amount of space
     * existing otherwise between two consecutive children.
     */
    SpaceAround(
        arrangement = Arrangement.SpaceAround,
    );
}
