/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * [ModuleDokkaPlugin.kt] created by Ji Sungbin on 22. 8. 20. 오후 8:54
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import team.duckie.quackquack.convention.applyPlugins
import team.duckie.quackquack.convention.libs

/**
 * Android 프레임워크에 의존적이지 않은 순수한 Dokka 모듈을 구성합니다.
 */
internal class ModuleDokkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(libs.findPlugin("dokka").get().get().pluginId)

            tasks.withType<DokkaTaskPartial> {
                suppressInheritedMembers.set(true)

                dokkaSourceSets.configureEach {
                    documentedVisibilities.set(Visibility.values().toList())
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
