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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.component.QuackDropDown
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackSimpleLabel
import team.duckie.quackquack.ui.component.QuackTitle2
import team.duckie.quackquack.ui.icon.QuackIcon

class EtcPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackSimpleLabel" to { QuackSimpleLabelDemo() },
        "QuackDropDown " to { QuackDropDownDemo() },
        "Modifier 적용한 레이아웃" to { QuackModifierDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Etc",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackSimpleLabelDemo() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        ),
    ) {
        QuackSimpleLabel(
            text = "거래완료",
            active = false,
        )
        QuackSimpleLabel(
            text = "예약중",
            active = true,
        )
    }
}

@Composable
fun QuackDropDownDemo() {
    QuackDropDown(
        title = "판매중",
        onClick = {},
    )
}

@Composable
fun QuackModifierDemo(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = 36.dp,
            ),
    ){
        QuackTitle2(
            text = "왼쪽에 배치되야함",
        )
        QuackImage(
            src = QuackIcon.Close,
            modifier = Modifier.align(
                alignment = Alignment.CenterEnd,
            ),
        )
        QuackTitle2(
            text = "오른쪽에 배치되야함",
            modifier = Modifier.align(
                alignment = Alignment.CenterEnd,
            ),
        )
    }
}