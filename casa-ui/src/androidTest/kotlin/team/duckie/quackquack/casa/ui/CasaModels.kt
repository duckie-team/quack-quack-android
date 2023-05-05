/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.ui

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import team.duckie.quackquack.casa.material.CasaModel

private val textQuackTextCasaModel = CasaModel(
    name = "AwEsOmEQuackText",
    domain = "text",
    kdocDefaultSection = "QuackText KDOC",
    components = persistentListOf(),
)

private val buttonQuackButtonCasaModel = CasaModel(
    name = "AwesomeQuackButton",
    domain = "button",
    kdocDefaultSection = "QuackButton KDOC",
    components = persistentListOf(),
)

private val tagQuackTagCasaModel = CasaModel(
    name = "QuackTag",
    domain = "tag",
    kdocDefaultSection = "QuackTag KDOC",
    components = persistentListOf(),
)

val casaModels: ImmutableList<CasaModel> = persistentListOf(
    textQuackTextCasaModel,
    buttonQuackButtonCasaModel,
    tagQuackTagCasaModel,
)
