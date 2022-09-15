package me.injin.kotlininaction.coroutines

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

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

    //기본 순차적 실행
    //비동기 네트워크 같은경우 순차적으로 수행을 하고싶으면 그냥 로직대로 적으면된다
    //코루틴에서는 일반코드처럼 작성하면 비동기 일지라도 순차적으로 수행하는게 기본이다.
    @Test
    fun `Sequential By default`() = runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }

    //비동기 목적으로 사용할때 async 키워드를 사용하자 + await
    //async..await.. job 을 반환했다 await 가 가능하다.
    @Test
    fun `Concurrent using async`() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    suspend fun doSomethingUsefulOne(): Int {
        println("doSomethingUsefulOne")
        delay(1000L) //실제 네트워크 통신이라던지 무거운 작업을 실행하는 것을 대체함.
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        println("doSomethingUsefulTwo")
        delay(1000L) //실제 네트워크 통신이라던지 무거운 작업을 실행하는 것을 대체함.
        return 29
    }

    //async job 이지만 지연시킬 수 있는 방법
    //start() 를 지우게되면, 아래쪽 await() 를 만나서 실행하게된다.
    @Test
    fun `Lazily started async`() = runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
            println("some computation")
            one.start()
            two.start()
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    //코틀린이 권장하는 스타일
    //함수를 만들되 globalScope 를 사용하면 exception 이 발생해도 취소가 되지않는다.
    //coroutineScope 를 사용하면 익셉션이 발생되면 취소가 가능하다.
    @Test
    fun `Structured concurrency with async`() = runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    //자식 코루틴이 취소되면 부모 코루틴에도 취소가 전달된다.

    @Test
    fun `Cancellation propagated coroutines hierarchy`() = runBlocking<Unit> {
        try {
            failedConcurrentSum()
        } catch(e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }

    suspend fun failedConcurrentSum(): Int = coroutineScope {

        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // 매우 오랫동안 계산하는 척
                42
            } finally {
                println("First child was cancelled")
            }
        }

        val two = async<Int> {
            println("Second child throws an exception")
            delay(1000L)
            throw ArithmeticException() // 코루틴이 취소된다.
        }

        val three = async<Int> {
            try {
                println("Third child")
                11
            } finally {
                println("Third child was cancelled")
            }
        }
        one.await() + two.await() + three.await()
    }

    //context and Dispatchers
    /*
    코루틴이 시작될때는 coroutine context 에서 실행된다.
    coroutine context 어떠한 요소들을 설정할 수 있다.
    job 객체는 await, cancel 등을 설정할 수 있고
    Dispatchers 는 어떤 쓰레드나, 쓰레드풀에서 실행될지 결정할 수 있다.
    */
    /**
        코루틴 문맥에는 코루틴이 어느 스레드에서 동작할지를 결정하는 CoroutineDispatcher 가 포함된다.
        dispatcher 는 코루틴이 특정 스레드에서 실행되도록 할 수도 있고, 스레드 풀에서 실행되도록 할 수도 있고,
        또는 어느 스레드에서 실행될지 결정하지 않은 상태로 놔 둘 수도 있다.
        launch 와 async 같은 모든 코루틴 빌더에서 CoroutineContext 매개변수를 지정할 수 있다.
     */
    @Test
    fun dispatchersTest() = runBlocking<Unit> {
        launch { // parent(main)에서 작동
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // 결정되지 않음: main에서 작동할 것
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        //코틀린 공식 문서에서는 일반적인 경우 Dispatcher.Unconfined의 사용을 권장하지 않는다. 원문은 다음과 같다.
        launch(Dispatchers.Default) { // DefaultDispatcher가 결정함
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // 자기만의 스레드를 생성
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
        //아래와 같은 방법으로 사용하는게 더 좋다.use 키워드를 이용해 자원을 반납할 수 있다.
        newSingleThreadContext("MyOwnThread").use {
            launch(it) {
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }
}


















