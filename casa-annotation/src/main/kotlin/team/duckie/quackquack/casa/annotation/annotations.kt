/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.casa.annotation

/**
 * `CasaModel`에 포함할 컴포넌트임을 나타냅니다.
 *
 * @throws IllegalStateException 컴포넌트 인자가 NonNull에 기본값이 없고 [CasaValue]도
 * 없을 때 [IllegalStateException]이 발생합니다.
 */
@SugarGeneratorUsage
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class Casa

/**
 * casa component로 나타낼 때 기본값이 없는 인자에 사용할 값을 나타냅니다.
 *
 * 예를 들어 아래와 같은 컴포넌트가 있습니다.
 *
 * ```
 * @Composable
 * fun Awesome(who: String) {}
 * ```
 *
 * 위 `Awesome` 컴포넌트의 `who` 인자는 기본값이 없으므로 [CasaValue]로
 * `who` 인자의 값을 명시해야 합니다.
 *
 * ```
 * @Composable
 * fun Awesome(@CasaValue("\"DUCKIE TEAM\"") who: String) {}
 * ```
 *
 * @throws IllegalArgumentException 하나의 인자에 2개 이상의 [CasaValue]가 사용됐을 때
 * [IllegalArgumentException]이 발생합니다.
 *
 * @param literal casa component에 사용할 인자 값의 *리터럴*
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
public annotation class CasaValue(val literal: String)
