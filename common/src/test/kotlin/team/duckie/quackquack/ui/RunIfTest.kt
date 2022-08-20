/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [RunIfTest.kt] created by Ji Sungbin on 22. 8. 21. 오전 1:44
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import org.junit.Test
import team.duckie.quackquack.common.runIf

private const val Empty = ""
private const val ByeWorld = "bye world"

class StringWrapper {
    var value = Empty
}

class RunIfTest {
    @Test
    fun `string changed`() {
        val string = StringWrapper()
        string.runIf(string.value == Empty) {
            value = ByeWorld
            this
        }
        assert(string.value == ByeWorld)
    }

    @Test
    fun `string not changed`() {
        val string = StringWrapper()
        string.runIf(string.value == ByeWorld) {
            value = Empty
            this
        }
        assert(string.value == Empty)
    }
}
