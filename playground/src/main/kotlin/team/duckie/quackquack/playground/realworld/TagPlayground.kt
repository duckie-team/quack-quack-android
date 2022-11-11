/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackGrayscaleTag
import team.duckie.quackquack.ui.component.QuackIconTag
import team.duckie.quackquack.ui.component.QuackMultiLineTagRow
import team.duckie.quackquack.ui.component.QuackRowTag
import team.duckie.quackquack.ui.component.QuackSingleRowTag
import team.duckie.quackquack.ui.component.QuackTag
import team.duckie.quackquack.ui.icon.QuackIcon

class TagPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackTagDemo" to { QuackTagDemo() },
        "QuackGrayscaleTagDemo" to { QuackGrayscaleTagDemo() },
        "QuackIconTagDemo" to { QuackIconTagDemo() },
        "QuackTagRowDemo" to { QuackTagRowDemo() },
        "QuackTagScrollableRowDemo" to { QuackSingleRowTag() },
        "QuackSingleRowTag" to { QuackSingleRowTag() },
        "QuackSingleRowTagDeletable" to { QuackSingleRowTagDeletable() },
        "QuackMultiTagRow" to { QuackMultiTagRowDemo() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Row",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackTagDemo() {
    var isSelected by remember {
        mutableStateOf(
            value = false,
        )
    }
    QuackTag(
        text = "QuackTag",
        isSelected = isSelected,
        onClick = {
            isSelected = !isSelected
        },
    )
}

@Composable
fun QuackGrayscaleTagDemo() {
    var isSelected by remember {
        mutableStateOf(
            value = false,
        )
    }
    QuackGrayscaleTag(
        text = "QuackGrayscaleTag",
        trailingText = "99+",
        onClick = {
            isSelected = !isSelected
        },
    )
}

@Composable
fun QuackIconTagDemo() {
    var isSelected by remember {
        mutableStateOf(
            value = false,
        )
    }
    QuackIconTag(
        text = "QuackIconTag",
        icon = QuackIcon.Heart,
        isSelected = isSelected,
        onClick = {
            isSelected = !isSelected
        },
        onClickIcon = {
            isSelected = !isSelected
        },
    )
}

@Composable
fun QuackTagRowDemo() {
    val items = remember {
        persistentListOf(
            "친절하고 매너가 좋아요",
            "답장이 빨라요",
            "입금을 제때 해줘요",
            "답장이 느려요",
            "너무 늦은 시간에 연락해요",
            "무리하게 가격을 깎아요",
        )
    }
    val itemsSelection = remember {
        mutableStateListOf(
            elements = Array(
                size = items.size,
                init = { false },
            )
        )
    }

    QuackRowTag(
        title = "이런 점이 최고였어요",
        items = items,
        itemsSelection = itemsSelection,
        onClick = { index ->
            itemsSelection[index] = !itemsSelection[index]
        },
    )
}

@Composable
fun QuackSingleRowTag() {
    val items = remember {
        persistentListOf(
            "친절하고 매너가 좋아요",
            "답장이 빨라요",
            "입금을 제때 해줘요",
            "답장이 느려요",
            "너무 늦은 시간에 연락해요",
            "무리하게 가격을 깎아요",
        )
    }
    val itemsSelection = remember {
        mutableStateListOf(
            elements = Array(
                size = items.size,
                init = { false },
            )
        )
    }
    QuackSingleRowTag(
        title = "이런 점이 최고였어요",
        items = items,
        itemsSelection = itemsSelection,
        onClick = { index ->
            itemsSelection[index] = !itemsSelection[index]
        },
    )
}

@Composable
fun QuackSingleRowTagDeletable() {
    val items = remember {
        mutableStateListOf(
            "친절하고 매너가 좋아요",
            "답장이 빨라요",
            "입금을 제때 해줘요",
            "답장이 느려요",
            "너무 늦은 시간에 연락해요",
            "무리하게 가격을 깎아요",
        )
    }
    QuackSingleRowTag(
        title = "이런 점이 최고였어요",
        items = items,
        itemsSelection = remember(
            key1 = items.size,
        ) {
            List(
                size = items.size,
                init = { false },
            )
        },
        onClick = { index ->
            items.remove(
                element = items[index],
            )
        },
        iconResource = QuackIcon.Close,
    )
}

@Composable
fun QuackMultiTagRowDemo() {

    val list = remember {
        mutableStateOf(
            listOf(
                "마블",
                "덕키",
                "픽사",
                "아바타",
                "탑건",
                "무지개양말",
                "피드내용",
                "추가한태그",
                "태그",
                "피드",
                "다른유저",
                "제목",
            )
        )
    }
    QuackMultiLineTagRow(
        title = "추가한 태그",
        items = list.value,
        icon = QuackIcon.Close,
        onClickIcon = { index ->
            list.value = list.value - list.value[index]
        },
    )

}
