plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.dokka") version Versions.BuildUtil.Dokka
    jacoco
}

android {
    namespace = "land.sungbin.duckie.quackquack.lint.writing"
}