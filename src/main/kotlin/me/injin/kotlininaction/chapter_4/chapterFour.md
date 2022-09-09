#4장

- 클래스, 객체, 인터페이스


- 코틀린 인터페이스
  - 자바 8 인터페이스와 비슷하다
  - interface 키워드만 사용한다
  - override 변경자를 꼭 사용해야 한다.
    - 변경자는 실수로 사우이 클래스의 메소드를 오버라이하는 경우를 방지해준다.
    - 이를 어길시 컴파일이 안된다.
  - default 메소드를 구현할 수 있다 자바와는 달리 default 키워드 사용할 필요가 없다.

- 코틀린의 클래스와 메소드는 기본적으로 final 이다. 상속이 되지 않는다.
  - 이펙티브 자바 - "상송을 위한 설계와 문서를 갖추거나, 그럴 수 없다면 상속을 금지하라"
- 추상메소드를 선언하면 인스턴스와 할 수 없으며, 하위 클래스에서 추상 멤버를 오버라이드해야하만 하는게 보통이다.
  - 추상 멤버는 항상 열려있다. 따라서 open 변경자를 명시할 필요가 없다.
  - 추상 클래스에 속했더라도 비추상 함수는 기본적으로 final 이지만, 원한다면 open 변경자를 사용해 오버라이드를 허용할 수 있다.


- 가시성 변경자 - (자바로 치면 접근제어자 인듯)
  - internal: 모듈내부에서만 볼 수 있음.
    - 모듈은 한 번에 한거번에 컴파일되는 코틀린 파일들을 의마한다.
  - protected
    - 자바에서는 같은 패키지 안에서 protected 멤버에 접근할 수 있지만
    - 코틀린 protected 멤버는 오직 어떤 클래스나 그 클래스를 상속한 클래스 안에서만 보인다.

- 내부 클래스와 중첩 클래스
```kotlin
fun main() {
	Outer.Nested().introduce()
    
    val outer = Outer()
    val inner = outer.Inner()
    
    inner.introcudeInner()
    inner.introduceOuter()
    
    outer.text = "Changed Outer class"
    inner.introduceOuter()
}

class Outer {
    var text = "Outer class"
    
    class Nested {
        fun introduce() {
            println("Nested class")
        }
    }
    
    inner class Inner {
        var text = "Inner Class"
        
        fun introcudeInner() {
            println(text)
        }
        
        fun introduceOuter() {
            println(this@Outer.text) //바깥쪽 Outer 참조하려면 thos@Outer 라고 써야함.
        }
    }
}
```  
- 중첩클래스: class 안에 class 를 선언하여 강력한 결합을 나타낸다.
  - 외부클래스의 내용 공유안됨
- 내부클래스: class 안에 inner 키워드를 사용한다
  - 외부 클래스의 속성, 함수 사용가능
  - 내부 클래스 Inner 안에서 바깥쪽 클래스 Outer 의 참조에 접근하려면 this@Outer 라고 써야한다

- 봉인된 클래스: Sealed Class
  - sealed를 사용하지 않은 when 식
```text
아래의 코드에서 상위 클래스인 Expr에는 숫자를 표현하는 Num과 덧셈 연산을 표현하는 Sum 이라는
두 하위 클래스가 있습니다. sealed class로 선언되지 않고 
interface 로 선언된 Expr 을 Num 과 Sum 이 구현하고 있습니다.
이러한 Num 과 Sum 을 when 을 사용하여 분기하여 코드를 작성할 때 when 이 받는 인자 타입을
Expr 로 주면 두 클래스 모두 Expr 타입을 구현하고 있기에 편리하게 사용할 수 있습니다.
다만 이러한 방식으로 작성하면 Num 과 Sum 타입뿐만 아니라 Expr 을 구현한 다른 타입도 올 수 있기에
else 분기를 반드시 넣어줘야만 합니다.
```
```kotlin
interface Expr
class Num(val value: Int): Expr
class Sum(val left: Expr, val right: Expr): Expr

fun eval(e: Expr): Int =
    when(e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> // "else" 분기가 반드시 있어야 한다
            throw IllegalArgumentException("Unknown expression")
    }


```
- 코틀린 컴파일러는 when을 사용해 Expr 타입의 값을 검사할 때 꼭 디폴트 분기인 else 분기를 덧붙이게 강제합니다. 왜냐하면 Expr을 구현한 다른 클래스가 when의 인자로 들어갈 수 있는데, 이러한 상황을 컴파일러는 판단하지 못하기에 else 구문을 추가하여 그러한 상황에 대처하는 것입니다. 그래서 위 예제는 else 분기를 작성하였고, 반환할 만한 의미 있는 값이 없으므로 예외를 던졌습니다.

