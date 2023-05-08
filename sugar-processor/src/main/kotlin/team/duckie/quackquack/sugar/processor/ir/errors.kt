/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.sugar.processor.ir

internal object NotSupportedError {
  internal fun nestedFunctionalType(name: String?): String {
    return "Nested functional types are not currently supported due to implementation complexity." +
      " ($name)".getIfGivenIsNotNull(name)
  }
}

internal object SourceError {
  internal fun quackComponentFqnUnavailable(name: String?): String {
    return "A Quack component was detected, but unable to look up a fully qualified name. " +
      "Is it an anonymous object?" + " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun importClazzFqnUnavailable(name: String?): String {
    return "Can't look up the fully qualified name of the class given as `clazz` in `@Imports`. " +
      "Is it an anonymous class?" + " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun quackComponentWithoutSugarToken(name: String?): String {
    return "A Quack component was detected, but no SugarToken was applied." +
      " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun multipleSugarTokenIsNotAllowed(name: String?): String {
    return "A Sugar component can only contain one SugarToken." +
      " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun sugarNamePrefixIsNotQuack(name: String?): String {
    return "Quack component names must start with `SugarName.PREFIX_NAME" +
      " (= $QuackComponentPrefix)`." + " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun sugarNameWithoutTokenName(name: String?): String {
    return "When specifying the sugar component name directly, " +
      "`SugarName.TOKEN_NAME (= $SugarTokenName)` must be used." +
      " ($name)".getIfGivenIsNotNull(name)
  }

  internal fun sugarTokenButNoCompanionObject(name: String?): String {
    return "The SugarToken class must include a companion object. " +
      "See the sugar component creation policy for more information." +
      " ($name)".getIfGivenIsNotNull(name)
  }
}

internal object PoetError {
  internal fun sugarComponentButNoSugarRefer(name: String?): String {
    return "The SugarRefer for the Sugar component is missing." +
      " ($name)".getIfGivenIsNotNull(name)
  }
}

internal object SugarVisitError {
  internal fun noMatchedSugarIrData(name: String?): String {
    return "No SugarIrData was found for the given SugarRefer. " +
      "Please report it in a GitHub Issue. (https://link.duckie.team/quackquack-bug)" +
      " ($name)".getIfGivenIsNotNull(name)
  }
}

internal object SugarTransformError {
  internal fun sugarComponentAndSugarReferHasDifferentParameters(name: String?): String {
    return "The Sugar component has a parameter that doesn't exist in the SugarRefer." +
      " ($name)".getIfGivenIsNotNull(name)
  }
}

private fun String.getIfGivenIsNotNull(given: Any?) =
  if (given == null) "" else this
