/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin

val stubs = arrayOf(
    kotlin(
        "Modifier.kt",
        """
        package androidx.compose.ui
        interface Modifier { companion object : Modifier }
        """,
    ),
    kotlin(
        "Composable.kt",
        """
        package androidx.compose.runtime
        annotation class Composable
        """,
    ),
)
