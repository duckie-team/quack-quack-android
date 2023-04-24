/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@RequiresOptIn(
    message = "This QuackQuack API is experimental and is likely to change or " +
            "to be removed in the future.",
)
public annotation class ExperimentalQuackQuackApi
