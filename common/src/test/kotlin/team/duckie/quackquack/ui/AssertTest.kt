/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [AssertTest.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:55
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui

import org.junit.Test
import team.duckie.quackquack.common.npe
import team.duckie.quackquack.common.runtimeCheck

class AssertTest {
    @Test(expected = IllegalStateException::class)
    fun `throw ise`() {
        runtimeCheck(false)
    }

    @Test
    fun passed() {
        runtimeCheck(true)
    }

    @Test(expected = NullPointerException::class)
    fun `throw npe`() {
        npe()
    }
}
