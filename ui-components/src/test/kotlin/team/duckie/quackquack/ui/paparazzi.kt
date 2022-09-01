/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [paparazzi.kt] created by Ji Sungbin on 22. 8. 31. 오후 6:24
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import com.android.ide.common.rendering.api.SessionParams.RenderingMode

fun paparazzi() = Paparazzi(
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
