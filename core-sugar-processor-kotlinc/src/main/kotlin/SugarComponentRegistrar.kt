/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:OptIn(ExperimentalCompilerApi::class)

import com.google.auto.service.AutoService
import ir.SugarIrExtension
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@Suppress("DEPRECATION")
@AutoService(ComponentRegistrar::class)
class SugarComponentRegistrar : ComponentRegistrar {
    override val supportsK2 = true

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration,
    ) {
        val sugarPath = checkNotNull(configuration[KEY_SUGAR_PATH]) { "sugarPath was missing." }

        project.extensionArea
            .getExtensionPoint(IrGenerationExtension.extensionPointName)
            .registerExtension(
                SugarIrExtension(
                    logger = configuration.getLogger(),
                    sugarPath = sugarPath,
                ),
                LoadingOrder.FIRST,
                project,
            )
    }
}