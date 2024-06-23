#3장 - 재사용성

- Item 19: knowledge 를 반복하여 사용하지 말라
    - Don't Repeat Yourself
    - 프로젝트에서 이미 있던 코드를 복사해서 붙여넣고 있다면 잘못된 것이다.
    - 느낀점: 너무 극단적이게 SRP 를 지키는건 아닌거 같다..매번 느끼지만 기능별로 쪼개는게 중요한거 같다.
        - 연습이 많이 필요한거 같다. 함수를 짤때 한번 더 생각을 하자.
        - 나와의 귀차니즘을 타파하자..!!부지런해 지자!!ㅋㅋㅎㅎ

```text
**클래스를 변경하는 이유는 단 한가지여야 한다**
단일 책임 원칙(SRP)에 따른 코드 예시를 들자면, 하나의 클래스가 여러 기능을 담당하는 대신, 
각 클래스가 하나의 기능만을 책임지도록 설계합니다. 
예를 들어, Report 클래스가 데이터를 처리하고 결과를 출력하는 두 가지 책임을 갖는 대신, 
데이터 처리는 ReportProcessor 클래스에서, 
결과 출력은 ReportPrinter 클래스에서 담당하게 설계할 수 있습니다. 
이렇게 함으로써, 데이터 처리 방식이 변경되어도 출력 방식에는 영향을 주지 않고, 
반대의 경우도 마찬가지로 유지보수와 확장성이 향상됩니다.
```

- Item20: 일반적인 알고리즘을 반복해서 구현하지 말라.
    - stdlib 에 대부분의 알고리즘이 구현되어있다. 시간 되면 자주쓰이는 것들을 확인해 봐야겠다.
    - 코틀린의 확장함수 또한 살펴봐야겠다..
        - ex)coerceIn 함수같은..쓸만한 함수들을 익혀놔야겠다.
    - 표준 라이브러리에 없는 알고리즘이 필요할 경우 범용 유틸리티로 만들것을 고민해 보자.
    - https://kotlinlang.org/api/latest/jvm/stdlib/

- Item21: 일반적인 프로퍼티 패턴은 프로퍼티 위임으로 만들어라.
    - 프로퍼티 위임을 사용하여, 다양한 조건에서 적용해보자.
    - 코틀린에서는 디자인 패턴 Delegate Pattern 을 쉽게 구현할 수 있다.
    - by lazy {}
    - 보일러플레이트 코드를 줄일 수 있다.

- 기존코드

```kotlin
/**
 * 아래 코드를 보시면 인터페이스 IWindow, 클래스 TransparentWindow, UI가 있습니다.
 * 클래스들은 모두 IWindow 인터페이스를 상속받았습니다.
 * UI는 TransparentWindow 를 상속받지 않고, mWindow 를 클래스 내부에 갖고 있습니다.
 * 그리고 UI는 mWindow.getWidth()처럼 mWindow 의 함수를 호출해주고 있습니다.
 * 이 구조에서, UI 클래스는 TransparentWindow 의 기능을 내부 변수 mWindow 에 위임하였습니다.
 * 이 구조를 유지하기 위해 UI는 IWindow 의 인터페이스를 상속받았고 오버라이드 메소드 안에서
mWindow 의 메소드를 호출해주었습니다.
 * 여기서 형식적인 코드(boilerplate code)는 UI.getWidth()와 UI.getHeight()입니다.
 * 만약 IWindow 의 메소드가 20개라면, 20개에 대한 wrapper 메소드를 모두 작성해주어야 합니다.
 */
interface IWindow {
    fun getWidth(): Int
    fun getHeight(): Int
}

open class TransparentWindow : IWindow {
    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight(): Int {
        return 150
    }
}

class UI(window: IWindow) : IWindow {
    val mWindow: IWindow = window

    override fun getWidth(): Int {
        return mWindow.getWidth()
    }

    override fun getHeight(): Int {
        return mWindow.getHeight()
    }
}
```

- Delegate pattern 적용

```kotlin
fun main(args: Array<String>) {
    val window: IWindow = TransparentWindow()
    val ui = UI(window)
    println("Width : ${ui.getWidth()}, height: ${ui.getHeight()}")
}

interface IWindow {
    fun getWidth(): Int
    fun getHeight(): Int
}

open class TransparentWindow : IWindow {
    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight(): Int {
        return 150
    }
}

class UI(window: IWindow) : IWindow by window
```

