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
import team.duckie.quackquack.ui.component.QuackSelectableCardImage
import team.duckie.quackquack.ui.icon.QuackIcon

class CardPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackCardImageDemo" to { QuackCardImageDemo() },
        "QuackCardImageRow" to { QuackCardImageRowDemo() },
        "QuackCheckableCardImage" to { QuackCheckableCardImageDemo()}
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
        image = "https://picsum.photos/id/237/200/300",
        size = 150.dp,
        cornerIcon = QuackIcon.ImageEdit,
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
                "https://picsum.photos/id/237/200/300",
            ),
            cornerIcon = QuackIcon.ImageEdit,
        )
        QuackCardImageRow(
            images = persistentListOf(
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
            ),
            cornerIcon = QuackIcon.ImageEdit,
        )
        QuackCardImageRow(
            images = persistentListOf(
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
                "https://picsum.photos/id/237/200/300",
            ),
            cornerIcon = QuackIcon.ImageEdit,
        )

    }
}

@Composable
fun QuackCheckableCardImageDemo(){
    val isSelected = remember { mutableStateOf(false)}
    QuackSelectableCardImage(
        checked = isSelected.value,
        image = "https://picsum.photos/id/237/200/300",
        onClick = {
            isSelected.value = !isSelected.value
        },
    )
}
