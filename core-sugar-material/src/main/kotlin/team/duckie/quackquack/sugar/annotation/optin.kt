/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.sugar.annotation

// TODO: 문서 제공
@MustBeDocumented
@RequiresOptIn(
    message = "This indicates that the feature should only be used in the Sugar Compiler. " +
            "Using it arbitrarily can lead to unexpected bugs.",
)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarCompilerApi
