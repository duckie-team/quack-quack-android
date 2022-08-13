plugins {
    `kotlin-dsl`
}

group = "land.sungbin.duckie.quackquack.plugin"

// JavaVersion.VERSION_11 변수로 하면 빌드 에러뜸
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.build.gradle)
    implementation(libs.build.kotlin)
}

// quackquack 변수로 하면 빌드 에러뜸
gradlePlugin {
    plugins {
        register("applicationPlugin") {
            id = "quackquack.application.plugin"
            implementationClass = "ApplicationPlugin"
        }
        register("applicationComposePlugin") {
            id = "quackquack.application.compose.plugin"
            implementationClass = "ApplicationComposePlugin"
        }
        register("applicationJacocoPlugin") {
            id = "quackquack.application.jacoco.plugin"
            implementationClass = "ApplicationJacocoPlugin"
        }
        register("libraryPlugin") {
            id = "quackquack.library.plugin"
            implementationClass = "LibraryPlugin"
        }
        register("libraryComposePlugin") {
            id = "quackquack.library.compose.plugin"
            implementationClass = "LibraryComposePlugin"
        }
        register("libraryJacocoPlugin") {
            id = "quackquack.library.jacoco.plugin"
            implementationClass = "LibraryJacocoPlugin"
        }
        register("benchmarkPlugin") {
            id = "quackquack.benchmark.plugin"
            implementationClass = "BenchmarkPlugin"
        }
    }
}