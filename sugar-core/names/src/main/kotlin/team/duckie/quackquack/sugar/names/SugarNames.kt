/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(SugarCompilerApi::class, SugarGeneratorUsage::class)
@file:Suppress(
  "OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION",
  "OPT_IN_MARKER_CAN_ONLY_BE_USED_AS_ANNOTATION_OR_ARGUMENT_IN_OPT_IN",
  "unused",
)

package team.duckie.quackquack.sugar.names

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import org.jetbrains.kotlin.name.FqName
import team.duckie.quackquack.casa.annotation.Casa
import team.duckie.quackquack.casa.annotation.CasaValue
import team.duckie.quackquack.casa.annotation.SugarGeneratorUsage
import team.duckie.quackquack.sugar.material.Imports
import team.duckie.quackquack.sugar.material.SugarCompilerApi
import team.duckie.quackquack.sugar.material.SugarGeneratedFile
import team.duckie.quackquack.sugar.material.SugarName
import team.duckie.quackquack.sugar.material.SugarRefer
import team.duckie.quackquack.sugar.material.SugarToken
import team.duckie.quackquack.sugar.material.Sugarable

val RequiresOptInFqn = RequiresOptIn::class.qualifiedName!!.toFqnClass()

val ComposableFqn = "androidx.compose.runtime.Composable".toFqnClass()
val ComposableCn = ComposableFqn.asString().toCnClass()
val NonRestartableComposableCn = "androidx.compose.runtime.NonRestartableComposable".toCnClass()

const val QuackComponentPrefix = SugarName.PREFIX_NAME

const val SugarDefaultName = SugarName.DEFAULT_NAME
const val SugarTokenName = SugarName.TOKEN_NAME

val CasaCn = Casa::class.qualifiedName!!.toCnClass()
val CasaValueCn = CasaValue::class.asClassName()
val CasaValueFqn = CasaValue::class.qualifiedName!!.toFqnClass()

val SugarCompilerApiCn = SugarCompilerApi::class.asClassName()
val SugarGeneratorUsageCn = SugarGeneratorUsage::class.asClassName()
val SugarGeneratedFileCn = SugarGeneratedFile::class.asClassName()
val SugarGeneratedFileFqn = SugarGeneratedFile::class.qualifiedName!!.toFqnClass()

// sugar 함수에 리플렉션으로 접근 불가
val SugarFqn = "team.duckie.quackquack.sugar.material.sugar".toFqnClass()
val SugarNameFqn = SugarName::class.qualifiedName!!.toFqnClass()
val SugarTokenFqn = SugarToken::class.qualifiedName!!.toFqnClass()
val SugarReferCn = SugarRefer::class.asClassName()
val SugarReferFqn = SugarRefer::class.qualifiedName!!.toFqnClass()

val ImportsFqn = Imports::class.qualifiedName!!.toFqnClass()
val SugarableFqn = Sugarable::class.qualifiedName!!.toFqnClass()

private fun String.toFqnClass() = FqName(this)
private fun String.toCnClass() = ClassName.bestGuess(this)