- item22: 일반적인 알고리즘을 구현할 때 제네릭을 사용하라.
    - 타입 파라미터의 중요한 기능 중 하나는 구체적인 타입의 서브타입만 사용하게 타입을 제한하는 것.
      타입 아규먼트를 사용하는 함수를 제네릭 함수라고 부릅니다.

함수에서는 fun 키워드와 함수 이름 사이에 <T> 형태의 코드를 입력합니다.클래스와 인터페이스는 이름 뒤에 <T> 형태의 코드를 입력합니다.
타입 파라미터를 사용하면 개발자는 여러 가지 이득을 얻지만, 프로그램은 실질적인 이득이 없습니다.JVM 바이트 코드의 제한으로 인해, 컴파일 시점에 제네릭과 관련된 정보는 사라집니다.따라서 런타임 때 어떤 이득도
얻을 수 없습니다.

제네릭 제한

타입 파라미터의 중요한 기능 중 하나는 구체적인 타입의 서브타입만 사용하게 타입을 제한하는 것

```
기본 사용

// T는 Number 또는 Number의 하위 타입이어야 함
fun <T : Number> processNumber(value: T) {
    println(value.toDouble())
}

fun main() {
    processNumber(42)       // Int는 Number의 하위 타입
    processNumber(3.14)     // Double은 Number의 하위 타입
    // processNumber("string") // 오류: String은 Number의 하위 타입이 아님
}

클래스에서의 사용
제네릭 클래스를 정의할 때도 슈퍼타입 제한을 설정할 수 있습니다.

// T는 Comparable<T>를 구현한 타입이어야 함
class Box<T : Comparable<T>>(val value: T) {
    fun compareTo(other: T): Int {
        return value.compareTo(other)
    }
}

fun main() {
    val intBox = Box(42)
    val strBox = Box("Hello")

    println(intBox.compareTo(100))   // 정상 작동
    println(strBox.compareTo("World")) // 정상 작동
    // val anyBox = Box(Any()) // 오류: Any는 Comparable을 구현하지 않음
}
 
다중 상한 경계 설정
코틀린에서는 제네릭 타입 파라미터에 다중 상한 경계를 설정할 수 있습니다. 
이를 통해 제네릭 타입이 여러 인터페이스나 클래스를 구현하거나 확장하도록 제한할 수 있습니다.

// T는 Number를 상속하고, Comparable<T>를 구현해야 함
fun <T> sortNumbers(list: List<T>) where T : Number, T : Comparable<T> {
    list.sorted().forEach { println(it) }
}

fun main() {
    val numberList = listOf(3, 1, 4, 1, 5)
    sortNumbers(numberList) // 정상 작동

    // val invalidList = listOf("a", "b", "c")
    // sortNumbers(invalidList) // 오류: String은 Number를 상속하지 않음
}
```

---

- item23: 타입 파라미터의 섀도잉을 피하라.
    - 섀도잉 이란
        - 프로퍼티와 파라미터가 같은 이름을 가지게 되면, 지역 파라미터가 외부 스코프에 있는 프로퍼티를 가립니다.
    - 이름이 같은순간부터 별로인듯.

---

- Item24: 제네릭 타입과 variance 한정자를 활용하라.
    - 무공변, 공변성, 반공변성 얘기..
    - example 디렉토리 아래 Generics_Invariant test 시리즈를 참고하자..
        - Generics_Invariant: 무공변
        - Generics_Covariant: 공변성
        - Generics_Covariant: 반공변성
    - 타입 파라미터 기본적인 variance 의 동작은 무공변 이다.
    - 코틀린에서는
    - List 와 Set 타입 파라미터는 공변성 이다.(out 한정자)
    - Array, MutableList, MutableSet, MutableMap 의 타입 파라미터는 무공변(지정없음)이다.
    - 함수 타입의 파라미터 타입은 반공변성(in 한정자)이다.
    - 리턴타입은 공변성(out 한정자)이다.
        - 리턴만 되는 타입에는 out 한정자를 사용합니다.
        - 허용만 되는 타입에는 in 한정자를 사용합니다.

---

- Item25: 공통 모듈을 추출해서 여러 플랫폼에서 재사용하라.
    - 코틀린을 사용하면 널리 사용되는 대부분의 장치와 플랫폼을 대상으로 개발할 수 있으며,
    - 원하는 코드들을 재사용할 수 있다.




