/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackLargeDeletableImage
import team.duckie.quackquack.ui.component.QuackSelectableImage
import team.duckie.quackquack.ui.component.QuackSmallDeletableImage
import team.duckie.quackquack.ui.component.QuackTitle1

private const val ImageUrl = "https://picsum.photos/id/237/200/300"

class SelectableImagePlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackSelectableImage" to { QuackSelectableImageDemo() },
        "QuackSmallDeletableImage" to { QuackSmallDeletableImageDemo() },
        "QuackLargeDeletableImage" to { QuackLargeDeletableImageDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "SelectableImage",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackSelectableImageDemo() {

    val isSelected = remember { mutableStateOf(false) }

    QuackSelectableImage(
        isSelected = isSelected.value,
        image = ImageUrl,
        onClick = {
            isSelected.value = !isSelected.value
        },
    )
}

@Composable
fun QuackSmallDeletableImageDemo() {
    val isDeleted = remember { mutableStateOf(false) }
    Box {
        if (isDeleted.value) {
            QuackTitle1(
                text = "이미지 복구하기",
                onClick = {
                    isDeleted.value = false
                },
            )
        } else {
            QuackSmallDeletableImage(
                image = ImageUrl,
                onClick = {
                    isDeleted.value = true
                },
            )
        }
    }
}

@Composable
fun QuackLargeDeletableImageDemo() {
    val isDeleted = remember { mutableStateOf(false) }
    Box {
        if (isDeleted.value) {
            QuackTitle1(
                text = "이미지 복구하기",
                onClick = {
                    isDeleted.value = false
                },
            )
        } else {
            QuackLargeDeletableImage(
                image = ImageUrl,
                onClick = {
                    isDeleted.value = true
                },
            )
        }
    }
}
