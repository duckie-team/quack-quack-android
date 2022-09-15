/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(PluginEnum.AndroidLibrary)
}

ext {
    val properties = mapOf(
        "PUBLISH_GROUP_ID" to "team.duckie.quack",
        "PUBLISH_VERSION" to "1.0.0",
        "PUBLISH_ARTIFACT_ID" to "quack-lint-compose",
        "PUBLISH_DESCRIPTION" to "Duckie's design system custom lint for Compose",
        "PUBLISH_URL" to "https://github.com/sungbinland/duckie-quack-quack",
        "PUBLISH_LICENSE_NAME" to "MIT License",
        "PUBLISH_LICENSE_URL" to "https://github.com/sungbinland/duckie-quack-quack/blob/develop/LICENSE",
        "PUBLISH_DEVELOPER_ID" to "jisungbin",
        "PUBLISH_DEVELOPER_NAME" to "Ji Sungbin",
        "PUBLISH_DEVELOPER_EMAIL" to "ji@sungb.in",
        "PUBLISH_SCM_CONNECTION" to "scm:git:github.com/sungbinland/duckie-quack-quack.git",
        "PUBLISH_SCM_DEVELOPER_CONNECTION" to "scm:git:ssh://github.com/sungbinland/duckie-quack-quack.git",
        "PUBLISH_SCM_URL" to "https://github.com/sungbinland/duckie-quack-quack/tree/main",
    )

    properties.forEach { (key, value) ->
        set(key, value)
    }
}

android {
    namespace = "team.duckie.quackquack.lint.compose.publish"
}

apply(from = "$rootDir/scripts/publish-module.gradle")

dependencies {
    add("lintPublish", projects.lintCompose)
}
