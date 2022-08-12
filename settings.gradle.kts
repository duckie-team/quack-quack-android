/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "quack-quack"
include(
    ":common",
    ":playground",
    ":lint-compose",
    ":lint-core",
    ":lint-writing",
    ":ui-components",
)
