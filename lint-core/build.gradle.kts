plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.dokka)
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.lint.core"
}

dependencies {
    implementations(projects.common)
    setupLint(core = libs.bundles.lint, test = libs.test.lint)
    setupJunit(core = libs.test.junit.core, engine = libs.test.junit.engine)
}