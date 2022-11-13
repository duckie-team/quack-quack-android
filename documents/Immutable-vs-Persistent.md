## Immutable-vs-Persistent

- Immutable: `ImmutableList`, `ImmutableSet` 등. 계약에 따라 구현자의 실제 불변성을 지정합니다.
- Persistent: `PersistentList`, `PersistentSet` 등. 변경 불가능한 컬렉션을 확장하고 수정이 적용된 영구 컬렉션의 새 인스턴스를 반환하는 효율적인 수정 작업을 제공합니다. 반환된 컬렉션은 데이터 구조의 일부를 원본 영구 컬렉션과 공유할 수 있습니다.

### 요약

- Immutable: 불변성을 보장하는 컬렉션
- Persistent: 변경 불가능한 컬렉션을 확장하고 수정이 적용된 영구 컬렉션의 새 인스턴스를 반환하는 효율적인 수정 작업을 제공합니다.
