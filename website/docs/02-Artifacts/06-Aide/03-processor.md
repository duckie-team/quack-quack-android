---
sidebar_label: 'Processor'
---

# aide-processor

본 모듈은 [`aide`](01-overview.md)의 규칙을 자동 생산합니다. 기본 작동은 [`KSP`](https://kotlinlang.org/docs/ksp-overview.html)와 [`kotlinpoet`](https://github.com/square/kotlinpoet)으로 진행됩니다.

생성하는 규칙은 `QuackComponents`, `AideModifiers`가 있습니다.

- `QuackComponents`: aide에서 감지한 꽥꽥 컴포저블 모음
- `AideModifiers`: 디자인 컴포넌트 도메인별 허용하는 데코레이터 Modifier 모음

#### `aide["text"] = listOf("span", "highlight")`

key는 컴포넌트의 도메인을 나타내고, value는 해당 도메인에서 사용 가능한 데코레이션 Modifier의 이름을 나타냅니다.

#### `aide["_span"] = emptyList()`, `aide["_highlight"] = emptyList()`

이 부분은 감지된 Modifier가 데코레이션 Modifier인지 확인할 때 `O(1)`만에 진행하기 위해 생성됩니다. 만약 `aideModifiers["_$modifierName"]` 값이 null이 아니라면 유효한 데코레이션 Modifier로 간주할 수 있습니다.

## KSP Options

// TODO

## Download ![](https://img.shields.io/maven-central/v/team.duckie.quackquack.aide/aide-processor?style=flat-square)

```kotlin
ksp("team.duckie.quackquack.aide:aide-processor:${version}")
```