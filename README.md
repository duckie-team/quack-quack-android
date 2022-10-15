<p align="center">
  <img src="./assets/logo-icon.svg" width="20%" alt="quackquack" />
</p>
<h1 align="center">QuackQuack</h1>
<h5 align="center"><a href="https://github.com/duckie-team/duckie-app">Duckie</a> Design System</h5>
<p align="center">
  <a href="LICENSE"><img alt="MIT License" src="https://img.shields.io/badge/License-MIT-blue"/></a>
  <a href="https://developer.android.com/about/versions/marshmallow"><img alt="API 23+" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg"/></a>
  <a href="https://github.com/duckie-team/duckie-app/blob/main/documents/codestyle.md"><img alt="codestyle" src="https://raw.githubusercontent.com/duckie-team/duckie-app/main/assets/codestyle-duckie.svg"/></a>
</p>

---

# ‼️ WIP ‼️

## Introduction

QuackQuack is a design system and Duckie team's code-style custom lint to be used in [Duckie](https://github.com/duckie-team/duckie-app-mvp). QuackQuack consists of a total of 5 modules.

- `ui-components`: QuackQuack designed composable collection
- `lint-core`: Lint for all codebases
- `lint-quack`: Lint that recommends using QuackQuack UI components instead of Material components
- `lint-compose`: Lint for Jetpack Compose codebase
- `lint-writing`:  Lint for UX Writing

