/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material

import androidx.compose.foundation.background
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.inspectable
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** 컴포넌트에 주어진 디자인 스펙을 적용합니다. */
@Stable
public fun Modifier.quackSurface(
  shape: Shape = RectangleShape,
  backgroundColor: QuackColor = QuackColor.Unspecified,
  border: QuackBorder? = null,
  elevation: Dp = 0.dp,
  role: Role? = null,
  rippleEnabled: Boolean = true,
  rippleColor: QuackColor = QuackColor.Unspecified,
  onClick: (() -> Unit)? = null,
): Modifier =
  inspectable(
    inspectorInfo = debugInspectorInfo {
      name = "quackSurface"
      properties["shape"] = shape
      properties["backgroundColor"] = backgroundColor
      properties["border"] = border
      properties["elevation"] = elevation
      properties["role"] = role
      properties["rippleEnabled"] = rippleEnabled
      properties["rippleColor"] = rippleColor
      properties["onClick"] = onClick
    },
  ) {
    this
      .shadow(
        elevation = elevation,
        shape = shape,
        clip = false,
      )
      .clip(shape = shape)
      .background(
        color = backgroundColor.value,
        shape = shape,
      )
      .quackClickable(
        role = role,
        rippleEnabled = rippleEnabled,
        rippleColor = rippleColor,
        onClick = onClick,
      )
      .quackBorder(
        border = border,
        shape = shape,
      )
  }
