/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackAnnotatedBody2
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBody2
import team.duckie.quackquack.ui.component.QuackBody3
import team.duckie.quackquack.ui.component.QuackHeadLine1
import team.duckie.quackquack.ui.component.QuackHeadLine2
import team.duckie.quackquack.ui.component.QuackHighlightBody1
import team.duckie.quackquack.ui.component.QuackLarge1
import team.duckie.quackquack.ui.component.QuackSubtitle
import team.duckie.quackquack.ui.component.QuackSubtitle2
import team.duckie.quackquack.ui.component.QuackTitle1
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.component.QuackUnderlineBody3
import team.duckie.quackquack.ui.component.QuackUnderlineHeadLine2

class TypographyPlayground : PlaygroundActivity(
    name = "Typography",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackSplashSloganDemo.name to { QuackSplashSloganDemo() },
        ::QuackHeadLine1Demo.name to { QuackHeadLine1Demo() },
        ::QuackHeadLine2Demo.name to { QuackHeadLine2Demo() },
        ::QuackTitle1Demo.name to { QuackTitle1Demo() },
        ::QuackTitle2Demo.name to { QuackTitle2Demo() },
        ::QuackSubtitleDemo.name to { QuackSubtitleDemo() },
        ::QuackSubtitle2Demo.name to { QuackSubtitle2Demo() },
        ::QuackBody1Demo.name to { QuackBody1Demo() },
        ::QuackBody2Demo.name to { QuackBody2Demo() },
        ::QuackBody3Demo.name to { QuackBody3Demo() },
        ::QuackUnderlineHeadLine2Demo.name to { QuackUnderlineHeadLine2Demo() },
        ::QuackUnderlineBody3Demo.name to { QuackUnderlineBody3Demo() },
        ::QuackHighlightBody1Demo.name to { QuackHighlightBody1Demo() },
        ::QuackAnnotatedBody2Demo.name to { QuackAnnotatedBody2Demo() },
    )
}

@Composable
fun QuackSplashSloganDemo() {
    val toast = rememberToast()

    QuackLarge1(
        text = "QuackSplashSlogan + 30.dp padding (for click area test)",
        onClick = { toast("QuackSplashSlogan") },
        padding = PaddingValues(30.dp),
    )
}

@Composable
fun QuackHeadLine1Demo() {
    val toast = rememberToast()

    QuackHeadLine1(
        text = "QuackHeadLine1",
        onClick = { toast("QuackHeadLine1") },
    )
}

@Composable
fun QuackHeadLine2Demo() {
    val toast = rememberToast()

    QuackHeadLine2(
        text = "QuackHeadLine2",
        onClick = { toast("QuackHeadLine2") },
    )
}

@Composable
fun QuackTitle1Demo() {
    val toast = rememberToast()

    QuackTitle1(
        text = "QuackTitle1",
        onClick = { toast("QuackTitle1") },
    )
}

@Composable
fun QuackTitle2Demo() {
    val toast = rememberToast()

    QuackTitle2(
        text = "QuackTitle2",
        onClick = { toast("QuackTitle2") },
    )
}

@Composable
fun QuackSubtitleDemo() {
    val toast = rememberToast()

    QuackSubtitle(
        text = "QuackSubtitle",
        onClick = { toast("QuackSubtitle") },
    )
}

@Composable
fun QuackSubtitle2Demo() {
    val toast = rememberToast()

    QuackSubtitle2(
        text = "QuackSubtitle2",
        onClick = { toast("QuackSubtitle2") },
    )
}

@Composable
fun QuackBody1Demo() {
    val toast = rememberToast()

    QuackBody1(
        text = "QuackBody1",
        onClick = { toast("QuackBody1") },
    )
}

@Composable
fun QuackBody2Demo() {
    val toast = rememberToast()

    QuackBody2(
        text = "QuackBody2",
        onClick = { toast("QuackBody2") },
    )
}

@Composable
fun QuackBody3Demo() {
    val toast = rememberToast()

    QuackBody3(
        text = "QuackBody3",
        onClick = { toast("QuackBody3") },
    )
}

@Composable
fun QuackUnderlineHeadLine2Demo() {
    val toast = rememberToast()

    QuackUnderlineHeadLine2(
        text = "QuackUnderlineHeadLine2",
        underlineTexts = persistentListOf(
            "Quack",
            "HeadLine2",
        ),
        onClick = { toast("QuackUnderlineHeadLine2") },
    )
}

@Composable
fun QuackUnderlineBody3Demo() {
    val toast = rememberToast()

    QuackUnderlineBody3(
        text = "QuackUnderlineBody3",
        underlineTexts = persistentListOf(
            "Quack",
            "Body3",
        ),
        onClick = { toast("QuackUnderlineBody3") },
    )
}

@Composable
fun QuackHighlightBody1Demo() {
    val toast = rememberToast()

    QuackHighlightBody1(
        text = "QuackHighlightBody1",
        highlightTexts = persistentListOf(
            "Quack",
            "Body1",
        ),
        onClick = { toast("QuackHighlightBody1") },
    )
}

@Composable
fun QuackAnnotatedBody2Demo() {
    val toast = rememberToast()

    QuackAnnotatedBody2(
        text = "QuackAnnotatedBody2",
        highlightTextPairs = persistentListOf(
            "Quack" to { toast("Quack") },
            "Body2" to { toast("Body2") },
        ),
        underlineEnabled = true,
        onClick = { toast("QuackAnnotatedBody2") },
    )
}
