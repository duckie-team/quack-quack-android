/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.sugar.material

/**
 * `core-sugar-processor-kotlinc` 모듈에서만 사용돼야 함을 나타내는 OptIn 어노테이션입니다.
 */
@MustBeDocumented
@RequiresOptIn(
    message = "This indicates that the feature should only be used in the Sugar Compiler. " +
            "Using it arbitrarily can lead to unexpected bugs.",
)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarCompilerApi
