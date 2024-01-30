package me.injin.exampleTest

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

}
