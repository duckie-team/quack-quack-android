// TAKEN FROM: https://github.com/duckie-team/quack-quack-android/pull/167
// maybe needs re-implementation

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.internal.QuackText
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.modifier.quackClickable
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

private val QuackFabContainerElevation = 3.dp

private val QuackFabContainerShape = CircleShape
private val QuackFabMenuShape = RoundedCornerShape(
    size = 12.dp,
)

private val QuackFabContainerSize = 48.dp

private val QuackFabIconResource = QuackIcon.Plus
private val QuackFabIconTint = QuackColor.White
private val QuackFabIconSize = DpSize(
    width = 20.dp,
    height = 20.dp,
)
private const val QuackFabIconRotate = 45f
private val QuackFabMenuIconSize = DpSize(
    width = 24.dp,
    height = 24.dp,
)

private val QuackFabContainerColor = QuackColor.DuckieOrange
private val QuackFabMenuBackgroundColor = QuackColor.White
private val QuackFabMenuItemColor = QuackColor.Black

private val QuackFabMenuItemTextStyle = QuackTextStyle.Subtitle.change(
    color = QuackFabMenuItemColor
)

private val QuackFabMenuPadding = PaddingValues(
    horizontal = 16.dp - 8.dp,
    vertical = 20.dp - 8.dp,
)
private val QuackFabAndMenuSpace = 8.dp
private val QuackFabMenuHorizontalPadding = 8.dp
private val QuackMenuContentSpacedBy = 4.dp
private val QuackFabMenuTextPadding = PaddingValues(
    vertical = 3.dp + 8.dp,
)

/**
 * ????????? ?????? FloatingActionButton ??? ????????????.
 *
 * @param icon ??? FAB ??? ????????? ?????????
 * @param onClick ??? FAB ??? ???????????? ??? ????????? ??????
 */
@Composable
@NonRestartableComposable
public fun QuackFab(
    icon: QuackIcon,
    onClick: () -> Unit,
): Unit = QuackFabImpl(
    icon = icon,
    onClick = onClick,
    rotate = 0f,
)

/**
 * ????????? ?????? FloatingActionButton ??? ????????? ????????????.
 *
 * @param icon ??? FAB ??? ????????? ?????????
 * @param onClick ??? FAB ??? ???????????? ??? ????????? ??????
 * @param rotate [icon] ??? ?????? ??????
 */
@Composable
private fun QuackFabImpl(
    icon: QuackIcon,
    onClick: () -> Unit,
    rotate: Float,
) {
    QuackSurface(
        modifier = Modifier.size(
            size = QuackFabContainerSize,
        ),
        shape = QuackFabContainerShape,
        backgroundColor = QuackFabContainerColor,
        elevation = QuackFabContainerElevation,
        onClick = onClick,
    ) {
        Box(
            // needs for set size in box inlining
            modifier = Modifier.size(
                size = QuackFabIconSize,
            ),
        ) {
            QuackImage(
                modifier = Modifier.rotate(
                    degrees = rotate,
                ),
                src = icon,
                size = QuackFabIconSize,
                tint = QuackFabIconTint,
            )
        }
    }
}

/**
 * [QuackMenuFab] ?????? ????????? ?????????
 *
 * @param icon ????????? ????????? ?????????
 * @param text ????????? ????????? ?????????
 */
public data class QuackMenuFabItem(
    public val icon: QuackIcon,
    public val text: String,
)

/**
 * [QuackFab] ?????? ?????? ????????? ????????? ????????????.
 *
 * ????????? ?????? ????????? ?????? FAB ??? ?????? [QuackIcon.Plus] ???????????? ????????????, [45???][QuackFabIconRotate] ????????????
 * ????????? ?????? ????????????. ?????? ????????? FAB ??? ???????????? 8 dp ?????? ?????? ????????? ????????????.
 * ?????? ???????????? ?????? FAB ??? ????????? [Column] ?????? ?????? ????????? ???????????????.
 *
 * @param items ????????? ????????? ????????????
 * @param expanded FAB ??? expand ????????? ??????. ?????? true ??????
 * FAB ??? ???????????? [45???][QuackFabIconRotate] ????????????, ????????? ???????????????.
 * @param onFabClick FAB ??? ???????????? ??? ????????? ??????
 * @param onItemClick ????????? ???????????? ???????????? ??? ????????? ??????
 */
@Composable
public fun QuackMenuFab(
    items: PersistentList<QuackMenuFabItem>,
    expanded: Boolean,
    onFabClick: () -> Unit,
    onItemClick: (index: Int, item: QuackMenuFabItem) -> Unit,
) {
    val density = LocalDensity.current
    val textWidths = remember(
        key1 = items,
    ) {
        mutableStateListOf(
            elements = Array(
                size = items.size,
                init = { 0.dp },
            )
        )
    }
    val textWidthModifier = remember(
        key1 = items,
        key2 = textWidths.all { width ->
            width.value != 0f
        },
    ) {
        when (
            textWidths.all { width ->
                width.value != 0f
            }
        ) {
            true -> Modifier.width(
                width = textWidths.max(),
            )
            else -> Modifier.wrapContentWidth()
        }
    }
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = QuackFabAndMenuSpace,
        ),
        horizontalAlignment = Alignment.End,
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = QuackFabMenuBackgroundColor.composeColor,
                    shape = QuackFabMenuShape,
                )
                .clip(
                    shape = QuackFabMenuShape,
                ),
            visible = expanded,
            enter = fadeIn(
                animationSpec = QuackAnimationSpec(),
            ) + expandVertically(
                animationSpec = QuackAnimationSpec(),
            ),
            exit = fadeOut(
                animationSpec = QuackAnimationSpec(),
            ) + shrinkVertically(
                animationSpec = QuackAnimationSpec(),
            ),
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentSize(),
                contentPadding = QuackFabMenuPadding,
            ) {
                itemsIndexed(
                    items = items,
                    key = { _, item -> item.text },
                ) { index, item ->
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .quackClickable(
                                rippleEnabled = false,
                                onClick = {
                                    onItemClick(
                                        /*index = */
                                        index,
                                        /*item = */
                                        item,
                                    )
                                },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = QuackMenuContentSpacedBy,
                        ),
                    ) {
                        QuackImage(
                            modifier = Modifier.padding(
                                start = QuackFabMenuHorizontalPadding,
                            ),
                            src = item.icon,
                            size = QuackFabMenuIconSize,
                            tint = QuackFabMenuItemColor,
                        )
                        QuackText(
                            modifier = Modifier
                                .then(
                                    other = textWidthModifier,
                                )
                                .onSizeChanged { size ->
                                    with(
                                        receiver = density,
                                    ) {
                                        textWidths[index] = size.width.toDp()
                                    }
                                }
                                .padding(
                                    paddingValues = QuackFabMenuTextPadding,
                                )
                                .padding(
                                    end = QuackFabMenuHorizontalPadding,
                                ),
                            text = item.text,
                            style = QuackFabMenuItemTextStyle.change(
                                textAlign = TextAlign.Start,
                            ),
                        )
                    }
                }
            }
        }
        QuackFabImpl(
            icon = QuackFabIconResource,
            onClick = onFabClick,
            rotate = animateFloatAsState(
                targetValue = if (expanded) QuackFabIconRotate else 0f,
                animationSpec = QuackAnimationSpec(),
            ).value,
        )
    }
}
