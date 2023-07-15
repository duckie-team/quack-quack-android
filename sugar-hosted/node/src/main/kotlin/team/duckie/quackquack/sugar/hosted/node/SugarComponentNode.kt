/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.sugar.hosted.node

import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.util.backend.kotlinc.toFqnStringOrEmpty

/**
 * 소스 파일의 IR을 방문하면서 수집할 정보들을 관리합니다.
 *
 * @param owner IR이 제공된 함수
 * @param referFqn IR이 제공된 함수의 [fully-qualified name][FqName].
 * [owner]에서 직접 가져오는 방식보다 안전한 방식으로 fqn이 제공됩니다.
 * @param kdocGetter IR이 제공된 함수의 Sugared-KDoc을 계산하는 람다.
 * 사용된 Sugar Token의 리터럴을 람다 인자로 제공해야 합니다.
 * @param sugarName 생성할 sugar component의 네이밍 규칙.
 * [`@SugarToken`][SugarName] 값을 가져옵니다.
 * @param sugarToken 생성할 sugar component의 Sugar Token에 해당하는 [인자][IrValueParameter].
 * [`@SugarToken`][SugarToken]이 달린 인자를 가져옵니다.
 * @param tokenFqExpressions Sugar Token의 expression 모음. 예를 들면 다음과 같습니다.
 *
 * ```
 * package team.duckie.theme
 *
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
 * // ["team.duckie.theme.Theme.Default", "team.duckie.theme.Theme.Dark", "team.duckie.theme.Theme.Light", "team.duckie.theme.Theme.System"]
 * ```
 *
 * @param parameters IR이 제공된 함수의 인자 모음. sugar component 생성에 필요한 정보만 수집합니다.
 * 자세한 수집 정보는 [SugarParameter]를 확인하세요.
 */
data class SugarComponentNode(
  val owner: IrFunction,
  val referFqn: FqName,
  val kdocGetter: (usedTokenLiteral: String) -> String,
  val sugarName: String?,
  val sugarToken: IrValueParameter,
  val tokenFqExpressions: List<String>,
  val parameters: List<SugarParameter>,
  val optins: List<IrConstructorCall>,
) {
  /** [parameters]에서 Sugar Token을 제외한 [요소][SugarParameter]만 불러옵니다. */
  val parametersWithoutToken: List<SugarParameter> =
    parameters.toMutableList().apply { removeIf(SugarParameter::isToken) }

  override fun toString() =
    """
    owner: ${owner.name.asString()}
    referFqn: ${referFqn.asString()}
    kdoc: ${kdocGetter("SugarToken")}
    sugarName: $sugarName
    sugarToken: ${sugarToken.name.asString()}
    tokenExpressions: $tokenFqExpressions
    parameters: ${parameters.joinToString("\n\n", prefix = "\n")}
    optins: ${optins.joinToString(transform = IrConstructorCall::toFqnStringOrEmpty)}
    """.trimIndent()
}
