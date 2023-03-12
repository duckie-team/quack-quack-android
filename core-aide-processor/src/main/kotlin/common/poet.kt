/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package common

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec

internal const val GeneratedComment = "This file was automatically generated. Do not modify it arbitrarily."

internal val suppressAnnotation = AnnotationSpec
    .builder(Suppress::class)
    .addMember("%S, %S, %S", "NoConsecutiveBlankLines", "PackageDirectoryMismatch", "ktlint")
    .useSiteTarget(AnnotationSpec.UseSiteTarget.FILE)
    .build()

internal fun FileSpec.Builder.addSuppressAnnotation(): FileSpec.Builder {
    return addAnnotation(suppressAnnotation)
}

internal fun FileSpec.Builder.addGeneratedComment(): FileSpec.Builder {
    return addFileComment(GeneratedComment)
}
