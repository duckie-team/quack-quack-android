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
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.playground.theme.PlaygroundTheme

class MainActivity : BaseActivity() {
    private val playgroundActivities = persistentListOf(
        TabPlayground::class,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundActivities(
                    activities = playgroundActivities,
                )
            }
        }
    }
}
