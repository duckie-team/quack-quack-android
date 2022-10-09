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
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackHighlightHeadLine2

class TypoPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackHighlightHeadLine2" to { QuackHighlightHeadLine2Demo() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "QuackTypo",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackHighlightHeadLine2Demo() {
    val text = "우주사령관님과 거래를 하셨군요!"

    Column {
        QuackHighlightHeadLine2(
            text = text,
            highlightText = "우주사령관",
            onClick = {},
        )
    }
}
