/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.ui.util

/**
 * MagicNumber 을 예외적으로 허용할 때 사용하는 어노테이션 입니다.
 *
 * @param because MagicNumber 을 허용하는 이유
 */
@Retention(AnnotationRetention.SOURCE)
internal annotation class AllowMagicNumber(
    val because: String,
)
