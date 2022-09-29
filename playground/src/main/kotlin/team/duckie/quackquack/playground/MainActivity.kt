/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnusedPrivateMember")

package team.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.first
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.ContentBorder
import team.duckie.quackquack.playground.base.PlaygroundActivities
import team.duckie.quackquack.playground.realworld.*
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.playground.util.PreferenceConfigs
import team.duckie.quackquack.playground.util.dataStore
import team.duckie.quackquack.ui.animation.QuackAnimationMillis
import team.duckie.quackquack.ui.textstyle.QuackFontScale

private val PlaygroundActivities = persistentListOf(
    TabPlayground::class,
    ButtonPlayground::class,
    TextFieldPlayground::class,
    FabPlayground::class,
    TogglePlayground::class,
    TypoPlayground::class,
    CardPlayground::class,
)

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundDemo()
            /*SingleDemo {
                QuackTextFieldErrorStateLiveDemo()
            }*/
            /*MultiDemo {
                QuackTextFieldWithNoDecorationDemo()
                QuackTextFieldWithLeadingDecorationDemo()
                QuackTextFieldWithTrailingDecorationDemo()
                QuackTextFieldWithAllDecorationDemo()
                QuackTextFieldErrorStateDemo()
            }*/
        }
    }
}

/**
 * 하나의 디자인 컴포넌트만 보여주는 데모 컴포저블 입니다.
 *
 * @param content 표시할 단일 디자인 컴포넌트
 */
@Composable
private fun SingleDemo(
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        ContentBorder {
            content()
        }
    }
}

/**
 * 여러 디자인 컴포넌트를 보여주는 데모 컴포저블 입니다.
 *
 * @param content 표시할 디자인 컴포넌트들
 */
@Composable
private fun MultiDemo(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}

/**
 * 모든 디자인 컴포넌트를 리스트 형식으로 보여주는 데모 컴포저블 입니다.
 * [PlaygroundActivities] 에 포함된 액티비티에 있는 모든 디자인 컴포넌트를 보여줍니다.
 */
@Composable
private fun PlaygroundDemo() {
    val context = LocalContext.current.applicationContext
    LaunchedEffect(
        key1 = Unit,
    ) {
        context.dataStore.data.first().let { preference ->
            QuackAnimationMillis = (
                    preference[PreferenceConfigs.AnimationDurationKey]
                        ?: QuackAnimationMillis
                    ).coerceAtLeast(
                    minimumValue = 0,
                )
            QuackFontScale = (
                    preference[PreferenceConfigs.FontScaleKey]
                        ?: QuackFontScale
                    ).coerceAtLeast(
                    minimumValue = 0.0,
                )
        }
    }
    PlaygroundTheme {
        PlaygroundActivities(
            activities = PlaygroundActivities,
        )
    }
}
