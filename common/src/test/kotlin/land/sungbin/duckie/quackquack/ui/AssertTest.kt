package land.sungbin.duckie.quackquack.ui

import land.sungbin.duckie.quackquack.common.requireNonNull
import land.sungbin.duckie.quackquack.common.runtimeCheck
import org.junit.Test

import org.junit.Assert.*

class AssertTest {
    @Suppress("RedundantNullableReturnType")
    private val nullableValue: String? = "Bye, World!"
    private val nullValue: String? = null

    @Test
    fun `non-null-value`() {
        assertNotNull(requireNonNull(nullableValue))
    }

    @Test(expected = NullPointerException::class)
    fun `throw-npe`() {
        requireNonNull(nullValue)
    }

    @Test(expected = IllegalStateException::class)
    fun `throw-ise`() {
        runtimeCheck(false)
    }
}