/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

// TAKEN FROM: https://github.com/androidx/androidx/tree/androidx-main/compose/lint/common/src/main/java/androidx/compose/lint

@file:Suppress("unused")

package team.duckie.quackquack.common.lint.util

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiType
import com.intellij.psi.util.InheritanceUtil
import team.duckie.quackquack.common.lint.Name
import team.duckie.quackquack.common.lint.PackageName

/**
 * Returns whether [this] has [packageName] as its package name.
 */
fun PsiMethod.isInPackageName(packageName: PackageName): Boolean {
    val actual = (containingFile as? PsiJavaFile)?.packageName
    return packageName.javaPackageName == actual
}

/**
 * Whether this [PsiMethod] returns Unit
 */
val PsiMethod.isReturnsUnit get() = returnType.isVoidOrUnit

/**
 * Whether this [PsiType] is `void` or [Unit]
 *
 * In Kotlin 1.6 some expressions now explicitly return [Unit] instead of just being [PsiType.VOID],
 * so this returns whether this type is either.
 */
val PsiType?.isVoidOrUnit get() = this == PsiType.VOID || this?.canonicalText == Unit.toString()

/**
 * @return whether [this] inherits from [name]. Returns `true` if [this] _is_ directly [name].
 */
fun PsiType.inheritsFrom(name: Name) = InheritanceUtil.isInheritor(this, name.javaFqn)

/**
 * @return whether [this] inherits from [name]. Returns `true` if [this] _is_ directly [name].
 */
fun PsiClass.inheritsFrom(name: Name) = InheritanceUtil.isInheritor(this, name.javaFqn)
