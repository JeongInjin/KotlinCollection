package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test
import java.util.*

class Item7Test {

    @Test
    fun nullOrFailureTest() {
        val result = someMethod(1)
        when (result) {
            is Success -> println("is Success")
            is Failure -> println("is Failure")
        }
        println("====================")
        val result2 = someMethod(2)
        when (result2) {
            is Success -> println("is Success")
            is Failure -> println("is Failure")
        }
    }

    val random = Random()

    fun someMethod(num: Int): Result<String> {
//        val num = random.nextInt(5)
        return if (num == 1) {
            println("Failure(IllegalStateException())")
            Failure(IllegalStateException())
        } else {
            Success("직업 성공")
        }
    }

    sealed class Result<out T>
    class Success<out T>(val result: T) : Result<T>()
    class Failure(val throwable: Throwable) : Result<Nothing>()
}