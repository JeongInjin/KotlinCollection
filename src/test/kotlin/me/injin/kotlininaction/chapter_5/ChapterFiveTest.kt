package me.injin.kotlininaction.chapter_5

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import java.util.stream.Collectors




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

        println(people.maxByOrNull { p: Person -> p.age })
        println(people.maxByOrNull { p -> p.age })
        println(people.maxByOrNull { it.age })
    }

    @Test
    fun `인자로 받은 함수 호출`() {
        twoAndThree { a, b -> a + b }
        twoAndThree { a, b -> a * b }
    }

    @Test
    fun `mapTest`() {
        var people = listOf(Person("aa", 29), Person("bb", 31))
        val map = people.map { it.name }
        println(map)
//        people.map { Person::name } =>
        val mapImproved = people.map(Person::name)
        println(mapImproved)

        val filter = people.filter { it.age > 30 }
        println(filter)
        val filterMap = people.filter { it.age > 30 }.map(Person::name)
        println(filterMap)
    }

    @Test
    fun sequenceTest() {
        var people = listOf(Person("aa", 29), Person("bb", 31))
        val toList = people.asSequence()
            .map(Person::name)
            .filter { it.startsWith("a") }
            .toList()
        println(toList)
    }

    @Test
    fun sequenceTest2() {
        val toList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence()
            .map { println("map $it"); it * it }
            .filter { println("filter $it"); it % 2 == 0 }
            .toList()
        println("================================")
        println(toList)
    }

    @Test
    fun sequenceTest3() {
        val find = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9).asSequence()
            .map { it * it }
            .find { it > 3 }
        println(find)
    }

    @Test
    fun sequenceTest4() {
        val fruits = listOf("apple", "banana", "kiwi", "cherry")
        fruits.asSequence()
            .filter {
                println("checking the length of $it")
                it.length > 5
            }
            .map {
                println("mapping to the length of $it")
                "${it.length}"
            }
            .take(1)
    }
    @Test
    fun sequenceTest5() {
        val fruits = listOf("apple", "banana", "kiwi", "cherry")

        fruits.filter {
            println("checking the length of $it")
            it.length > 5
        }
            .map {
                println("mapping to the length of $it")
                "${it.length}"
            }
            .take(1)
    }
    @Test
    fun sequenceTest6() {
        val fruits: List<String> = listOf("apple", "banana", "kiwi", "cherry")
        fruits.stream()
            .filter { name: String ->
                println("checking the length of $name")
                name.length > 5
            }
            .map { name: String ->
                println("mapping to the length of $name")
                "" + name.length
            }
            .collect(Collectors.toList())
    }


    fun twoAndThree(operation: (Int, Int) -> Int) {
        val result = operation(2, 3)
        println("The result is $result")
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





