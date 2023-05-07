/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend

public fun String.bestGuessToKotlinPackageName(): String {
    // make sure testable
    // require(contains("src/main/kotlin")) { "The given package is not a Kotlin package." }
    return substringAfterLast("src/main/kotlin/").replace("/", ".")
}

/**
 * 주어진 문자열 리스트를 문자열 [리터럴](https://en.wikipedia.org/wiki/Literal_(computer_programming))로 반환합니다.
 *
 * ```
 * Input: listOf("1", "2", "3")
 * Output: "listOf(\"1\", \"2\", \"3\")"
 * ```
 */
public fun Collection<String>.toLiteralListString(): String {
    return joinToString(
        prefix = "listOf(",
        postfix = ")",
        transform = { "\"$it\"" },
    )
}
