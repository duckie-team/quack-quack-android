/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

/**
 * MagicNumber 을 예외적으로 허용할 때 사용하는 어노테이션 입니다.
 *
 * @param because 이 경우에 MagicNumber 을 왜 허용하는지 명시해야 합니다.
 */
@Retention(AnnotationRetention.SOURCE)
internal annotation class AllowMagicNumber(
    @Suppress("unused") val because: String,
)
