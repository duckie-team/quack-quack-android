/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
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
        "TypedModifier.kt",
        """
        package team.duckie.quackquack.aide.annotation
        annotation class TypedModifier
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
