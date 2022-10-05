/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "FunctionName",
    "NOTHING_TO_INLINE",
)

package team.duckie.quackquack.ui.benchmark.app

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.quackquack.ui.animation.QuackDefaultAnimationMillis
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackFab
import team.duckie.quackquack.ui.component.QuackGrayscaleTag
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackIconTag
import team.duckie.quackquack.ui.component.QuackIconTextToggle
import team.duckie.quackquack.ui.component.QuackIconToggle
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLarge40WhiteButton
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeWhiteButton
import team.duckie.quackquack.ui.component.QuackMainTab
import team.duckie.quackquack.ui.component.QuackMediumBorderToggleButton
import team.duckie.quackquack.ui.component.QuackMenuFab
import team.duckie.quackquack.ui.component.QuackMenuFabItem
import team.duckie.quackquack.ui.component.QuackRoundCheckBox
import team.duckie.quackquack.ui.component.QuackRowTag
import team.duckie.quackquack.ui.component.QuackSmallBorderToggleButton
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackSquareCheckBox
import team.duckie.quackquack.ui.component.QuackSubTab
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackTag
import team.duckie.quackquack.ui.component.QuackTextField
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.constant.QuackHeight
import team.duckie.quackquack.ui.constant.QuackWidth
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.textstyle.QuackDefaultFontScale
import team.duckie.quackquack.ui.textstyle.QuackFontScale
import team.duckie.quackquack.ui.textstyle.QuackTextStyle

inline fun ImmutableCollectionsBenchmark() {
    persistentListOf<Any>()
    emptyList<Any>().toPersistentList()
}

inline fun QuackAnimationSpecBenchmark() {
    QuackDefaultAnimationMillis
}

inline fun QuackColorBenchmark() {
    QuackColor.Black
    QuackColor.DuckieOrange
    QuackColor.Gray1
    QuackColor.Gray2
    QuackColor.Gray3
    QuackColor.Gray4
    QuackColor.OrangeRed
    QuackColor.White
}

@Composable
inline fun QuackButtonBenchmark() {
    QuackLarge40WhiteButton(
        text = "",
        onClick = {},
    )

    QuackLargeButton(
        text = "",
        onClick = {},
    )

    QuackLargeWhiteButton(
        text = "",
        onClick = {},
    )

    QuackMediumBorderToggleButton(
        text = "",
        selected = true,
        onClick = {},
    )

    QuackSmallBorderToggleButton(
        text = "",
        selected = true,
        onClick = {},
    )

    QuackSmallButton(
        text = "",
        enabled = true,
        onClick = {},
    )

    QuackToggleChip(
        text = "",
        selected = true,
        onClick = {},
    )
}

@Composable
inline fun QuackFabBenchmark() {
    QuackFab(
        icon = QuackIcon.Heart,
        onClick = {},
    )
    QuackMenuFab(
        items = persistentListOf(
            QuackMenuFabItem(
                icon = QuackIcon.FilledHeart,
                text = "FilledHeart",
            ),
        ),
        expanded = true,
        onFabClick = {},
        onItemClick = { _, _ -> },
    )
}

@Composable
inline fun QuackImageBenchmark() {
    QuackImage(
        src = null,
        overrideSize = DpSize.Zero,
        tint = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )
}

@Composable
inline fun QuackTabBenchmark() {
    QuackMainTab(
        titles = persistentListOf(
            "",
        ),
        tabStartHorizontalPadding = 0.dp,
        selectedTabIndex = 0,
        onTabSelected = {},
    )

    QuackSubTab(
        titles = persistentListOf(
            "",
        ),
        tabStartHorizontalPadding = 0.dp,
        selectedTabIndex = 0,
        onTabSelected = {},
    )
}

