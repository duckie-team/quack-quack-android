/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
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
 * [Paparazzi] 디바이스의 기본 높이
 */
const val DefaultDeviceHeight = 200

// https://github.com/duckie-team/duckie-quack-quack/issues/242
/**
 * [DeviceOption] 를 config 로 받는 [Map]
 *
 * 키 값으론 클래스명이 권장됩니다.
 */
val DevideSizeHolders = mutableMapOf<String, DeviceOption>()

/**
 * [Paparazzi] 디바이스의 사이즈
 *
 * @param screenHeight 디바이스의 높이.
 * 기본값으로 [DefaultDeviceHeight] 로 지정합니다.
 * @param screenWidth 디바이스의 너비
 * @param matchFullWidth 항상 전체 너비를 사용할지 여부
 */
data class DeviceOption(
    val screenHeight: Int = DefaultDeviceHeight,
    val screenWidth: Int? = null,
    val matchFullWidth: Boolean = true,
)

/**
 * 덕키 디자인 시스템의 스냅샷 테스트를 위한 맞춤 설정을 마친
 * [Paparazzi] 객체를 생성합니다.
 *
 * 각각 테스트에서 개별 인스턴스를 사용하기 위해 변수가 아닌
 * 함수를 사용합니다.
 *
 * @param deviceOption 디바이스의 사이즈.
 * 기본값으로 높이만 [DefaultDeviceHeight] 로 지정합니다.
 * @param configId [deviceOption] 를 저장하기 위한 키.
 * 키가 null 이 아닐때만 [deviceOption] 를 저장합니다.
 * 이 값을 **절대** 중복되면 안됩니다.
 * @return 스냅샷을 찍기 위한 준비를 마친 [Paparazzi] 객체
 */
fun paparazzi(
    deviceOption: DeviceOption? = DeviceOption(),
    configId: String? = null,
): Paparazzi {
    if (configId != null) {
        checkNotNull(
            value = deviceOption,
            lazyMessage = { "deviceSize must not be null when configId is not null" },
        )
        DevideSizeHolders[configId] = deviceOption
    }
    return Paparazzi(
        environment = detectEnvironment().copy(
            platformDir = "${androidHome()}/platforms/android-31",
            compileSdkVersion = 31,
        ),
        maxPercentDifference = 0.0,
    )
}

/**
 * [Paparazzi.snapshot] 을 진행할 때 캡처할 컴포저블을 [Box] 로 감싸고
 * 가운데로 정렬하여 캡처합니다. 또한 배경 색상도 흰색으로 지정합니다.
 *
 * @param parameterNames 스냅샷의 이름에 적용될 parameterized test 인자들의 이름
 * @param fontScale 스냅샷에 적용할 폰트 스케일
 * @param configId 초기 [paparazzi] 에서 설정한 [DeviceOption] 를 복원하기 위한 키
 * @param deviceConfig 스냅샷을 찍을 디바이스
 * @param overrideScreenHeight 스냅샷을 찍을 디바이스의 재설정할 높이
 * @param overrideScreenWidth 스냅샷을 찍을 디바이스의 재설정할 너비.
 * 기본값으로 [deviceConfig] 의 너비를 사용합니다.
 * @param matchFullWidth 항상 전체 너비를 사용할지 여부
 * @param content 캡처할 컴포저블
 */
// Needs Paparazzi#gif: https://github.com/cashapp/paparazzi/issues/496
inline fun Paparazzi.boxSnapshot(
    parameterNames: List<String>, // required with parameterized test
    fontScale: Float = 1f,
    configId: String? = null,
    deviceConfig: QuackDeviceConfig,
    overrideScreenHeight: Int? = null,
    overrideScreenWidth: Int? = null,
    matchFullWidth: Boolean = DevideSizeHolders[configId]?.matchFullWidth ?: true,
    crossinline content: @Composable BoxScope.() -> Unit,
) {
    val overrideSize = (
            DevideSizeHolders[configId] ?: DeviceOption(
                screenHeight = DefaultDeviceHeight,
                screenWidth = deviceConfig.value.screenWidth,
            )
            ).let { size ->
            DeviceOption(
                screenHeight = overrideScreenHeight ?: size.screenHeight,
                screenWidth = deviceConfig.value.screenWidth.takeIf { matchFullWidth }
                    ?: overrideScreenWidth
                    ?: size.screenWidth,
            )
        }
    unsafeUpdateConfig(
        deviceConfig = deviceConfig.value.copy(
            screenHeight = overrideSize.screenHeight,
            screenWidth = overrideSize.screenWidth ?: error(
                message = "No screen width",
            ),
        ),
    )
    snapshot(
        name = parameterNames.joinToString(
            separator = ",",
            prefix = "[",
            postfix = "]",
        ),
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
