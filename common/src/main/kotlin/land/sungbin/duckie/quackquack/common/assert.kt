package land.sungbin.duckie.quackquack.common

private val DefaultMessage = """
    A runtime error occurred in quack-quack. Please report it through GitHub issues.
    https://github.com/sungbinland/quack-quack/issues/
""".trimIndent()

fun runtimeCheck(value: Boolean, lazyMessage: () -> String = { DefaultMessage }) {
    if (!value) throw IllegalStateException(lazyMessage())
}

fun <T> requireNonNull(value: T?, lazyMessage: () -> String = { DefaultMessage }): T {
    return value ?: throw NullPointerException(lazyMessage())
}
