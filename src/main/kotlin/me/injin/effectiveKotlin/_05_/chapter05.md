#5장 - 객체생성

- item33: 생성자 대신 팩토리 함수를 사용하라.
  - 생성자 역할을 대신 해 주는 함수를 팩토리 함수라 부른다.
  - 장점
    - 함수에 이름을 붙일 수 있다.
    - 함수가 원하는 형태의 타입을 리턴할 수 있다.(구체 클래스 대신 인터페이스 반환이 가능하다)
    - 호출될 때마다 새 객체를 만들 필요가 없다.

  - 팩토리 함수 들..
    1. Compaion 객체 팩토리 함수  
         팩토리 함수를 정의하는 가장 일반적인 방법은 Companion 객체를 사용하는것이다.
```kotlin
class MyLinkedList<T>(
val head : T,
val tail : MyLinkedList<T>?
){
companion object{
fun <T> of(vararg elements : T) : MyLinkedList<T>? {

     }
    }
}

val list = MyLinkedList.of(1,2)
```
- 코틀린에서는 인터페이스에서도 이러반 접근방법을 구현할수 있다.
```kotlin
 class MyLinkedList<T>(
 	val head : T,
    val tail : MyLinkedList<T>?
 ){
 
 }
interface MyList<T>{

 	companion object{
     fun <T> of(vararg elements : T) : MyLinkedList<T>? {}
   }
 }
 
 val list = MyLinkedList.of(1,2)
```
- companion 객체는 더 많은 기능을 갖고 있다.
- companion 객체는 인터페이스를 구현할 수 있고, 클래스를 상속받을 수 있다.
- companion 객체 팩토리는 값을 가질수 있어서 캐싱을 구현하거나, 가짜 객체를 생성할 수 있다.
```kotlin
abstract class ActivityFactor{
	abstract fun getIntent(context: Cotnext) : Intent
	
    fun start(context : Context)[
    	val intent = getIntent(context)
        context.startActivity(intent)
    }
   
}

class MainActivity : xxx{

companion object : ActivityFactory(){
	overide fun getIntext(cotnext: Context) Intent =
    Intent(context ,MainActivity::class.java
	}
}

//사용

val intent = MainActivity.getIntent(context)
MainActivity.start(context)
MainAcitivity.startForResult(activity, requestCode)
```

  - 확장 팩토리 함수
    - 이미 companion 객체가 존재할때, 이 객체의 함수처럼 사용할 수 있는 팩토리 함수를 만들어야 할 때에는,
      - 확장 함수를 사용하자.
```kotlin
//다음과 같이 Tool 인터페이스를 교체할 수는 없다고 가정해 보자.

interface Tool{
	compainon obejct{/*...*/}
} 
//아래와 같이 companion 객체를 활용해서 확장함수를 정의할 수 있다.

fun Tool.Compainon.createBigTool : BigTool{
}

//다만 companion 객체를 확장하려면 적어도 비어 있는 companion 객체가 필요하다.
```

---
- item34: 기본 생성자에 이름 있는 옵션 아규먼트를 사용하라.
  - 코틀린은 아규먼트 이름과 디폴트 값이 지정 가능하여, 점층적 생성자 패턴과 빌더 패턴을 사용하지 않아도 된다.
  - 점층적 생성자 패턴?
    - 생성자에 n개의 아규먼트가 있을때 기본값을 주어 생략이 가능한 방법.(기본 자바 new 인스턴스 생성 방식)
  - 빌더 패턴?
    - 생성자에 n개의 아규먼트가 있을때 기본값을 주어 직접 이름을 명시
  
---
- item35: 복잡한 객체를 생성하기 위한 DSL 을 정의하라.
  - 흐음~?

---





