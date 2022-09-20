<p align="center">
  <img src="./assets/quackquack-logo.svg" width="20%" alt="quackquack" />
</p>
<h1 align="center">QuackQuack</h1>
<h5 align="center"><a href="https://github.com/sungbinland/duckie-android">Duckie</a> Design System</h5>
<p align="center">
  <a href="LICENSE"><img alt="MIT License" src="https://img.shields.io/badge/License-MIT-blue"/></a>
  <a href="https://developer.android.com/about/versions/marshmallow"><img alt="API 23+" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg"/></a>
  <a href="https://github.com/sungbinland/duckie-android/blob/main/docs/codestyle.md"><img alt="codestyle" src="https://raw.githubusercontent.com/sungbinland/duckie-android/main/assets/codestyle-duckie.svg"/></a>
</p>

---

### Introduce

QuackQuack is a design system to be used in [Duckie](https://github.com/sungbinland/duckie-android), and it consists of a total of 5 modules.

- `ui-components`: QuackQuack designed composable collection
- `lint-core`: Lint for all codebases
- `lint-quack`: Lint that recommends using QuackQuack UI components
- `lint-compose`: Lint for Jetpack Compose codebase
- `lint-writing`:  Lint for UX Writing (WIP)

QuackQuack is a work in progress, and each lint rule used in the project will be documented soon. You can check why QuackQuack was born on the [Duckie Tech blog](https://medium.com/duckie-stories/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7) (ko).

### Preview

QuackQuack's design components can be previewed by building the Playground module(or [quack-ui.duckie.team](https://quack-ui.duckie.team/) (not recommended)). If you don't feel like building it yourself, you can download it from the [Google PlayStore](https://play.google.com/store/apps/details?team.duckie.quackquack.playground).

### Usage

WIP

### Download

QuackQuack is available in the Maven repository. [BOM](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#bill-of-materials-bom-poms) is currently not supported due to deployment [issues](https://github.com/sungbinland/duckie-quack-quack/issues/114). Once the issue is resolved, BOM deployment is enabled. (we are want for your help!)

> As said above, `quack-lint-writing` is not deployed because it's WIP.

![quack-ui-components](https://img.shields.io/maven-central/v/team.duckie.quack/quack-ui-components?color=FF8800&label=quack-ui-components&style=flat-square) ![quack-lint-core](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-core?color=FF8800&label=quack-lint-core&style=flat-square) ![quack-lint-quack](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-quack?color=FF8800&label=quack-lint-quack&style=flat-square) ![quack-lint-compose](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-compose?color=FF8800&label=quack-lint-compose&style=flat-square) ![quack-lint-writing](https://img.shields.io/maven-central/v/team.duckie.quack/quack-lint-writing?color=FF8800&label=quack-lint-writing&style=flat-square)

```kotlin
dependencies {
    implementation("team.duckie.quack:quack-ui-components:{version}")
    implementation("team.duckie.quack:quack-lint-core:{version}")
    implementation("team.duckie.quack:quack-lint-quack:{version}")
    implementation("team.duckie.quack:quack-lint-compose:{version}")
    implementation("team.duckie.quack:quack-lint-writing:{version}")
}
```

###  Build

QuackQuack is developed using the latest version of Android Studio Canary. And also requires a build configuration file. Therefore, to build, you need to unzip the `quackuser-secrets.tar` file. You can do it easily with the following command:

```bash
tar xvf quackuser-secrets.tar
```

### Contribute

We love your contribution! The easiest way for you to start contributing is to to check the [Issue](https://github.com/sungbinland/duckie-quack-quack/issues). Regardless, any contribution is welcome, just make sure you follow the contribution guide. Please check out the [CONTRIBUTING.md](.github/CONTRIBUTING.md) documentation.

### Pronounce (for Korean)

`꿱꿱` 이 아닌, `꽥꽥` 이라고 발음합니다.

### Articles (ko)

1. [Introducing Duckie's design system "QuackQuack".](https://blog.duckie.team/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7)

### Documents

1. [quack-ui.duckie.team](https://quack-ui.duckie.team/): A snapshot image of the developed QuackQuack UI components. It is updated whenever a PR with `UI` and `deploy` labels is opened. To capture a snapshot of a component, use [paparazzi](https://github.com/cashapp/paparazzi).
2. [quack-test.duckie.team](https://quack-test.duckie.team/): Full test coverage HTML report from QuackQuack. It is updated whenever a PR with `test` and `deploy` labels is opened. Test coverage measurements use [Kover](https://github.com/Kotlin/kotlinx-kover).
3. [quack-docs.duckie.team](https://quack-docs.duckie.team/): Full KDoc content from QuackQuack. It is updated whenever a PR with `dokka` and `deploy` labels is opened. HTML generation for deploy in KDoc uses [dokka](https://github.com/Kotlin/dokka). (ko)
4. [documents](/documents): Documents for internal reference of Duckie. Code rules and important notes are noted. (ko)
