package me.injin.exampleTest

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class coroutineTest {

    @Test
    fun `코루틴 딥다이브 94페이지`() {
        runBlocking { // 메인 코루틴 시작
            val job = Job() // 부모 Job 생성

            launch(job) {// 첫번째 코루틴 시작
                repeat(5) { num ->
                    delay(200)
                    println("Rep$num")
                }
            }

            launch { // 독립적인 두번째 코루틴 시작
                delay(500)
                println("second")
                job.complete() // 첫 번째 코루틴의 부모 Job 을 완료 상태로 변경
            }

            job.join() // 첫 번째 코루틴 작업 완료를 기다림

            launch(job) { // 세번 째 코루틴 시작(부모 Job을 상속받음)
                println("Will not be printed")
            }

            println("Done")
        }
    }

    @Test
    fun `98페이지`() {
        runBlocking {
            val job = launch {
                repeat(1_000) { i ->
                    delay(200)
                    println("Printing $i")
                }
            }

            delay(1100)
            job.cancel()
            job.join()
            println("Cancelled successfully")
        }
    }

    @Test
    fun `104페이지`() {
        runBlocking {
            val job = launch {
                println("1000 s")
                delay(1000)
                println("1000 e")
            }
            job.invokeOnCompletion { exception: Throwable? ->
                println("Finished")
            }

            println("400 s")
            delay(400)
            println("400 e")
            job.cancelAndJoin()
            println("job.cancelAndJoin() e")
        }
    }

}
