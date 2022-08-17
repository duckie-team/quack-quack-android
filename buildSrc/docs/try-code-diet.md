### 코드 절약을 위해 시도해본 방법

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

// usage
private val DependencyScope.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyScope.setupJuit() {
    delegate(method = TestImplementation, libs.findLibrary("test-junit-core"))
    delegate(method = TestRuntimeOnly, libs.findLibrary("test-junit-engine"))
}
```

libs 를 정의하여 buildSrc 에서 바로 libs 를 통해 dependency 추가를 시도해 보았지만 실패함. (원인 파악 불가)

