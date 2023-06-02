/*
 * Designed and developed by Duckie Team 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/duckie-team/quack-quack-android/blob/main/LICENSE
 */

package team.duckie.quackquack.material.icon.generator

import br.com.devsrsouza.svg2compose.Svg2Compose
import br.com.devsrsouza.svg2compose.VectorType
import java.io.File

private val rootDir by lazy { System.getProperty("user.dir") }

fun main() {
  val rawIconDir = File(rootDir, "material-icon-generator/src/raw-icon")
  val srcDir = File(rootDir, "material-icon/src/main/kotlin")

  Svg2Compose.parse(
    accessorName = "QuackIcon",
    applicationIconPackage = "team.duckie.quackquack.material.icon",
    vectorsDirectory = rawIconDir,
    outputSourceDirectory = srcDir,
    allAssetsPropertyName = "AllIcons",
    iconNameTransformer = { name, _ ->
      name
        .substringBefore(
          "24",
          missingDelimiterValue = name
            .substringBefore(
              "20",
              missingDelimiterValue = name.substringBefore("16"),
            ),
        )
    },
    type = VectorType.SVG,
  )

  println("QuackIcon generated.")
}
