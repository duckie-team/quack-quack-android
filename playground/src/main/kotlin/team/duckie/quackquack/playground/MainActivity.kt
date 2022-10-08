/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnusedPrivateMember")

package team.duckie.quackquack.playground

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.first
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.ContentBorder
import team.duckie.quackquack.playground.base.PlaygroundActivities
import team.duckie.quackquack.playground.base.fontScale
import team.duckie.quackquack.playground.base.showComponentBounds
import team.duckie.quackquack.playground.realworld.ButtonPlayground
import team.duckie.quackquack.playground.realworld.FabPlayground
import team.duckie.quackquack.playground.realworld.TabPlayground
import team.duckie.quackquack.playground.realworld.TagPlayground
import team.duckie.quackquack.playground.realworld.TextFieldPlayground
import team.duckie.quackquack.playground.realworld.TogglePlayground
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.playground.util.PreferenceConfigs
import team.duckie.quackquack.playground.util.dataStore
import team.duckie.quackquack.playground.util.verticalInsetsPadding
import team.duckie.quackquack.ui.animation.QuackAnimationMillis

private const val DefaultSplashScreenExitAnimationDurationMillis = 200L
private val PlaygroundActivities = listOf(
    TabPlayground::class,
    ButtonPlayground::class,
    TextFieldPlayground::class,
    FabPlayground::class,
    TogglePlayground::class,
    TagPlayground::class,
).sortedBy { playgroundActivity ->
    playgroundActivity.simpleName
}.toPersistentList()

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            PlaygroundDemo()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ALPHA,
                    1f,
                    0f,
                ).run {
                    interpolator = AnticipateInterpolator()
                    duration = DefaultSplashScreenExitAnimationDurationMillis
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
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
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            )
            .verticalInsetsPadding(),
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
        modifier = Modifier
            .fillMaxSize()
            .verticalInsetsPadding(),
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
            fontScale = (
                    preference[PreferenceConfigs.FontScaleKey]
                        ?: 1f
                    ).coerceAtLeast(
                    minimumValue = 1f,
                )
            showComponentBounds =
                preference[PreferenceConfigs.ShowComponentBounds] ?: showComponentBounds
        }
    }
    PlaygroundTheme {
        PlaygroundActivities(
            activities = PlaygroundActivities,
        )
    }
}
