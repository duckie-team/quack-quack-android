/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

@file:Suppress(
    "UnstableApiUsage",
    "DSL_SCOPE_VIOLATION",
)

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(libs.plugins.gradle.maven.publish.base.get().pluginId)
}

group = "team.duckie.quack"
version = libs.versions.quack.lint.compose.get()

android {
    namespace = "team.duckie.quackquack.lint.compose.publish"
}

publishing {
    publications.withType<MavenPublication> {
        artifactId = "quack-lint-compose"
    }
}

mavenPublishing {
    publishToMavenCentral(
        host = SonatypeHost.S01,
        automaticRelease = true,
    )

    signAllPublications()

    pom {
        name.set("quack-lint-compose")
        description.set("Duckie's design system custom lint for Jetpack Compose")
        inceptionYear.set("2022")
        url.set("https://github.com/sungbinland/duckie-quack-quack")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://github.com/sungbinland/duckie-quack-quack/blob/develop/LICENSE")
            }
        }

        developers {
            developer {
                id.set("jisungbin")
                name.set("Ji Sungbin")
                url.set("https://sungb.in")
                email.set("ji@sungb.in")
            }
        }

        scm {
            url.set("https://github.com/sungbinland/duckie-quack-quack/tree/main")
            connection.set("scm:git:github.com/sungbinland/duckie-quack-quack.git")
            developerConnection.set("scm:git:ssh://github.com/sungbinland/duckie-quack-quack.git")
        }
    }

    configure(
        platform = AndroidSingleVariantLibrary(
            publishJavadocJar = false,
        ),
    )
}

/*quackArtifactPublish {
    version = libs.versions.quack.lint.compose.get()
    type = QuackArtifactType.LintCompose
}*/
