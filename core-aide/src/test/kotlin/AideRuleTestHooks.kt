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
private val aideMainRules = File(projectDir, "src/main/kotlin/rule").listFiles()
private val aideTestRules = File(projectDir, "src/test/kotlin/rule").listFiles()!!

// Map<String, String>: filename - code
private var previousAideRules = mutableMapOf<String, String>()

val aideRuleTestStart: BeforeTest = {
    checkAideRulesAreValid()
    aideMainRules!!.forEach { rule ->
        previousAideRules[rule.name] = rule.readText()
        aideTestRules.find { testRule -> testRule.name == rule.name }!!.let { testRule ->
            rule.writeText(testRule.readText())
        }
    }
}

val aideRuleTestFinish: AfterTest = {
    checkAideRulesAreValid()
    check(previousAideRules.isNotEmpty()) {
        "The `aideRuleTestStart` hook was not called or no previous AideRules exist."
    }
    aideMainRules!!.forEach { rule ->
        rule.writeText(previousAideRules[rule.name]!!)
    }
}

private val aideRuleFileNames = listOf("AideModifiers.kt", "QuackComponents.kt")
private fun checkAideRulesAreValid() {
    val ruleFiles = aideMainRules.orEmpty().also { rules ->
        check(rules.isNotEmpty()) { "No AideRules were created" }
    }
    ruleFiles.forEach { rule ->
        check(rule.readText().isNotEmpty()) {
            "The AideRule file provided is empty: ${rule.name}"
        }
        check(aideRuleFileNames.contains(rule.name)) {
            "An unknown AideRule file: ${rule.name}"
        }
    }
}
