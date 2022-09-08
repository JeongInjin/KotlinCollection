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
  - 
