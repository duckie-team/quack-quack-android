/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.name.FqName

internal fun quackComponentFqnUnavailable(declaration: IrSimpleFunction?): String {
    return "A Quack component was detected, but unable to look up a fully qualified name. " +
            "Is it an anonymous object? " +
            "(${declaration?.name?.asString()})".getIfGivenIsNotNull(declaration)
}

internal fun multipleSugarTokenIsNotAllowed(declaration: IrSimpleFunction?): String {
    return "A Sugar component can only contain one SugarToken. " +
            "(${declaration?.name?.asString()})".getIfGivenIsNotNull(declaration)
}

internal fun quackComponentWithoutSugarToken(fqn: FqName?): String {
    return "A Quack component was detected, but no SugarToken was applied. " +
            "(${fqn?.asString()})".getIfGivenIsNotNull(fqn)
}

internal fun sugarNamePrefixIsNotQuack(name: String?): String {
    return "Quack component names must start with `SugarName.PREFIX_NAME " +
            "(= $QuackComponentPrefix)`. " +
            "($name)".getIfGivenIsNotNull(name)
}

internal fun sugarNameWithoutTokenName(name: String?): String {
    return "When specifying the sugar component name directly, " +
            "`SugarName.TOKEN_NAME (= $SugarTokenName)` must be used. " +
            "($name)".getIfGivenIsNotNull(name)
}

internal fun sugarComponentButNoSugarRefer(declaration: IrSimpleFunction?): String {
    return "The SugarRefer for the Sugar component is missing. " +
            "(${declaration?.name?.asString()})".getIfGivenIsNotNull(declaration)
}

internal fun noMatchedSugarIrData(declaration: IrSimpleFunction?): String {
    return "No SugarIrData was found for the given SugarRefer. " +
            "Please report it in a GitHub Issue. (https://link.duckie.team/quackquack-bug) " +
            "(${declaration?.name?.asString()})".getIfGivenIsNotNull(declaration)
}

internal fun sugarComponentAndSugarReferHasDifferentParameters(
    sugarIrData: SugarIrData?,
    parameter: IrValueParameter?,
): String {
    return "The Sugar component has a parameter that doesn't exist in the SugarRefer. " +
            "(${sugarIrData?.referFqn?.asString()}#${parameter?.name?.asString()})"
                .getIfGivenIsNotNull(sugarIrData, parameter)
}

private fun String.getIfGivenIsNotNull(vararg given: Any?): String {
    return takeIf { given.all { it != null } }.orEmpty()
}
