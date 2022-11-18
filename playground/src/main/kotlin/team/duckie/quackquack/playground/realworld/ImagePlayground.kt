/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.R
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.icon.QuackIcon

class ImagePlayground : PlaygroundActivity(
    name = "Image",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackImageDemo.name to { QuackImageDemo() },
        ::QuackSelectableImageDemo.name to { QuackSelectableImageDemo() },
    )
}

private const val DuckieDevBanner =
    "https://raw.githubusercontent.com/duckie-team/.github/fbca5c5ca8d7475af662a8c5d29eacc9ba0de2ea/assets/dev_banner.svg"

@Composable
fun QuackImageDemo() {
    val toast = rememberToast()

    QuackImage(
        src = DuckieDevBanner,
        badge = {
            QuackImage(
                src = QuackIcon.FilledHeart,
            )
        },
        onClick = { toast("QuackImage clicked") },
    )
}

@Composable
fun QuackSelectableImageDemo() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }

    QuackSelectableImage(
        src = ContextCompat.getDrawable(
            context, R.drawable.ic_quack,
        ),
        isSelected = selected,
        onClick = { selected = !selected },
    )
}
