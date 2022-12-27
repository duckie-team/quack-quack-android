/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackGrayscaleTag
import team.duckie.quackquack.ui.component.QuackLazyVerticalGridTag
import team.duckie.quackquack.ui.component.QuackRoundTag
import team.duckie.quackquack.ui.component.QuackSingeLazyRowTag
import team.duckie.quackquack.ui.component.QuackSmallGrayTag
import team.duckie.quackquack.ui.component.QuackTagType
import team.duckie.quackquack.ui.icon.QuackIcon

class TagPlayground : PlaygroundActivity(name = "Tag") {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackSmallGrayTagDemo.name to { QuackSmallGrayTagDemo() },
        ::QuackGrayscaleTagDemo.name to { QuackGrayscaleTagDemo() },
        ::QuackCircleTagDemo.name to { QuackCircleTagDemo() },
        ::QuackRoundTagDemo.name to { QuackRoundTagDemo() },
        ::QuackLazyVerticalGridTagDemo.name to { QuackLazyVerticalGridTagDemo() },
        ::QuackSingeLazyRowTagDemo.name to { QuackSingeLazyRowTagDemo() },
    )
}

@Composable
fun QuackSmallGrayTagDemo() {
    val toast = rememberToast()

    QuackSmallGrayTag(
        text = "Small",
        onClick = { toast("QuackSmallGrayTag clicked") },
    )
}

@Composable
fun QuackGrayscaleTagDemo() {
    val toast = rememberToast()

    QuackGrayscaleTag(
        text = "GrayscaleTag",
        trailingText = "99+",
        onClick = { toast("GrayscaleTag clicked") },
    )
}

@Composable
fun QuackCircleTagDemo() {
    var selected by remember { mutableStateOf(false) }

    QuackCircleTag(
        text = "CircleTag",
        trailingIcon = QuackIcon.FilledHeart,
        isSelected = selected,
        onClick = { selected = !selected },
    )
}

@Composable
fun QuackRoundTagDemo() {
    var selected by remember { mutableStateOf(false) }

    QuackRoundTag(
        text = "RoundTag",
        isSelected = selected,
        onClick = { selected = !selected },
    )
}

private val LazyTagContentHorizontalPadding = 20.dp
private const val LazyTagItemChunkedSize = 5
private val LazyTagDemoRange = 0..30

@Composable
fun QuackLazyVerticalGridTagDemo() {
    val toast = rememberToast()

    QuackLazyVerticalGridTag(
        contentPadding = PaddingValues(
            horizontal = LazyTagContentHorizontalPadding,
        ),
        title = "Title",
        items = LazyTagDemoRange.map(Int::toString),
        itemSelections = LazyTagDemoRange.map { Random.nextBoolean() },
        itemChunkedSize = LazyTagItemChunkedSize,
        tagType = QuackTagType.Round,
        onClick = { index ->
            toast("Clicked $index index.")
        },
    )
}

@Composable
fun QuackSingeLazyRowTagDemo() {
    val toast = rememberToast()

    QuackSingeLazyRowTag(
        contentPadding = PaddingValues(
            horizontal = LazyTagContentHorizontalPadding,
        ),
        title = "QuackSingeLazyRow",
        items = LazyTagDemoRange.map(Int::toString),
        itemSelections = LazyTagDemoRange.map { Random.nextBoolean() },
        tagType = QuackTagType.Round,
        onClick = { index ->
            toast("Clicked $index index.")
        },
    )
}
