/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("PackageNaming")

package team.duckie.quackquack.ui.util_test

import org.junit.Test
import team.duckie.quackquack.ui.util.runIf

private const val Empty = ""
private const val ByeWorld = "bye world"

class RunIfTest {
    private class StringWrapper {
        var value = Empty
    }

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
