/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.impl.kotlin.KSValueParameterImpl
import com.squareup.kotlinpoet.ksp.TypeParameterResolver
import com.squareup.kotlinpoet.ksp.toTypeName
import common.Names.SugarImportFqn
import common.Names.SugarTokenFqn
import kotlin.reflect.KClass

private fun Sequence<KSAnnotation>.singleTypedOrNull(fqn: String): KSAnnotation? {
    return find { annotation ->
        annotation.shortName.getShortName() == fqn.substringAfterLast(".") &&
                annotation.annotationType.resolve().declaration.qualifiedName?.asString() == fqn
    }
}

internal fun KSValueParameter.asCoreSugarParameter(typeParamResolver: TypeParameterResolver): CoreSugarParameter {
    val importArgument = annotations.singleTypedOrNull(SugarImportFqn)?.arguments?.first()
    val importValue = importArgument?.value?.let { imports ->
        @Suppress("UNCHECKED_CAST")
        imports as Array<KClass<*>>
    }

    val sugarTokenArgument = annotations.singleTypedOrNull(SugarTokenFqn)?.arguments?.first()
    val sugarTokenValue = sugarTokenArgument?.value?.let { sugarToken ->
        sugarToken as String
    }

    val defaultValue = (this as KSValueParameterImpl).ktParameter.defaultValue

    return CoreSugarParameter(
        name = name!!,
        type = type.toTypeName(typeParamResolver),
        imports = importValue,
        defaultValue = defaultValue?.children?.mapNotNull { child ->
            child.text
        }?.joinToString(""),
        sugarToken = sugarTokenValue,
    )
}
