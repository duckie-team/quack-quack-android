/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

/** 꽥꽥의 DSL 범위를 지정합니다. */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@DslMarker
internal annotation class QuackDsl
