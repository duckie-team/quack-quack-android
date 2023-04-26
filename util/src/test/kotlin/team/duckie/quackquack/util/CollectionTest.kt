/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.withNotNull

class CollectionTest : StringSpec() {
    init {
        "fastFirstIsInstanceOrNull로 반환된 요소는 T로 캐스팅 가능함" {
            val elements = listOf(1, 'Q', Float.Companion, "4", Any())
            val stringElement = elements.fastFirstIsInstanceOrNull<String>()

            expectThat(stringElement).withNotNull { isA<String>() }
        }

        "fastFirstIsInstanceOrNull가 null를 반환하면 주어진 리스트에 존재하지 않음" {
            val elements = listOf(1, 'Q', Float.Companion, "4", Any())
            val stringElement = elements.fastFirstIsInstanceOrNull<Double>()

            stringElement.shouldBeNull()
        }
    }
}
