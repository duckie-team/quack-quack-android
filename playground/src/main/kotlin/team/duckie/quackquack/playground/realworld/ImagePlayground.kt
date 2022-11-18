/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.R
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.icon.QuackIcon
import team.duckie.quackquack.ui.util.DpSize

class ImagePlayground : PlaygroundActivity(
    name = "Image",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackImageDemo.name to { QuackImageDemo() },
        ::QuackSelectableImageDemo.name to { QuackSelectableImageDemo() },
    )
}

@Composable
fun QuackImageDemo() {
    val context = LocalContext.current
    val toast = rememberToast()

    QuackImage(
        src = ContextCompat.getDrawable(
            context, R.drawable.banner_quack_dev,
        ),
        size = DpSize(
            width = 400.dp,
            height = 180.dp,
        ),
        badge = {
            QuackImage(
                src = QuackIcon.FilledHeart,
            )
        },
        badgeSize = DpSize(
            all = 24.dp,
        ),
        onClick = { toast("QuackImage clicked") },
    )
}

@Composable
fun QuackSelectableImageDemo() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }

    QuackSelectableImage(
        modifier = Modifier.aspectRatio(
            ratio = 1f,
        ),
        src = ContextCompat.getDrawable(
            context, R.drawable.ic_quack,
        ),
        isSelected = selected,
        onClick = { selected = !selected },
    )
}
