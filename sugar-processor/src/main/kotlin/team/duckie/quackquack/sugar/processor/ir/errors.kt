/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(UnsafeCastFunction::class)

package team.duckie.quackquack.sugar.processor.ir

import org.jetbrains.kotlin.ir.backend.js.utils.asString
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrClassReference
import org.jetbrains.kotlin.ir.expressions.IrVarargElement
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.utils.addToStdlib.UnsafeCastFunction
import org.jetbrains.kotlin.utils.addToStdlib.cast

internal object NotSupportedError {
    internal fun functionalType(name: String?): String {
        return "Functional types are not currently supported due to generic type erasure. " +
                "($name)".getIfGivenIsNotNullOrEmpty(name)
    }
}

internal object SourceError {
    internal fun quackComponentFqnUnavailable(declaration: IrSimpleFunction?): String {
        return "A Quack component was detected, but unable to look up a fully qualified name. " +
                "Is it an anonymous object? " +
                "(${declaration?.name?.asString()})".getIfGivenIsNotNullOrEmpty(declaration)
    }

    internal fun importClazzFqnUnavailable(element: IrVarargElement?): String {
        return "Can't look up the fully qualified name of the class given as `clazz` in `@Imports`. " +
                "Is it an anonymous class? " +
                "(${element?.cast<IrClassReference>()?.type?.asString()})"
                    .getIfGivenIsNotNullOrEmpty(element)
    }

    internal fun quackComponentWithoutSugarToken(fqn: FqName?): String {
        return "A Quack component was detected, but no SugarToken was applied. " +
                "(${fqn?.asString()})".getIfGivenIsNotNullOrEmpty(fqn)
    }

    internal fun multipleSugarTokenIsNotAllowed(declaration: IrSimpleFunction?): String {
        return "A Sugar component can only contain one SugarToken. " +
                "(${declaration?.name?.asString()})".getIfGivenIsNotNullOrEmpty(declaration)
    }

    internal fun sugarNamePrefixIsNotQuack(name: String?): String {
        return "Quack component names must start with `SugarName.PREFIX_NAME " +
                "(= $QuackComponentPrefix)`. " +
                "($name)".getIfGivenIsNotNullOrEmpty(name)
    }

    internal fun sugarNameWithoutTokenName(name: String?): String {
        return "When specifying the sugar component name directly, " +
                "`SugarName.TOKEN_NAME (= $SugarTokenName)` must be used. " +
                "($name)".getIfGivenIsNotNullOrEmpty(name)
    }

    internal fun sugarTokenButNoCompanionObject(name: String?): String {
        return "The SugarToken class must include a companion object. " +
                "See the sugar component creation policy for more information. " +
                "($name)".getIfGivenIsNotNullOrEmpty(name)
    }
}

internal object PoetError {
    internal fun sugarComponentButNoSugarRefer(declaration: IrSimpleFunction?): String {
        return "The SugarRefer for the Sugar component is missing. " +
                "(${declaration?.name?.asString()})".getIfGivenIsNotNullOrEmpty(declaration)
    }
}

internal object SugarVisitError {
    internal fun noMatchedSugarIrData(declaration: IrSimpleFunction?): String {
        return "No SugarIrData was found for the given SugarRefer. " +
                "Please report it in a GitHub Issue. (https://link.duckie.team/quackquack-bug) " +
                "(${declaration?.name?.asString()})".getIfGivenIsNotNullOrEmpty(declaration)
    }
}

internal object SugarTransformError {
    internal fun sugarComponentAndSugarReferHasDifferentParameters(
        sugarIrData: SugarIrData?,
        parameter: IrValueParameter?,
    ): String {
        return "The Sugar component has a parameter that doesn't exist in the SugarRefer. " +
                "(${sugarIrData?.referFqn?.asString()}#${parameter?.name?.asString()})"
                    .getIfGivenIsNotNullOrEmpty(sugarIrData, parameter)
    }
}

private fun String.getIfGivenIsNotNullOrEmpty(vararg given: Any?): String {
    return takeIf { given.all { it != null } }.orEmpty()
}
