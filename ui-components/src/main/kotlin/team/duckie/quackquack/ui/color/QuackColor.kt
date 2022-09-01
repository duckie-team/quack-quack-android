/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [QuackColor.kt] created by Ji Sungbin on 22. 8. 14. 오후 7:48
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.color

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import kotlin.math.pow
import kotlin.reflect.KProperty
import team.duckie.quackquack.common.AllowMagicNumber
import team.duckie.quackquack.ui.animation.quackTween

/**
 * 덕키에서 사용할 색상을 정의합니다. 추상화를 위해 컴포즈의 [Color] 를 그대로 사용하는게 아닌
 * 이 클래스를 사용해야 합니다.
 *
 * copy 를 통한 값 변경은 덕키 스타일 가이드의 색상 사전 정의가 깨짐으로
 * copy 생성을 방지하기 위해 data class 가 아닌 class 가 사용됐습니다.
 *
 * 색상 정의는 코드 스타일 통일을 위해 ARGB 형식이 아닌 Hex Color 형식으로 해야 합니다.
 *
 * @param value 색상 값. 이 인자로 색상을 사용하기 위해 컴포즈의 [Color] 로 받습니다.
 */
@Immutable
@JvmInline
value class QuackColor private constructor(
    val value: Color,
) {
    companion object {
        // Unspecified 는 색상의 기본 인자 값으로만 사용되야 하며,
        // 실제 컴포넌트에서는 사용되서는 안됩니다.
        @Stable
        internal val Unspecified = QuackColor(
            value = Color.Unspecified,
        )

        @Stable
        val DuckieOrange = QuackColor(
            value = Color(0xFFFF8300),
        )

        @Stable
        val Black = QuackColor(
            value = Color(0xFF222222),
        )

        @Stable
        val Gray1 = QuackColor(
            value = Color(0xFF666666),
        )

        @Stable
        val Gray2 = QuackColor(
            value = Color(0xFFA8A8A8),
        )

        @Stable
        val Gray3 = QuackColor(
            value = Color(0xFFEFEFEF),
        )

        @Stable
        val Gray4 = QuackColor(
            value = Color(0xFFF6F6F6),
        )

        @Stable
        val White = QuackColor(
            value = Color(0xFFFFFFFF),
        )

        @Stable
        val OrangeRed = QuackColor(
            value = Color(0xFFFF2929),
        )

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

        @AllowMagicNumber
        private fun multiplyColumn(
            column: Int,
            x: Float,
            y: Float,
            z: Float,
            matrix: FloatArray
        ) = x * matrix[column] + y * matrix[3 + column] + z * matrix[6 + column]

        // copied from compose original implementation
        internal val VectorConverter: (colorSpace: ColorSpace) -> TwoWayConverter<QuackColor, AnimationVector4D> =
            { colorSpace ->
                TwoWayConverter(
                    convertToVector = { quackColor ->
                        val color by quackColor
                        val colorXyz = color.convert(
                            colorSpace = ColorSpaces.CieXyz,
                        )

                        val x = colorXyz.red
                        val y = colorXyz.green
                        val z = colorXyz.blue

                        @AllowMagicNumber
                        val l = multiplyColumn(
                            column = 0,
                            x = x,
                            y = y,
                            z = z,
                            matrix = M1,
                        ).pow(x = 1f / 3f)

                        @AllowMagicNumber
                        val a = multiplyColumn(
                            column = 1,
                            x = x,
                            y = y,
                            z = z,
                            matrix = M1,
                        ).pow(x = 1f / 3f)

                        @AllowMagicNumber
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
                    convertFromVector = {
                        val l = it.v2.pow(x = 3f)
                        val a = it.v3.pow(x = 3f)
                        val b = it.v4.pow(x = 3f)

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
                            alpha = it.v1.coerceIn(
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

                        QuackColor(
                            value = colorXyz.convert(
                                colorSpace = colorSpace,
                            ),
                        )
                    }
                )
            }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value
}

/**
 * [QuackColor] 에 색상에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * @param targetValue 색상 변경을 감지할 [QuackColor]
 * @param animationSpec 색상 변경을 감지했을 때 적용할 애니메이션 스팩
 *
 * @return 색상이 변경됐을 때 색상이 변경되는 애니메이션의 [State] 객체
 */
@Composable
internal fun animateQuackColorAsState(
    targetValue: QuackColor,
    animationSpec: AnimationSpec<QuackColor> = quackTween(),
): State<QuackColor> {
    val converter = remember(targetValue.value.colorSpace) {
        (QuackColor.VectorConverter)(targetValue.value.colorSpace)
    }
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = converter,
        animationSpec = animationSpec,
        finishedListener = null,
    )
}
