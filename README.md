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

> **Warning**: Version 1.x.x was released for internal use for the MVP of Duckie products. Therefore, the stability/performance of some components has not been verified, so if you are interested in this project, we recommend using the 2.x.x version to be released later.

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

## Maintainer

- Design: [Hyejin Kim](https://www.behance.net/hyejinkim32)
- UI Components: [@jisungbin](https://github.com/jisungbin), [@EvergreenTree97](https://github.com/EvergreenTree97), [@goddoro](https://github.com/goddoro)
- Lints: [@limsaehyun](https://github.com/limsaehyun), [@riflockle7](https://github.com/riflockle7)

## Pronounce (for Korean)

`꿱꿱` 이 아닌, `꽥꽥` 이라고 발음합니다.

## Articles (ko)

1. [Introducing Duckie's design system "QuackQuack".](https://blog.duckie.team/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7)

## License

QuackQuack is designed and developed by 2022 SungbinLand, Team Duckie, and licensed under MIT. please see the [License](LICENSE) file.

