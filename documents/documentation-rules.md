# 문서화 규칙

꽥꽥 코드를 작성하면서 매번 헷갈리는 문서화 규칙들을 기술합니다.

> **Note**: 이 규칙들은 꽥꽥 린트와 별개로 관리됩니다.

---

1. 변수명이 곧 문서화를 나타내는 필드들엔 주석을 명시하지 않습니다.

   ```kotlin
   // QuackMediumButton 의 Shape 임을 변수명과 구현에서 알 수 있으므로 KDoc 을 명시하지 않음
   val QuackMediumButtonShape = RoundedCornerShape(
       size = 12.dp,
   )
   ```
   
2. 모든 함수와 클래스에는 KDoc 을 작성합니다.
