// buildTypes, AndroidConfig
@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.test")
    id("kotlin-android")
}

android {
    namespace = "land.sungbin.duckie.quackquack.ui.benchmark"
    compileSdk = ApplicationConstants.compileSdk

    compileOptions {
        sourceCompatibility = ApplicationConstants.javaVersion
        targetCompatibility = ApplicationConstants.javaVersion
    }

    kotlinOptions {
        jvmTarget = ApplicationConstants.jvmTarget
    }

    defaultConfig {
        minSdk = ApplicationConstants.minSdk
        targetSdk = ApplicationConstants.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("release")
        }
    }

    targetProjectPath = ":playground"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementations(libs.androidx.junit, libs.androidx.benchmark)
}

androidComponents {
    beforeVariants(selector().all()) {
        it.enable = it.buildType == "benchmark"
    }
}
