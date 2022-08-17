/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ApplicationConstants.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:52
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

import org.gradle.api.JavaVersion

internal object ApplicationConstants {
    const val minSdk = 21
    const val targetSdk = 33
    const val compileSdk = 33
    const val jvmTarget = "11"
    const val versionCode = 2
    const val versionName = "0.1.1"
    val javaVersion = JavaVersion.VERSION_11
}
