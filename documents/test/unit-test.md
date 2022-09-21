# 단위 테스트

덕키는 TDD 를 기본으로 합니다. 하지만 스냅샷 테스트는 TDD 가 불가능하고 UI 테스트는 비용이 큼으로 단위 테스트만 TDD 를 진행합니다.

### 왜?

덕키에서 단위 테스트는 아래와 같은 이유로 진행합니다.

- 리팩토링 내성: 리팩토링은 "기존 결과에 영향을 미치지 않고 코드의 로직을 개선하는 행위" 를 뜻합니다. 테스트가 없다면 리팩토링 후에 기존 결과에 영향을 미치지 않았는지
  확인하기 위해 구현된 모든 로직들을 검토해야 합니다. 이는 리팩토링마다 생산성 저하를 의미하고 리팩토링을 멀리하게 되는 이유가 됩니다. 하지만 테스트 코드가 있다면 리팩토링
  후에 전체 테스트를 실행해 보는 것만으로 기존 결과에 영향을 미치지 않았는지 검토할 수 있습니다.
- 남의 코드에 대한 신뢰성: 본인 의외에 다른 사람이 작성한 코드가 내가 생각한대로 100% 작동할 것이라고 확신하는건 쉽지 않습니다. 하지만 테스트 코드가 있다면 해당 테스트가
  성공함을 확인함으로써 이 코드가 내가 생각한대로 작동한다는걸 확신할 후 있습니다.
- 남의 코드에 대한 빠른 이해: 내 코드 짤 시간도 부족한데 내 피쳐를 개발하기 위해 남이 짠 코드를 이해하겠다고 나의 소중한 시간을 할애하여 남의 코드를 해석하고 있는건 매우
  비효율적 입니다. 하지만 테스트 코드가 있다면 이 코드가 어떤 분기에서 어떻게 다른 결과를 나타내는지 쉽게 파악할 수 있게 됩니다.
- 깨끗한 코드: 테스트를 안하게 된다면 Clean Code 의 조건 중 하나인 "testable 한 코드" 조건이 무시됩니다. 그러면 자연스럽게 Clean Code 와는 점점
  멀어지게 됩니다.

### 테스트 코드 규칙

- 모든 테스트는 빠른 파악을 위해 테스트 클래스 위에 어떤 조건에서 성공하고 실패하는지를 주석으로 나타내야 합니다.

```kotlin
/**
 * 테스트 성공 조건
 * 1. 컴포저블 함수여야 함
 * 2. 컴포저블을 방출하는 역할이여야 함
 * 3. "List", "Map", "Set" 타입이 포함된 인자를 대상으로 경고해야 함 (MutableCollections)
 * 4. "Immutable", "Persistent" 타입이 포함된 인자는 경고하지 말아야 함 (ImmutableCollections)
 */
class PreferredImmutableCollectionsTest
```

- 테스트 함수 명명은 ~should, ~must,  ~can 과 같이 제안문이 아닌 ~is 같이 원하는 결과가 확정으로 들어나야 합니다. 테스트는 내가 원하는대로 잘 작동하는지를
  확인하는 절차이므로 **제안이 아닌 확정**으로 네이밍 해야 합니다.

```kotlin
@Test
fun `Don't use Public at MutableCollection`() // 잘못된 명명

@Test
fun `Using MutableCollections as public throws an error`() // 올바른 명명
```

- 테스트 코드에서는 리소스 하드 코딩이 허용됩니다. 테스트 코드는 프로덕트 코드에 반영되지 않고, 빠른 주기로 반복적인 테스트가 요구됨으로 매번 리소스를 따로 작성하는건 큰
  비용으로 다가올 수 있습니다. 따라서 테스트 코드에서는 리소스 하드 코딩이 허용됩니다.

```kotlin
fun main() {
  println("Hello, World!") // 하드 코딩이 허용됩니다.
}
```

- 모든 테스트 대역은 mock 과 stub 을 구분하여 test 폴더에 저장해야 합니다. 테스트 대역을 제품 코드의 실제 데이터와 혼동 방지와 테스트 대역을 잘못된 상황에서
  사용함을 방지하기 위함 입니다.

```kotlin
// /test/mock/SmtpMock.kt
class SmtpMock : Smtp {
  override suspend fun sendEmail(
    to: String,
    from: String,
    subject: String,
    content: String
  ): SmtpResult { /* ... */ }
}

// test/stub/UserDatabaseStub.kt
class UserDatabaseStub : Database {
  override suspend fun getFromId(
    id: Int
  ): User { /* .. */ }
}
```

### 프레임워크

덕키는 [JUnit4](https://junit.org/junit4/) 와 [Mockk](https://github.com/mockk/mockk) 를 사용합니다. JUnit5 는
아직 공식 Gradle 지원이 없을 뿐더라, 덕키팀에서 메인으로 사용하는 Jetpack Compose 의 UI 테스트 경우에도 JUnit4 를 사용합니다. Mockk 는 코틀린으로
개발됐음으로 코틀린과의 친화성을 위해 채택하였습니다.

### Mocking

덕키에서 Mock 은 위에서 말했듯이 Mockk 를 사용합니다. 하지만 Mockk 은 IO 가 없는 비즈니스 로직에만 사용합니다. IO Mocking 은 덕키에서 사용하는 IO
클라이언트인 [ktor](https://ktor.io/)
의 [MockEngine](https://api.ktor.io/ktor-client/ktor-client-mock/io.ktor.client.engine.mock/-mock-engine/index.html)
를 사용합니다.

테스트에서 테스트 대역을 사용하는 것은 테스트의 회귀 감지 능력을 저하시킵니다. 따라서 **Domain 계층**은 최대한 안드로이드 의존성이 없게 설계돼야 합니다. 덕키에서
Domain 계층은 오직 [Kotlin](https://github.com/JetBrains/kotlin) 하나의 의존성만을 갖습니다.

