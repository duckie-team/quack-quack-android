/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "FunctionName",
    "NOTHING_TO_INLINE",
)

package team.duckie.quackquack.ui.benchmark.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.quackquack.ui.animation.QuackDefaultAnimationMillis
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackDialogMenuItem
import team.duckie.quackquack.ui.component.QuackFloatingActionButton
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackLarge40WhiteButton
import team.duckie.quackquack.ui.component.QuackLargeButton
import team.duckie.quackquack.ui.component.QuackLargeWhiteButton
import team.duckie.quackquack.ui.component.QuackMediumBorderToggleButton
import team.duckie.quackquack.ui.component.QuackMenuFloatingActionButton
import team.duckie.quackquack.ui.component.QuackSmallBorderToggleButton
import team.duckie.quackquack.ui.component.QuackSmallButton
import team.duckie.quackquack.ui.component.QuackToggleChip
import team.duckie.quackquack.ui.icon.QuackIcon

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
    QuackDialogMenuItem(
        icon = QuackIcon.Area,
        text = "",
    )

    QuackFloatingActionButton(
        icon = QuackIcon.Area,
        onClick = {},
    )

    QuackMenuFloatingActionButton(
        expanded = true,
        onClickButton = {},
        onDismissRequest = {},
        menuItems = persistentListOf(
            QuackDialogMenuItem(
                icon = QuackIcon.Feed,
                text = "",
            ),
        ),
        onClickMenuItem = {},
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
