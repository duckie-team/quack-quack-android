/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

class TopAppBarPlayground : PlaygroundActivity(
    name = "TopAppBar",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackTopAppBarTypeLogoAndIconsDemo.name to { QuackTopAppBarTypeLogoAndIconsDemo() },
        ::QuackTopAppBarTypeTextDemo.name to { QuackTopAppBarTypeTextDemo() },
    )
}

@Composable
fun QuackTopAppBarTypeLogoAndIconsDemo() {
    val toast = rememberToast()

    QuackTopAppBar(
        leadingIcon = QuackIcon.Heart,
        leadingText = "Heart",
        onLeadingIconClick = { toast("Heart clicked") },
        showLogoAtCenter = true,
        onCenterClick = { toast("Logo clicked") },
        trailingIcon = QuackIcon.FilledHeart,
        trailingExtraIcon = QuackIcon.WhiteHeart,
        onTrailingIconClick = { toast("FilledHeart clicked") },
        onTrailingExtraIconClick = { toast("WhiteHeart clicked") },
    )
}

@Composable
fun QuackTopAppBarTypeTextDemo() {
    val toast = rememberToast()

    QuackTopAppBar(
        leadingIcon = QuackIcon.Heart,
        leadingText = "Heart",
        onLeadingIconClick = { toast("Heart clicked") },
        centerText = "DUCKIE!",
        onCenterClick = { toast("Logo clicked") },
        trailingText = "trailing",
        onTrailingTextClick = { toast("trailing clicked") },
    )
}
