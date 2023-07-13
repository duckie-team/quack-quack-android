/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.material

import kotlin.reflect.KClass

/**
 * sugar component의 이름을 직접 명시합니다. 만약 직접 명시하지 않으면 기본 정책에
 * 맞게 네이밍이 진행됩니다. sugar component의 기본 네이밍 정책은 꽥꽥 웹 문서를 참고하세요.
 *
 * sugar component의 이름을 직접 명시할 때는 다음과 같은 규칙이 보장돼야 합니다.
 *
 * 1. "Quack" *접두사*를 가져야 합니다. "Quack"은 [SugarName.PREFIX_NAME] 상수로
 * 사용할 수 있습니다.
 * 2. sugar token의 적용된 필드명을 나타내는 "<<SUGAR_TOKEN>>"이 무조건 포함돼야 합니다.
 * "<<SUGAR_TOKEN>>"는 [SugarName.TOKEN_NAME] 상수로 사용할 수 있습니다.
 * 3. "<<DEFAULT_NAME>>"이 제공되면 기본 네이밍 정책을 그대로 사용합니다. "<<DEFAULT_NAME>>"는
 * [SugarName.DEFAULT_NAME] 상수로 사용할 수 있습니다.
 *
 * 예를 들어 다음과 같은 컴포넌트가 있습니다.
 *
 * ```
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     val Default = Theme(1)
 *   }
 * }
 *
 * @SugarToken(SugarName.PREFIX_NAME + SugarName.TOKEN_NAME + "Component")
 * @Composable
 * fun QuackAwesome(@SugarToken theme: Theme) {
 *   QuackTheme(theme = theme)
 * }
 * ```
 *
 * 이 컴포넌트는 다음과 같은 sugar가 생성됩니다.
 *
 * ```
 * @SugarRefer("QuackAwesome")
 * @Composable
 * fun QuackDefaultComponent() {
 *   QuackTheme(theme = Theme.Default)
 * }
 * ```
 *
 * @param name 생성할 sugar component의 이름
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarName(val name: String = DEFAULT_NAME) {
  public companion object {
    /** 모든 꽥꽥 컴포넌트의 공통 접두사 */
    public const val PREFIX_NAME: String = "Quack"

    /** sugar component의 기본 이름 */
    public const val DEFAULT_NAME: String = "<<DEFAULT_NAME>>"

    /** sugar component를 생성하는 데 사용된 sugar token 이름 */
    public const val TOKEN_NAME: String = "<<SUGAR_TOKEN>>"
  }
}

