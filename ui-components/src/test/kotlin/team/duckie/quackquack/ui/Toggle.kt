/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [Toggle.kt] created by Ji Sungbin on 22. 8. 25. 오전 7:37
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("TestFunctionName")

package team.duckie.quackquack.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserColor
import com.airbnb.android.showkase.models.ShowkaseBrowserTypography
import com.android.ide.common.rendering.api.SessionParams
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class Toggle {
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

    private object ColorVariantProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = Showkase.getMetadata().colorList.map(::ColorVariant)
    }

    private object TypographyVariantProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = Showkase.getMetadata().typographyList.map(::TextStyleVariant)
    }

    @Test
    fun QuackToggle(
        @TestParameter(valuesProvider = ColorVariantProvider::class) colorVariant: ColorVariant,
        @TestParameter(valuesProvider = TypographyVariantProvider::class) textStyleVariant: TextStyleVariant,
    ) {
        paparazzi.snapshot(name = "[color:${colorVariant.name}]-[textStyle:${textStyleVariant.name}]") {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = colorVariant.color),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "QuackToggle",
                    style = textStyleVariant.textStyle,
                )
            }
        }
    }

    @Test
    fun QuackToggle2(
        @TestParameter(valuesProvider = ColorVariantProvider::class) colorVariant: ColorVariant,
        @TestParameter(valuesProvider = TypographyVariantProvider::class) textStyleVariant: TextStyleVariant,
    ) {
        paparazzi.snapshot(name = "[color:${colorVariant.name}]-[textStyle:${textStyleVariant.name}]") {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = colorVariant.color),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "QuackToggle2",
                    style = textStyleVariant.textStyle,
                )
            }
        }
    }

    class ColorVariant(value: ShowkaseBrowserColor) {
        val color = value.color
        val name = value.colorName
        override fun toString() = "color"
    }

    class TextStyleVariant(value: ShowkaseBrowserTypography) {
        val textStyle = value.textStyle
        val name = value.typographyName
        override fun toString() = "typography"
    }
}
