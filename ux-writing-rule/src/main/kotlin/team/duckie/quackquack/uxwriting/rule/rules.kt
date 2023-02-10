/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package team.duckie.quackquack.uxwriting.rule

/**
 * [잘못된 writing - 올바른 writing - 잘못된 이유]
 *
 * 정해진 writing 규칙 가이드에 맞게 [Triple] 를 생성하기 위한
 * infix 확장 함수 입니다.
 *
 * @param message 잘못된 이유
 * @return [잘못된 writing - 올바른 writing] + 잘못된 이유
 */
private infix fun Pair<String, String>.cause(
    message: String,
) = Triple(
    first = first,
    second = second,
    third = message,
)

object BasicWritingRule {
    val Hello = "Hi" to "Hello" cause "Hi 가 아닌 Hello 로 인사해야 합니다."

    /**
     * 기본에 해당하는 모든 writing 규칙들을 반환합니다.
     *
     * @return [BasicWritingRule] 에 있는 모든 규칙들
     */
    operator fun invoke(): List<Triple<String, String, String>> = listOf(
        Hello,
    )
}
