/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package team.duckie.quackquack.sugar.hosted.error

import team.duckie.quackquack.sugar.hosted.names.QuackComponentPrefix
import team.duckie.quackquack.sugar.hosted.names.SugarTokenName

object NotSupportedError {
  fun nestedFunctionalType(name: String?) =
    "Nested functional types are not currently supported due to implementation complexity." +
      " ($name)".getIfGivenIsNotNull(name)
}

object SourceError {
  fun quackComponentFqnUnavailable(name: String?) =
    "A Quack component was detected, but unable to look up a fully qualified name. " +
      "Is it an anonymous object?" + " ($name)".getIfGivenIsNotNull(name)

  fun importClazzFqnUnavailable(name: String?) =
    "Can't look up the fully qualified name of the class given as `clazz` in `@Imports`. " +
      "Is it an anonymous class?" + " ($name)".getIfGivenIsNotNull(name)

  fun quackComponentWithoutSugarToken(name: String?) =
    "A Quack component was detected, but no SugarToken was applied." +
      " ($name)".getIfGivenIsNotNull(name)

  fun multipleSugarTokenIsNotAllowed(name: String?) =
    "A Sugar component can only contain one SugarToken." +
      " ($name)".getIfGivenIsNotNull(name)

  fun sugarNamePrefixIsNotQuack(name: String?) =
    "Quack component names must start with `SugarName.PREFIX_NAME" +
      " (= $QuackComponentPrefix)`." + " ($name)".getIfGivenIsNotNull(name)

  fun sugarNameWithoutTokenName(name: String?) =
    "When specifying the sugar component name directly, " +
      "`SugarName.TOKEN_NAME (= $SugarTokenName)` must be used." +
      " ($name)".getIfGivenIsNotNull(name)

  fun sugarTokenButNoCompanionObject(name: String?) =
    "The SugarToken class must include a companion object. " +
      "See the sugar component creation policy for more information." +
      " ($name)".getIfGivenIsNotNull(name)
}

object SugarVisitError {
  fun noMatchedSugarComponentNode(name: String?) =
    "No SugarComponentNode was found for the given SugarRefer. " +
      "Please report it in a GitHub Issue. (https://link.duckie.team/quackquack-bug)" +
      " ($name)".getIfGivenIsNotNull(name)
}

object SugarTransformError {
  fun sugarComponentAndSugarReferHasDifferentParameters(name: String?) =
    "The Sugar component has a parameter that doesn't exist in the SugarRefer." +
      " ($name)".getIfGivenIsNotNull(name)
}

private fun String.getIfGivenIsNotNull(given: Any?) =
  if (given == null) "" else this
