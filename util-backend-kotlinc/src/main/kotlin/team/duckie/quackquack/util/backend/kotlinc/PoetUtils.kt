/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.kotlinc

import com.squareup.kotlinpoet.FileSpec
import org.jetbrains.kotlin.name.FqName

public fun FileSpec.Builder.addImports(vararg imports: FqName): FileSpec.Builder =
  addImports(imports.asList())

public fun FileSpec.Builder.addImports(imports: List<FqName>): FileSpec.Builder =
  apply {
    imports.forEach { import ->
      addImport(
        packageName = import.parent().asString(),
        import.shortName().asString(),
      )
    }
  }
