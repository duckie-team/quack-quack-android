/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

// TAKEN FROM: https://github.com/androidx/androidx/tree/androidx-main/compose/lint/common/src/main/java/androidx/compose/lint

@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "unused",
    "FunctionName",
)

package team.duckie.quackquack.common.lint.compose

import team.duckie.quackquack.common.lint.Name
import team.duckie.quackquack.common.lint.Package

/**
 * Contains common names used for lint checks.
 */
object Names {
    object Runtime {
        val PackageName = Package("androidx.compose.runtime")
        val Composable = Name(PackageName, "Composable")
    }
}
