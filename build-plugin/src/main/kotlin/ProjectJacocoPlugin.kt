import land.sungbin.duckie.quackquack.plugin.PluginEnum
import land.sungbin.duckie.quackquack.plugin.applyPlugins
import land.sungbin.duckie.quackquack.plugin.configureJacocoForAllCoverage
import org.gradle.api.Plugin
import org.gradle.api.Project

class ProjectJacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins(PluginEnum.Jacoco)
            configureJacocoForAllCoverage()
        }
    }
}