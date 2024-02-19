package me.injin.coroutineDeepdive

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class coroutineTest {

    @Test
    fun `94 페이지`() {
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
    fun `98 페이지`() {
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
    fun `104 페이지`() {
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

    @Test
    fun `113 페이지`() {
        runBlocking {
            launch {
                delay(1000)
                throw Error("Some error")
            }

            launch {
                delay(2000)
                println("Will not be printed")
            }

            launch {
                delay(500) // 예외 발생보다 빠릅니다.
                println("Will be printed")
            }

            launch {
                delay(2000)
                println("Will not be printed")
            }

        }
    }

    @Test
    fun `115 페이지`() {
        runBlocking {
            // try-catch 구문으로 래핑하지 마세요. 무시됩니다.
            try {
                launch {
                    delay(1000)
                    throw Error("Some error")
                }
            } catch (e: Throwable) { // 여기선 아무 도움이 되지 않습니다.
                println("will not be printed")
            }

            launch {
                delay(2000)
                println("will not be printed")
            }
        }
    }

    // 127 페이지 이렇게 구횬하면 안됩니다.
//    suspend fun getUserProfile(scope: CoroutineScope): UserProfileData {
//        val user = scope.async { getUserData() }
//        val notifucation = scope.async { getNotifications() }
//
//        return UserprofileData(user.await(), notification.await())
//    }

    @Test
    fun `128 페이지`() {
        runBlocking {
            val details = try {
                getUserDetails()
            } catch (e: Exception) {
                println("Error: ${e.message}")
                null
            }

            val tweets = async { getTweets() }
            println("User: $details")
            println("Tweets: ${tweets.await()}")
            println(details)
        }
    }

    data class Details(val name: String, val followers: Int)
    data class Tweet(val text: String)

    fun getFollowersNumber(): Int = throw Error("Service exception")
    suspend fun getUserName(): String {
        delay(500)
        return "JeongInjin"
    }

    suspend fun getTweets(): List<Tweet> {
        delay(1000)
        return listOf(Tweet("Hello, World"))
    }

    suspend fun CoroutineScope.getUserDetails(): Details {
        val name = async { getUserName() }
        val tweets = async { getFollowersNumber() }
        return Details(name.await(), tweets.await())
    }

    suspend fun longTask() = coroutineScope {
        launch {
            delay(1000)
            val name = coroutineContext[CoroutineName]?.name
            println("Task1 from $name")
        }
        launch {
            delay(2000)
            val name = coroutineContext[CoroutineName]?.name
            println("Task2 from $name")
        }
    }

    @Test
    fun `130 페이지`() = runBlocking(CoroutineName("Parent")) {
        println("Start")
        longTask()
        println("End")
    }


}
