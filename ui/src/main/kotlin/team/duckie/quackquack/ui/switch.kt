/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("ModifierParameter")

package team.duckie.quackquack.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import team.duckie.quackquack.material.QuackColor
import team.duckie.quackquack.material.quackClickable
import team.duckie.quackquack.sugar.material.NoSugar
import team.duckie.quackquack.ui.plugin.interceptor.rememberInterceptedStyleSafely
import team.duckie.quackquack.ui.util.onDrawFront

/**
 * [QuackSwitch]를 그리는데 사용할 색상을 나타냅니다.
 *
 * @param track 활성화 상태의 track 색상
 * @param disableTrack 비활성화 상태의 track 색상
 * @param thumb 활성화 상태의 thumb 색상
 * @param thumbStroke 활성화 상태의 thumb-stroke 색상
 * @param disableThumb 비활성화 상태의 thumb 색상
 * @param disableThumbStroke 비활성화 상태의 thumb-stroke 색상
 */
@Immutable
public class QuackSwitchColors private constructor(
  public val track: QuackColor = QuackColor.DuckieOrange,
  public val disableTrack: QuackColor = QuackColor.Gray3,
  public val thumb: QuackColor = QuackColor.White,
  public val thumbStroke: QuackColor = QuackColor.DuckieOrange,
  public val disableThumb: QuackColor = QuackColor.White,
  public val disableThumbStroke: QuackColor = QuackColor.Gray2,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is QuackSwitchColors) return false

    if (track != other.track) return false
    if (disableTrack != other.disableTrack) return false
    if (thumb != other.thumb) return false
    if (thumbStroke != other.thumbStroke) return false
    if (disableThumb != other.disableThumb) return false

    return disableThumbStroke == other.disableThumbStroke
  }

  override fun hashCode(): Int {
    var result = track.hashCode()
    result = 31 * result + disableTrack.hashCode()
    result = 31 * result + thumb.hashCode()
    result = 31 * result + thumbStroke.hashCode()
    result = 31 * result + disableThumb.hashCode()
    result = 31 * result + disableThumbStroke.hashCode()
    return result
  }

  /** 일부 필드를 수정하여 새로운 [QuackSwitchColors] 인스턴스를 만듭니다. */
  @Stable
  public fun copy(
    track: QuackColor = this.track,
    disableTrack: QuackColor = this.disableTrack,
    thumb: QuackColor = this.thumb,
    thumbStroke: QuackColor = this.thumbStroke,
    disableThumb: QuackColor = this.disableThumb,
    disableThumbStroke: QuackColor = this.disableThumbStroke,
  ): QuackSwitchColors =
    QuackSwitchColors(
      track = track,
      disableTrack = disableTrack,
      thumb = thumb,
      thumbStroke = thumbStroke,
      disableThumb = disableThumb,
      disableThumbStroke = disableThumbStroke,
    )

  public companion object {
    /** 디자인 가이드라인에 정의된 기본 색상을 가져옵니다. */
    @Stable
    public val defaultSwitchColors: QuackSwitchColors = QuackSwitchColors()
  }
}

private val width = 28.dp
private val height = 16.dp

private val trackHeight = 12.dp
private val thumbStrokeThickness = 1.dp

private const val AnimationMillis = 300
private val sizeTweenSpec = tween<Float>(durationMillis = AnimationMillis)
private val colorTweenSpec = tween<Color>(durationMillis = AnimationMillis)

/**
 * 꽥꽥 디자인 가이드라인에 의거한 스위치를 그립니다.
 *
 * 이 컴포넌트의 사이즈는 디자인 가이드라인에 따라 자체적으로 결정되며,
 * 사용자가 제공한 값은 영향을 미치지 않습니다.
 *
 * @param enabled 활성화 여부
 * @param colors 스위치를 그리는데 사용할 색상 모음
 * @param onClick 스위치가 클릭됐을 때 실행할 람다식. 토글은 stable 릴리스 전에 지원될 예정입니다.
 */
// TODO(impl): anchored-draggable 지원 (toggle)
@NoSugar
@Composable
public fun QuackSwitch(
  enabled: Boolean,
  colors: QuackSwitchColors = QuackSwitchColors.defaultSwitchColors,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  @Suppress("NAME_SHADOWING")
  val colors = rememberInterceptedStyleSafely<QuackSwitchColors>(style = colors, modifier = modifier)

  val density = LocalDensity.current

  val enabledXCenter = remember(density, width, height) {
    with(density) { width.toPx() - height.toPx() / 2 }
  }
  val disabledXCenter = remember(density, height) {
    with(density) { height.toPx() / 2 }
  }

  val trackColorAnimated by animateColorAsState(
    targetValue = (if (enabled) colors.track else colors.disableTrack).value,
    animationSpec = colorTweenSpec,
    label = "QuackSwitchTrackColor",
  )
  val thumbXCenterOffsetAnimated by animateFloatAsState(
    targetValue = if (enabled) enabledXCenter else disabledXCenter,
    animationSpec = sizeTweenSpec,
    label = "QuackSwitchThumbXCenterOffset",
  )

  Box(
    modifier
      .size(width = width, height = height)
      .quackClickable(
        role = Role.Switch,
        onClick = onClick,
        rippleEnabled = false,
      )
      .drawWithCache {
        val centerOffset = Offset(x = thumbXCenterOffsetAnimated, y = size.height / 2)

        val track = object {
          val color = trackColorAnimated
          val size = Size(width = width.toPx(), height = trackHeight.toPx())
          val cornerRadius = CornerRadius(x = 30.dp.toPx(), y = 30.dp.toPx())
          val topLeft =
            Offset(
              x = 0f,
              y = Alignment.CenterVertically
                .align(size = trackHeight.roundToPx(), space = height.roundToPx())
                .toFloat(),
            )
        }
        val thumb = object {
          val color = (if (enabled) colors.thumb else colors.disableThumb).value
          val radius = height.toPx() / 2
        }
        val thumbStroke = object {
          val color = (if (enabled) colors.thumbStroke else colors.disableThumbStroke).value
          val radius = height.toPx() / 2 - thumbStrokeThickness.toPx() / 2
          val style = Stroke(width = thumbStrokeThickness.toPx(), cap = StrokeCap.Round)
        }

        onDrawFront {
          // track
          drawRoundRect(
            color = track.color,
            size = track.size,
            topLeft = track.topLeft,
            cornerRadius = track.cornerRadius,
          )
          // thumb
          drawCircle(
            color = thumb.color,
            radius = thumb.radius,
            center = centerOffset,
          )
          // thumb stroke
          drawCircle(
            color = thumbStroke.color,
            style = thumbStroke.style,
            radius = thumbStroke.radius,
            center = centerOffset,
          )
        }
      },
  )
}
