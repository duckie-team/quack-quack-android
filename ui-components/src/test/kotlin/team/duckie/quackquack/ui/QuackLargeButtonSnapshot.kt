/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackLargeButtonSnapshot.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:57
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.ui.component.QuackLargeButton

class QuackLargeButtonSnapshot {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
    )

    @Test
    fun `quack large button snapshot`() {
        paparazzi.snapshot {
            QuackLargeButton(
                text = "QuackLargeButton snapshot",
                onClick = {},
            )
        }
    }
}
