/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [typography.kt] created by Ji Sungbin on 22. 8. 21. 오후 2:07
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackText

@Composable
fun QuackHeadLine1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.HeadLine1.changeColor(color),
    )
}

@Composable
fun QuackHeadLine2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.HeadLine2.changeColor(color),
    )
}

@Composable
fun QuackTitle1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Title1.changeColor(color),
    )
}

@Composable
fun QuackTitle2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Title2.changeColor(color),
    )
}

@Composable
fun QuackSubtitle(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Subtitle.changeColor(color),
    )
}

@Composable
fun QuackBody1(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Body1.changeColor(color),
    )
}

@Composable
fun QuackBody2(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Body2.changeColor(color),
    )
}

@Composable
fun QuackBody3(
    modifier: Modifier = Modifier,
    text: String,
    color: QuackColor = QuackColor.Black,
) {
    QuackText(
        modifier = modifier,
        text = text,
        style = QuackTextStyle.Body3.changeColor(color),
    )
}
