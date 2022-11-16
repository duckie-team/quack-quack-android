/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastFirstOrNull
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.util.Zero
import team.duckie.quackquack.ui.util.heightOrZero
import team.duckie.quackquack.ui.util.layoutId
import team.duckie.quackquack.ui.util.npe
import team.duckie.quackquack.ui.util.widthOrZero

internal const val QuackTextFieldLayoutId = "QuackTextFieldContent"
internal const val QuackTextFieldPlaceholderLayoutId = "QuackTextFieldPlaceholderContent"
internal const val QuackTextFieldLeadingContentLayoutId = "QuackTextFieldLeadingContent"
internal const val QuackTextFieldTrailingContentLayoutId = "QuackTextFieldTrailingContent"

/**
 * [QuackBasicTextField] 를 measure 하기 위한 [MeasurePolicy]
 *
 * 이 클래스는 직접 사용하면 안됩니다. 리컴포지션 퍼포먼스를 위해
 * remember 로 감싼 인스턴스를 반환하는 [rememberQuackTextFieldMeasurePolicy] 를
 * 사용하세요.
 */
private class QuackTextFieldMeasurePolicy : MeasurePolicy {
    override fun MeasureScope.measure(
        measurables: List<Measurable>,
        constraints: Constraints,
    ): MeasureResult {
        var occupiedSpaceHorizontally = 0
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )

        // measure leading icon
        val leadingPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.measure(
            constraints = looseConstraints,
        )
        occupiedSpaceHorizontally += leadingPlaceable.widthOrZero()

        // measure trailing icon
        val trailingPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.measure(
            constraints = looseConstraints.offset(
                horizontal = -occupiedSpaceHorizontally,
            ),
        )
        occupiedSpaceHorizontally += trailingPlaceable.widthOrZero()

