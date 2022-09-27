/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackTagItem
import team.duckie.quackquack.ui.component.QuackTagRow

class RowPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackTagRowDemo" to { QuackTagRowDemo() },
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
fun QuackTagRowDemo() {
    QuackTagRow(
        title = "이런 점이 최고였어요",
        items = persistentListOf(
            QuackTagItem(
                isSelected = false,
                text = "친절하고 매너가 좋아요",
            ),
            QuackTagItem(
                isSelected = false,
                text = "답장이 빨라요",
            ),
            QuackTagItem(
                isSelected = false,
                text = "입금을 제때 해줘요",
            ),
        ),
        onClickItem = {},
    )
}
