/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

package rules

import internal.projectTestingMode
import io.kotest.core.spec.AfterSpec
import io.kotest.core.spec.BeforeSpec

val setUpProjectTestModeRule: BeforeSpec = {
    projectTestingMode = true
}

val tearDownProjectTestingRule: AfterSpec = {
    projectTestingMode = false
}