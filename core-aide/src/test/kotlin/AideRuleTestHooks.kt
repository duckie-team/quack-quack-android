/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/2.x.x/LICENSE
 */

import io.kotest.core.spec.AfterTest
import io.kotest.core.spec.BeforeTest
import java.io.File

private const val AideRulePath = "src/main/kotlin/rule"
private val projectDir = System.getProperty("user.dir")
private val aideRuleDir = File(projectDir, AideRulePath).also { rules ->
    println("AideRuleDir: ${rules.path}")
}

// Map<String, String>: filename - code
private var previousAideRules = mutableMapOf<String, String>()

val aideRuleTestStart: BeforeTest = {
    checkAideRulesAreValid()
    aideRuleDir.listFiles()!!.forEach { rule ->
        previousAideRules[rule.name] = rule.readText()
    }
}

val aideRuleTestFinish: AfterTest = {
    checkAideRulesAreValid()
    check(previousAideRules.isNotEmpty()) {
        "The `aideRuleTestStart` hook was not called or no previous AideRules exist."
    }
    aideRuleDir.listFiles()!!.forEach { rule ->
        rule.writeText(previousAideRules[rule.name]!!)
    }
}

private val aideRuleFileNames = listOf("AideModifiers.kt", "QuackComponents.kt")
private fun checkAideRulesAreValid() {
    val ruleFiles = aideRuleDir.listFiles().orEmpty().also { rules ->
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
