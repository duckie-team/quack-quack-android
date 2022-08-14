# 컴포넌트들 구현 노트

## Surface

모든 컴포넌트들의 기본 컴포저블이며, Box 로 구현됨. 
content 에 BoxScope 는 불필요 할 것이라고 생각하고, BoxScope 를 노출하지 않음.

## 버튼

### 네이밍

~~버튼의 경우 radius 가 다 달라져서 `QuackButton${radius}` 로 명명함~~

radius 를 인자로 받는걸로 하고 `QuackButton` 으로 명명함

### 인자값

1. 배경 색상
2. 그림자
3. 리플 여부
4. 리플 색상
5. 버튼 텍스트
6. 버튼 텍스트 옵션
7. 클릭 람다