/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package common

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import common.Names.QuackComponentPrefix
import common.Names.UnitSn

internal val KSDeclaration.requireContainingFile: KSFile
    get() = requireNotNull(containingFile) {
        "This($simpleName) symbol didn't come from the source file. " +
                "Is that symbol in the class file?"
    }

internal val Dependencies.Companion.Empty: Dependencies
    get() = Dependencies(aggregating = false)

internal val KSFunctionDeclaration.isPublicQuackComponent: Boolean
    get() {
        // 1. 공개 함수이고,
        // 2. 함수명이 Quack으로 시작하며,
        // 3. 확장 함수가 아니고,
        // 4. 반환 타입이 없어야 함
        return modifiers.contains(Modifier.PUBLIC) &&
                simpleName.asString().startsWith(QuackComponentPrefix) &&
                extensionReceiver == null &&
                returnType.toString() == UnitSn
    }
