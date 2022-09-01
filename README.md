<p align="center">
  <img src="./assets/toss-duck.svg" width="10%" alt="duck" />
</p>
<h1 align="center">quack-quack</h1>
<h5 align="center"><a href="https://github.com/sungbinland/duckie">덕키</a> 디자인 시스템</h5>
<p align="center">
  <a href="LICENSE"><img alt="MIT License" src="https://img.shields.io/badge/License-MIT-blue"/></a>
  <a href="https://developer.android.com/about/versions/marshmallow"><img alt="API 23+" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg"/></a>
  <a href="https://codecov.io/gh/sungbinland/duckie-quack-quack" > <img src="https://codecov.io/gh/sungbinland/duckie-quack-quack/branch/develop/graph/badge.svg?token=ACRJ1R22YD"/></a>
</p>


---

### Introduce

quack-quack 은 [duckie](https://github.com/sungbinland/duckie) 에서 사용될 디자인 시스템이며, 총 5개의 모듈로 구성돼 있습니다.

- `ui-components`: 디자인된 컴포저블 모음
- `lint-core`: 모든 코드에 공통적으로 적용될 코드 린트
- `lint-quack`: 꽥꽥 UI 컴포넌트 사용을 권장하는 린트
- `lint-compose`: 컴포즈 코드에 적용될 코드 린트
- `lint-writing`:  UX Writing 에 적용될 린트

각각 린트들의 규칙은 [sungbinland-style-guide](https://github.com/sungbinland/sungbinland-style-guide) 에서 확인하실 수 있으며, quack-quack 의 탄생기 및 목적은 [덕키 기술 블로그](https://medium.com/duckie-stories/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7)에서 확인하실 수 있습니다.

### Pronounce

`꿱꿱` 이 아닌, `꽥꽥` 이라고 발음합니다.

### Articles

1. [덕키의 디자인 시스템 “꽥꽥” 을 소개합니다.](https://blog.duckie.team/%EB%8D%95%ED%82%A4%EC%9D%98-%EB%94%94%EC%9E%90%EC%9D%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EA%BD%A5%EA%BD%A5-%EC%9D%84-%EC%86%8C%EA%B0%9C%ED%95%A9%EB%8B%88%EB%8B%A4-59d962c4bf7)

### Documents

1. [quack-ui.duckie.team](https://quack-ui.duckie.team/): 개발된 UI 컴포넌트들 스냅샷 이미지, `UI` 와 `deploy` label 이 붙은 PR 이 올라올 때마다 갱신됩니다. 컴포넌트의 스냅샷 캡처는 [paparazzi](https://github.com/cashapp/paparazzi) 를 사용합니다.
2. [quack-test.duckie.team](https://quack-test.duckie.team/): 꽥꽥의 전체 테스트 커버리지 HTML 리포트, `test` 와 `deploy` label 이 붙은 PR 이 올라올 때마다 갱신됩니다. 테스트 커버리지 측정은 [Kover](https://github.com/Kotlin/kotlinx-kover) 를 사용합니다.
3. [quack-docs.duckie.team](https://quack-docs.duckie.team/): 꽥꽥의 전체 KDoc 내용들, `dokka` 와 `deploy` label 이 붙은 PR 이 올라올 때마다 갱신됩니다. KDoc 에서 배포용 HTML 생성은 [dokka](https://github.com/Kotlin/dokka) 를 사용합니다.
4. [documents](/documents) 폴더: 덕키 내부에서 참고하기 위한 문서입니다. 코드 규칙들과 중요한 내용들이 기록돼 있습니다.

### Credit

꽥꽥 아이콘은 [토스페이스](https://toss.im/tossface)의 오리 이모지를 사용하였습니다.
