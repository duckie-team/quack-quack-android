/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList

private val gridHorizontalPadding = 8.dp


/**
 * QuackSimpleGridLayout을 구현합니다.
 *
 * @param columns
 * @param items
 * @param verticalSpace
 * @param horizontalSpace
 * @param horizontalPadding
 * @param itemContent
 *
 */

@Composable
fun <T> QuackSimpleGridLayout(
    columns: Int,
    items: PersistentList<T>,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    horizontalPadding: Dp = gridHorizontalPadding,
    itemContent: @Composable (Int, T) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(
            paddingValues = PaddingValues(
                horizontal = horizontalPadding,
            )
        ),
        columns = GridCells.Fixed(
            count = columns
        ),
        content = {
            itemsIndexed(
                items = items,
            ) { index, item ->
                QuackSimpleGridItem(
                    verticalSpace = verticalSpace,
                    horizontalSpace = horizontalSpace,
                    itemContent = {
                        itemContent(
                            index,
                            item,
                        )
                    },
                    index = index,
                    size = items.size,
                )
            }
        },
    )
}

/**
 * QuackHeaderGridLayout 을 구현합니다.
 *
 * GridLayout 중에 첫 아이템만 다른 아이템으로 사용해야하는 UI에 사용할 수 있습니다.
 *
 * @param columns
 * @param items
 * @param verticalSpace
 * @param horizontalSpace
 * @param horizontalPadding
 * @param itemContent
 * @param header
 */

@Composable
fun <T> QuackHeaderGridLayout(
    columns: Int,
    items: PersistentList<T>,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    horizontalPadding: Dp = gridHorizontalPadding,
    itemContent: @Composable (Int, T) -> Unit,
    header: @Composable () -> Unit,
) {

    val gridItems = listOf(items[0]) + items

    LazyVerticalGrid(
        modifier = Modifier.padding(
            paddingValues = PaddingValues(
                horizontal = horizontalPadding,
            )
        ),
        columns = GridCells.Fixed(
            count = columns
        ),
        content = {
            itemsIndexed(
                items = gridItems,
            ) { index, item ->
                QuackSimpleGridItem(
                    verticalSpace = verticalSpace,
                    horizontalSpace = horizontalSpace,
                    itemContent = {
                        itemContent(
                            index,
                            item,
                        )
                    },
                    header = header,
                    index = index,
                    size = gridItems.size,
                )
            }
        },
    )
}

/**
 * QuackSimpleGridItem
 *
 * @param index
 * @param size
 * @param verticalSpace
 * @param horizontalSpace
 * @param itemContent
 * @param footer
 * @param header
 */

@Composable
private fun QuackSimpleGridItem(
    index: Int,
    size: Int,
    verticalSpace: Dp,
    horizontalSpace: Dp,
    itemContent: @Composable () -> Unit,
    footer: (@Composable () -> Unit)? = null,
    header: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = Modifier.padding(
            paddingValues = PaddingValues(
                horizontal = horizontalSpace,
                vertical = verticalSpace,
            )
        ),
    ) {
        if ( index == 0 && header != null){
            header()
        }
        else if ( index == size && footer != null){
            footer()
        }
        else {
            itemContent()
        }
    }
}
