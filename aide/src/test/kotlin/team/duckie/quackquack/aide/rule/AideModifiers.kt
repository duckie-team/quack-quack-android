/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.aide.rule

val testAideModifiers: Map<String, List<String>> = run {
  val aide = mutableMapOf<String, List<String>>()

  aide["text"] = listOf("span")
  aide["_span"] = emptyList()
  aide["_longParameters"] = emptyList()
  aide["_onClick"] = emptyList()

  aide
}
