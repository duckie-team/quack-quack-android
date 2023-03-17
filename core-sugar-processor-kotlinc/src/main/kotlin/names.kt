/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import org.jetbrains.kotlin.name.FqName

internal const val QuackComponentPrefix = "Quack"

internal const val ComposableFqn = "androidx.compose.runtime.Composable"

internal const val SugarNameFqn = "team.duckie.quackquack.sugar.annotation.SugarName"
internal const val SugarDefaultName = "<<DEFAULT_NAME>>"
internal const val SugarTokenName = "<<SUGAR_TOKEN>>"

internal const val SugarTokenFqn = "team.duckie.quackquack.sugar.annotation.SugarToken"
internal const val SugarImportsFqn = "team.duckie.quackquack.sugar.annotation.Imports"

internal fun String.toFqnClass() = FqName(this)
