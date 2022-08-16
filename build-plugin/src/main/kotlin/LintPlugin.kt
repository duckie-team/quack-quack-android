import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.compileOnlys
import land.sungbin.duckie.quackquack.plugin.libs
import land.sungbin.duckie.quackquack.plugin.setupJunit
import land.sungbin.duckie.quackquack.plugin.setupLint
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [LintPlugin.kt] created by Ji Sungbin on 22. 8. 16. 오후 7:29
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

internal class LintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(
                PluginEnum.JavaLibrary,
                PluginEnum.PureKotlin,
                PluginEnum.Lint,
            )

            extensions.configure<JavaPluginExtension> {
                val version = JavaVersion.VERSION_1_8
                sourceCompatibility = version
                targetCompatibility = version
            }

            dependencies {
                dependencies {
                    compileOnlys(
                        libs.findLibrary("kotlin-stdlib").get(),
                    )
                    setupJunit(
                        core = libs.findLibrary("test-junit-core").get(),
                        engine = libs.findLibrary("test-junit-engine").get(),
                    )
                    setupLint(
                        core = libs.findBundle("lint").get(),
                        test = libs.findLibrary("test-lint").get(),
                    )
                }
            }
        }
    }
}
