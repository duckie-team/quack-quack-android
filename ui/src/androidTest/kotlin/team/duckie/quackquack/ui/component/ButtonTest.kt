/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.ui.component

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ButtonTest {
    @get:Rule
    val rule = createComposeRule()

    // `QuackLargeButtonDefaults` 스타일이 골든 디자인과 일치해야 함
    @Test
    fun QuackLargeButtonDefaults_style_match_golden_design() {

    }

    // `QuackMediumButtonDefaults` 스타일이 골든 디자인과 일치해야 함
    @Test
    fun QuackMediumButtonDefaults_style_match_golden_design() {

    }

    // `QuackSmallButtonDefaults` 스타일이 골든 디자인과 일치해야 함
    @Test
    fun QuackSmallButtonDefaults_style_match_golden_design() {

    }

    // `QuackLargeButtonDefaults` 스타일에 커스텀 프로퍼티를 적용할 수 있어야 함
    @Test
    fun QuackLargeButtonDefaults_style_with_custom_properties() {

    }

    // `QuackMediumButtonDefaults` 스타일에 커스텀 프로퍼티를 적용할 수 있어야 함
    @Test
    fun QuackMediumButtonDefaults_style_with_custom_properties() {

    }

    // `QuackSmallButtonDefaults` 스타일에 커스텀 프로퍼티를 적용할 수 있어야 함
    @Test
    fun QuackSmallButtonDefaults_style_with_custom_properties() {

    }

    // `enabled`가 false일 때는 `onClick` 이벤트가 작동하면 안됨
    @Test
    fun onClick_shouldnt_works_when_enabled_is_false() {

    }

    // 직접 사이즈를 지정했을 경우에는 `contentPadding`이 적용되면 안됨
    @Test
    fun contentPadding_shouldnt_works_when_size_specified() {

    }

    // `contentPadding`은 버튼 텍스트를 기준으로 적용돼야 함
    @Test
    fun contentPadding_works_with_text_baseline() {

    }

    // `QuackSmallButtonDefaults` 스타일은 데코레이터가 적용되면 안됨
    @Test
    fun QuackSmallButtonDefaults_style_shouldnt_accept_decorate() {

    }
}
