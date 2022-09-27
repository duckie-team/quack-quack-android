/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import org.gradle.api.JavaVersion

/**
 * 그레이들 설정에 사용될 기본 상수 값들 모음
 */
internal object ApplicationConstants {
    const val minSdk = 23
    const val targetSdk = 33
    const val compileSdk = 33
    const val jvmTarget = "11"
    const val versionCode = 7
    const val versionName = "0.1.7"
    val javaVersion = JavaVersion.VERSION_11
}
