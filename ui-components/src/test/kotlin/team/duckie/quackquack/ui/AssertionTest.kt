/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "KDocFields",
    "PackageNaming",
)

package team.duckie.quackquack.ui

import org.junit.Assert
import org.junit.Test
import team.duckie.quackquack.ui.util.npe
import team.duckie.quackquack.ui.util.runtimeCheck

private const val ByeWorld = "bye world"

public class AssertionTest {
    @Test(expected = IllegalStateException::class)
    public fun `throw ise with default message`() {
        runtimeCheck(false)
    }

    @Test
    public fun `throw ise with custom message`() {
        val exception = Assert.assertThrows(
            IllegalStateException::class.java
        ) {
            runtimeCheck(false) { ByeWorld }
        }
        Assert.assertTrue(exception.message == ByeWorld)
    }

    @Test
    public fun passed() {
        runtimeCheck(true)
    }

    @Test(expected = NullPointerException::class)
    public fun `throw npe with default message`() {
        npe()
    }

    @Test
    public fun `throw npe with custom message`() {
        val exception = Assert.assertThrows(
            NullPointerException::class.java
        ) {
            npe { ByeWorld }
        }
        Assert.assertTrue(exception.message == ByeWorld)
    }
}
