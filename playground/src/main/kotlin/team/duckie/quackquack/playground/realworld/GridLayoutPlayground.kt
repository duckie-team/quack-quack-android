/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import team.duckie.quackquack.playground.R
import team.duckie.quackquack.playground.base.PlaygroundActivity
import team.duckie.quackquack.ui.component.QuackGridLayout

class GridLayoutPlayground : PlaygroundActivity(
    name = "GridLayout",
) {
    override val items: ImmutableList<Pair<String, @Composable () -> Unit>> = persistentListOf(
        ::QuackGridLayoutDemo.name to { QuackGridLayoutDemo() },
    )
}

@Composable
fun QuackGridLayoutDemo() {
    val items = remember {
        List(
            size = 100,
            init = { R.drawable.ic_quack_dev },
        ).toImmutableList()
    }

    QuackGridLayout(
        items = items,
    ) { _, item ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = item),
            contentDescription = null,
        )
    }
}
