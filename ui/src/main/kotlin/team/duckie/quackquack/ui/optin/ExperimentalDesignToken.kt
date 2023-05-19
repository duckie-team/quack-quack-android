/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.optin

/** 실험적인 디자인 토큰을 사용하기 전 경고를 나타냅니다. */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
  message = "This design token is experimental, and its specification is not complete. " +
    "Also, it may change significantly in the future.",
)
public annotation class ExperimentalDesignToken
