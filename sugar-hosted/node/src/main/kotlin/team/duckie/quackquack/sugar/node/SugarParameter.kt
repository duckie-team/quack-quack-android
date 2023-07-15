/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.node

import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.util.dumpKotlinLike
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.names.ComposableFqn

/**
 * [IrValueParameter]에서 sugar component 생성에 필요한 정보를 관리합니다.
 *
 * @param name 인자의 이름
 * @param type 인자의 타입
 * @param isToken 인자가 [Sugar Token][SugarToken]인지 여부
 * @param isComposable 인자에 [`@Composable`][ComposableFqn] 어노테이션이 달려있는지
 * 여부
 * @param imports [type] 외에 추가로 import가 필요한 클래스의 [fully-qualified name][FqName]으로
 * 구성된 목록. 자세한 정보는 [`@Imports`][Imports] 어노테이션을 확인하세요.
 * @param casaValueLiteral 만약 인자에 [`@CasaValue`][CasaValue] 어노테이션이 달려있다면
 * [CasaValue.literal]로 제공된 값
 * @param defaultValue 인자의 기본 값
 */
data class SugarParameter(
  val owner: IrFunction,
  val name: Name,
  val type: IrType,
  val isToken: Boolean,
  val isComposable: Boolean,
  val imports: List<FqName>,
  val casaValueLiteral: String?,
  val defaultValue: IrExpressionBody?,
) {
  override fun toString() =
    """
    owner: ${owner.name.asString()}
    name: ${name.asString()}
    type: ${type.asString()}
    isToken: $isToken
    isComposable: $isComposable
    imports: ${imports.joinToString(transform = FqName::asString)}
    casaValueLiteral: $casaValueLiteral
    defaultValue: ${defaultValue?.dumpKotlinLike()}
    """.trimIndent()
}
