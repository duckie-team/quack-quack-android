/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

/**
 * 직접적으로 사용되면 안되는 API 임을 나타내기 위해 사용됩니다.
 */
@RequiresOptIn(
    message = "이 API 는 직접적으로 사용하면 안됩니다. " +
            "이 수단을 가져올 수 있는 다른 API 를 사용해 주세요.",
)
@Retention(AnnotationRetention.BINARY)
internal annotation class DoNotUseDirectly
