/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package ir

import ComposableFqn
import QuackComponentPrefix
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.types.isUnit
import org.jetbrains.kotlin.ir.util.hasAnnotation
import toFqnClass

/**
 * 주어진 [함수][IrSimpleFunction]가 꽥꽥 컴포넌트인지 여부를 나타냅니다.
 * 다음과 같은 조건이 일치하면 꽥꽥 컴포넌트라고 판단합니다.
 *
 * 1. [@Composable][ComposableFqn] 어노테이션이 적용돼 있습니다.
 * 2. 함수의 접근제한자가 public입니다.
 * 3. 함수의 반환 타입이 [Unit] 입니다.
 * 4. 함수의 이름이 [Quack][QuackComponentPrefix]으로 시작합니다.
 */
internal val IrSimpleFunction.isQuackComponent: Boolean
    get() = hasAnnotation(ComposableFqn.toFqnClass()) &&
            visibility.isPublicAPI &&
            returnType.isUnit() &&
            name.asString().startsWith(QuackComponentPrefix)

internal val IrType.unsafeFqn: String
    get() = classFqName!!.asString()
