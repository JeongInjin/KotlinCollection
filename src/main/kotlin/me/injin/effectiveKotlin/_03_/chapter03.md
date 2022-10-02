#3장 - 재사용성

- Item 19: knowledge 를 반복하여 사용하지 말라
  - Don't Repeat Yourself
  - 프로젝트에서 이미 있던 코드를 복사해서 붙여넣고 있다면 잘못된 것이다.
  - 느낀점: 너무 극단적이게 SRP 를 지키는건 아닌거 같다..매번 느끼지만 기능별로 쪼개는게 중요한거 같다.
    - 연습이 많이 필요한거 같다. 함수를 짤때 한번 더 생각을 하자.
    - 나와의 귀차니즘을 타파하자..!!부지런해 지자!!ㅋㅋㅎㅎ

- Item20: 일반적인 알고리즘을 반복해서 구현하지 말라.
  - stdlib 에 대부분의 알고리즘이 구현되어있다. 시간 되면 자주쓰이는 것들을 확인해 봐야겠다.
  - 코틀린의 확장함수 또한 살펴봐야겠다..
    - ex)coerceIn 함수같은..쓸만한 함수들을 익혀놔야겠다.
  - 표준 라이브러리에 없는 알고리즘이 필요할 경우 범용 유틸리티로 만들것을 고민해 보자.
  - https://kotlinlang.org/api/latest/jvm/stdlib/

- Item21: 일반적인 프로퍼티 패턴은 프로퍼티 위임으로 만들어라.
  - 프로퍼티 위임을 사용하여, 다양한 조건에서 적용해보자.
  - 코틀린에서는 디자인 패턴 Delegate Pattern 을 쉽게 구현할 수 있다.
  - by lay {}
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
    fun getWidth() : Int
    fun getHeight() : Int
}

open class TransparentWindow : IWindow {
    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight() : Int{
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
    fun getWidth() : Int
    fun getHeight() : Int
}

open class TransparentWindow : IWindow {
    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight() : Int{
        return 150
    }
}

class UI(window: IWindow) : IWindow by window
```