#3장

- 컬렉션
```kotlin
//코틀린에서 컬렉션 만들기
    
    val set = hashSetOf(1, 7, 53)

    val list = arrayListOf(1, 7, 35)
    
    // 여기서 to 가 언어가 제공하는 특별한 키워드가 아니라 일반 함수라는 점에 유의 하자.
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

/**
 * println(set.javaClass) <- jacaClass 는 자바 getClass() 에 해당하는 코틀린 코드
 * 결과 => class java.util.HashSet
 */
```
- 위의 결과를 보면 코틀린은 자시만의 컬렉션 기능을 제공하지 않는다.
- 이유는?
  - 표준 자바 컬렉션을 활용하면 자바 코드와 상호작용하기가 쉽다.

#확장함수
- 마치 내가 js 에서 StringUtil 메서드를 민드는것 처럼 사용할 수 있다.
  - import 를 받아서 사용한다.
  - 확장함수는 오버라이딩 할 수 없다.
- 확장프로퍼티 - 123 페이지 처럼 프로퍼티를 받아 set 형식으로도 사용할 수 있다.
- <b>가변인자</b>
  - vararg 라는 키워드를 사용하고 *를 이용해 넘긴다.
    - https://zion830.tistory.com/124
  - 제네릭 vararg 파라미터를 제네릭 형식으로도 받을 수 있다.
```kotlin
fun showAll(vararg s: String) {
  println(s.joinToString())
}

fun main(args: Array<String>) {
  val test = arrayOf("A", "B")
  showAll(test) // error
  showAll(*test) // OK
}

//제네릭
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

val a = arrayOf(1, 2, 3)
val list1 = asList(a) // OK
val list2 = asList(-1, 0, *a, 4) // OK
```

- 중위호출

```kotlin
val map = mapOf(1 to "one")
```
  - 여기서 to 라는 단어는 코틀린 키워드가 아니라, 중위호출 이라는 특별한 방식으로 to 라는 알번 매소드를 호출한 것이다.
  - infix 변경자를 함수 선언 앞에 추가해야 한다.

#3.6 로컬 함수와 확장
- 중복을 없애는 방법을 제시한다.
- 로컬함수를 이용하여 중복된 코드를 회피할 수 있다.
  - 136p 리스트 3.12 참초 -> 3.13 까지 참조
  - 사용할만 한거 같다.필요한 경우를 생각하여, 적용해 봐야겠다.
  - 