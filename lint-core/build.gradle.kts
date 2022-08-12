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
    setupJunit(core = libs.test.junit.core, engine = libs.test.junit.engine)
}