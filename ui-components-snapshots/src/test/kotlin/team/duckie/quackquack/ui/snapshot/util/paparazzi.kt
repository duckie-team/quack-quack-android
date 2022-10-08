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
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import team.duckie.quackquack.ui.snapshot.provider.QuackDeviceConfig

/**
 * 덕키 디자인 시스템의 스냅샷 테스트를 위한 맞춤 설정을 마친
 * [Paparazzi] 객체를 생성합니다.
 *
 * 각각 테스트에서 개별 인스턴스를 사용하기 위해 변수가 아닌
 * 함수를 사용합니다.
 *
 * @return 스냅샷을 찍기 위한 준비를 마친 [Paparazzi] 객체
 */
fun paparazzi() = Paparazzi(
    environment = detectEnvironment().copy(
        platformDir = "${androidHome()}/platforms/android-31",
        compileSdkVersion = 31,
    ),
    maxPercentDifference = 0.0,
    deviceConfig = QuackDeviceConfig.PIXEL_5.value,
)

/**
 * [Paparazzi.snapshot] 을 진행할 때 캡처할 컴포저블을 [Box] 로 감싸고
 * 가운데로 정렬하여 캡처합니다. 또한 배경 색상도 흰색으로 지정합니다.
 *
 * @param name 스냅샷의 추가 명칭
 * @param fontScale 스냅샷에 적용할 폰트 스케일
 * @param deviceConfig 스냅샷을 찍을 디바이스
 * @param content 캡처할 컴포저블
 */
// Needs Paparazzi#gif: https://github.com/cashapp/paparazzi/issues/496
inline fun Paparazzi.boxSnapshot(
    name: String, // required with parameterized test
    fontScale: Float = 1f,
    deviceConfig: QuackDeviceConfig,
    screenHeight: Int = 200,
    screenWidth: Int = deviceConfig.value.screenWidth,
    crossinline content: @Composable BoxScope.() -> Unit,
) {
    unsafeUpdateConfig(
        deviceConfig = deviceConfig.value.copy(
            screenHeight = screenHeight,
            screenWidth = screenWidth,
        ),
    )
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
