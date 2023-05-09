/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

/** 실험적인 API를 사용하기 전 경고를 나타냅니다. */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
  message = "This QuackQuack API is still in an experimental stage " +
    "and may encounter unexpected bugs. Use is at your own risk.",
)
public annotation class ExperimentalQuackQuackApi
