/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [Names.kt] created by Ji Sungbin on 22. 8. 18. 오후 10:39
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// TAKEN FROM: https://github.com/androidx/androidx/tree/androidx-main/compose/lint/common/src/main/java/androidx/compose/lint

@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "unused",
    "FunctionName",
)

package team.duckie.quackquack.common.lint.compose

import kotlinx.metadata.ClassName

/**
 * Contains common names used for lint checks.
 */
object Names {
    object Runtime {
        val PackageName = Package("androidx.compose.runtime")
        val Composable = Name(PackageName, "Composable")
    }
}

/**
 * Represents a qualified package
 *
 * @property segments the segments representing the package
 */
class PackageName internal constructor(internal val segments: List<String>) {
    /**
     * The Java-style package name for this [Name], separated with `.`
     */
    val javaPackageName get() = segments.joinToString(".")
}

/**
 * Represents the qualified name for an element
 *
 * @property pkg the package for this element
 * @property nameSegments the segments representing the element - there can be multiple in the
 * case of nested classes.
 */
class Name internal constructor(
    private val pkg: PackageName,
    private val nameSegments: List<String>,
) {
    /**
     * The short name for this [Name]
     */
    val shortName get() = nameSegments.last()

    /**
     * The Java-style fully qualified name for this [Name], separated with `.`
     */
    val javaFqn
        get() = pkg.segments.joinToString(".", postfix = ".") +
                nameSegments.joinToString(".")

    /**
     * The [ClassName] for use with kotlinx.metadata. Note that in kotlinx.metadata the actual
     * type might be different from the underlying JVM type, for example:
     * kotlin/Int -> java/lang/Integer
     */
    val kmClassName
        get() = pkg.segments.joinToString("/", postfix = "/") +
                nameSegments.joinToString(".")
}

/**
 * @return a [PackageName] with a Java-style (separated with `.`) [packageName].
 */
fun Package(packageName: String) = PackageName(
    segments = packageName.split("."),
)

/**
 * @return a [PackageName] with a Java-style (separated with `.`) [packageName].
 */
fun Package(packageName: PackageName, shortName: String) = PackageName(
    segments = packageName.segments + shortName.split("."),
)

/**
 * @return a [Name] with the provided [pkg] and Java-style (separated with `.`) [shortName].
 */
fun Name(pkg: PackageName, shortName: String) = Name(
    pkg = pkg,
    nameSegments = shortName.split("."),
)
