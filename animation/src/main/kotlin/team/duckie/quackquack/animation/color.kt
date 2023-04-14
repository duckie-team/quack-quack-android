/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("PrivatePropertyName", "MagicNumber")

package team.duckie.quackquack.animation

import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import kotlin.math.pow
import team.duckie.quackquack.material.QuackColor

/* ----- START: Copied from AOSP ----- */

private val M1 = floatArrayOf(
    0.80405736f,
    0.026893456f,
    0.04586542f,
    0.3188387f,
    0.9319606f,
    0.26299807f,
    -0.11419419f,
    0.05105356f,
    0.83999807f,
)

private val InverseM1 = floatArrayOf(
    1.2485008f,
    -0.032856926f,
    -0.057883114f,
    -0.48331892f,
    1.1044513f,
    -0.3194066f,
    0.19910365f,
    -0.07159331f,
    1.202023f,
)

private fun multiplyColumn(
    column: Int,
    x: Float,
    y: Float,
    z: Float,
    matrix: FloatArray,
) = x * matrix[column] + y * matrix[3 + column] + z * matrix[6 + column]

private val QuackColorVectorConverter: (colorSpace: ColorSpace) -> TwoWayConverter<QuackColor, AnimationVector4D> =
    { colorSpace ->
        TwoWayConverter(
            convertToVector = { quackColor ->
                val color = quackColor.value
                val colorXyz = color.convert(colorSpace = ColorSpaces.CieXyz)

                val x = colorXyz.red
                val y = colorXyz.green
                val z = colorXyz.blue

                val l = multiplyColumn(
                    column = 0,
                    x = x,
                    y = y,
                    z = z,
                    matrix = M1,
                ).pow(x = 1f / 3f)

                val a = multiplyColumn(
                    column = 1,
                    x = x,
                    y = y,
                    z = z,
                    matrix = M1,
                ).pow(x = 1f / 3f)

                val b = multiplyColumn(
                    column = 2,
                    x = x,
                    y = y,
                    z = z,
                    matrix = M1,
                ).pow(x = 1f / 3f)

                AnimationVector4D(
                    v1 = color.alpha,
                    v2 = l,
                    v3 = a,
                    v4 = b,
                )
            },
            convertFromVector = { vector ->
                val l = vector.v2.pow(x = 3f)
                val a = vector.v3.pow(x = 3f)
                val b = vector.v4.pow(x = 3f)

                val x = multiplyColumn(
                    column = 0,
                    x = l,
                    y = a,
                    z = b,
                    matrix = InverseM1,
                )
                val y = multiplyColumn(
                    column = 1,
                    x = l,
                    y = a,
                    z = b,
                    matrix = InverseM1,
                )
                val z = multiplyColumn(
                    column = 2,
                    x = l,
                    y = a,
                    z = b,
                    matrix = InverseM1,
                )

                val colorXyz = Color(
                    alpha = vector.v1.coerceIn(
                        minimumValue = 0f,
                        maximumValue = 1f,
                    ),
                    red = x.coerceIn(
                        minimumValue = -2f,
                        maximumValue = 2f,
                    ),
                    green = y.coerceIn(
                        minimumValue = -2f,
                        maximumValue = 2f,
                    ),
                    blue = z.coerceIn(
                        minimumValue = -2f,
                        maximumValue = 2f,
                    ),
                    colorSpace = ColorSpaces.CieXyz,
                )

                QuackColor(colorXyz.convert(colorSpace = colorSpace))
            },
        )
    }

/* ----- END: Copied from AOSP ----- */

/**
 * [QuackColor]에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * @param targetValue 색상 변경을 감지할 [QuackColor]
 * @param label Android Studio Animation Inspecter에서 해당 애니메이션을 나타낼 레이블
 * @param finishedListener 애니메이션이 끝났을 때 실행될 콜백
 *
 * @return 색상이 변경됐을 때 색상이 변경되는 애니메이션의 [State] 객체
 */
@Composable
public fun animateQuackColorAsState(
    targetValue: QuackColor,
    label: String = "animateQuackColorAsState",
    finishedListener: ((color: QuackColor) -> Unit)? = null,
): State<QuackColor> {
    val converter = remember(targetValue.value.colorSpace) {
        QuackColorVectorConverter(targetValue.value.colorSpace)
    }
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = converter,
        animationSpec = QuackAnimationSpec(),
        finishedListener = finishedListener,
        label = label,
    )
}
