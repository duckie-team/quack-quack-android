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

import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.quackquack.ui.animation.QuackDefaultAnimationMillis
import team.duckie.quackquack.ui.color.QuackColor

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

inline fun QuackButtonBenchMark() {
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

inline fun QuackFabBenchMark() {
    QuackDialogMenuItem(icon = QuackIcon.Area, text = "")

    QuackFloatingActionButton(icon = QuackIcon.Area) {

    }

    QuackMenuFloatingActionButton(
        expanded = true,
        onClickButton = {},
        onDismissRequest = {},
        menuItems =  persistentListOf(
            QuackDialogMenuItem(
                icon = QuackIcon.Feed,
                text = "피드",
            ),
        ),
        onClickMenuItem = {}
    )
}

inline fun QuackImageBenchMark() {
    QuackImage(
        src = null,
        overrideSize = DpSize.Zero,
        tint = QuackColor.Black,
        rippleEnabled = true,
        onClick = {}
    )
}
