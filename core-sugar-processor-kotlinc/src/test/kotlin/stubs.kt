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
        "sugar-annotations.kt",
        """
        package team.duckie.quackquack.sugar.material
        
        import kotlin.reflect.KClass
        
        @Target(AnnotationTarget.FUNCTION)
        @Retention(AnnotationRetention.BINARY)
        annotation class SugarName(val name: String = DEFAULT_NAME) {
            companion object {
                const val PREFIX_NAME: String = "Quack"
                const val DEFAULT_NAME: String = "<<DEFAULT_NAME>>"
                const val TOKEN_NAME: String = "<<SUGAR_TOKEN>>"
            }
        }
        
        @Target(AnnotationTarget.VALUE_PARAMETER)
        @Retention(AnnotationRetention.BINARY)
        annotation class SugarToken
        
        @Target(AnnotationTarget.FUNCTION)
        @Retention(AnnotationRetention.BINARY)
        annotation class SugarRefer(val fqn: String)
        
        @Target(AnnotationTarget.FUNCTION)
        @Retention(AnnotationRetention.BINARY)
        annotation class NoSugar
        
        @Target(AnnotationTarget.FILE)
        @Retention(AnnotationRetention.BINARY)
        annotation class GeneratedFile
        
        @Target(AnnotationTarget.VALUE_PARAMETER)
        @Retention(AnnotationRetention.BINARY)
        annotation class Imports(vararg val clazz: KClass<*>)
        """,
    ),
)