If you are ok in Korean, you can check why QuackQuack was born on the [Duckie tech blog](https://blog.duckie.team/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7).

## Preview

QuackQuack's design components can be previewed by building the Playground module. If you don't feel like building it yourself, you can download it from the [Google PlayStore](https://play.google.com/store/apps/details?team.duckie.quackquack.playground).

## Download

QuackQuack is available in the Maven repository. [BOM](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) is currently not supported due to [publishing issues](https://github.com/sungbinland/duckie-quack-quack/issues/114). Once the issue is resolved, BOM is published. (we are want for your help!)

> **Warning**: Version 1.x.x was released for internal use for the MVP of Duckie products. Therefore, the stability of some components has not been verified, so if you are interested in this project, we recommend using the 2.x.x version to be released later.

![quack-ui-components](https://img.shields.io/maven-central/v/team.duckie.quack/quack-ui-components?label=quack-ui-components&style=flat-square) ![quack-lint-core](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-core?label=quack-lint-core&style=flat-square) ![quack-lint-quack](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-quack?label=quack-lint-quack&style=flat-square) ![quack-lint-compose](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-compose?label=quack-lint-compose&style=flat-square) ![quack-lint-writing](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-writing?label=quack-lint-writing&style=flat-square)

```kotlin
dependencies {
    implementation("team.duckie.quack:quack-ui-components:{version}")
    implementation("team.duckie.quack:quack-lint-core:{version}")
    implementation("team.duckie.quack:quack-lint-compose:{version}")
    implementation("team.duckie.quack:quack-lint-quack:{version}")
    // implementation("team.duckie.quack:quack-lint-writing:{version}")
}
```

## Usage

To be written...

##  Build

**QuackQuack is developed using the latest version of Android Studio Canary**. And also requires a build configuration file. Therefore, to build, you need to unzip the `quackuser-secrets.tar` file. You can do it easily with the following command:

```bash
tar xvf quackuser-secrets.tar
```

## Contribute

> We love your contribution! 

Duckie's team is all Korean, so we're not good at English. So, most of the documentation was done in Korean. If you are familiar with Korean and English, perhaps the easiest and first contribution you can make is the English translation of documents. It will take some time, but it's a worthwhile incredible contribution for all of us, and we love English grammar improvements too, not just translations!

Anyway, *any* contribution is welcome, just make sure you follow the [contribution guide](.github/CONTRIBUTING.md).

## Maintainers

- Design: [Hyejin Kim](https://www.behance.net/hyejinkim32)
- Documentations & Automations: [@jisungbin](https://github.com/jisungbin)
- UI Components: [@jisungbin](https://github.com/jisungbin), [@EvergreenTree97](https://github.com/EvergreenTree97), [@goddoro](https://github.com/goddoro)
- Lints: [@limsaehyun](https://github.com/limsaehyun), [@riflockle7](https://github.com/riflockle7)

## Tech Stacks (Korean)

꽥꽥은 [androidx](https://github.com/androidx/androidx)(Gradle), [NowInAndroid](https://github.com/android/nowinandroid)(Gradle Convention Plugins), [DroidKaigi](https://github.com/DroidKaigi/conference-app-2022)(Github Actions), [Paparazzi](https://github.com/cashapp/paparazzi)(Github Actions) 를 적극 참고하여 개발됐습니다. 이 중 가치있게 생각되는 것들을 기록합니다.

### Automations

[Github Actions](https://github.com/features/actions) 를 활용하여 CI/CD 를 진행하고 있습니다.

#### [ALL] GRADLE_OPTS

`-Dorg.gradle.daemon=false` 

모든 자동화 워크플로우에서는 [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html) 을 비활성화 합니다. 이는 자동화에서 할당된 Worker 가 이전 Job 과 동일한 Worker 라는 보장을 할 수 없기 때문입니다. 

> **Note**: 모든 자동화 워크플로우에서는 [Gradle Build Action](https://github.com/marketplace/actions/gradle-build-action) 액션을 통해 [Build Caching](https://docs.gradle.org/current/userguide/build_cache.html) 을 하고 있습니다. 따라서 이 조건 하에는 [incremental build](https://docs.gradle.org/current/userguide/performance.html#incremental_build) 를 사용하는 것이 이득을 볼 수 있습니다.

#### Android CI (android-ci.yml)

`master` 브런치로 PR 이 올라올 때마다 실행되며, 빌드 여부와 테스트 커버리지를 검사합니다. 또한 UI 컴포넌트들의 스냅샷 이미지를 배포합니다.

- 프로젝트 빌드
  - `./gradlew build` 를 사용하여 프로젝트 빌드, 코드 린트 검사, 테스트 등을 진행합니다.
  - 만약 빌드 실패시 [Gradle Build Scan](https://scans.gradle.com/) 링크를 해당 PR 의 댓글로 첨부합니다.
  - 새로운 코드가 정상적으로 빌드되는지 자동으로 검사하기 위한 과정입니다.
- 테스트 커버리지 검사
  - [Kover](https://github.com/Kotlin/kotlinx-kover) 를 이용하여 테스트 커버리지를 검사합니다.
  - 커버리지 결과를 가지고 리드미 Badge 업데이트와 해당 PR 에 comment 첨부를 진행합니다. 각각 액션으로 [Kotlinx Kover Report](https://github.com/marketplace/actions/kotlinx-kover-report) 와 [Badge Action](https://github.com/marketplace/actions/badge-action) 를 이용합니다.
  - 전체 코드의 테스트 커버리지를 자동으로 추적하기 위한 과정입니다. 덕키는 테스트 커버리지 50% 이상을 목표로 합니다.
- UI 컴포넌트들의 스냅샷 이미지 배포
  - 새로운 UI 컴포넌트를 merge 하기 전에 미리 UI 를 확인해 보기 위해 각각 컴포넌트들의 스냅샷을 캡처하여 [snapshots.duckie.team](https://snapshots.duckie.team/quack) 으로 배포합니다.
  - 스냅샷을 에뮬레이터나 실기기 없이 CI 환경에서 캡처하기 위해 [Paparazzi](https://github.com/cashapp/paparazzi) 를 사용합니다.
  - 매번 UI 를 확인하기 위해 해당 브런치를 다운받고 빌드하는 것이 너무 번거로워 UI 컴포넌트들의 스냅샷 캡처부터 캡처 이미지들 배포까지의 모든 과정을 자동화합니다.
  - 단순 UI 컴포넌트 확인 의외에 다양한 font scale 과 device scale 에서도 의도한대로 디자인이 잘 나오는지 확인할 수 있습니다. 덕키에서는 1배수와 1.5배수의 font scale 과 일반과 테블릿의 device scale 에서 스냅샷 캡처를 진행합니다.

#### Artifacts Snapshot (artifact-snapshot.yml)

PR 이 `master` 브런치로 merge 될 때마다 실행되며, 꽥꽥 아티팩트의 스냅샷 버전과 새로운 플레이그라운드를 배포합니다.

- 스냅샷 배포
  - `Android CI` 작업에 의해 빌드 성공이 검증된 상태이므로 별도의 빌드 확인 과정없이 바로 스냅샷 배포를 진행합니다.
  - ... TODO
  - 항상 최신 버전의 아티팩트를 사용할 수 있게 스냅샷 버전 배포를 자동화합니다.
- 새로운 플레이그라운드 배포
  - 꽥꽥의 UI 컴포넌트는 빠른 미리보기를 위해 [플레이그라운드](/playground)를 제공합니다. 해당 플레이그라운드는 [플레이스토어](https://play.google.com/store/apps/details?team.duckie.quackquack.playground)와 [firebase app distribution](https://firebase.google.com/docs/app-distribution) 으로 배포됩니다. 
  - 플레이스토어 배포는 [Upload Android Release to Play Store](https://github.com/marketplace/actions/upload-android-release-to-play-store) 액션을 이용하며, 모두가 다운로드할 수 있게 배포됩니다. firebase app distribution 은 [Firebase App Distribution](https://github.com/marketplace/actions/firebase-app-distribution) 액션을 이용하며, 플레이스토어 심사 과정 없이 덕키팀 내부에서 먼저 확인해보기 위해 덕키팀을 대상으로 배포됩니다.
  - 매번 두 가지의 배포를 수동으로 진행하는 것은 번거로운 일이므로 이를 자동화하기 위한 과정입니다.
  - firebase app distribution 으로 배포된 플레이그라운드를 가지고 덕키팀 디자이너가 UI 컴포넌트가 올바르게 나왔는지 확인합니다. 만약 문제가 있다면 수정을 진행하고, 완벽하다면 다음 feature 개발을 진행합니다.

##### [ALL] Publishing & Bump Target

모든 배포는 해당 PR 의 label 로 지정된 target 을 기준으로 진행되며, `publish` 하기 전에 해당 target 의 버전을 bump 하는 단계가 진행됩니다. 꽥꽥 아티팩트의 모든 버전은 [quackquack-version](/quackquack-version) 폴더 안에 있는 텍스트 파일로 관리됩니다. 

- [GitHub Script](https://github.com/marketplace/actions/github-script) 액션으로 PR 의 label 에서 target 만을 추출한 후, 해당 target 으로 `./gradlew bumpVersion ` 을 실행하여 bump 를 진행합니다. `bumpVersion` task 는 프로젝트 루트 [build.gradle.kts](build.gradle.kts) 에서 추가하고 있습니다.
- bump 로 변경된 버전 파일을 `git push` 하는 것으로 bump 과정이 마무리됩니다.
- 버전 관리를 매번 수동으로 진행하는 것은 번거롭고 착고 가능성이 있어서 이를 자동화하기 위한 과정입니다.

#### Artifacts Publish (artifact-publish.yml)

`master` 브런치로 merge 된 PR 에 `/publish` comment 가 추가될 때마다 실행되며 성공적으로 빌드됐다면 새로운 버전을 배포합니다.

- 아티팩트 빌드
  - 배포된 아티팩트는 여러 환경에서 실행될 수 있으므로 `MacOS`, `Windows`, `ubuntu` 환경에서 빌드를 각각 진행합니다. 또한 여러 자바 버전도 사용될 수 있으므로 LTS 에 해당되는 [11, 12, 16, 18] 버전을 기준으로 빌드를 진행합니다.
  - 만약 빌드 실패시 [Gradle Build Scan](https://scans.gradle.com/) 링크를 해당 PR 의 댓글로 첨부합니다.
  - 아티팩트가 모든 환경에서 정상적으로 빌드되는지 검사하기 위한 과정입니다.
- 아티팩트 배포
  - [gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin) 을 이용하여 MavenCentral 에 배포를 진행합니다. 
  - 배포가 성공적으로 진행됐다면 GitHub Release 와 Git Tag 를 하는 것으로 배포 작업이 마무리됩니다.
  - 매번 수동 배포는 번거롭고 배포 키를 가진 사람만 배포를 할 수 있다는 문제가 있어서 이를 자동화하기 위한 과정입니다.

> **Note**: 아티팩트의 대상인 Sonatype 은 동시 업로드를 별도의 것으로 보고 각각 두 개의 staging repository 를 생성합니다 [[#]](https://github.com/JakeWharton/dagger-reflect/pull/38). 이를 방지하기 위해 `--no-parallel` 옵션이 필요하지만, 꽥꽥의 경우에는 배포할 모듈을 하나로 특정지어 `publish` 하므로 이 옵션이 필요하지 않습니다. 

### Gradle Convention Plugins

모든 Gradle 에는 [Gradle Convention Plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html) 을 적용합니다. 반복적인 Gradle 코드를 줄이고, 최소한의 변경으로 공통되는 모든 부분에 변경 사항을 반영하기 위해 사용합니다.

#### Android Application, Android Library

> [[1]](build-logic/src/main/kotlin/AndroidApplicationPlugin.kt), [[2]](build-logic/src/main/kotlin/AndroidLibraryPlugin.kt)

기본적인 `com.android.application` 또는 `com.android.library` 을 구성합니다. 추가로 벤치마크를 위해 `benchmark` variant 도 추가합니다.

#### Android Lint, Android Lint Common

> [[1]](build-logic/src/main/kotlin/AndroidLintPlugin.kt), [[2]](build-logic/src/main/kotlin/AndroidCommonLintPlugin.kt)

`com.android.lint` 를 구성하고 린트 개발에 필요한 의존성들을 추가합니다.

#### Android Compose [Application] [Library], Android Library Compose UI Test

>  [[1 - Application]](build-logic/src/main/kotlin/AndroidApplicationComposePlugin.kt) [[1 - Library]](build-logic/src/main/kotlin/AndroidLibraryComposePlugin.kt), [[2]](build-logic/src/main/kotlin/AndroidLibraryComposeUiTestPlugin.kt)

각각 variant 에 맞게 [Jetpack Compose](https://developer.android.com/jetpack/compose) 사용 환경을 구성합니다. 또한 `Library` variant 에서는 [Compose UI Test](https://developer.android.com/jetpack/compose/testing) 사용 환경도 추가로 구성합니다. 현재 꽥꽥 프로젝트에서는 `ui-components` 모듈만 UI 테스트가 필요하고, 해당 모듈이 `Library` variant 로 구성돼 있습니다.

#### UI Components Benchmark

> [[1]](build-logic/src/main/kotlin/AndroidQuackUiComponentsBenchmarkPlugin.kt)

`com.android.test` 와 [Macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview) 을 구성합니다. `ui-components` 모듈을 대상으로 [baseline profiles](https://developer.android.com/topic/performance/baselineprofiles/overview) 을 추출하기 위해 사용됩니다.

#### Jvm Dokka, Jvm Kover, Jvm Library

> [[1]](build-logic/src/main/kotlin/JvmDokkaPlugin.kt), [[2]](build-logic/src/main/kotlin/JvmKoverPlugin.kt), [[3]](build-logic/src/main/kotlin/JvmLibraryPlugin.kt)

각각 [dokka](https://github.com/Kotlin/dokka), [kover](https://github.com/Kotlin/kotlinx-kover), `java-library` 를 구성합니다.

#### Artifacts Publishing

> [[1]](build-logic/src/main/kotlin/AndroidQuackPublishPlugin.kt)

[gradle-maven-publish-plugin](https://github.com/vanniktech/gradle-maven-publish-plugin) 을 구성합니다. 꽥꽥의 Conventions Plugins 중에 유일하게 별도 [extensions](build-logic/src/main/kotlin/team/duckie/quackquack/convention/QuackPublishExtension.kt) 을 만들어 진행됩니다.

```kotlin
open class QuackPublishExtension {
    open lateinit var type: QuackArtifactType
    internal val isNotInitialized get() = !::type.isInitialized
    override fun toString() = "artifactName: ${type.artifactId}, " +
            "description: ${type.description}"
}

sealed class QuackArtifactType(
    val artifactId: String,
    val description: String,
) {
    object UiComponents : QuackArtifactType(
        artifactId = "quack-ui-components",
        description = "Duckie's design system core module",
    )

    object LintCore : QuackArtifactType(
        artifactId = "quack-lint-core",
        description = buildLintArtifactDescription(
            target = "Duckie codebase",
        ),
    )

    object LintQuack : QuackArtifactType(
        artifactId = "quack-lint-quack",
        description = buildLintArtifactDescription(
            target = "QuackQuack ui components",
        ),
    )

    object LintCompose : QuackArtifactType(
        artifactId = "quack-lint-compose",
        description = buildLintArtifactDescription(
            target = "Jetpack Compose codebase",
        ),
    )
}
```

꽥꽥에서 배포 가능한 아티팩트들을 미리 타입으로 한정지어 각각 아티팩트에 맞는 groupId 와 artifactId 를 하드코딩합니다. groupId 와 artifactId 를 한 곳에서 관리하여 추후 유지보수하기 편하고, 오탈자 방지를 위해 도입됐습니다.

주어진 아티팩트 타입에 따라 배포 플러그인을 자동으로 구성해주기 때문에 실제 사용에서는 단 3줄로 모든 배포 준비가 끝납니다.

```kotlin
import team.duckie.quackquack.convention.QuackArtifactType

plugins {
    id(ConventionEnum.AndroidQuackPublish)
}

quackArtifactPublish {
    type = QuackArtifactType.UiComponents
}
```

### UI Components

꽥꽥의 UI 컴포넌트들은 다양한 스케일의 UI 를 빠른 속도로 개발하기 위해 모두 [Jetpack Compose](https://developer.android.com/jetpack/compose) 로 개발됐습니다. 또한 사용자의 접근성을 1순위로 고려하였습니다.

- 넉넉한 터치 영역 ([touch target size](https://support.google.com/accessibility/android/answer/7101858)): 기본적으로 터치 영역이 작은 컴포넌트들은 주어진 터치 영역을 그대로 사용하는게 아닌 컴포넌트 마진의 절반 값을 패딩으로 적용하여 해당 패딩만큼 추가로 터치 영역을 가져가게 설계했습니다.
- 유동적인 컴포넌트 사이즈: 대부분 컴포넌트들의 사이즈를 정적으로 고정시켜두는게 아닌 컴포넌트 내부 패딩으로 사이즈를 결정합니다. 따라서 다양한 font scale 에도 컴포넌트 텍스트의 뭉개짐 없이 완벽한 비율의 컴포넌트를 볼 수 있습니다. 추가로, font scale 에 따라 아이콘의 사이즈도 같이 조정됩니다.
- 아름다운 UI 전환: 모든 컴포넌트들에는 변화 애니메이션이 적용돼 있습니다. 예를 들어 아이콘의 경우라면 틴트 색상 변화 및 아이콘 리소스 변화에 애니메이션이 적용되고, 텍스트의 경우라면 보여지고 있는 텍스트가 변할 때 애니메이션이 적용됩니다.

| Quack Components |                            Animated Result                             |
|:----------------:|:----------------------------------------------------------------------:|
|  QuackHeadLine1  | <img src="assets/ui-components/animated-quacktext.mp4" width="33%" />  |
|    QuackImage    | <img src="assets/ui-components/animated-quackimage.mp4" width="33%" /> |

### Lints

To be written...

## Pronounce (Korean)

`꿱꿱` 이 아닌, `꽥꽥` 이라고 발음합니다.

## Articles (Korean)

1. [Introducing Duckie's design system "QuackQuack".](https://blog.duckie.team/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7)

## License

QuackQuack is designed and developed by 2022 SungbinLand, Team Duckie, and licensed under MIT. please see the [License](LICENSE) file.
