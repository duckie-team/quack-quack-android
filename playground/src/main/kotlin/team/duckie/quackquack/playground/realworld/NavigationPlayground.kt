/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground.realworld

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackDropDown
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

class NavigationPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackTopAppBar" to { QuackTopAppBarDemo() },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = "Navigation",
                    items = items,
                )
            }
        }
    }
}

@Composable
fun QuackTopAppBarDemo(){
    val headline = "Headline2"
    val trailingText = "완료"
    Column(
        modifier = Modifier
            .background(QuackColor.Gray3.value)
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ){
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onClickLeadingIcon = {},
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onClickLeadingIcon = {},
            headline = headline,
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.ArrowBack,
            onClickLeadingIcon = {},
            trailingIcon = QuackIcon.More,
            secondTrailingIcon = QuackIcon.Share,
            onClickTrailingIcon = {},
            onClickSecondTrailingIcon = {},
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.Profile,
            trailingIcon = QuackIcon.Filter,
            onClickTrailingIcon = {},
            centerContent = {
                DuckieLogo()
            }
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            trailingText = trailingText,
            onClickTrailingText = {},
            centerContent = {
                DropDown()
            }
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.Profile,
            trailingIcon = QuackIcon.Setting,
            onClickTrailingIcon = {},
            headline = headline,
        )
    }
}

@Composable
private fun DuckieLogo(){
    Box(
        modifier = Modifier.width(100.dp)
    ) {
        QuackImage(image = team.duckie.quackquack.ui.R.drawable.duckie_text_logo)
    }
}

@Composable
private fun DropDown(){
    Row{
        QuackBody1(text = "body1")
        QuackImage(icon = QuackIcon.ArrowDown)
    }
}
