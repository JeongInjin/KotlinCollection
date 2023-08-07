package me.injin.cookbook._07_

import org.junit.jupiter.api.Test

class testCode {

    // 주로 초기화에 사용
    @Test
    fun `7_1 apply로 객체 생성 후에 초기화하기`() {
        val person = Person().apply {
            name = "John"
            age = 30
            address = "New York"
            println("Person object: $this")
        }

        println(person)
    }

    @Test
    fun `7_2 부수 효과를 위해 also 사용하기`() {
        val person = Person().also {
            it.name = "John"
            it.age = 30
            it.address = "New York"
            println("Person object: $it")
        }

        println("Original person object: $person")
    }

    @Test
    fun `7_4 임시 변수로 let 사용하기`() {
        // 리펙토링 이전
        val numbers = mutableListOf("one", "two", "three", "four", "five")
        val resultList = numbers.map { it.length }.filter { it > 3 }
        println(resultList)

        // 리펙토링 이후
        numbers.map { it.length }.filter { it > 3 }.let {
            println(it)
            // 필요하다면 더 많은 함수를 호출
        }

        // 결과 출력이 전부라면..
        numbers.map { it.length }.filter { it > 3 }.let(::println)
    }
}

data class Person(var name: String = "", var age: Int = 0, var address: String = "")