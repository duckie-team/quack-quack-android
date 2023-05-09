/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.util.backend.ksp

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

/**
 * 주어진 [함수][KSFunctionDeclaration]가 꽥꽥 컴포넌트인지 조회합니다.
 * 다음과 같은 조건이 일치하면 꽥꽥 컴포넌트라고 판단합니다.
 *
 * 1. [@Composable][ComposableFqn] 어노테이션이 적용돼 있습니다.
 * 2. 함수의 접근제한자가 public 입니다.
 * 3. 함수의 반환 타입이 [Unit] 입니다.
 * 4. 함수의 이름이 [Quack][QuackComponentPrefix]으로 시작합니다.
 */
public val KSFunctionDeclaration.isPublicQuackComponent: Boolean
  get() = annotations.any { annotation ->
    annotation.shortName.getShortName() == ComposableSn &&
      annotation.annotationType.resolve().declaration.qualifiedName?.asString() == ComposableFqn
  } &&
    modifiers.contains(Modifier.PUBLIC) &&
    returnType.toString() == UnitSn &&
    simpleName.asString().startsWith(QuackComponentPrefix)

/**
 * 주어진 [함수][KSFunctionDeclaration]가 public [Modifier][ModifierSn] 인지 조회합니다.
 * 다음과 같은 조건이 일치하면 꽥꽥 컴포넌트라고 판단합니다.
 *
 * 1. 함수의 접근제한자가 public 입니다.
 * 2. 함수의 receiver 타입이 [Modifier][ModifierSn] 입니다.
 * 4. 함수의 반환 타입이 [Modifier][ModifierSn] 입니다.
 */
public val KSFunctionDeclaration.isPublicModifier: Boolean
  get() = modifiers.contains(Modifier.PUBLIC) &&
    extensionReceiver.toString() == ModifierSn &&
    returnType.toString() == ModifierSn
