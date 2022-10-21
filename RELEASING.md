# RELEASING NOTE

##  Prerequisites & Labels

모든 배포 작동화는 PR 이 `master` 브런치로 open 됐을 때만 작동하며, 특정 label 이 중요하게 작용합니다.

### BumpType 

`BumpType` label 은 배포 과정에서 bump 할 대상을 정의합니다.

| Label                                                        | Description               |
| ------------------------------------------------------------ | ------------------------- |
| ![label-preview](https://img.shields.io/badge/BumpType__Major-E99695?style=flat-square) | Major 버전을 bump 합니다. |
| ![label-preview](https://img.shields.io/badge/BumpType__Minor-E99695?style=flat-square)                                                      | Minor 버전을 bump 합니다. |
| ![label-preview](https://img.shields.io/badge/BumpType__Patch-E99695?style=flat-square)                                                      | Patch 버전을 bump 합니다. |

### ReleaseTarget

`ReleaseTarget` label 은 배포 과정에서 release 할 대상을 정의합니다.

| Label                                                        | Description                                        |
| ------------------------------------------------------------ | -------------------------------------------------- |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__Playground-F9D0C4?style=flat-square) | `ui-components` 의 `playground` 를 release 합니다. |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__LintCore-F9D0C4?style=flat-square) | `lint-core` 를 release 합니다.                     |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__LintQuack-F9D0C4?style=flat-square) | `lint-quack` 를 release 합니다.                    |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__LintCompose-F9D0C4?style=flat-square) | `lint-compose` 를 release 합니다.                  |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__LintWriting-F9D0C4?style=flat-square) | `lint-writing` 를 release 합니다.                  |
| ![label-preview](https://img.shields.io/badge/ReleaseTarget__UiComponents-F9D0C4?style=flat-square) | `ui-components` 를 release 합니다.                 |

## 버전 관리

모든 아티팩트의 버전은 프로젝트 루트에 [versions](versions) 폴더로 관리됩니다.

| File name           | Content                                        | Description                                                  |
| ------------------- | ---------------------------------------------- | ------------------------------------------------------------ |
| `ui-components.txt` | `major=x; minor=y; patch=z;`                   | `x.y.z` 버전을 `ui-components` 배포에 사용합니다.            |
| `playground.txt`    | `major=x; minor=y; patch=z; code=n;` | `x.y.z` 을 `versionName`, `n` 을 `verionCode` 로 `playground` 릴리스에 사용합니다. |
| `lint-core.txt`     | `major=x; minor=y; patch=z;`                   | `x.y.z` 버전을 `lint-core` 배포에 사용합니다.                |
| `lint-quack.txt`    | `major=x; minor=y; patch=z;`                   | `x.y.z` 버전을 `lint-quack` 배포에 사용합니다.               |
| `lint-compose.txt`  | `major=x; minor=y; patch=z;`                   | `x.y.z` 버전을 `lint-compose` 배포에 사용합니다.             |
| `lint-writing.txt`  | `major=x; minor=y; patch=z;`                   | `x.y.z` 버전을 `lint-writing` 배포에 사용합니다.             |

자동화의 bump 과정에서 `BumpType` 과 `ReleaseTarget` label 에 맞게 각각 버전 파일에서 bump 가 진행됩니다.

## 배포 시나리오

꽥꽥은 master 브런치 하나를 메인으로 가져갑니다. 따라서 **master 브런치로 들어오는 모든 커밋은 스냅샷 버전으로 배포**되고, **정식 배포는 ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) label 을 가진 PR 이 master 브런치에 merge 됐을 때** 진행됩니다. 

### 스냅샷 배포

스냅샷 버전은 versions 폴더 안에 있는 현재 버전에서 `y` 값을 +1 하고, `z` 값을 0 으로 교체한 값을 사용합니다. 즉, **`z` 는 항상 0 으로 고정**됩니다. 이는 "스냅샷" 버전임을 강조하기 위함입니다.

**스냅샷 배포는 bump 하는 과정을 진행하지 않고, 해당 PR 에 ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) label 이 없을 때**만 진행됩니다. 

만약 `ReleaseTarget` label 이 설정되지 않았다면 스냅샷 배포는 건너뜁니다. 

### 정식 배포

**정식 배포는 해당 PR 에 ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) label 이 있을 때** 진행됩니다. 정식 배포 과정이 진행되면 bump 하는 과정을 먼저 시작하고, bump 가 진행된 이후 결정된 버전으로 publishing 을 시작합니다. bump 된 파일은 배포가 마무리된 이후 `git push` 를 통해 `master` 브런치에 바로 반영됩니다.

## Artifact 배포

꽥꽥 **아티팩트 배포는 ![label-preview](https://img.shields.io/badge/release-D4C5F9?style=flat-square) label 이 없을 때**만 진행되며, 진행 순서는 다음과 같습니다.

1. ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) :o:: 미리 정의한 `bump` gradle task 를 label 된 이름들로 진행하여 `BumpType` 과 `ReleaseTarget` label 에 맞게 bump 과정을 진행합니다.
2. ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) :x:: 미리 정의한 `setSnapshotVersion` gradle task 를 진행하여 스냅샷 버전을 설정합니다.
3. 지정된 `ReleaseTarget` label 에 맞게 publish 과정을 시작합니다.
5. ![label-preview](https://img.shields.io/badge/publish-FBCA04?style=flat-square) :o:: 새로운 버전으로 `git tag` 및 깃허브 Release 등록을 진행합니다.

> **Warning**: `BumpType` 과 `ReleaseTarget` label 이 모두 지정돼 있지 않다면 배포에 필요한 gradle task 실행에 실패하여 배포 자동화에 실패합니다.

## Playground 릴리스

꽥꽥 **플레이그라운드 릴리스는 ![label-preview](https://img.shields.io/badge/release-D4C5F9?style=flat-square) label 이 있을 때**만 진행되며, 진행 순서는 다음과 같습니다.

1. 미리 정의한 `bump` gradle task 를 진행하여 `BumpType` label 에 맞게 `playground` 의 bump 과정을 진행합니다.
2. `playground` 의 release 과정을 시작합니다. Releasing 되면서 덕키팀 슬랙에 APK 가 [Firebase App Distribution](https://firebase.google.com/docs/app-distribution) 을 통해 전달되고, AAB 가 Google Playstore 에 업데이트 요청됩니다.

> **Note**: Playground 릴리스는 Artifact 배포와는 다르게 `BumpType` label 만 요구합니다.

## 경고

모든 자동화는 최대 1개까지만 배포를 진행할 수 있습니다. **동시에 여러 배포는 불가능**합니다. 따라서 `BumpType` 과 `ReleaseTarget` label 은 각각 하나씩만 설정돼야 합니다. 여러개의 동시 배포가 필요한 경우는 수동 배포를 권장합니다.
