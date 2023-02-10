/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

/**
 * QuackGridLayout 를 그리는데 필요한 리소스들을 정의합니다.
 */
private object QuackGridDefaults {
    val HorizontalGap = 3.dp
    val VerticalGap = 3.dp

    /**
     * 한 줄당 들어갈 수 있는 최대 컴포넌트 수
     */
    const val MaxComponentCountsForLine = 3
}

/**
 * Duckie implements the GridLayout component used for the gallery view.
 *
 * @param modifier the modifier to apply to this layout
 * @param items the data list
 * @param state the state object to be used to control or observe the list's state
 * @param verticalSpacing the vertical spacing between items
 * @param horizontalSpacing the horizontal spacing between items
 * @param key a factory of stable and unique keys representing the item. Using the same key
 * for multiple items in the grid is not allowed. Type of the key should be saveable
 * via Bundle on Android. If null is passed the position in the grid will represent the key.
 * When you specify the key the scroll position will be maintained based on the key, which
 * means if you add/remove items before the current visible item the item with the given key
 * will be kept as the first visible one.
 * @param span define custom spans for the items. Default is 1x1. It is good practice to leave
 * it `null` when this matches the intended behavior, as providing a custom implementation
 * impacts performance
 * @param contentType a factory of the content types for the item. The item compositions of
 * the same type could be reused more efficiently. Note that null is a valid type and items of such
 * type will be considered compatible.
 * @param itemContent the content displayed by a single item
 */
@Composable
public fun <T> QuackGridLayout(
    modifier: Modifier = Modifier,
    items: ImmutableList<T>,
    state: LazyGridState = rememberLazyGridState(),
    verticalSpacing: Dp = QuackGridDefaults.VerticalGap,
    horizontalSpacing: Dp = QuackGridDefaults.HorizontalGap,
    key: ((index: Int, item: T) -> Any)? = null,
    span: (LazyGridItemSpanScope.(index: Int, item: T) -> GridItemSpan)? = null,
    contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    itemContent: @Composable (index: Int, item: T) -> Unit,
): Unit = with(
    receiver = QuackGridDefaults,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        columns = GridCells.Fixed(
            count = MaxComponentCountsForLine
        ),
        verticalArrangement = Arrangement.spacedBy(
            space = verticalSpacing,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = horizontalSpacing,
        ),
    ) {
        itemsIndexed(
            items = items,
            key = key,
            span = span,
            contentType = contentType,
        ) { index, item ->
            itemContent(
                index = index,
                item = item,
            )
        }
    }
}
