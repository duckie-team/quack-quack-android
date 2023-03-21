/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.sugar.material

/**
 * `core-sugar-processor-kotlinc`로 sugar component를 생성할 때 함수의
 * default argument가 있는 인자에 기본 값으로 사용됩니다.
 *
 * ```
 * // original
 * @Composable
 * fun QuackAwesome(
 *     modifier: Modifier = Modifier,
 *     @SugarToken theme: Theme,
 * ) {
 *     QuackTheme(
 *         modifier = Modifier,
 *         theme = theme,
 *     )
 * }
 *
 * // generated
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackDefaultAwesome(
 *     modifier: Modifier = sugar(),
 * ) {
 *     QuackTheme(
 *         modifier = Modifier,
 *         theme = Theme.Default,
 *     )
 * }
 * ```
 *
 * `sugar()`는 컴파일 과정에서 SugarRefer의 default argument로 ir transform 됩니다.
 *
 * ```
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackDefaultAwesome(
 *     modifier: Modifier = sugar(), // after ir transform: `sugar()` -> `Modifier`
 * ) {
 *     QuackTheme(
 *         modifier = Modifier,
 *         theme = Theme.Default,
 *     )
 * }
 * ```
 *
 * 자세한 내용은 `core-sugar-processor-kotlinc` 모듈을 참고하세요.
 *
 * *이 어노테이션은 꽥꽥 컴파일러에서만 사용될 목적으로 설계됐습니다.
 * 임의로 사용할 경우 예상치 못한 버그가 발생할 수 있습니다.*
 */
@SugarCompilerApi
public fun <T> sugar(): T {
    throw NotImplementedError(
        "SugarIrTransform이 진행되지 않았습니다. " +
                "`core-sugar-processor-kotlinc` 모듈이 적용돼 있나요?"
    )
}
