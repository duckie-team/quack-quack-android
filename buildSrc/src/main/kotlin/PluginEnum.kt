/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

object PluginEnum {
    private const val prefix = "quackquack"

    const val AndroidLint = "$prefix.android.lint"
    const val AndroidCommonLintPlugin = "$prefix.android.common.lint"
    const val AndroidBenchmark = "$prefix.android.benchmark"
    const val AndroidApplication = "$prefix.android.application"
    const val AndroidApplicationCompose = "$prefix.android.application.compose"
    const val AndroidLibrary = "$prefix.android.library"
    const val AndroidLibraryCompose = "$prefix.android.library.compose"
    const val AndroidLibraryComposeUiTest = "$prefix.android.library.compose.uitest"

    const val JvmKover = "$prefix.jvm.kover"
    const val JvmLibrary = "$prefix.jvm.library"
    const val JvmDokka = "$prefix.jvm.dokka"
}
