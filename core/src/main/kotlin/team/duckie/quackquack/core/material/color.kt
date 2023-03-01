/*
 * Designed and developed by Duckie Team, 2022~2023
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

@file:Suppress("unused", "MagicNumber")

package team.duckie.quackquack.core.material

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import kotlin.math.pow
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 덕키에서 사용할 색상을 정의합니다.
 *
 * 색상 정의는 코드 스타일 통일을 위해 ARGB 형식이 아닌 Hex 형식으로 해야 합니다.
 *
 * @param value 색상 값. 이 인자로 색상을 사용하기 위해 컴포즈의 [Color]로 받습니다.
 */
@Immutable
@JvmInline
public value class QuackColor(public val value: Color) : ReadOnlyProperty<Any, Color> {
    /**
     * [QuackColor]를 [Brush]로 변환합니다.
     */
    @Stable
    public fun toBrush(): SolidColor = SolidColor(value)

    /**
     * 정해진 [QuackColor]에서 일부 값만 변경이 필요할 때가 있습니다.
     * 이를 대응하기 위해 현재 [QuackColor]에서 변경을 허용하는
     * 필드만 변경하여 새로운 [QuackColor]를 반환하는 API를 구현합니다.
     *
     * 투명도 변경은 고정된 디자인의 목적을 해치지 않을 것으로 예상하여
     * public으로 노출합니다.
     *
     * @param alpha 변경할 투명도
     *
     * @return [alpha] 값이 변경된 [QuackColor]
     */
    @Stable
    public fun change(alpha: Float): QuackColor {
        return if (alpha == value.alpha) {
            this
        } else {
            QuackColor(value.copy(alpha = alpha))
        }
    }

    public companion object {
        // Unspecified 는 색상의 기본 인자 값으로만 사용되야 하며,
        // 실제 컴포넌트에서는 사용되서는 안됩니다.
        internal val Unspecified: QuackColor = QuackColor(Color.Unspecified)

        // Transparent 는 색상의 기본 값으로만 사용되야 하며,
        // 실제 컴포넌트에서는 사용되서는 안됩니다.
        internal val Transparent: QuackColor = QuackColor(Color.Transparent)

        public val DuckieOrange: QuackColor = QuackColor(Color(0xFFFF8300))

        public val Alert: QuackColor = QuackColor(Color(0xFFFF2929))
        public val Dimmed: QuackColor = QuackColor(Color.Black.copy(alpha = 0.6f))

        public val Gray1: QuackColor = QuackColor(Color(0xFF666666))
        public val Gray2: QuackColor = QuackColor(Color(0xFFA8A8A8))
        public val Gray3: QuackColor = QuackColor(Color(0xFFEFEFEF))
        public val Gray4: QuackColor = QuackColor(Color(0xFFF6F6F6))

        public val Black: QuackColor = QuackColor(Color(0xFF222222))
        public val White: QuackColor = QuackColor(Color(0xFFFFFFFF))

        /* ----- Copied from AOSP ----- */

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

        internal val VectorConverter: (colorSpace: ColorSpace) -> TwoWayConverter<QuackColor, AnimationVector4D> =
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

        /* ----- Copied from AOSP ----- */
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): Color {
        return value
    }
}

/**
 * [QuackColor] 에 색상에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * animationSpec 으로 항상 [QuackAnimationSpec] 을 사용합니다.
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
    return animateQuackColorAsStateInternal(
        targetValue = targetValue,
        label = label,
        finishedListener = finishedListener,
    )
}

/**
 * [QuackColor]에 색상에 변경이 있을 때 애니메이션을 적용합니다.
 *
 * @param targetValue 색상 변경을 감지할 [QuackColor]
 * @param animationSpec 애니메이션에 사용할 [AnimationSpec]. 일부 컴포넌트는 애니메이션 없이 진행돼야 할 때도 있습니다.
 * 이 상황을 대응하기 위해 이 값을 예외적으로 받습니다.기본값은 [QuackAnimationSpec]을 사용합니다.
 * @param label Android Studio Animation Inspecter에서 해당 애니메이션을 나타낼 레이블
 * @param finishedListener 애니메이션이 끝났을 때 실행될 콜백
 *
 * @return 색상이 변경됐을 때 색상이 변경되는 애니메이션의 [State] 객체
 */
@Composable
internal fun animateQuackColorAsStateInternal(
    targetValue: QuackColor,
    animationSpec: AnimationSpec<QuackColor> = QuackAnimationSpec(),
    label: String = "animateQuackColorAsState",
    finishedListener: ((color: QuackColor) -> Unit)? = null,
): State<QuackColor> {
    val converter = remember(targetValue.value.colorSpace) {
        (QuackColor.VectorConverter).invoke(targetValue.value.colorSpace)
    }
    return animateValueAsState(
        targetValue = targetValue,
        typeConverter = converter,
        animationSpec = animationSpec,
        finishedListener = finishedListener,
        label = label,
    )
}
