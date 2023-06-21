/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)

package team.duckie.quackquack.material

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

/**
 * [Modifier.clickable]에 다양한 옵션을 부여합니다.
 *
 * @param role 사용자 인터페이스 요소의 유형. 접근성 서비스는 이를 사용하여 요소를
 * 설명하거나 사용자 지정할 수 있습니다.
 * @param rippleColor 표시될 리플의 색상. null이들어오면 [Color.Unspecified]를 사용합니다.
 * @param rippleEnabled 리플을 설정할지 여부
 * @param onLongClick 컴포넌트가 길게 클릭됐을 떄 실행할 람다식. null이 들어오면 long-clickable을
 * 설정하지 않습니다.
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식. null이 들어오면 빈 람다를 사용합니다.
 *
 * @return [Modifier.clickable] 속성이 적용된 [Modifier]
 */
@Stable
public fun Modifier.quackClickable(
  role: Role? = null,
  rippleColor: QuackColor? = null,
  rippleEnabled: Boolean = true,
  onLongClick: (() -> Unit)? = null,
  onClick: (() -> Unit)?,
): Modifier =
  composed {
    quackClickable(
      role = role,
      rippleColor = rippleColor,
      rippleEnabled = rippleEnabled,
      interactionSource = remember { MutableInteractionSource() },
      onLongClick = onLongClick,
      onClick = onClick,
    )
  }

/**
 * [Modifier.clickable]에 다양한 옵션을 부여합니다.
 *
 * @param role 사용자 인터페이스 요소의 유형. 접근성 서비스는 이를 사용하여 요소를
 * 설명하거나 사용자 지정할 수 있습니다.
 * @param rippleColor 표시될 리플의 색상. null이들어오면 [Color.Unspecified]를 사용합니다.
 * @param rippleEnabled 리플을 설정할지 여부
 * @param interactionSource 이 텍스트 필드의 인터랙션 스트림을 나타내는 변경 가능한 인터랙션 소스입니다.
 * 인터랙션을 관찰하고 다른 인터랙션에서 이 텍스트 필드의 모양/동작을 커스터마이징하려면 자신만의 변경 가능한
 * 인터랙션 소스를 생성하여 전달할 수 있습니다.
 * @param onLongClick 컴포넌트가 길게 클릭됐을 떄 실행할 람다식. null이 들어오면 long-clickable을
 * 설정하지 않습니다.
 * @param onClick 컴포넌트가 클릭됐을 때 실행할 람다식. null이 들어오면 빈 람다를 사용합니다.
 *
 * @return [Modifier.clickable] 속성이 적용된 [Modifier]
 */
@Stable
public fun Modifier.quackClickable(
  role: Role? = null,
  rippleColor: QuackColor? = null,
  rippleEnabled: Boolean = true,
  interactionSource: MutableInteractionSource,
  onLongClick: (() -> Unit)? = null,
  onClick: (() -> Unit)?,
): Modifier =
  if (onClick == null && onLongClick == null) this
  else
    composed(
      fullyQualifiedName = "team.duckie.quackquack.material.quackClickable",
      keys = arrayOf(role, rippleColor, rippleEnabled, interactionSource, onLongClick, onClick),
      inspectorInfo = debugInspectorInfo {
        name = "quackClickable"
        properties["role"] = role
        properties["rippleColor"] = rippleColor
        properties["rippleEnabled"] = rippleEnabled
        properties["interactionSource"] = interactionSource
        properties["onClick"] = onClick
        properties["onLongClick"] = onLongClick
      },
    ) {
      val ripple = rememberRipple(color = rippleColor?.value ?: Color.Unspecified)

      combinedClickable(
        indication = if (rippleEnabled) ripple else null,
        interactionSource = interactionSource,
        role = role,
        onClick = onClick ?: {},
        onLongClick = onLongClick,
      )
    }
