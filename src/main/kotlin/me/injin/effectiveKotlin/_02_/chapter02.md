#2장 - 가독성성

- item11: 가독성을 목표로 설계하라.
    - 가독성을 생각해서 코드를 작성하자.
    - 사람에 따라 다르게 느낄 수 있다.
        - 숙력된 개발자만을 위한 코드는 좋은 코드가 아니다.
    - 지불할 만한 가치가 있는 코드는 사용하자.
    - 복잡하다 느껴질때는 정당한 이유가 있는지, 비용을 지불할만 한지 생각해보자.

- item12: 연산자 오보로드를 할 때는 의미에 맞게 사용하라.
    - 이름의 이미에 맞게 사용하자.
    - 연산자 의미가 명확하지 않는다면 연산자 오버로딩을 사용하지 않는 것이 좋다.
    - 꼭 같은 형태로 사용하고 싶다면 infix 확장 함수 또는 톱레벨 함수를 활용하자.
        - *톱레벨? - 클래스 또는 다른 대상 내부에 있지않고, 가장 외부에 있는 함수를 의미

- item13: Unit? 을 리턴하지 말라.
    - Unit? 은 Unit 또는 null 을 가질 수 있다.
    - 오해를 불러 일으키기 쉬우며, Unit? 을 리턴하거나, 이를 기반으로 연산하지 않는 것이 좋다.

- item14: 변수 타입이 명확하지 않은 경우 확실하게 지정하라.

```kotlin
val data = getSomeData()
```

- 위의 코드는 타입을 숨기고 있다. 가독성을 위해 코드를 설계할 때 읽는 사람에게 중요한 정보를 숨겨서는 안 된다.
- ”코드를 읽으면서 함수 정의를 보며 타입을 확인하면 되지 않나?” 라고 생각할 수도 있지만 이는 곧 가독성이 떨어진다는 의미이다.

- item15: 리시버를 명시적으로 참조하라.
    - 짧게 적을 수 있다는 이유만으로 리시버를 제거하지 말자.
    - 리시버를 명시적으로 적어 주는 것이 좋다.

- item16: 프로퍼티는 동작이 아니라 상태를 나타내야 한다.
    - 자바의 필드와는 다른 개념이다.
    - 둘다 데이터를 저장한다는 점은 같으나,
    - 기본적으로 프로퍼티는 사용자 정의(세터, 게터)를 가질 수 있다.
    - 파생프로퍼티
        - 기본적으로 다른 프로퍼티의 값에 기반하여 그 값이 결정되는 프로퍼티
    - 프로퍼티 와 함수 구분 팁
        - 이 프로퍼티를 함수로 정의할 경우 접두사로 get, set 을 붙일 것인가?
            - 맞다면 프로퍼티
            - 아니라면 함수로 만들자.
    - 원칙적으로 프로퍼티는 상태를 나타내거나 설정하기 위한 목적으로만 사용하자.
    - 대신 함수로 사용하는 것이 좋은 경우도 있다.
        - 연산 비용이 높거나 시간 복잡도가 O(1) 보다 큰 경우
            - 관습적으로 프로퍼티를 사용할때 연산 비용이 많이 필요하다고 생각하지 않는다.
        - 비즈니스 로직을 포함한 경우
            - 로깅, 리스너 통지, 바인드된 요소 변경과 같은 단순한 동작 이상을 할것이라고는 기대하지 않기때문.
        - 실행마다 다른 값이 나올 때
        - getter 에서 프롶퍼티의 상태 변경이 일어나야 하는 경우
            - 관습적으로 getter 에서 상태변화를 일으킨다고 생각하지 않는다.

- item17: Named Argument 를 사용하라.
    - 음..에매할때 사용하면 좋을듯 하다.
    - 여튼 이러할때 쓰는걸 추천한다
        - 디폴트 아규먼트의 경우
        - 같은 타입의 파라미터가 많은 경우
        - 함수 타입의 파라미터가 있는 경우(마지막 경우 제외)

- item18: 코딩 컨벤션을 지켜라.
    - 잘 지키자.
    - https://kotlinlang.org/docs/coding-conventions.html#naming-rules
    - 인텔리제이
        - Preferences(단축키 : cmd + , ) > Editor > Coding Style > Kotlin > 오른쪽 상단에 Set form.. > Kotlin Style Guide 선택




 

 