- 그리고 이러한 디폴트 분기가 있으면 클래스 계층에 새로운 하위 클래스를 추가하더라도 컴파일러가 when이 모든 경우를 처리하는지 제대로 검사할 수 없습니다. 따라서 새로운 클래스의 처리를 잊어버리면 디폴트 분기가 선택되기에 버그가 발생할 수 있습니다.

- sealed를 사용한 when 식
  - 코틀린에서는 이런 문제를 해결하기 위해 sealed 클래스를 사용합니다. 상위 클래스에 sealed 변경자를 붙이면 그 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있습니다.
```kotlin
// 기반 클래스를 sealed로 봉인
// 하위 클래스를 중첩 클래스로 넣어도 되고 안 넣고 따로 선언해도 됨
sealed class Expr {
    
}

// 기반 클래스를 상속받음
// 중첩해도 되고 안해도 됨
class Num(val value: Int): Expr()
class Sum(val left: Expr, val right: Expr): Expr()

fun eval(e: Expr): Int =
    // "when" 식이 모든 하위 클래스를 검사하므로 별도의 "else" 분기가 없어도 됨
    when(e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
    }
```
- when 식에서 sealed 클래스의 모든 하위 클래스를 처리한다면 디폴트 분기(else 분기)가 필요 없다는 이점이 있습니다.


- 클래스 초기화
```kotlin
class User(val nickNname: String)
```
- 클래스 이름 뒤에 오는 괄호로 둘러싸인 코드를 "주 생성자" 라고 부른다.
- constructor, init 키워드
  - init
    - 초기화 블록을 시작: 인스턴스화 될 때 실행
  - constructor
    - 추가적으로 생성자를 만들고 싶을때, 최종적으로 주 생성자인 this 를 호출해야한다
    - 코틀린에서는 기본적으로 부생성자 보다, default parameter 형식을 권장한다.
      - https://jjeda.tistory.com/24 
```kotlin
class User private constructor(
    val username: String,
    val email: String
) {
  companion object {
    fun of(username: String, email: String): User {
      return User(username, email)
    }
  }
}
//이렇게  companion object 를 통해 of() 라는 메서드를 제공해주면, 
//인스턴스화 하지않고 User 클래스 내부에 있는 메서드에 접근할 수 있습니다. 
// 이 동반객체는 private 으로 선언한 생성자에 접근할 수 있고, 그렇게 호출한 결과를 리턴합니다.

User("jjeda","jjeda@naver.com") // X
User.of(username = "jjeda", email = "jjeda@naver.com") // O
```
  - customGetter 를 사용해보자
```kotlin
class Person(/*...*/){
    //custom getter
    val isAdult: Boolean
    get() = this.age >= 20
}
```
- 인스턴스 get 할때 변경
```kotlin
  //아래와 같이 get 할때 필요에따라 커스텀 가능하다.
    class Person(name: String, var age: Int = 1) {
        val name = name
            get() = field.toUpperCase()
      //field 예약어를 사용해야하는데, name.toUpperCase() 시, name 을 호출할경우
      // getter 를 부르고 getter 안에는 name 이 있는 무한루프 상태에 빠지기때문에.
      //이를 자기자신을 가르키는 보이지 않는 필드라 backing field 라 한다.
      
      //아래와 같은 방법을 추천한다
      /**
       * 생성자 프러파티 name 을 원래대로 val name 으로 변경하고
       * toUpperCaseName 같이 사용하면 좀 더 깔끔하다.
       */
      val toUpperCaseName: String
        get() = this.name.uppercase()
    }
```
   
    
