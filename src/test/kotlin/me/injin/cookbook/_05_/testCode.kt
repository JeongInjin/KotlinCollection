package me.injin.cookbook._05_

import org.junit.jupiter.api.Test
import java.time.LocalDate

class testCode {
    @Test
    fun `5_1 배열 다루기`() {
        val Strings = arrayOf("a", "b", "c", "d")
        val nullStringArray = arrayOfNulls<String>(5)

        for ((index, value) in Strings.withIndex()) {
            println("Index: $index maps to $value")
        }
    }

    @Test
    fun `5_2 컬렉션 생성하기`() {
        var numList = listOf(3, 1, 4, 1, 5, 9)
        var numSet = setOf(3, 1, 4, 1, 5, 9)
        var map = mapOf(1 to "one", 2 to "two", 3 to "three")

        /**
         * 기보적으로 코틀린 컬렉샨은 '불변' 이다.
         * 변경하고싶다면 아래와 같이 가변 클래스를 사용하자.
         */

        var numList2 = mutableListOf(3, 1, 4, 1, 5, 9)
        var numSet2 = mutableSetOf(3, 1, 4, 1, 5, 9)
        var map2 = mutableMapOf(1 to "one", 2 to "two", 3 to "three")

    }

    @Test
    fun `5_3 컬렉션에서 읽기 전용 뷰 생성하기`() {
        var numList = mutableListOf(3, 1, 4, 1, 5, 9)
        var numSet = mutableSetOf(3, 1, 4, 1, 5, 9)
        var map = mutableMapOf(1 to "one", 2 to "two", 3 to "three")

        var readOnlyNumList: List<Int> = numList.toList()
    }

    @Test
    fun `5_11 타입으로 컬렉션을 필터링하기`() {
        val list = listOf("a", LocalDate.now(), 3, 1, 4, "b")
        val strings = list.filter { it is String }
        for (s in strings) {
            println("s: $s")
        }

        println("==============================")

        val filterIsInstanceAny = list.filterIsInstance<Any>()
        val filterIsInstance = list.filterIsInstance<Int>()
        for (s in filterIsInstance) {
            println("s: $s")
        }

        println("==============================")

        val filterIsInstanceLocalDate = list.filterIsInstance(LocalDate::class.java)
        for (localDate in filterIsInstanceLocalDate) {
            println("localDate: $localDate")
        }

    }
}