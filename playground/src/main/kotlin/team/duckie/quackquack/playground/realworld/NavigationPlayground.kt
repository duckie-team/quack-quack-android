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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundSection
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.ui.color.QuackColor
import team.duckie.quackquack.ui.component.QuackBody1
import team.duckie.quackquack.ui.component.QuackBottomNavigation
import team.duckie.quackquack.ui.component.QuackImage
import team.duckie.quackquack.ui.component.QuackTopAppBar
import team.duckie.quackquack.ui.icon.QuackIcon

class NavigationPlayground : BaseActivity() {
    @Suppress("RemoveExplicitTypeArguments")
    private val items = persistentListOf<Pair<String, @Composable () -> Unit>>(
        "QuackTopAppBar" to { QuackTopAppBarDemo() },
        "QuackBottomNavigation" to { QuackBottomNavigationDemo() },
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
fun QuackTopAppBarDemo() {
    val headline = "Headline2"
    val trailingText = "완료"
    Column(
        modifier = Modifier
            .background(
                color = QuackColor.Gray3.composeColor,
            )
            .fillMaxSize()
            .padding(
                top = 100.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 5.dp,
        ),
    ) {
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
            },
        )
        QuackTopAppBar(
            leadingIcon = QuackIcon.Close,
            trailingText = trailingText,
            onClickTrailingText = {},
            centerContent = {
                DropDown()
            },
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
private fun DuckieLogo() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        QuackImage(
            src = team.duckie.quackquack.ui.R.drawable.duckie_text_logo,
            overrideSize = DpSize(
                width = 72.dp,
                height = 24.dp,
            )
        )
    }
}

@Composable
private fun DropDown() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuackBody1(
                text = "body1",
            )
            QuackImage(
                src = QuackIcon.ArrowDown,
                overrideSize = DpSize(
                    width = 24.dp,
                    height = 24.dp,
                ),
            )
        }
    }
}

@Composable
fun QuackBottomNavigationDemo() {
    val selectedIndex = remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .background(
                color = QuackColor.Gray3.composeColor,
            )
            .fillMaxSize()
            .padding(
                top = 100.dp,
            ),
    ) {
        QuackBottomNavigation(
            selectedIndex = selectedIndex.value,
            onClick = { index ->
                selectedIndex.value = index
            },
        )
    }
}
