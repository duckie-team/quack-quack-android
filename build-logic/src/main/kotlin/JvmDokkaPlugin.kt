/*
 * Designed and developed by Duckie Team, 2022
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/master/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크에 의존적이지 않은 순수한 Dokka 모듈을 구성합니다.
 */
internal class JvmDokkaPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins(
                libs.findPlugin("dokka").get().get().pluginId,
            )

            tasks.withType<DokkaTaskPartial> {
                suppressInheritedMembers.set(true)

                dokkaSourceSets.configureEach {
                    documentedVisibilities.set(DokkaConfiguration.Visibility.values().toList())
                    jdkVersion.set(11)
                }

                pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                    footerMessage =
                        """made with <span style="color: #ff8300;">❤</span> by <a href="https://duckie.team/">Duckie</a>"""
                }
            }
        }
    }
}
