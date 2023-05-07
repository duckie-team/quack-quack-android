/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

@file:Suppress("DEPRECATION")
@file:OptIn(ExperimentalCompilerApi::class)

package team.duckie.quackquack.sugar.processor

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.com.intellij.mock.MockProject
import org.jetbrains.kotlin.com.intellij.openapi.extensions.LoadingOrder
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import team.duckie.quackquack.sugar.processor.ir.SugarIrExtension
import team.duckie.quackquack.sugar.processor.ir.SugarIrVisitor
import team.duckie.quackquack.util.backend.kotlinc.getLogger

/**
 * ### Deprecated된 메서드를 사용하는 이유
 *
 * Compose Compiler의
 * [`Default Arguments Transform`](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/compiler/compiler-hosted/src/main/java/androidx/compose/compiler/plugins/kotlin/lower/ComposableFunctionBodyTransformer.kt;l=341-365)에
 * 의해 모든 컴포저블 함수에서 default argument의 값이 null로 변경됩니다. 하지만 sugar component를
 * 생성하기 위해선 default value의 값을 보존해야 합니다. 이를 위해 [SugarIrVisitor]가 Compose
 * Compiler보다 먼저 적용될 수 있도록 Compiler Plugin의 적용 순서를 조정할 수 있는 deprecated된
 * [registerProjectComponents] 메서드를 사용합니다. deprecated되지 않은
 * [CompilerPluginRegistrar]를 사용하면 Compiler Plugin의 적용 순서를 조정할 수 없습니다.
 */
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
    /**
     * [ComponentRegistrar]의 complie test는 DeprecatedError 상태로 항상 테스트에 실패합니다.
     * 이를 해결하기 위해 [SugarComponentRegistrar]의 [CompilerPluginRegistrar] 버전을
     * 제공합니다. 이 함수는 오직 테스트 코드에서만 사용돼야 합니다. (테스트 환경에서는 Compose Compiler가 적용되지
     * 않으니 유효합니다.)
     */
    internal fun asPluginRegistrar() = object : CompilerPluginRegistrar() {
      override val supportsK2 = false

      override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        IrGenerationExtension.registerExtension(configuration.getSugarIrExtension())
      }
    }

    private fun CompilerConfiguration.getSugarIrExtension(): SugarIrExtension {
      val sugarPath = requireNotNull(this[KEY_SUGAR_PATH]) { "sugarPath was missing." }
      val poet = this[KEY_POET]?.toBooleanStrict() ?: true

      return SugarIrExtension(
        logger = getLogger("sugar-processor"),
        sugarPath = sugarPath,
        poet = poet,
      )
    }
  }
}
