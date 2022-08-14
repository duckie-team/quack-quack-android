# QQ-Layout 구현 노트

### 처음에 생각했던 방법

```kotlin
private typealias ColumnContent = @Composable ColumnScope.() -> Unit

@LayoutScopeMarker
@Immutable
interface QqColumnScope {
    @Stable
    fun header(content: ColumnContent)

    @Stable
    fun content(content: ColumnContent)

    @Stable
    fun footer(content: ColumnContent)
}

private object QqColumnScopeInstance : QqColumnScope {
    override fun header(content: ColumnContent) {
        TODO("Not yet implemented")
    }

    override fun content(content: ColumnContent) {
        TODO("Not yet implemented")
    }

    override fun footer(content: ColumnContent) {
        TODO("Not yet implemented")
    }
}
```

content scope 로 header, content, footer 만 정의해서 넘겨주는 방법. 하지만 이렇게 한다면 header, content, footer 의
composable 을 얻어올 방법이 없고, 만약 방법을 만든다면 QqColumnScopeInstance 를 class 로 만들어서 값을 받아와야 함. 하지만
QqColumnScopeInstance 를 class 로 만들면 매번 컴포지션마다 새로운 인스턴스를 만들게 되어 매우 비효율 적으로 예상됨.

또한 ColumnScope 도 같이 사용할 수 있게 하는것도 문제였음

```kotlin
private class QqColumnScopeInstance(columnScope: ColumnScope) : QqColumnScope,
    ColumnScope by columnScope {

    lateinit var header: ColumnContent
    lateinit var content: ColumnContent
    lateinit var footer: ColumnContent

    override fun header(content: ColumnContent) {
        header = content
    }

    override fun content(content: ColumnContent) {
        this.content = content
    }

    override fun footer(content: ColumnContent) {
        footer = content
    }
}
```

위 코드는 QqColumnScopeInstance 에서 ColumnScope 까지 사용할 수 있게 하고, header, content, footer 의 컴포저블을 가져올 수 있게
구현한 코드임. 문제점은 header, content, footer 의 컴포저블을 얻어오기 위함과 ColumnScope 를 델리게이트 하기 위해 인자로 ColumnScope 의
구현을 받아야 함. 따라서 object 가 아닌 class 로 QqColumnScopeInstance 구현을 요구하고 이는 QqColumn 을 쓸 때마다 새로운
QqColumnScopeInstance 인스턴스가 생성됨을 의미함.

이러한 이유로 위와 같은 구현은 순위에서 제거됨.

#### 해결 방법

이 방법에서 문제는 매번 컴포지션마다 QqColumnScopeInstance 의 새로운 인스턴스가 만들어지는 거였음. 그렇다면 QqColumnScopeInstance 을
remember 로 감싼다면? :bulb:

### 다시 생각해본 방법

header, content, footer 를 각각 인자로 받는 방법도 생각해 봤음. 하지만 이렇게 한다면 각 계층간 state 공유가 필요할 수도 있지만, 이렇게 인자로 계층을
아예 분리시켜 버리면 state 를 공유 받을 방법이 전역 state 말고는 없음. 예를 들어 header 에 TextField 가 있고, 이 TextField 의
TextFieldValue state 를 통해 content 를 구성해야 할 때가 있을 수 있음.

---

### 채택한 방법

처음에 생각한 방법에서 해결 방법을 이용하여 구현하는 것이 다시 생각해본 방법의 문제 점을 보안할 수 있는 유일한 구현이라고 판단해서 이 방법을 채택함.

### 구현 방법

Column 이 아닌 ConstraintLayout 을 이용해 구현함.

```kotlin
Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceBetween
) {
    Box( // header
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.Black)
    )
    Box( // content
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(color = Color.Green)
    )
    Box( // footer
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.Blue)
    )
}
```

이렇게 Column 으로 구현했다면 content 에서 fillMaxSize 를 하게 되면 footer 가 content 에 가려져서 안보임. ConstraintLayout 을
이용하여 특정 영역의 경계를 확실하게 나누는 것으로 결정함.

