/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier

/** [KSDeclaration.containingFile]을 non-null하게 조회합니다. */
public val KSDeclaration.requireContainingFile: KSFile
  get() = requireNotNull(containingFile) {
    "This($simpleName) symbol didn't come from the source file. " +
      "Is that symbol in the class file?"
  }

public val Dependencies.Companion.Empty: Dependencies
  get() = Dependencies(aggregating = false)

public val KSFunctionDeclaration.isPublicQuackComponent: Boolean
  get() {
    // 1. 공개 함수이고,
    // 2. 함수명이 Quack으로 시작하며,
    // 3. 반환 타입이 없어야 함
    return modifiers.contains(Modifier.PUBLIC) &&
      simpleName.asString().startsWith(QuackComponentPrefix) &&
      returnType.toString() == UnitSn
  }

public val KSFunctionDeclaration.isPublicModifier: Boolean
  get() {
    // 1. 공개 함수이고,
    // 2. Modifier의 확장 함수이며,
    // 3. Modifier를 반환해야 함
    return modifiers.contains(Modifier.PUBLIC) &&
      extensionReceiver.toString() == ModifierSn &&
      returnType.toString() == ModifierSn
  }
