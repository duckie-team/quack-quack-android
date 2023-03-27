/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package internal

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.kotlin.dsl.create

internal fun configureGmd(extension: CommonExtension<*, *, *, *>) {
    extension.testOptions {
        managedDevices {
            devices {
                val (device, apiLevel, systemImageSource, taskName) = DeviceConfig(
                    device = "Pixel 4",
                    apiLevel = 30,
                    systemImageSource = "aosp-atd", // https://developer.android.com/studio/test/gradle-managed-devices#gmd-atd
                )
                create<ManagedVirtualDevice>(taskName).apply {
                    this.device = device
                    this.apiLevel = apiLevel
                    this.systemImageSource = systemImageSource
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("api")
        append(apiLevel.toString())
        append(systemImageSource.replace("-", ""))
    }

    operator fun component4() = taskName
}
