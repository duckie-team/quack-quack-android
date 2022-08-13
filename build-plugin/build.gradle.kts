plugins {
    `kotlin-dsl`
}

group = "land.sungbin.duckie.quackquack.plugin"

java {
    val version = JavaVersion.VERSION_11
    sourceCompatibility = version
    targetCompatibility = version
}

dependencies {
    implementation(libs.build.gradle)
    implementation(libs.build.kotlin)
}

gradlePlugin {
    val prefix = "quackquack"
    plugins {
        register("applicationPlugin") {
            id = "$prefix.application.plugin"
            implementationClass = "ApplicationPlugin"
        }
        register("applicationComposePlugin") {
            id = "$prefix.application.compose.plugin"
            implementationClass = "ApplicationComposePlugin"
        }
        register("applicationJacocoPlugin") {
            id = "$prefix.application.jacoco.plugin"
            implementationClass = "ApplicationJacocoPlugin"
        }
        register("libraryPlugin") {
            id = "$prefix.library.plugin"
            implementationClass = "LibraryPlugin"
        }
        register("libraryComposePlugin") {
            id = "$prefix.library.compose.plugin"
            implementationClass = "LibraryComposePlugin"
        }
        register("libraryJacocoPlugin") {
            id = "$prefix.library.jacoco.plugin"
            implementationClass = "LibraryJacocoPlugin"
        }
        register("benchmarkPlugin") {
            id = "$prefix.benchmark.plugin"
            implementationClass = "BenchmarkPlugin"
        }
        register("projectJacoco") {
            id = "$prefix.project.jacoco.plugin"
            implementationClass = "ProjectJacocoPlugin"
        }
    }
}