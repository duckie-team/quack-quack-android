/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackCardImage
import team.duckie.quackquack.ui.component.QuackCardImageRow
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSelectableCardImage
import team.duckie.quackquack.ui.icon.QuackIcon

private const val imageUrl = "https://picsum.photos/id/237/200/300"

class CardPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackCardImageDemo" to { QuackCardImageDemo() },
        "QuackCardImageRow" to { QuackCardImageRowDemo() },
        "QuackCheckableCardImage" to { QuackCheckableCardImageDemo() }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "QuackCardImage",
                    items = items,
                )
            }
        }
    }
}


@Composable
fun QuackCardImageDemo() {
    QuackCardImage(
        image = imageUrl,
        size = 150.dp,
        cornerIcon = {
            QuackImageEdit()
        },
    )
}

@Composable
fun QuackCardImageRowDemo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp
        ),
    ) {
        QuackCardImageRow(
            images = persistentListOf(
                imageUrl,
            ),
            cornerIcon = {
                QuackImageEdit()
            },
        )
        QuackCardImageRow(
            images = persistentListOf(
                imageUrl,
                imageUrl,
            ),
            cornerIcon = {
                QuackImageEdit()
            },
        )
        QuackCardImageRow(
            images = persistentListOf(
                imageUrl,
                imageUrl,
                imageUrl,
            ),
            cornerIcon = {
                QuackImageEdit()
            },
        )

    }
}

@Composable
fun QuackImageEdit() {
    QuackImage(
        icon = QuackIcon.ImageEdit
    )
}

@Composable
fun QuackCheckableCardImageDemo() {
    val isSelected = remember { mutableStateOf(false) }
    QuackSelectableCardImage(
        checked = isSelected.value,
        image = imageUrl,
        onClick = {
            isSelected.value = !isSelected.value
        },
    )
}