/**
 * sugar component에 사용할 sugar token을 나타냅니다.
 * sugar token은 다음과 같은 규칙을 보장해야 합니다.
 *
 * 1. companion object가 있어야 합니다.
 * 2. sugar token의 값들은 companion object 안에 public variable로 정의돼야 합니다.
 *
 * 예를 들어 다음과 같은 sugar token은 유효합니다.
 *
 * ```
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     val Default = Theme(1)
 *     val Dark = Theme(2)
 *     val Light = Theme(3)
 *     val System = Theme(4)
 *   }
 * }
 * ```
 *
 * 다음과 같은 sugar token은 무효합니다.
 *
 * ```
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     fun getDefault() = Theme(1) // function 입니다.
 *     fun Dark() = Theme(2) // function 입니다.
 *     private val Light = Theme(3) // private 입니다.
 *     private val System = Theme(4) // private 입니다.
 *   }
 * }
 *
 * class Flaver { // companion object가 없습니다.
 *   val debug = DebugFlaver()
 *   val release = ReleaseFlaver()
 * }
 * ```
 *
 * 또 다른 예시로, 다음과 같은 컴포넌트가 있습니다.
 *
 * ```
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     val Default = Theme(1)
 *     val Dark = Theme(2)
 *     val Light = Theme(3)
 *     val System = Theme(4)
 *   }
 * }
 *
 * @Composable
 * fun QuackAwesome(@SugarToken theme: Theme) {
 *   QuackTheme(theme = theme)
 * }
 * ```
 *
 * 위 컴포넌트의 sugar는 이렇게 생성됩니다.
 *
 * ```
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackDefaultAwesome() {
 *   QuackTheme(theme = Theme.Default)
 * }
 *
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackDarkAwesome() {
 *   QuackTheme(theme = Theme.Dark)
 * }
 *
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackLightAwesome() {
 *   QuackTheme(theme = Theme.Light)
 * }
 *
 * @Composable
 * @SugarRefer("QuackAwesome")
 * fun QuackSystemAwesome() {
 *   QuackTheme(theme = Theme.System)
 * }
 * ```
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarToken

/**
 * 생성된 sugar component의 원래 대상에 해당하는 fully-qualified name을 나타냅니다.
 * 예를 들어 다음과 같은 컴포넌트가 있습니다.
 *
 * ```
 * package team.duckie.awesome
 *
 * @JvmInline
 * value class Theme(val index: Int) {
 *   companion object {
 *     val Default = Theme(1)
 *   }
 * }
 *
 * @Composable
 * fun QuackAwesome(@SugarToken theme: Theme) {
 *   QuackTheme(theme = theme)
 * }
 * ```
 *
 * 이 컴포넌트의 sugar는 이렇게 생성됩니다.
 *
 * ```
 * @Composable
 * @SugarRefer("team.duckie.awesome.QuackAwesome") // fully-qualified name
 * fun QuackDefaultTest() {
 *   QuackTheme(theme = Theme.Default)
 * }
 * ```
 *
 * 이 정보는 sugar component 처리 과정에서 원래 함수의 IR 정보를 조회하기 위해 추가됩니다.
 * 자세한 정보는 꽥꽥 웹 문서를 참고하세요.
 *
 * 이 어노테이션은 꽥꽥 컴파일러에서만 사용될 목적으로 설계됐습니다.
 * 임의로 사용할 경우 예상치 못한 버그가 발생할 수 있습니다.
 */
@SugarCompilerApi
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarRefer(val fqn: String)

/**
 * 이 컴포넌트의 sugar 생성을 활성화합니다.
 * 이 어노테이션이 붙은 컴포넌트만 sugar component가 생성됩니다.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
public annotation class Sugarable

/**
 * 자동 생성된 sugar component 파일임을 나타냅니다.
 * 이 어노테이션이 붙은 파일만 sugar component 처리가 진행됩니다.
 * 자세한 정보는 꽥꽥 웹 문서를 참고하세요.
 *
 * 이 어노테이션은 꽥꽥 컴파일러에서만 사용될 목적으로 설계됐습니다.
 * 임의로 사용할 경우 예상치 못한 버그가 발생할 수 있습니다.
 */
@SugarCompilerApi
@MustBeDocumented
@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.BINARY)
public annotation class SugarGeneratedFile

/**
 * 자동 생성되는 sugar component 코드에 추가로 import 돼야 하는 클래스를 나타냅니다.
 * 함수의 인자에 적용될 수 있으며, 인자의 타입과 인자의 기본 값 타입이 다를 경우 사용할 수 있습니다.
 *
 * ```
 * // file: flaver.kt
 * package team.duckie.flaver
 *
 * interface Flaver
 *
 *
 *
 * // file: dev.kt
 * package team.duckie.flaver.impl
 *
 * class Dev : Flaver
 *
 *
 *
 * // file: component.kt
 * @Composable
 * fun Component(
 *   @SugarToken theme: Theme,
 *   @Imports(team.duckie.flaver.impl.Dev::class) flaver: Flaver = Dev,
 * )
 * ```
 *
 * @param clazz 추가로 import할 클래스들
 */
@MustBeDocumented
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.BINARY)
public annotation class Imports(vararg val clazz: KClass<*>)
