package me.injin.coroutineDeepdive

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class coroutineTest30 {

    @Test
    fun `30_3 test`() {
        main30_3()
    }

    @Test
    fun `30_4 test`() {
        main30_4()
    }

    @Test
    fun `30_5 test`() {
        main30_5()
    }

    @Test
    fun `30_6 test`() {
        main30_6()
    }

    @Test
    fun `30_7 test`() {
        main30_7()
    }

    @Test
    fun `30_8 test`() {
        main30_8()
    }

    @Test
    fun `30_9 test`() {
        main30_9()
    }

    @Test
    fun `30_10 test`() {
        main30_10()
    }

}


// async 코루틴 빌더 뒤에 await 를 호출하지 마세요

// 이렇게 구현하지 마세요.
//suspend fun getUser(): User = coroutineScope {
//    val user = async { repo.getUser() }.await()
//    user.toUser()
//}


// 이렇게 구현하세요.
//suspend fun getUser(): User {
//    val user = repo.getUser()
//    return user.toUser()
//}

// -------------------------------------------------------------------


fun main30_1() = runBlocking {
    val deferredList = listOf(
            async { delay(1000); println("Task 1 completed"); 1 },
            async { delay(500); throw RuntimeException("Error in Task 2"); 2 },
            async { delay(100); println("Task 3 completed"); 3 }
    )

    try {
        val results = deferredList.awaitAll() // 모든 작업을 동시에 기다림
        println("Results: $results")
    } catch (e: Exception) {
        println("Caught an exception: ${e.message}")
    }

    val individualResults = mutableListOf<Int>()
    try {
        deferredList.map {
            try {
                individualResults.add(it.await()) // 각 작업을 차례로 기다림
            } catch (e: Exception) {
                println("Caught an exception in map: ${e.message}")
            }
        }
        println("Individual Results: $individualResults")
    } catch (e: Exception) {
        println("Caught an exception: ${e.message}")
    }
}

suspend fun fetchDataFromNetwork(): String {
    // 네트워크 호출을 가정한 IO 작업
    return withContext(Dispatchers.IO) {
        // 네트워크 요청을 여기서 수행
        "데이터"
    }
}

suspend fun processData(data: String): String {
    // 데이터 처리를 가정한 CPU 집약적 작업
    return withContext(Dispatchers.Default) {
        // 데이터 처리 로직
        data.reversed() // 예시로, 데이터를 뒤집는 작업을 수행
    }
}

fun main30_2() = runBlocking {
    val data = fetchDataFromNetwork()
    val processedData = processData(data)
    println(processedData)
}


suspend fun performLongRunningTask() {
    for (i in 1..100) {
        // 시간이 많이 소요되는 연산 수행
        heavyCalculation(i)

        // 다른 코루틴에게 실행을 양보
        yield()
    }
}

suspend fun performLongRunningTask2() {
    for (i in 1..100) {
        // 시간이 많이 소요되는 연산 수행
        heavyCalculation(i)

        // 다른 코루틴에게 실행을 양보
        yield()
    }
}

suspend fun heavyCalculation(i: Int) {
    // 복잡한 계산 수행 (예시)
    println("i: $i")
    Thread.sleep(10) // 대체로, 실제 애플리케이션에서는 Thread.sleep 대신 실제 계산을 수행
}

fun main30_3() = runBlocking {
    launch {
        performLongRunningTask()
    }
    launch {
        performLongRunningTask2()
    }
    // 다른 코루틴 작업을 여기에 추가할 수 있습니다.
}

suspend fun longTask() = coroutineScope {
    launch {
        delay(1000)
        println("Done 1")
    }
    launch {
        delay(2000)
        println("Done 2")
    }
}

fun main30_4() = runBlocking {
    println("Before")
    longTask()
    println("After")
}

// 이렇게 구현하면 안 됩니다.
fun main30_5() = runBlocking(SupervisorJob()) {
    launch {
        delay(1000)
        throw Error()
    }
    launch {
        delay(1000)
        println("Done")
    }
    launch {
        delay(3000)
        println("Done")
    }
}

// withContext 를 SupervisorJob 과 함께 사용할 때도 비슷한 실수를 많이 합니다.
// 이렇게 구현하면 안 됩니다.
class NotificationsSender30_bad(private val client: NotificationClient) {
    suspend fun sendNotifications(notificationabcs: List<Notificationabc>) = withContext(SupervisorJob()) {
        for (n in notificationabcs) {
            launch {
                client.send(n)
            }
        }
    }
}

// 이렇게 구현하세요
class NotificationsSender30_good(private val client: NotificationClient) {
    suspend fun sendNotifications(notificationabcs: List<Notificationabc>) = supervisorScope {
        for (n in notificationabcs) {
            launch {
                client.send(n)
            }
        }
    }
}

// Job 으로
fun main30_6() = runBlocking {
    val scopeWithJob = CoroutineScope(Job() + Dispatchers.IO)

    scopeWithJob.launch {
        println("Task 1 is starting")
        throw Exception("Error in Task 1")
    }

    scopeWithJob.launch {
        delay(100) // Task 1에서 예외가 발생하면 이 작업은 취소됩니다.
        println("Task 2 is running") // 이 메시지는 출력되지 않습니다.
    }

    delay(500) // 충분한 실행 시간을 주기
}

// SupervisorJob 으로..
fun main30_7() = runBlocking {
    val scopeWithSupervisorJob = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    scopeWithSupervisorJob.launch {
        println("Task 1 is starting")
        throw Exception("Error in Task 1") // 이 예외는 이 코루틴에만 영향을 미칩니다.
    }

    scopeWithSupervisorJob.launch {
        delay(100) // Task 1에서 예외가 발생해도, 이 작업은 계속 실행됩니다.
        println("Task 2 is running") // 이 메시지는 정상적으로 출력됩니다.
    }

    delay(500) // 충분한 실행 시간을 주기
}

fun main30_8() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)

    // 자식 코루틴 1
    val childJob1 = scope.launch {
        delay(1000)
        println("Child coroutine 1 completed.")
    }

    // 자식 코루틴 2
    val childJob2 = scope.launch {
        delay(2000)
        println("Child coroutine 2 completed.")
    }

    // 특정 조건에서 첫 번째 자식 코루틴만 취소
    println("Cancelling child coroutine 1...")
    childJob1.cancel() // 첫 번째 자식 코루틴 취소

    // 나머지 작업이 완료되기를 기다림
    childJob2.join() // 두 번째 코루틴의 완료를 기다림
    println("Main coroutine is completed.")
}

fun main30_9(): Unit = runBlocking {
    val job = Job()
    launch(job) {
        delay(1000)
        println("Text 1")
    }

    launch(job) {
        delay(2000)
        println("Text 2")
    }

    job.join() // 여기서 영원히 대기하게 된다.
    println("Will not be prointed")
}

fun main30_10() = runBlocking {
    val job1 = launch {
        delay(1000)
        println("Text 1")
    }

    val job2 = launch {
        delay(2000)
        println("Text 2")
    }

    // 각 코루틴의 완료를 기다립니다.
    job1.join()
    job2.join()
    println("Both coroutines have completed")
}

