/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import team.duckie.quackquack.ui.component.QuackSelectableImageType
import team.duckie.quackquack.ui.util.DpSize

class ImagePlayground : PlaygroundActivity(
    name = "Image",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackImageDemo.name to { QuackImageDemo() },
        ::QuackSelectableImageTypeTopEndCheckboxDemo.name to { QuackSelectableImageTypeTopEndCheckboxDemo() },
        ::QuackSelectableImageTypeCheckOverlayDemo.name to { QuackSelectableImageTypeCheckOverlayDemo() },
    )
}

@Composable
fun QuackImageDemo() {
    val context = LocalContext.current
    val toast = rememberToast()

    QuackImage(
        src = remember {
            ContextCompat.getDrawable(
                context, R.drawable.banner_quack_dev,
            )
        },
        size = DpSize(
            width = 400.dp,
            height = 180.dp,
        ),
        badge = {
            QuackRoundCheckBoxDemo()
        },
        badgeSize = DpSize(
            all = 24.dp,
        ),
        rippleEnabled = false,
        onClick = { toast("QuackImage clicked") },
        onLongClick = { toast("QuackImage long clicked") },
    )
}

@Composable
fun QuackSelectableImageTypeTopEndCheckboxDemo() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }

    QuackSelectableImage(
        src = remember {
            ContextCompat.getDrawable(
                context, R.drawable.ic_quack,
            )
        },
        size = DpSize(
            all = 250.dp,
        ),
        isSelected = selected,
        rippleEnabled = false,
        onClick = { selected = !selected },
    )
}

@Composable
fun QuackSelectableImageTypeCheckOverlayDemo() {
    val context = LocalContext.current
    var selected by remember { mutableStateOf(false) }

    QuackSelectableImage(
        src = remember {
            ContextCompat.getDrawable(
                context, R.drawable.ic_quack,
            )
        },
        size = DpSize(
            all = 250.dp,
        ),
        shape = CircleShape,
        isSelected = selected,
        selectableType = QuackSelectableImageType.CheckOverlay,
        rippleEnabled = false,
        onClick = { selected = !selected },
    )
}
