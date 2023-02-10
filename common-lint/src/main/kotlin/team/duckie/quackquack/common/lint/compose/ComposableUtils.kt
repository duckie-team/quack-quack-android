/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

// TAKEN FROM: https://github.com/androidx/androidx/tree/androidx-main/compose/lint/common/src/main/java/androidx/compose/lint

@file:Suppress("unused")

package team.duckie.quackquack.common.lint.compose

import com.intellij.lang.java.JavaLanguage
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiParameter
import com.intellij.psi.impl.compiled.ClsParameterImpl
import com.intellij.psi.impl.light.LightParameter
import kotlinx.metadata.jvm.annotations
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.psiUtil.getParentOfType
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UAnonymousClass
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UDeclaration
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UParameter
import org.jetbrains.uast.UTypeReferenceExpression
import org.jetbrains.uast.UVariable
import org.jetbrains.uast.getContainingDeclaration
import org.jetbrains.uast.getContainingUClass
import org.jetbrains.uast.getParameterForArgument
import org.jetbrains.uast.toUElement
import org.jetbrains.uast.withContainingElements

/**
 * Returns whether this [UExpression] is invoked within the body of a Composable function or
 * lambda.
 *
 * This searches parent declarations until we find a lambda expression or a function, and looks
 * to see if these are Composable.
 */
fun UExpression.isInvokedWithinComposable() = ComposableBodyVisitor(this).isComposable()

/**
 * Returns whether this method is @Composable or not
 */
val PsiMethod.isComposable
    get() = annotations.any { annotation ->
        annotation.qualifiedName == Names.Runtime.Composable.javaFqn
    }

/**
 * Returns whether this variable's type is @Composable or not
 */
val UVariable.isComposable: Boolean // 타입 명시 필수
    get() {
        val annotationOnLambda = when (val initializer = uastInitializer) {
            is ULambdaExpression -> {
                val source = initializer.sourcePsi
                if (source is KtFunction) {
                    source.hasComposableAnnotation
                } else {
                    initializer.findAnnotation(Names.Runtime.Composable.javaFqn) != null
                }
            }
            else -> false
        }
        val annotationOnType = typeReference?.isComposable == true
        return annotationOnLambda || annotationOnType
    }

/**
 * Returns whether this parameter's type is @Composable or not
 */
private val PsiParameter.isComposable
    get() = when {
        this is ClsParameterImpl || (this is LightParameter && this.language is JavaLanguage) -> {
            val containingMethod = getParentOfType<PsiMethod>(true)
            val kmFunction = containingMethod!!.toKmFunction()

            val kmValueParameter = kmFunction?.valueParameters?.find { parameter ->
                parameter.name == name
            }

            kmValueParameter?.type?.annotations?.find { annotation ->
                annotation.className == Names.Runtime.Composable.kmClassName
            } != null
        }
        else -> (toUElement() as UParameter).typeReference!!.isComposable
    }

/**
 * Returns whether this lambda expression is @Composable or not
 */
val ULambdaExpression.isComposable
    get() = when (val lambdaParent = uastParent) {
        is UCallExpression -> {
            val parameter = lambdaParent.getParameterForArgument(this)
            parameter?.isComposable == true
        }
        is UVariable -> {
            lambdaParent.isComposable
        }
        else -> false
    }

/**
 * Helper class that visits parent declarations above the provided [expression], until it
 * finds a lambda or method. This 'boundary' is used as the indicator for whether this
 * [expression] can be considered to be inside a Composable body or not.
 *
 * @see isComposable
 * @see parentUElements
 */
private class ComposableBodyVisitor(
    private val expression: UExpression,
) {
    /**
     * @return whether the body can be considered Composable or not
     */
    fun isComposable() = when (val element = parentUElements.last()) {
        is UMethod -> element.isComposable
        is ULambdaExpression -> element.isComposable
        else -> false
    }

    /**
     * Returns all parent [UElement]s until and including the boundary lambda / method.
     */
    fun parentUElements() = parentUElements

    /**
     * The outermost UElement that corresponds to the surrounding UDeclaration that contains
     * [expression], with the following special cases:
     *
     * - if the containing UDeclaration is a local property, we ignore it and search above as
     * it still could be created in the context of a Composable body
     * - if the containing UDeclaration is an anonymous class (object { }), we ignore it and
     * search above as it still could be created in the context of a Composable body
     */
    private val boundaryUElement by lazy {
        var containingDeclaration = expression.getContainingDeclaration()

        fun UDeclaration.isLocalProperty() = (sourcePsi as? KtProperty)?.isLocal == true
        fun UDeclaration.isAnonymousClass() = this is UAnonymousClass
        fun UDeclaration.isPropertyInsideAnonymousClass() =
            getContainingUClass()?.isAnonymousClass() == true

        fun UDeclaration.isContainingDeclaration() =
            isLocalProperty() || isAnonymousClass() || isPropertyInsideAnonymousClass()

        while (containingDeclaration != null && containingDeclaration.isContainingDeclaration()) {
            containingDeclaration = containingDeclaration.getContainingDeclaration()
        }

        containingDeclaration
    }

    private val parentUElements by lazy {
        val elements = mutableListOf<UElement>()

        for (element in expression.withContainingElements) {
            elements += element
            when (element) {
                is ULambdaExpression -> break
                is UMethod -> break
                boundaryUElement -> break
            }
        }
        elements
    }
}

/**
 * Returns whether this type reference is @Composable or not
 */
private val UTypeReferenceExpression.isComposable: Boolean // 타입 명시 필수
    get() {
        if (type.hasAnnotation(Names.Runtime.Composable.javaFqn)) return true
        return (sourcePsi as? KtTypeReference)?.hasComposableAnnotation == true
    }

/**
 * Returns whether this annotated declaration has a Composable annotation
 */
private val KtAnnotated.hasComposableAnnotation
    get() = annotationEntries.any { annotationEntry ->
        (annotationEntry.toUElement() as UAnnotation).qualifiedName == Names.Runtime.Composable.javaFqn
    }
