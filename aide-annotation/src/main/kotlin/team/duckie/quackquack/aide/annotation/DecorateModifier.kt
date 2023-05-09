/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.aide.annotation

/**
 * 해당 컴포넌트 도메인에서 데코레이터로 사용 가능한 Modifier임을 나타냅니다.
 *
 * 예를 들어 `text.kt` 파일에 다음과 같은 코드가 있습니다.
 *
 * ```
 * @Composable
 * fun Text(
 * modifier: Modifier = Modifier,
 * text: String,
 * ) {
 * BasicText(modifier = modifier, text = text)
 * }
 *
 * @DecorateModifier
 * fun Modifier.highlight(text: String, color: Color): Modifier {
 * // ... awesome code
 * }
 * ```
 *
 * 위와 같은 경우에 `Text` 컴포넌트는 데코레이션 Modifier로 `Modifier.highlight`만 허용됩니다.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
public annotation class DecorateModifier
