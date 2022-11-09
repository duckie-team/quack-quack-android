/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

// TAKEN FROM: https://github.com/androidx/androidx/tree/androidx-main/compose/lint/common/src/main/java/androidx/compose/lint

@file:Suppress(
    "unused",
    "PrivatePropertyName",
    "MemberVisibilityCanBePrivate",
)

package team.duckie.quackquack.common.lint.util

import org.jetbrains.kotlin.psi.KtLambdaExpression
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.KtSimpleNameExpression
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.isAncestor
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.UParameter
import org.jetbrains.uast.toUElement

/**
 * 암시적 매개변수 `it` 을 나타냅니다.
 */
const val ItName = "it"

/**
 * [UParameter] 의 타입 레퍼런스와 타입명을 제공하는 레퍼 클래스 입니다.
 * [UParameter.typed] 의 반환 타입으로만 사용됩니다. 따라서 internal 생성자를 갖습니다.
 */
class UParameterType internal constructor(
    val reference: KtTypeReference,
    val name: String,
) {
    internal constructor(typeReference: KtTypeReference) : this(
        reference = typeReference,
        name = typeReference.text,
    )

    operator fun component1() = reference
    operator fun component2() = name
}

/**
 * 주어진 [UParameter] 의 타입 레퍼런스와 타입명을 반환합니다.
 */
val UParameter.typed: UParameterType? // 타입 명시 필수
    get() {
        val typeReference = (sourcePsi as? KtParameter)?.typeReference ?: return null
        return UParameterType(
            typeReference = typeReference,
        )
    }

/**
 * Returns a list of unreferenced parameters in [this]. If no parameters have been specified, but
 * there is an implicit `it` parameter, this will return a list containing an
 * [UnreferencedParameter] with `it` as the name.
 */
fun ULambdaExpression.findUnreferencedParameters(): List<UnreferencedParameter> {
    val lambdaExpression = sourcePsi as? KtLambdaExpression ?: return emptyList()
    return LambdaParameterVisitor(lambdaExpression).findUnreferencedParameters()
}

/**
 * Helper class that visits references inside [lambda], calculating what parameters within
 * [lambda] are unreferenced inside the expression.
 */
private class LambdaParameterVisitor(private val lambda: KtLambdaExpression) {
    /**
     * Returns a list of [UnreferencedParameter]s inside [lambda]. Inner lambdas are checked to
     * ensure that they are not shadowing a parameter name, as a reference inside a shadowed lambda
     * will refer to that lambda's parameter, and not the outer parameter.
     *
     * If no parameters have been specified, but there is an implicit `it` parameter, this will
     * return a list containing an [UnreferencedParameter] with `it` as the name.
     */
    fun findUnreferencedParameters(): List<UnreferencedParameter> {
        return if (lambda.hasImplicitItParameter) {
            if (isParameterReferenced(ItName)) {
                emptyList()
            } else {
                listOf(UnreferencedParameter(ItName, null))
            }
        } else {
            lambda.valueParameters
                .filter { parameter ->
                    parameter.destructuringDeclaration == null
                }
                .filterNot { parameter ->
                    isParameterReferenced(parameter.name!!)
                }
                .map { parameter ->
                    UnreferencedParameter(parameter.name!!, parameter)
                }
        }
    }

    private fun isParameterReferenced(name: String): Boolean {
        val matchingReferences = references.filter { expression ->
            expression.getReferencedName() == name
        }

        if (matchingReferences.isEmpty()) return false

        val lambdasWithMatchingParameterName = innerLambdas.filter { innerLambda ->
            if (innerLambda.hasImplicitItParameter) {
                name == ItName
            } else {
                innerLambda.valueParameters
                    .filter { parameter ->
                        parameter.destructuringDeclaration == null
                    }
                    .any { parameter ->
                        parameter.name == name
                    }
            }
        }

        return matchingReferences.any { reference ->
            lambdasWithMatchingParameterName.none { expression ->
                expression.isAncestor(reference)
            }
        }
    }

    private val references by lazy {
        lambda.functionLiteral.collectDescendantsOfType<KtSimpleNameExpression>()
    }
    private val innerLambdas by lazy {
        lambda.functionLiteral.collectDescendantsOfType<KtLambdaExpression>()
    }
}

/**
 * Represents an unreferenced parameter.
 *
 * @property name the name of the parameter - if the parameter is an implicit `it` parameter,
 * this will be "it"
 * @property parameter the parameter that is not referenced - can be null if the parameter is an
 * implicit `it` parameter
 */
class UnreferencedParameter(
    val name: String,
    val parameter: KtParameter?,
)

/**
 * Returns whether this lambda expression has an implicit `it` parameter - meaning it has
 * one parameter, and the parameter is not named explicitly.
 */
val KtLambdaExpression.hasImplicitItParameter: Boolean // 타입 명시 필수
    get() {
        return when {
            functionLiteral.hasParameterSpecification() -> false
            (toUElement() as? ULambdaExpression)?.valueParameters?.size != 1 -> false
            else -> true
        }
    }
