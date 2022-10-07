/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Density
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment

/**
 * QuackQuack 에서 DSL Builder 를 만들 때 사용됩니다.
 */
@DslMarker
private annotation class QuackDsl

/**
 * [Paparazzi] 의 추가 설정을 위한 DSL 객체
 *
 * @param screenHeight 스냅샷의 세로 길이. 기존의 방법이였던
 * 1로 고정하면 스냅샷이 올바르게 찍히지 않아 조정하였습니다.
 * @param screenWidth 스냅샷의 가로 길이. 기본값으론 덕키의 스냅샷 테스트에서
 * 사용하는 디바이스인 PIXEL_5 의 기본 가로 길이를 사용합니다.
 */
data class PaparazziConfig(
    var screenHeight: Int = 200,
    var screenWidth: Int = DeviceConfig.PIXEL_5.screenWidth,
)

/**
 * 덕키 디자인 시스템의 스냅샷 테스트를 위한
 * 맞춤 설정을 마친 [Paparazzi] 객체를 생성합니다.
 *
 * @param config [Paparazzi] 의 추가 설정
 * @return 스냅샷을 찍기 위한 준비를 마친 [Paparazzi] 객체
 */
fun buildPaparazzi(@QuackDsl config: PaparazziConfig.() -> Unit = {}): Paparazzi {
    val paparazziConfig = PaparazziConfig().apply(config)
    return Paparazzi(
        environment = detectEnvironment().copy(
            platformDir = "${androidHome()}/platforms/android-31",
            compileSdkVersion = 31,
        ),
        maxPercentDifference = 0.0,
        deviceConfig = DeviceConfig.PIXEL_5.copy(
            softButtons = false,
            screenHeight = paparazziConfig.screenHeight,
            screenWidth = paparazziConfig.screenWidth,
        ),
    )
}

/**
 * [Paparazzi.snapshot] 을 진행할 때 캡처할 컴포저블을 [Box] 로 감싸고
 * 가운데로 정렬하여 캡처합니다. 또한 배경 색상도 흰색으로 지정합니다.
 *
 * @param name 스냅샷의 추가 명칭
 * @param fontScale 스냅샷에 적용할 폰트 스케일
 * @param content 캡처할 컴포저블
 */
// Needs Paparazzi#gif: https://github.com/cashapp/paparazzi/issues/496
inline fun Paparazzi.boxSnapshot(
    name: String, // required with parameterized test
    fontScale: Float = 1f,
    crossinline content: @Composable BoxScope.() -> Unit,
) {
    snapshot(
        name = name,
    ) {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
            LocalDensity provides Density(
                density = LocalDensity.current.density,
                fontScale = fontScale,
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    ),
                contentAlignment = Alignment.Center,
                content = content,
            )
        }
    }
}
