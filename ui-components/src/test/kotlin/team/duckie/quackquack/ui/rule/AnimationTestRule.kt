/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.rule

import androidx.compose.animation.core.SnapSpec
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import team.duckie.quackquack.ui.animation.QuackAnimationSpec
import team.duckie.quackquack.ui.animation.isSnapshotMode

/**
 * 애니메이션 스팩을 [SnapSpec] 으로 전환합니다.
 *
 * @see isSnapshotMode
 */
public class AnimationTestRule : TestWatcher() {
    override fun starting(description: Description?) {
        QuackAnimationSpec.isSnapshotMode = true
        super.starting(description)
    }

    override fun finished(description: Description?) {
        QuackAnimationSpec.isSnapshotMode = false
        super.finished(description)
    }
}
