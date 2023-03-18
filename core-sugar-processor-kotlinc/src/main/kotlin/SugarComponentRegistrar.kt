/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

@file:Suppress("DEPRECATION")
@file:OptIn(ExperimentalCompilerApi::class)

import com.google.auto.service.AutoService
import ir.SugarIrExtension
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

// TODO: 왜 deprecated된 메서드를 사용하는지 문서화
// 함수형 타입은 `Function0` 같이 generic이 삭제된 상태로 타입 추론이 진행되어
// sugar 생성이 불가능함 -> 문서화
@AutoService(ComponentRegistrar::class)
class SugarComponentRegistrar : ComponentRegistrar {
    override val supportsK2 = false

    override fun registerProjectComponents(
        project: MockProject,
        configuration: CompilerConfiguration,
    ) {
        project.extensionArea
            .getExtensionPoint(IrGenerationExtension.extensionPointName)
            .registerExtension(configuration.getSugarIrExtension(), LoadingOrder.FIRST, project)
    }

    internal companion object {
        @OptIn(ExperimentalCompilerApi::class)
        fun asPluginRegistrar() = object : CompilerPluginRegistrar() {
            override val supportsK2 = false

            override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
                IrGenerationExtension.registerExtension(configuration.getSugarIrExtension())
            }
        }

        private fun CompilerConfiguration.getSugarIrExtension(): SugarIrExtension {
            val sugarPath = checkNotNull(this[KEY_SUGAR_PATH]) { "sugarPath was missing." }
            val poet = this[KEY_POET]?.toBooleanStrict() ?: true

            return SugarIrExtension(
                logger = getLogger(),
                sugarPath = sugarPath,
                poet = poet,
            )
        }
    }
}
