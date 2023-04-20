---
sidebar_label: 'Contributing'
---

# How to contribute

quack-quack-android 프로젝트에 관심을 갖고 기여해 주셔서 감사합니다. 따라야 할 몇 가지 작은 규칙이 있습니다.

## Preparing a pull request for review

수정한 부분의 테스트 코드를 작성해 주세요.

```bash
./gradlew test
```

코드 스타일도 정돈돼야 합니다.

```bash
./gradlew ktlintFormat
./gradlew detekt
```

마지막으로 빌드가 정상적으로 성공하는지 확인해 주세요.

```bash
./gradlew build
```

## Code reviews

덕키 멤버의 PR을 포함한 모든 PR은 검토가 필요합니다. 검토는 최대 일주일 내로 진행됩니다.
