/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.provider

import app.cash.paparazzi.DeviceConfig

enum class QuackDeviceConfig(
    val value: DeviceConfig,
) {
    PIXEL_5(
        value = DeviceConfig.PIXEL_5.copy(
            softButtons = false,
        ),
    ) {
        override fun toString() = "Normal"
    },
    PIXEL_C(
        value = DeviceConfig.PIXEL_C.copy(
            // tablet
            softButtons = false,
        ),
    ) {
        override fun toString() = "Tablet"
    },
}
