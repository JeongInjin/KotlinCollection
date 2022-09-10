package me.injin.kotlininaction.chapter_5

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ChapterFiveTest {

    @Test
    fun `findTheOldsetTest`() {
        var people = listOf(Person("aa", 29), Person("bb", 31))
        findTheOldest(people)
    }

    @Test
    fun lambdaTest() {
        var people = listOf(Person("aa", 29), Person("bb", 31))
        println(people.maxByOrNull { it.age })
        println(people.maxOf { it.age })
    }

    //컬렉션을 직접 검색하기 - 람다 X
    fun findTheOldest(perple: List<Person>) {
        var maxAge = 0
        var theOldest: Person? = null
        for (person in perple) {
            if (person.age > maxAge) {
                maxAge = person.age
                theOldest = person
            }
        }
        println(theOldest)
    }
}
data class Person(val name: String, val age: Int)