        // measure text field
        val textFieldConstraints = constraints
            .copy(
                minHeight = 0,
            )
            .offset(
                horizontal = -occupiedSpaceHorizontally,
            )
        val textFieldPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == QuackTextFieldLayoutId
        }?.measure(
            constraints = textFieldConstraints,
        ) ?: npe()

        val placeholderPlaceable = measurables.fastFirstOrNull { measurable ->
            measurable.layoutId == QuackTextFieldPlaceholderLayoutId
        }?.measure(
            constraints = textFieldConstraints,
        )

        val width = calculateWidth(
            textFieldWidth = textFieldPlaceable.width,
            placeholderWidth = placeholderPlaceable.widthOrZero(),
            leadingWidth = leadingPlaceable.widthOrZero(),
            trailingWidth = trailingPlaceable.widthOrZero(),
            constraints = constraints,
        )
        val height = calculateHeight(
            textFieldHeight = textFieldPlaceable.height,
            placeholderHeight = placeholderPlaceable.heightOrZero(),
            leadingHeight = leadingPlaceable.heightOrZero(),
            trailingHeight = trailingPlaceable.heightOrZero(),
            constraints = constraints,
        )

        return layout(
            width = width,
            height = height,
        ) {
            placeTextField(
                width = width,
                height = height,
                textFieldPlaceable = textFieldPlaceable,
                placeholderPlaceable = placeholderPlaceable,
                leadingPlaceable = leadingPlaceable,
                trailingPlaceable = trailingPlaceable,
            )
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int = intrinsicHeight(
        measurables = measurables,
        width = width,
    ) { intrinsicMeasurable, intrinsicWidth ->
        intrinsicMeasurable.maxIntrinsicHeight(
            width = intrinsicWidth,
        )
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ): Int = intrinsicHeight(
        measurables = measurables,
        width = width,
    ) { intrinsicMeasurable, intrinsicWidth ->
        intrinsicMeasurable.minIntrinsicHeight(
            width = intrinsicWidth,
        )
    }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int = intrinsicWidth(
        measurables = measurables,
        height = height,
    ) { intrinsicMeasurable, intrinsicHeight ->
        intrinsicMeasurable.maxIntrinsicWidth(
            height = intrinsicHeight,
        )
    }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
    ): Int = intrinsicWidth(
        measurables = measurables,
        height = height,
    ) { intrinsicMeasurable, intrinsicHeight ->
        intrinsicMeasurable.minIntrinsicWidth(
            height = intrinsicHeight,
        )
    }

    /**
     * QuackTextField 를 배치할 수 있는 intrinsic 한 width 를 계산합니다.
     *
     * @param measurables QuackTextField 의 measurable 들
     * @param height QuackTextField 레이아웃의 높이
     * @param intrinsicMeasurer QuackTextField 의 intrinsic width 를 계산하는 함수
     *
     * @return QuackTextField 를 배치하기 위한 intrinsic width
     */
    private fun intrinsicWidth(
        measurables: List<IntrinsicMeasurable>,
        height: Int,
        intrinsicMeasurer: (
            intrinsicMeasurable: IntrinsicMeasurable,
            intrinsicHeight: Int,
        ) -> Int,
    ): Int {
        val textFieldWidth = intrinsicMeasurer(
            /* intrinsicMeasurable = */
            measurables.first { measurable ->
                measurable.layoutId == QuackTextFieldLayoutId
            },
            /* intrinsicHeight = */
            height,
        )

        val placeholderWidth = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldPlaceholderLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicHeight = */
                height,
            )
        } ?: 0

        val leadingWidth = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicHeight = */
                height,
            )
        } ?: 0

        val trailingWidth = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicHeight = */
                height,
            )
        } ?: 0

        return calculateWidth(
            textFieldWidth = textFieldWidth,
            placeholderWidth = placeholderWidth,
            leadingWidth = leadingWidth,
            trailingWidth = trailingWidth,
            constraints = Constraints.Zero,
        )
    }

    /**
     * QuackTextField 를 배치할 수 있는 intrinsic 한 height 를 계산합니다.
     *
     * @param measurables QuackTextField 의 measurable 들
     * @param width QuackTextField 레이아웃의 넓이
     * @param intrinsicMeasurer QuackTextField 의 intrinsic height 를 계산하는 함수
     *
     * @return QuackTextField 를 배치하기 위한 intrinsic height
     */
    private fun intrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
        intrinsicMeasurer: (
            intrinsicMeasurable: IntrinsicMeasurable,
            intrinsicWidth: Int,
        ) -> Int,
    ): Int {
        val textFieldHeight = intrinsicMeasurer(
            /* intrinsicMeasurable = */
            measurables.first { measurable ->
                measurable.layoutId == QuackTextFieldLayoutId
            },
            /* intrinsicWidth = */
            width,
        )

        val placeholderHeight = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldPlaceholderLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicWidth = */
                width,
            )
        } ?: 0

        val leadingHeight = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicWidth = */
                width,
            )
        } ?: 0

        val trailingHeight = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                /* intrinsicMeasurable = */
                measurable,
                /* intrinsicWidth = */
                width,
            )
        } ?: 0

        return calculateHeight(
            textFieldHeight = textFieldHeight,
            placeholderHeight = placeholderHeight,
            leadingHeight = leadingHeight,
            trailingHeight = trailingHeight,
            constraints = Constraints.Zero,
        )
    }
}

/**
 * [QuackTextFieldMeasurePolicy] 을 [remember] 로 저장한 값을 반환합니다.
 * 항상 같은 인스턴스를 사용해도 되므로 [remember] 의 key 가 지정되지 않습니다.
 *
 * @return remember 된 [QuackTextFieldMeasurePolicy] 의 인스턴스
 */
@Composable
private fun rememberQuackTextFieldMeasurePolicy(): QuackTextFieldMeasurePolicy {
    return remember {
        QuackTextFieldMeasurePolicy()
    }
}

/**
 * TextField 배치에 사용되는 constraints 중에서 제일 큰 넓이를 계산합니다.
 *
 * @param textFieldWidth TextField 의 넓이
 * @param placeholderWidth placeholder 의 넓이
 * @param leadingWidth leading decoration item 의 넓이
 * @param trailingWidth trailing decoration item 의 넓이
 * @param constraints 배치하려는 TextField 레이아웃의 [Constraints]
 *
 * @return 인자로 주어진 넓이 중에서 제일 큰 넓이
 */
