/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName

internal fun createAideMapKtSpec(
    propertyName: String,
    typedValues: TypedValues,
): FileSpec {
    val aideMap = PropertySpec
        .builder(
            name = propertyName,
            type = Map::class.asTypeName().parameterizedBy(
                Type::class.asTypeName(),
                List::class.asTypeName().parameterizedBy(String::class.asTypeName()),
            ),
        )
        .addModifiers(KModifier.INTERNAL)
        .initializer(
            CodeBlock.Builder()
                .addStatement("run {")
                .indent()
                .addStatement("val aide = mutableMapOf<String, List<String>>()")
                .apply {
                    typedValues.forEach { (type, values) ->
                        addStatement("aide[%S] = %L", type, values.toLiteralListString())
                    }
                }
                .addStatement("aide")
                .unindent()
                .addStatement("}")
                .build(),
        )
        .build()

    return FileSpec
        .builder(
            packageName = "",
            fileName = propertyName.replaceFirstChar(Char::titlecase),
        )
        .addProperty(aideMap)
        .build()
}
