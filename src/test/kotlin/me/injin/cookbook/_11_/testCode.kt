package me.injin.cookbook._11_

import org.junit.jupiter.api.Test
import java.util.stream.IntStream
import kotlin.system.measureTimeMillis

class testCode {

    @Test
    fun `코틀린 버전 알아내기`() {
        println("The current Kotlin versionis ${KotlinVersion.CURRENT}")
    }

    @Test
    fun `11_3 완벽한 when 강제하기`() {
        printMod3(3)
        printMod3SingleStatement(1)
    }

    @Test
    fun `11_7 경과 시간 측정하기`() {
        //코틀린 표준 라이브러리의 measureTimeMillis 또는 measureNanoTime 함수를 사용한다.
        println("This machine has ${Runtime.getRuntime().availableProcessors()} processors")

        var time = measureTimeMillis {
            IntStream.rangeClosed(1, 6)
                .map { doubleIt(it) }
                .sum()
        }

        println("Sequential stream took ${time}ms")

        time = measureTimeMillis {
            IntStream.rangeClosed(1, 6)
                .parallel()
                .map { doubleIt(it) }
                .sum()
        }

        println("Parallel stream took ${time}ms")
    }

    fun doubleIt(x: Int): Int {
        Thread.sleep(100L)
        println("doubling $x with in thread ${Thread.currentThread().name}")
        return x * 2
    }

    fun printMod3(n: Int) {
        when (n % 3) {
            0 -> println("$n % 3 == 0")
            1 -> println("$n % 3 == 1")
            2 -> println("$n % 3 == 2")
        }
    }

    //else 문 없이 컴파일이되지 않는다.
    fun printMod3SingleStatement(n: Int) = when (n % 3) {
        0 -> println("$n % 3 == 0")
        1 -> println("$n % 3 == 1")
        2 -> println("$n % 3 == 2")
        else -> println("Houston, we have a problem.......")
    }

}