private fun calculateWidth(
    textFieldWidth: Int,
    placeholderWidth: Int,
    leadingWidth: Int,
    trailingWidth: Int,
    constraints: Constraints,
) = maxOf(
    a = textFieldWidth,
    b = maxOf(
        a = placeholderWidth,
        b = leadingWidth,
        c = trailingWidth,
    ),
    c = constraints.minWidth,
)

/**
 * TextField 배치에 사용되는 constraints 중에서 제일 큰 높이를 계산합니다.
 *
 * @param textFieldHeight TextField 의 높이
 * @param placeholderHeight placeholder 의 높이
 * @param leadingHeight leading decoration item 의 높이
 * @param trailingHeight trailing decoration item 의 높이
 * @param constraints 배치하려는 TextField 레이아웃의 [Constraints]
 *
 * @return 인자로 주어진 높이 중에서 제일 큰 높이
 */
private fun calculateHeight(
    textFieldHeight: Int,
    placeholderHeight: Int,
    leadingHeight: Int,
    trailingHeight: Int,
    constraints: Constraints,
) = maxOf(
    a = textFieldHeight,
    b = maxOf(
        a = placeholderHeight,
        b = leadingHeight,
        c = trailingHeight,
    ),
    c = constraints.minHeight,
)

/**
 * TextField 의 [Placeable] 들을 배치하기 위한 오프셋을 계산하여 배치합니다.
 *
 * @param width TextField 레이아웃의 넓이
 * @param height TextField 레이아웃의 높이
 * @param textFieldPlaceable TextField 의 [Placeable]
 * @param placeholderPlaceable placeholder 의 [Placeable]
 * @param leadingPlaceable leading decoration item 의 [Placeable]
 * @param trailingPlaceable trailing decoration item 의 [Placeable]
 */
private fun Placeable.PlacementScope.placeTextField(
    width: Int,
    height: Int,
    textFieldPlaceable: Placeable,
    placeholderPlaceable: Placeable?,
    leadingPlaceable: Placeable?,
    trailingPlaceable: Placeable?,
) {
    leadingPlaceable?.placeRelative(
        x = 0,
        y = Alignment.CenterVertically.align(
            size = leadingPlaceable.height,
            space = height,
        ),
    )
    trailingPlaceable?.placeRelative(
        x = width - trailingPlaceable.width,
        y = Alignment.CenterVertically.align(
            size = trailingPlaceable.height,
            space = height,
        ),
    )
    textFieldPlaceable.placeRelative(
        x = leadingPlaceable.widthOrZero(),
        y = Alignment.CenterVertically.align(
            size = textFieldPlaceable.height,
            space = height,
        ),
    )
    placeholderPlaceable?.placeRelative(
        x = leadingPlaceable.widthOrZero(),
        y = Alignment.CenterVertically.align(
            size = placeholderPlaceable.height,
            space = height,
        ),
    )
}

/**
 * A decoration box used to draw decoration items for [QuackBasicTextField].
 *
 * @param textField BasicTextField to be treated as QuackTextField
 * @param placeholderContent A placeholder content to display when the entered text is empty
 * @param leadingContent The leading content of QuackTextField
 * @param trailingContent The trailing content of QuackTextField
 */
@Composable
internal fun QuackTextFieldDecorationBox(
    textField: @Composable () -> Unit,
    placeholderContent: (@Composable () -> Unit)?,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Layout(
        content = {
            if (leadingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldLeadingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    leadingContent()
                }
            }
            if (trailingContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldTrailingContentLayoutId,
                    ),
                    contentAlignment = Alignment.Center,
                ) {
                    trailingContent()
                }
            }
            Box(
                modifier = Modifier.layoutId(
                    layoutId = QuackTextFieldLayoutId,
                ),
                propagateMinConstraints = true,
            ) {
                textField()
            }
            if (placeholderContent != null) {
                Box(
                    modifier = Modifier.layoutId(
                        layoutId = QuackTextFieldPlaceholderLayoutId,
                    ),
                    propagateMinConstraints = true,
                ) {
                    placeholderContent()
                }
            }
        },
        measurePolicy = rememberQuackTextFieldMeasurePolicy(),
    )
}
