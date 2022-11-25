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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.rememberToast
import team.duckie.quackquack.ui.component.QuackCircleTag
import team.duckie.quackquack.ui.component.QuackGrayscaleTag
import team.duckie.quackquack.ui.component.QuackRoundTag
import team.duckie.quackquack.ui.icon.QuackIcon

class TagPlayground : PlaygroundActivity(
    name = "Tag",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackGrayscaleTagDemo.name to { QuackGrayscaleTagDemo() },
        ::QuackCircleTagDemo.name to { QuackCircleTagDemo() },
        ::QuackRoundTagDemo.name to { QuackRoundTagDemo() },
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

// TODO: QuackLazyVerticalGridTag 플레이그라운드
// QuackLazyVerticalGridTag 가 정말 꽥꽥에서 제공이 필요할까?
