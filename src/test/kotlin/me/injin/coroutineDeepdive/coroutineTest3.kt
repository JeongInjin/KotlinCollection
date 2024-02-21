package me.injin.coroutineDeepdive

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class CoroutineTest3 {
    suspend fun main() = coroutineScope {
        repeat(1_000) {
            launch { // Dispatchers.Default
                List(1000) { Random.nextLong() }.maxOrNull()

                val threadName = Thread.currentThread().name
                println("Running on thread: $threadName")
            }
        }
    }

    suspend fun main2() {
        val time = measureTimeMillis {
            coroutineScope {
                repeat(50) {
                    launch(Dispatchers.IO) { // Dispatchers.Default
                        Thread.sleep(1000)
                    }
                }

            }
        }
        println("Took $time ms")
    }

    @Test
    fun `146 페이지`() = runTest {
        main()
    }

    @Test
    fun `149 페이지`() = runTest {
        main2()
    }

    @Test
    fun `core 수`() {
        Runtime.getRuntime().availableProcessors().also { println(it) }
    }

    var i = 0;
    suspend fun main3(): Unit = coroutineScope {
        repeat(50) {
            launch(Dispatchers.IO) {
                i++
            }
        }
        println("i: $i")
    }

    @Test
    fun `154 페이지`() = runTest {
        main3()
    }

}
