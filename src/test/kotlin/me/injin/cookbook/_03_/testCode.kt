package me.injin.cookbook._03_

import org.junit.jupiter.api.Test

class testCode {

    @Test
    fun `레시피 3_4 지원 속성 기법`() {
        val customer = Customer("hello")
        println(customer.message)
        println("=====================")
        println(customer.message)
    }

    @Test
    fun `레시피 3_6 나중 초기화를 위해 lateinit 사용하기`() {
        val example = Example()
        println(example.printName())
        println("=====================")
        example.setup()
        println(example.printName())
    }
}

// 레시피 3.4 지원 속성 기법

class Customer(val name: String) {

    val message: List<String> by lazy { loadMessages() }

    private fun loadMessages(): MutableList<String> {
        return mutableListOf(
            "Intial contract",
            "Convinced them to use Kotlin",
            "Sold training class. Sweet"
        ).also { println("Loaded messages") }
    }
}

class Example {
    lateinit var name: String

    fun setup() {
        name = "John"
    }

    fun printName() {
        if (::name.isInitialized) {
            println(name)
        } else {
            println("Name has not been initialized yet.")
        }
    }
}