/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import io.kotest.core.spec.style.StringSpec

class ModifierInformationalTest : StringSpec({
    // TODO: lifecycle hook 정책 검토 필요
    // https://kotest.io/docs/framework/lifecycle-hooks.html
    beforeTest(aideRuleTestStart)
    afterTest(aideRuleTestFinish)

    "허용되지 않은 데코레이터를 사용했을 때 informational issue가 발생함 - `Modifier` 대상" {

    }

    "허용되지 않은 데코레이터를 사용했을 때 informational issue가 발생함 - `Modifier.Companion` 대상" {

    }

    "informational issue가 발생했을 때 유효한 QuickFix가 제공됨" {

    }
})
