/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

plugins {
  quackquack("jvm-kotlin")
}

repositories {
  maven("https://jitpack.io")
}

dependencies {
  implementations(
    libs.compose.svg.converter,
    libs.kotlin.kotlinpoet.core,
    // required dependencies by compose-svg-converter
    "com.google.guava:guava:23.6.1-jre",
    "com.android.tools:sdk-common:27.2.2",
    "com.android.tools:common:27.2.0-alpha16",
    "org.ogce:xpp3:1.1.6",
  )
}
