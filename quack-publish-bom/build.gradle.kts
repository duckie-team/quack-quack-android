/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

/* FIXME for BOM publish
What went wrong:
Execution failed for task ':app:desugarDebugAndroidTestFileDependencies'.
> Could not resolve all files for configuration ':app:debugRuntimeClasspath'.
   > Could not resolve team.duckie.quack:quack-bom:1.0.0-dev03.
     Required by:
         project :app
      > No matching variant of team.duckie.quack:quack-bom:1.0.0-dev03 was found. The consumer was configured to find a runtime of a platform, preferably optimized for Android, as well as attribute 'com.android.build.api.attributes.BuildTypeAttr' with value 'debug', attribute 'com.android.build.api.attributes.AgpVersionAttr' with value '7.4.0-alpha10', attribute 'org.jetbrains.kotlin.platform.type' with value 'androidJvm' but:
          - Variant 'releaseVariantReleaseApiPublication' capability team.duckie.quack:quack-bom:1.0.0-dev03:
              - Incompatible because this component declares an API of a library and the consumer needed a runtime of a platform
              - Other compatible attributes:
                  - Doesn't say anything about com.android.build.api.attributes.AgpVersionAttr (required '7.4.0-alpha10')
                  - Doesn't say anything about com.android.build.api.attributes.BuildTypeAttr (required 'debug')
                  - Doesn't say anything about its target Java environment (preferred optimized for Android)
                  - Doesn't say anything about org.jetbrains.kotlin.platform.type (required 'androidJvm')
          - Variant 'releaseVariantReleaseRuntimePublication' capability team.duckie.quack:quack-bom:1.0.0-dev03 declares a runtime of a component:
              - Incompatible because this component declares a library and the consumer needed a platform
              - Other compatible attributes:
                  - Doesn't say anything about com.android.build.api.attributes.AgpVersionAttr (required '7.4.0-alpha10')
                  - Doesn't say anything about com.android.build.api.attributes.BuildTypeAttr (required 'debug')
                  - Doesn't say anything about its target Java environment (preferred optimized for Android)
                  - Doesn't say anything about org.jetbrains.kotlin.platform.type (required 'androidJvm')
          - Variant 'releaseVariantReleaseSourcePublication' capability team.duckie.quack:quack-bom:1.0.0-dev03 declares a runtime of a component:
              - Incompatible because this component declares documentation and the consumer needed a platform
              - Other compatible attributes:
                  - Doesn't say anything about com.android.build.api.attributes.AgpVersionAttr (required '7.4.0-alpha10')
                  - Doesn't say anything about com.android.build.api.attributes.BuildTypeAttr (required 'debug')
                  - Doesn't say anything about its target Java environment (preferred optimized for Android)
                  - Doesn't say anything about org.jetbrains.kotlin.platform.type (required 'androidJvm')
   > Could not find team.duckie.quack:quack-ui-components:.
     Required by:
         project :app
   > Could not find team.duckie.quack:quack-lint-core:.
     Required by:
         project :app
   > Could not find team.duckie.quack:quack-lint-quack:.
     Required by:
         project :app
   > Could not find team.duckie.quack:quack-lint-compose:.
     Required by:
         project :app
*/

/*
plugins {
    id(libs.plugins.gradle.maven.publish.base.get().pluginId)
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.AndroidQuackPublish)
}

group = "team.duckie.quack"
version = libs.versions.quack.bom.get()

publishing {
    publications.withType<MavenPublication> {
        artifactId = "quack-bom"
    }
}

android {
    namespace = "team.duckie.quackquack.publish.bom"
}

quackArtifactPublish {
    type = QuackArtifactType.Bom
}

dependencies {
    constraints {
        apis(
            project(
                path = ":ui-components",
                configuration = "default",
            ),
            project(
                path = ":lint-core-publish",
                configuration = "default",
            ),
            project(
                path = ":lint-quack-publish",
                configuration = "default",
            ),
            project(
                path = ":lint-compose-publish",
                configuration = "default",
            ),
        )
    }
}
*/
