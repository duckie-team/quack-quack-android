/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

package team.duckie.quackquack.ui.snapshot.rule

import androidx.compose.animation.core.SnapSpec
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import team.duckie.quackquack.ui.animation.QuackAnimationSpec

/**
 * 애니메이션 스팩을 [SnapSpec] 으로 전환합니다.
 */
class AnimationTestRule : TestWatcher() {
    override fun starting(
        description: Description?,
    ) {
        QuackAnimationSpec.snapMode = true
        super.starting(description)
    }

    override fun finished(
        description: Description?,
    ) {
        QuackAnimationSpec.snapMode = false
        super.finished(description)
    }
}
