/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [MainActivity.kt] created by Ji Sungbin on 22. 8. 14. 오전 12:59
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UNUSED_VARIABLE", "unused")

package team.duckie.quackquack.playground

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity()

@Composable
fun Button() {
    // stub!
}

@Composable
fun Main() {
    // Button() <-- lint error
}
