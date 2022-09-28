package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test
import java.util.*

class Item7Test {

    @Test
    fun nullOrFailureTest() {
        someMethod()
    }

    val random = Random()

    fun someMethod(): Result<String> {
//        val num = random.nextInt(5)
        val num = 1
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