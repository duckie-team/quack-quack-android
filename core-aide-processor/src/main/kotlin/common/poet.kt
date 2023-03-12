/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/duckie-quack-quack/blob/main/LICENSE
 */

package common

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec

const val GeneratedComment = "This file was automatically generated. Do not modify it arbitrarily."

val suppressAnnotation = AnnotationSpec
    .builder(Suppress::class)
    .addMember("%S, %S, %S", "NoConsecutiveBlankLines", "PackageDirectoryMismatch", "ktlint")
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

fun FileSpec.Builder.addSuppressAnnotation(): FileSpec.Builder {
    return addAnnotation(suppressAnnotation)
}

fun FileSpec.Builder.addGeneratedComment(): FileSpec.Builder {
    return addFileComment(GeneratedComment)
}
