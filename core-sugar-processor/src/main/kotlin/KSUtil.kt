/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import Names.SugarImportFqn
import Names.SugarTokenFqn
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.impl.kotlin.KSValueParameterImpl
import com.squareup.kotlinpoet.ksp.TypeParameterResolver
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlin.reflect.KClass

private fun Sequence<KSAnnotation>.singleTypedOrNull(fqn: String): KSAnnotation? {
    return find { annotation ->
        annotation.shortName.getShortName() == fqn.substringAfterLast(".") &&
                annotation.annotationType.resolve().declaration.qualifiedName?.asString() == fqn
    }
}

internal fun KSValueParameter.asCoreSugarParameter(typeParamResolver: TypeParameterResolver): CoreSugarParameter {
    val importArgument = annotations.singleTypedOrNull(SugarImportFqn)?.arguments?.first()
    val importValue = importArgument?.value?.let { import -> import as KClass<*> }

    val sugarTokenArgument = annotations.singleTypedOrNull(SugarTokenFqn)?.arguments?.first()
    val sugarTokenValue = sugarTokenArgument?.value?.let { sugarToken ->
        sugarToken as String
    }

    val defaultValue = (this as KSValueParameterImpl).ktParameter.defaultValue

    return CoreSugarParameter(
        name = name!!,
        type = type.toTypeName(typeParamResolver),
        import = importValue,
        defaultValue = defaultValue,
        sugarToken = sugarTokenValue,
    )
}
