/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [PluginEnum.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:53
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.convention

/**
 * 플러그인 오타 방지를 위한 상수 값들 모음
 */
internal object PluginEnum {
    const val AndroidLint = "com.android.lint"
    const val AndroidTest = "com.android.test"
    const val AndroidApplication = "com.android.application"

    const val JavaLibrary = "java-library"
    const val AndroidLibrary = "com.android.library"

    const val PureKotlin = "kotlin"
    const val AndroidKotlin = "kotlin-android"
}
