package me.injin.kotlininaction.chapter_9

import org.junit.jupiter.api.Test

internal class ChapterNineTest {

    //제네릭 고차 함수 호출
    @Test
    fun mainTest() {
        println(onHalf(3))

        val str = StringBuilder("Hello World")
        ensureTrailingPeriod(str)
        print(str)
    }

    fun <T : Number> onHalf(value: T): Double = value.toDouble() / 2.0

    fun <T> ensureTrailingPeriod(seq: T)
            where T : CharSequence, T : java.lang.Appendable {
        if (!seq.endsWith('.')) seq.append(".")
    }

    @Test
    fun reifiedTest() {
        println(isA<String>("abc"))
        println(isA<String>(123))
    }

    inline fun <reified T> isA(value: Any) = value is T

    @Test
    fun filterIsInstanceTst() {
        val items = listOf("one", 2, "three")
        println(items.filterIsInstance<String>()) //[one, three]
    }

    @Test
    fun animalTest() {
        val herd = Herd<Cat>()
        feedAnimal(herd)
    }

}

interface Animal
open class Kitten : Animal
open class Cat : Kitten()

class Herd<out T : Animal> {
    // ...
}

fun feedAnimal(animals: Herd<Animal>) {
    // ...
}


