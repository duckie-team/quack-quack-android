/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MaterialSnapshot.kt] created by Ji Sungbin on 22. 8. 23. 오전 12:03
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserColor
import com.android.ide.common.rendering.api.SessionParams
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ColoringSnapshot {
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
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
        maxPercentDifference = 0.0,
    )

    private object PreviewProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues(): List<ShowkaseColorComponent> =
            Showkase.getMetadata().colorList.map(::ShowkaseColorComponent)
    }

    @Test
    fun `coloring boxes`(
        @TestParameter(valuesProvider = PreviewProvider::class) componentColor: ShowkaseColorComponent,
    ) {
        paparazzi.snapshot {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(color = componentColor.value),
            )
        }
    }
}

class ShowkaseColorComponent(
    private val showkaseColor: ShowkaseBrowserColor,
) {
    val value = showkaseColor.color
    override fun toString() = "${showkaseColor.colorGroup}_${showkaseColor.colorName}"
}
