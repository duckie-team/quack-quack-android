@file:Suppress("UnstableApiUsage", "unused")

package land.sungbin.duckie.quackquack.plugin

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureApplication(commonExtension: CommonExtension<*, *, *, *>) {
    commonExtension.apply {
        compileSdk = ApplicationConstants.compileSdk

        defaultConfig {
            minSdk = ApplicationConstants.minSdk
        }

        sourceSets {
            getByName("main").java.srcDirs("src/main/kotlin/")
        }

        compileOptions {
            sourceCompatibility = ApplicationConstants.javaVersion
            targetCompatibility = ApplicationConstants.javaVersion
        }

        kotlinOptions {
            jvmTarget = ApplicationConstants.jvmTarget
        }
    }
}
