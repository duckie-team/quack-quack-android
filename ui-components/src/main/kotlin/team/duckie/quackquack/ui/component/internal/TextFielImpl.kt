/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.offset
import kotlin.math.max
import team.duckie.quackquack.ui.component.QuackBasicTextField
import team.duckie.quackquack.ui.util.DoNotUseDirectly

private val IntrinsicMeasurable.layoutId: Any?
    get() = (parentData as? LayoutIdParentData)?.layoutId

private val ZeroConstraints = Constraints.fixed(
    width = 0,
    height = 0,
)

private const val QuackTextFieldLayoutId = "QuackTextFieldContent"
private const val QuackTextFieldLeadingContentLayoutId = "QuackTextFieldLeadingContent"
private const val QuackTextFieldTrailingContentLayoutId = "QuackTextFieldTrailingContent"

/**
 * [TextFieldMeasurePolicy] 을 [remember] 로 저장한 값을 반환합니다.
 * 항상 같은 인스턴스를 사용해도 되므로 [remember] 의 key 가 지정되지 않습니다.
 *
 * @return remember 된 [TextFieldMeasurePolicy] 의 인스턴스
 */
@Composable
@OptIn(DoNotUseDirectly::class)
internal fun rememberTextFieldMeasurePolicy() = remember {
    TextFieldMeasurePolicy()
}

/**
 * [QuackBasicTextField] 를 measure 하기 위한 [MeasurePolicy]
 *
 * @suppress 이 클래스는 직접 사용하면 안됩니다. 리컴포지션 퍼포먼스를 위해
 * remember 로 감싼 인스턴스를 반환하는 [rememberTextFieldMeasurePolicy] 를
 * 사용하세요.
 */
@DoNotUseDirectly
internal class TextFieldMeasurePolicy : MeasurePolicy {
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
        val leadingPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.measure(
            constraints = looseConstraints,
        )
        occupiedSpaceHorizontally += leadingPlaceable.widthOrZero()

        // measure trailing icon
        val trailingPlaceable = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.measure(
            constraints = looseConstraints.offset(
                horizontal = -occupiedSpaceHorizontally,
            ),
        )
        occupiedSpaceHorizontally += trailingPlaceable.widthOrZero()

        // measure input field
        val textFieldConstraints = constraints
            .copy(
                minHeight = 0,
            )
            .offset(
                horizontal = -occupiedSpaceHorizontally,
            )
        val textFieldPlaceable = measurables.first { measurable ->
            measurable.layoutId == QuackTextFieldLayoutId
        }.measure(
            constraints = textFieldConstraints,
        )

        val width = calculateWidth(
            textFieldWidth = textFieldPlaceable.width,
            leadingWidth = trailingPlaceable.widthOrZero(),
            trailingWidth = leadingPlaceable.widthOrZero(),
            constraints = constraints,
        )
        val height = calculateHeight(
            textFieldHeight = textFieldPlaceable.height,
            leadingHeight = leadingPlaceable.heightOrZero(),
            trailingHeight = trailingPlaceable.heightOrZero(),
            constraints = constraints,
        )

        return layout(width, height) {
            placeWithoutLabel(
                width,
                height,
                textFieldPlaceable,
                leadingPlaceable,
                trailingPlaceable,
            )
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurables: List<IntrinsicMeasurable>,
        width: Int,
    ) = intrinsicHeight(
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
    ) = intrinsicHeight(
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
    ) = intrinsicWidth(
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
    ) = intrinsicWidth(
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
            intrinsicWidth: Int,
        ) -> Int,
    ): Int {
        val textFieldWidth = intrinsicMeasurer(
            measurables.first { measurable ->
                measurable.layoutId == QuackTextFieldLayoutId
            },
            height,
        )

        val leadingWidth = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                measurable,
                height,
            )
        } ?: 0

        val trailingWidth = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                measurable,
                height,
            )
        } ?: 0

        return calculateWidth(
            textFieldWidth = textFieldWidth,
            leadingWidth = leadingWidth,
            trailingWidth = trailingWidth,
            constraints = ZeroConstraints,
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
            measurables.first { measurable ->
                measurable.layoutId == QuackTextFieldLayoutId
            },
            width,
        )

        val leadingHeight = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldLeadingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                measurable,
                width,
            )
        } ?: 0

        val trailingHeight = measurables.find { measurable ->
            measurable.layoutId == QuackTextFieldTrailingContentLayoutId
        }?.let { measurable ->
            intrinsicMeasurer(
                measurable,
                width,
            )
        } ?: 0

        return calculateHeight(
            textFieldHeight = textFieldHeight,
            leadingHeight = leadingHeight,
            trailingHeight = trailingHeight,
            constraints = ZeroConstraints,
        )
    }
}

/**
 * TextField 배치에 사용되는 constraints 중에서 제일 큰 넓이를 계산합니다.
 *
 * @param textFieldWidth TextField 의 넓이
 * @param leadingWidth leading decoration item 의 넓이
 * @param trailingWidth trailing decoration item 의 넓이
 * @param constraints 배치하려는 TextField 레이아웃의 [Constraints]
 *
 * @return 인자로 주어진 넓이 중에서 제일 큰 넓이
 */
private fun calculateWidth(
    textFieldWidth: Int,
    leadingWidth: Int,
    trailingWidth: Int,
    constraints: Constraints,
) = maxOf(
    a = textFieldWidth,
    b = max(
        a = leadingWidth,
        b = trailingWidth,
    ),
    c = constraints.minWidth,
)

/**
 * TextField 배치에 사용되는 constraints 중에서 제일 큰 높이를 계산합니다.
 *
 * @param textFieldHeight TextField 의 높이
 * @param leadingHeight leading decoration item 의 높이
 * @param trailingHeight trailing decoration item 의 높이
 * @param constraints 배치하려는 TextField 레이아웃의 [Constraints]
 *
 * @return 인자로 주어진 높이 중에서 제일 큰 높이
 */
private fun calculateHeight(
    textFieldHeight: Int,
    leadingHeight: Int,
    trailingHeight: Int,
    constraints: Constraints,
) = maxOf(
    a = textFieldHeight,
    b = max(
        a = leadingHeight,
        b = trailingHeight,
    ),
    c = constraints.minHeight,
)

/**
 * TextField 의 [Placeable] 들을 배치하기 위한 오프셋을 계산하여 배치합니다.
 *
 * @param width TextField 레이아웃의 넓이
 * @param height TextField 레이아웃의 높이
 * @param textFieldPlaceable TextField 의 [Placeable]
 * @param leadingPlaceable leading decoration item 의 [Placeable]
 * @param trailingPlaceable trailing decoration item 의 [Placeable]
 */
private fun Placeable.PlacementScope.placeWithoutLabel(
    width: Int,
    height: Int,
    textFieldPlaceable: Placeable,
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
}

/**
 * 주어진 [Placeable] 에서 width 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * width 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.widthOrZero() = this?.width ?: 0

/**
 * 주어진 [Placeable] 에서 height 값을 가져오거나, 유효하지 않을 경우
 * 0 을 반환합니다.
 *
 * @receiver width 값을 가져올 [Placeable]
 * @return receiver 로 주어진 [Placeable] 이 유효하다면 해당 [Placeable] 의
 * height 값을 반환하고, 유효하지 않다면 0 을 반환합니다.
 */
internal fun Placeable?.heightOrZero() = this?.height ?: 0
