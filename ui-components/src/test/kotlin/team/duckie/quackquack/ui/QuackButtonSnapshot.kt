/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackLargeButtonSnapshot.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import com.android.ide.common.rendering.api.SessionParams.RenderingMode
import org.junit.Rule
import org.junit.Test

class QuackButtonSnapshot {
    // https://github.com/cashapp/paparazzi/issues/489#issuecomment-1195674603
    // https://github.com/cashapp/paparazzi/issues/383#issuecomment-1086384499
    @get:Rule
    val paparazzi = Paparazzi(
        environment = detectEnvironment().copy(
            platformDir = "${androidHome()}/platforms/android-32",
            compileSdkVersion = 32,
        ),
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            softButtons = false,
            screenHeight = 1,
        ),
        renderingMode = RenderingMode.V_SCROLL,
    )

    @Test
    fun default_material_button() {
        paparazzi.snapshot {
            Button(onClick = {}) {
                Text(text = "Default Material Button!!!")
            }
        }
    }
}
