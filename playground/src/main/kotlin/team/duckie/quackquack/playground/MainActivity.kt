/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.playground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.first
import team.duckie.quackquack.playground.base.BaseActivity
import team.duckie.quackquack.playground.base.PlaygroundActivities
import team.duckie.quackquack.playground.realworld.ButtonPlayground
import team.duckie.quackquack.playground.realworld.FabPlayground
import team.duckie.quackquack.playground.realworld.TabPlayground
import team.duckie.quackquack.playground.realworld.TextFieldPlayground
import team.duckie.quackquack.playground.theme.PlaygroundTheme
import team.duckie.quackquack.playground.util.PreferenceConfigs
import team.duckie.quackquack.playground.util.dataStore
import team.duckie.quackquack.ui.animation.QuackAnimationMillis
import team.duckie.quackquack.ui.textstyle.QuackFontScale

class MainActivity : BaseActivity() {
    private val playgroundActivities = persistentListOf(
        TabPlayground::class,
        ButtonPlayground::class,
        TextFieldPlayground::class,
        FabPlayground::class,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(
                key1 = Unit,
            ) {
                applicationContext.dataStore.data.first().let { preference ->
                    QuackAnimationMillis = (
                            preference[PreferenceConfigs.AnimationDurationKey]
                                ?: QuackAnimationMillis
                            ).coerceAtLeast(minimumValue = 0)
                    QuackFontScale = (
                            preference[PreferenceConfigs.FontScaleKey] ?: QuackFontScale
                            ).coerceAtLeast(minimumValue = 0.0)
                }
            }
            PlaygroundTheme {
                PlaygroundActivities(
                    activities = playgroundActivities,
                )
            }
        }
    }
}
