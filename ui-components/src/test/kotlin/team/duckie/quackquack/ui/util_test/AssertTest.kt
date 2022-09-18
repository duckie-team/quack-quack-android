/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("PackageNaming")

package team.duckie.quackquack.ui.util_test

import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import team.duckie.quackquack.ui.util.npe
import team.duckie.quackquack.ui.util.runtimeCheck

private const val ByeWorld = "bye world"

class AssertTest {
    @Test(expected = IllegalStateException::class)
    fun `throw ise with default message`() {
        runtimeCheck(false)
    }

    @Test
    fun `throw ise with custom message`() {
        val exception = assertThrows(
            IllegalStateException::class.java
        ) {
            runtimeCheck(false) { ByeWorld }
        }
        assertTrue(exception.message == ByeWorld)
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
        val exception = assertThrows(
            NullPointerException::class.java
        ) {
            npe { ByeWorld }
        }
        assertTrue(exception.message == ByeWorld)
    }
}
