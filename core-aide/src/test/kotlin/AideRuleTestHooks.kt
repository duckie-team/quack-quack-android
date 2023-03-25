/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import io.kotest.core.spec.AfterTest
import io.kotest.core.spec.BeforeTest
import java.io.File

private val projectDir = System.getProperty("user.dir")
private val aideMainRules = File(projectDir, "src/main/kotlin/rule").listFiles()!!
private val aideTestRules = File(projectDir, "src/test/kotlin/rule").listFiles()!!

// Map<String, String>: filename - code
private var previousAideRules = mutableMapOf<String, String>()

val aideRuleTestStart: BeforeTest = {
    aideMainRules.forEach { rule ->
        previousAideRules[rule.name] = rule.readText()
        aideTestRules.first { testRule -> testRule.name == rule.name }.let { testRule ->
            rule.writeText(testRule.readText())
        }
    }
}

val aideRuleTestFinish: AfterTest = {
    aideMainRules.forEach { rule ->
        val previousAideRule = checkNotNull(previousAideRules[rule.name]) {
            "The `aideRuleTestStart` hook was not called or no previous AideRules exist."
        }
        rule.writeText(previousAideRule)
    }
}
