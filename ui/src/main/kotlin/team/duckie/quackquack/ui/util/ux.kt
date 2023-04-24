/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.util.DpSize

@SuppressLint("ComposableNaming")
@Composable
@ReadOnlyComposable
public inline fun <T> currentFontScale(content: (fontScale: Float) -> T): T {
    val configration = LocalConfiguration.current
    return content(configration.fontScale)
}

@Stable
public fun Modifier.fontScaleAwareIconSize(baseline: Dp = 24.dp): Modifier {
    return composed {
        currentFontScale { fontScale ->
            // value class 라 인스턴스 생성 비용 적음 (remember 보다 저렴함)
            val defaultSize = DpSize(all = baseline)
            size(defaultSize * fontScale)
        }
    }
}
