/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.playground.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import kotlinx.collections.immutable.ImmutableList
import team.duckie.quackquack.playground.theme.PlaygroundTheme

abstract class PlaygroundActivity(
    private val name: String,
    private val usePreviewDialog: Boolean = false,
) : BaseActivity() {
    abstract val items: ImmutableList<Pair<String, @Composable () -> Unit>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                PlaygroundSection(
                    title = name,
                    items = items,
                    usePreviewDialog = usePreviewDialog,
                )
            }
        }
    }
}
