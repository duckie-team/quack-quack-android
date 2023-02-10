/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

plugins {
    id(ConventionEnum.JvmLibrary)
    id(ConventionEnum.JvmDokka)
}

dependencies {
    // :common 이랑 타입 안맞아서 빌드 포함 금지
    // :common 은 안드로이드 전용 라이브러리지만,
    // :common-lint-test 는 JVM 라이브러리임.
    implementations(
        libs.lint.api,
        libs.test.lint,
        libs.test.junit.core,
    )
}
