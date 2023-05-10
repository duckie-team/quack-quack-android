/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sampled.annotation

/**
 * 이 어노테이션이 부착된 함수가 샘플 용도로 쓰임을 나타냅니다.
 * 샘플 함수는 `src/sample/kotlin` sourceset에 있어야 합니다.
 *
 * 이에 상응하는 린트 검사가 있어 KDoc의 @sample 태그로 레퍼런스된 함수에
 * 이 어노테이션이 있는지 확인하고, 이 어노테이션이 부착된 함수가 @sample 태그에서
 * 링크되는지 확인합니다.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class Sampled
