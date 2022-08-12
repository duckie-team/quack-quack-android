/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.dokka)
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.lint.writing"
}

dependencies {
    implementations(projects.common)
    setupLint(core = libs.bundles.lint, test = libs.test.lint)
    setupJunit(core = libs.test.junit.core, engine = libs.test.junit.engine)
}
