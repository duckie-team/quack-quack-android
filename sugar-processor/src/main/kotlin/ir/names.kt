/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(SugarCompilerApi::class, SugarGeneratorUsage::class)
@file:Suppress("OPT_IN_MARKER_CAN_ONLY_BE_USED_AS_ANNOTATION_OR_ARGUMENT_IN_OPT_IN")

package ir

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.casa.annotation.Casa
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.casa.annotation.SugarGeneratorUsage
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.sugar.material.SugarCompilerApi
import team.duckie.quackquack.sugar.material.SugarGeneratedFile
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarRefer
import team.duckie.quackquack.sugar.material.SugarToken

internal val ComposableFqn = "androidx.compose.runtime.Composable".toFqnClass()
internal val ComposableCn = ClassName.bestGuess(ComposableFqn.asString())

internal const val QuackComponentPrefix = SugarName.PREFIX_NAME

internal const val SugarDefaultName = SugarName.DEFAULT_NAME
internal const val SugarTokenName = SugarName.TOKEN_NAME

internal val CasaCn = ClassName.bestGuess(Casa::class.qualifiedName!!)
internal val CasaValueCn = CasaValue::class.asClassName()
internal val CasaValueFqn = CasaValue::class.qualifiedName!!.toFqnClass()

internal val SugarCompilerApiCn = SugarCompilerApi::class.asClassName()
internal val SugarGeneratorUsageCn = SugarGeneratorUsage::class.asClassName()
internal val SugarGeneratedFileCn = SugarGeneratedFile::class.asClassName()
internal val SugarGeneratedFileFqn = SugarGeneratedFile::class.qualifiedName!!.toFqnClass()

// sugar 함수에 리플렉션으로 접근 불가
internal val SugarFqn = "team.duckie.quackquack.sugar.material.sugar".toFqnClass()
internal val SugarNameFqn = SugarName::class.qualifiedName!!.toFqnClass()
internal val SugarTokenFqn = SugarToken::class.qualifiedName!!.toFqnClass()
internal val SugarReferCn = SugarRefer::class.asClassName()
internal val SugarReferFqn = SugarRefer::class.qualifiedName!!.toFqnClass()

internal val ImportsFqn = Imports::class.qualifiedName!!.toFqnClass()
internal val NoSugarFqn = NoSugar::class.qualifiedName!!.toFqnClass()

private fun String.toFqnClass() = FqName(this)
