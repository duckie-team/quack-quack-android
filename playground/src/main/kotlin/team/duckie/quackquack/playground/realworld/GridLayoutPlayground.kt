/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.playground.util.pastelRandom
import team.duckie.quackquack.ui.component.QuackGridLayout

class GridLayoutPlayground : PlaygroundActivity(
    name = "GridLayout",
    usePreviewDialog = true,
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackGridLayoutDemo.name to { QuackGridLayoutDemo() },
    )
}

@Composable
fun QuackGridLayoutDemo() {
    val colors = remember {
        List(
            size = 102, // 100 으로 하면 마지막에 2칸이 남음
            init = { Color.pastelRandom },
        ).toImmutableList()
    }

    QuackGridLayout(
        items = colors,
    ) { _, color ->
        Box(
            modifier = Modifier
                .aspectRatio(
                    ratio = 1f,
                )
                .fillMaxSize()
                .background(
                    color = color,
                ),
        )
    }
}
