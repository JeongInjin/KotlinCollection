package me.injin.kotlininaction.coroutines

import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.concurrent.thread

class CoroutinesTest {

    fun now() = ZonedDateTime.now().toLocalDateTime().truncatedTo(ChronoUnit.MILLIS)

    fun log(msg: String) = println("${now()}: ${Thread.currentThread()}: $msg")

    fun launchInGlobalScope() {
        GlobalScope.launch { log("coroutine started") }
    }

    //GlobalScope.launch 가 만들어낸 코루틴이 서로 다른 스레드에서 실행된다.
    @Test
    fun mainTest() {
        log("main() started")
        launchInGlobalScope()
        log("launchInGlobalScope() executed")
        Thread.sleep(500L)
        log("main() terminated")
    }



    @Test
    fun testCoroutines() = runBlocking {
        CoroutineScope(Dispatchers.Default)
            .launch {
                delay(1000L)
                println("scope.launch")
            }
        println("println")
        delay(1500L)
        //println
        //scope.launch
    }

    //yield example
    @Test
    fun yieldExample() {
        runBlocking {
            launch {
                log("1")
                yield()
                log("3")
                yield()
                log("5")
            }
            log("after first launch")
            launch {
                log("2")
                delay(1000L)
                log("4")
                delay(1000L)
                log("6")
            }
            log("after second launch")
        }
    }

    @Test
    fun repeatCoroutineTests() = runBlocking {
        repeat(100_000) {
            launch {
                delay(1000L)
                print(".")
            }
        }

        repeat(100_000) {
            thread {
                Thread.sleep(1000L)
                print(".")
            }.run()
        }
    }
}