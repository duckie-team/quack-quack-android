/*
 * Designed and developed by 2022 SungbinLand, Team Duckie
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/sungbinland/quack-quack/blob/main/LICENSE
 */

plugins {
    id(ConventionEnum.AndroidLibrary)
    id(ConventionEnum.JvmKover)
    id(ConventionEnum.JvmDokka)
}

group = "team.duckie.quack"
version = "1.0.0-dev01"

android {
    namespace = "team.duckie.quackquack.common"
}
