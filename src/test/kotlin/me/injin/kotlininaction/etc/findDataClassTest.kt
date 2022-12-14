package me.injin.kotlininaction.etc

import org.junit.jupiter.api.Test

class findDataClassTest {

    @Test
    fun findDataClassTest() {
        val testa = TestSealed.A(1)
        val testb = TestSealed.B(3.0)

        println("===")

        val classes = TestSealed::class.java.classes
        for (clazz in classes) {
            println(clazz.simpleName)
        }

        val testString = "A"
        val findClass = TestSealed::class.java.classes.find { it.simpleName == testString }


    }
}

sealed class TestSealed {
    // 객체별로 다른 프로퍼티
    class A(val int: Int) : TestSealed()
    class B(val double: Double) : TestSealed()
}

fun test(test: TestSealed) {
    when (test) {
        is TestSealed.A -> println("A")
        is TestSealed.B -> println("B")
    }
}