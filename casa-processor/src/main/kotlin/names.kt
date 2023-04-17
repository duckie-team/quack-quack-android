/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.squareup.kotlinpoet.ClassName
import team.duckie.quackquack.casa.annotation.Casa
import team.duckie.quackquack.casa.annotation.CasaValues

internal val CasaFqn = Casa::class.qualifiedName!!
internal val CasaValuesFqn = CasaValues::class.qualifiedName!!
internal val CasaModelCn = ClassName(packageName = "team.duckie.quackquack.casa.material", "CasaModel")
