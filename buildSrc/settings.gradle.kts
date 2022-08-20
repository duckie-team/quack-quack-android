/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [settings.gradle.kts] created by Ji Sungbin on 22. 8. 19. 오후 9:33
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
