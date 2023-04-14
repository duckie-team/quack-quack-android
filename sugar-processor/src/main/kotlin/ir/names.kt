/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(SugarCompilerApi::class)

package ir

import com.squareup.kotlinpoet.ClassName
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.sugar.material.GeneratedFile
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.sugar.material.SugarCompilerApi
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarRefer
import team.duckie.quackquack.sugar.material.SugarToken

internal val ComposableFqn = "androidx.compose.runtime.Composable".toFqnClass()
internal val ComposableCn = ClassName.bestGuess(ComposableFqn.asString())

internal const val QuackComponentPrefix = SugarName.PREFIX_NAME

internal const val SugarDefaultName = SugarName.DEFAULT_NAME
internal const val SugarTokenName = SugarName.TOKEN_NAME

internal val SugarFqn = "team.duckie.quackquack.sugar.material.sugar".toFqnClass()
internal val SugarNameFqn = SugarName::class.qualifiedName!!.toFqnClass()
internal val SugarTokenFqn = SugarToken::class.qualifiedName!!.toFqnClass()
internal val SugarReferFqn = SugarRefer::class.qualifiedName!!.toFqnClass()

internal val ImportsFqn = Imports::class.qualifiedName!!.toFqnClass()
internal val NoSugarFqn = NoSugar::class.qualifiedName!!.toFqnClass()
internal val GeneratedFileFqn = GeneratedFile::class.qualifiedName!!.toFqnClass()

private fun String.toFqnClass() = FqName(this)