@Composable
inline fun QuackTagBenchmark() {
    QuackGrayscaleTag(
        text = "",
        trailingText = "",
        onClick = {},
    )

    QuackIconTag(
        text = "",
        icon = QuackIcon.Area,
        isSelected = true,
        onClick = {},
        onClickIcon = {},
    )

    QuackRowTag(
        title = "",
        items = persistentListOf(),
        itemsSelection = emptyList(),
        onClick = {},
    )

    QuackTag(
        text = "",
        isSelected = true,
        onClick = {},
    )
}

@Composable
inline fun QuackTextFieldBenchmark() {
    QuackTextField(
        width = QuackWidth.Wrap,
        height = QuackHeight.Wrap,
        text = "",
        onTextChanged = {},
        textStyle = QuackTextStyle.Body1,
        placeholderText = "",
        isError = true,
        errorText = "",
        errorTextStyle = QuackTextStyle.Body1,
        leadingContent = {},
        trailingContent = {},
        keyboardOptions = KeyboardOptions.Default,
        keyboardActions = KeyboardActions.Default,
    )
}

@Composable
inline fun QuackToggleBenchmark() {
    QuackIconTextToggle(
        checkedIcon = QuackIcon.Area,
        uncheckedIcon = QuackIcon.Area,
        checked = true,
        text = "",
        onToggle = {},
    )

    QuackIconToggle(
        checkedIcon = QuackIcon.Area,
        uncheckedIcon = QuackIcon.Area,
        checked = true,
        onToggle = {},
    )

    QuackRoundCheckBox(
        checked = true,
        onToggle = {},
    )

    QuackSquareCheckBox(
        checked = true,
        onToggle = {},
    )
}

@Composable
inline fun QuackTypographyBenchmark() {
    QuackBody1(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackBody2(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackBody3(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackHeadLine1(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackHeadLine2(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackSubtitle(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackTitle1(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )

    QuackTitle2(
        text = "",
        color = QuackColor.Black,
        rippleEnabled = true,
        onClick = {},
    )
}

@Composable
inline fun QuackHeightBenchmark() {
    QuackHeight.Wrap
    QuackHeight.Custom(
        height = 0.dp,
    )
    QuackHeight.Fill
}

@Composable
inline fun QuackWidthBenchmark() {
    QuackWidth.Wrap
    QuackWidth.Custom(
        width = 0.dp,
    )
    QuackWidth.Fill
}

@Composable
inline fun QuackIconBenchmark() {
    QuackIcon.Area
    QuackIcon.ArrowBack
    QuackIcon.ArrowDown
    QuackIcon.ArrowRight
    QuackIcon.ArrowSend
    QuackIcon.Badge
    QuackIcon.Bookmark
    QuackIcon.Buy
    QuackIcon.Camera
    QuackIcon.Close
    QuackIcon.Comment
    QuackIcon.Delete
    QuackIcon.DeleteBg
    QuackIcon.Dm
    QuackIcon.Feed
    QuackIcon.FilledBookmark
    QuackIcon.FilledHeart
    QuackIcon.Filter
    QuackIcon.Heart
    QuackIcon.Image
    QuackIcon.ImageEdit
    QuackIcon.ImageEditBg
    QuackIcon.MarketPrice
    QuackIcon.More
    QuackIcon.NoticeAdd
    QuackIcon.Place
    QuackIcon.Plus
    QuackIcon.Profile
    QuackIcon.Search
    QuackIcon.Sell
    QuackIcon.Setting
    QuackIcon.Share
    QuackIcon.Tag
    QuackIcon.WhiteHeart
    QuackIcon.Won
}

@Composable
inline fun QuackTextStyleBenchmark() {
    QuackTextStyle.Body1
    QuackTextStyle.Body2
    QuackTextStyle.Body3
    QuackTextStyle.HeadLine1
    QuackTextStyle.HeadLine2
    QuackTextStyle.Subtitle
    QuackTextStyle.Title1
    QuackTextStyle.Title2

    QuackDefaultFontScale
    QuackFontScale
}
