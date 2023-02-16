/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.core.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test
import team.duckie.quackquack.core.material.QuackColor
import team.duckie.quackquack.core.material.QuackTypography
import team.duckie.quackquack.core.theme.QuackTheme

class TextTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = false,
    )

    @Test
    fun `Modifier#span과 buildAnnotatedString는 같은 텍스트 비쥬얼을 그림`() {
        paparazzi.snapshot {
            QuackTheme {
                Column {
                    QuackText(
                        modifier = Modifier.span(
                            texts = listOf("@성빈", "코루틴"),
                            style = SpanStyle(
                                color = QuackColor.DuckieOrange.value,
                                fontWeight = FontWeight.SemiBold,
                            ),
                        ),
                        text = "@성빈 코루틴 잘 쓰고 싶다.",
                        typography = QuackTypography.Body1,
                    )
                    BasicText(
                        modifier = Modifier.testTag("golden"),
                        text = buildAnnotatedString {
                            val text = "@성빈 코루틴 잘 쓰고 싶다."
                            val style = SpanStyle(
                                color = QuackColor.DuckieOrange.value,
                                fontWeight = FontWeight.SemiBold,
                            )
                            append(text)
                            listOf("@성빈", "코루틴").fastForEach { annotatedText ->
                                val annotatedStartIndex = text.indexOf(annotatedText)
                                if (annotatedStartIndex != -1) {
                                    addStyle(
                                        style = style,
                                        start = annotatedStartIndex,
                                        end = annotatedStartIndex + annotatedText.length,
                                    )
                                }
                            }
                        },
                        style = QuackTypography.Body1.asComposeStyle(),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}