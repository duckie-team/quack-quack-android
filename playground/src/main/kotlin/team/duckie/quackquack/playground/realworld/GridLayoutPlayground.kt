/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Collections
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackCardImage
import team.duckie.quackquack.ui.component.QuackIconToggle
import team.duckie.quackquack.ui.component.QuackSimpleGridLayout
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

private const val ImageUrl = "https://picsum.photos/id/237/200/300"

class GridLayoutPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackGridLayout" to { QuackGridLayoutDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Tab",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackGridLayoutDemo() {
    QuackSimpleGridLayout(
        columns = 3,
        items = FavoriteItem.makeMockData(),
        itemContent = { _, item ->
            DuckieFavoriteItem(
                item = item,
            )
        },
        horizontalSpace = 8.dp,
        verticalSpace = 14.dp,
    )
}

@Composable
fun DuckieFavoriteItem(
    item: FavoriteItem,
) {

    val checked = remember { mutableStateOf(true) }
    Column {
        QuackCardImage(
            image = ImageUrl,
            cornerIcon = {
                QuackIconToggle(
                    checkedIcon = QuackIcon.FilledHeart,
                    uncheckedIcon = QuackIcon.WhiteHeart,
                    checked = checked.value,
                ) {
                    checked.value = !checked.value
                }
            },
        )
        Spacer(
            modifier = Modifier.height(
                height = 8.dp
            )
        )
        QuackBody1(
            text = item.title,
        )
        Spacer(
            modifier = Modifier.height(
                height = 2.dp
            )
        )
        QuackTitle2(
            text = item.price,
        )
    }
}

data class FavoriteItem(
    val title: String = "덕딜 어쩌구 제목이 길면 뭔가 달라지는 것 같은 느낌적인 느낌",
    val price: String = "69000원",
) {
    companion object {
        fun makeMockData(count: Int = 10) =
            Collections.nCopies(count, FavoriteItem()).toPersistentList()
    }
}
