/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.util_test

import org.junit.Assert
import org.junit.Test
import team.duckie.quackquack.ui.util.npe
import team.duckie.quackquack.ui.util.runtimeCheck

private const val ByeWorld = "bye world"

class AssertionTest {
    @Test(expected = IllegalStateException::class)
    fun `throw ise with default message`() {
        runtimeCheck(false)
    }

    @Test
    fun `throw ise with custom message`() {
        val exception = Assert.assertThrows(
            IllegalStateException::class.java
        ) {
            runtimeCheck(false) { ByeWorld }
        }
        Assert.assertTrue(exception.message == ByeWorld)
    }

    @Test
    fun passed() {
        runtimeCheck(true)
    }

    @Test(expected = NullPointerException::class)
    fun `throw npe with default message`() {
        npe()
    }

    @Test
    fun `throw npe with custom message`() {
        val exception = Assert.assertThrows(
            NullPointerException::class.java
        ) {
            npe { ByeWorld }
        }
        Assert.assertTrue(exception.message == ByeWorld)
    }
}
