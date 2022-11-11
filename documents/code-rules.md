# code-rules

꽥꽥 코드를 작성하면서 매번 헷갈리는 코드 규칙들을 기술합니다.

---

## Stability 명시

- 원시 타입을 바로 사용하는 변수를 제외한 모든 변수들에는 안정성을 필수로 명시합니다.

  ```kotlin
  val int = 1 // 안정성 명시 생략
  
  @JvmInline
  value class IntWrapper(val value: Int)
  
  @Stable
  val intWrapper = IntWrapper(value = 1) // 안정성 명시 필수
  ```

- 모든 클래스는 안정성 추론이 자동으로 적용되므로 안정성 명시를 생략합니다.
