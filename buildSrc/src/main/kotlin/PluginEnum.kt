/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [PluginEnum.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:53
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

object PluginEnum {
    private const val prefix = "quackquack"

    const val Jacoco = "$prefix.project.jacoco.plugin"
    const val Benchmark = "$prefix.benchmark.plugin"
    const val Application = "$prefix.application.plugin"
    const val ApplicationCompose = "$prefix.application.compose.plugin"
    const val ApplicationJacoco = "$prefix.application.jacoco.plugin"
    const val Library = "$prefix.library.plugin"
    const val LibraryCompose = "$prefix.library.compose.plugin"
    const val LibraryJacoco = "$prefix.library.jacoco.plugin"
}