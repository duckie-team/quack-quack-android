/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.casa.processor

import com.tschuchort.compiletesting.SourceFile.Companion.kotlin

val stubs = listOf(
    kotlin(
        "Composable.kt",
        """
package androidx.compose.runtime

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER,
)
annotation class Composable
""",
    ),
    kotlin(
        "CasaAnnotations.kt",
        """
package team.duckie.quackquack.casa.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Casa

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class CasaValue(val literal: String)
        """,
    ),
    kotlin(
        "SugarMaterial.kt",
        """
package team.duckie.quackquack.sugar.material

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class SugarRefer(val fqn: String)
        """,
    ),
)
