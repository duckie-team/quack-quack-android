/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package team.duckie.quackquack.core.util

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

private const val Empty = ""
private const val ByeWorld = "bye world"

class RunIfTest {
    private class StringWrapper(var value: String)

    @Test
    fun `string changed`() {
        val string = StringWrapper(Empty)

        string.runIf(string.value == Empty) {
            value = ByeWorld
            this
        }

        expectThat(string.value).isEqualTo(ByeWorld)
    }

    @Test
    fun `string not changed`() {
        val string = StringWrapper(ByeWorld)

        string.runIf(string.value == Empty) {
            value = ByeWorld
            this
        }

        expectThat(string.value).isEqualTo(ByeWorld)
    }
}
