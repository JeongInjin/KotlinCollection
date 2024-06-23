package me.injin.effectiveKotlin._03_

import org.junit.jupiter.api.Test

class Item25 {

    // raw type 이란 제네릭 타입을 사용하지 않은 타입을 말한다.
    // 절대 권장하지 않음
    val list = listOf(1, 2, 3)

    // 코틀린 같은 경우에는 언어 초기부터 제네릭이 고려되었기 때문에 ray type 객체를 만들 수 없다
    // One type argument expected for interface List<out E>
    // val list2: List = listOf(1, 2, 3)

    @Test
    fun rawTypeTest() {
        val list = listOf(1, 2, 3)
//        val list2: List = list
//        println(list2)  // [1, 2, 3]
    }

    // 타입 소거를 확인할 수 있는 코드
    fun checkStringList(data: Any) {
        // Cannot check for instance of erased type: List<String>
//        if (data is List<String>) {
        //...
//        }
        if (data is List<*>) {
            println("List<String> 타입이 맞습니다.")
        }
    }

    // 때때로 제네릭 타입 파라미터까지 추측할 수도 있다.
    fun checkMutableList(data: Collection<String>) {
        if (data is MutableList<String>) {
            println("MutableList<String> 타입이 맞습니다.")
        }
    }


}
