package me.injin.coroutineDeepdive

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

class coroutineTest2 {

    data class Details(val name: String, val follwers: Int)
    data class Tweets(val text: String)
    class ApiException(val code: Int, message: String) : Throwable(message)

    fun getFollowersNumber(): Int {
        throw ApiException(500, "Service unavailable")
    }

    suspend fun getUserName(): String {
        delay(500)
        return "Kotlin"
    }

    suspend fun getTweets(): List<Tweets> {
        delay(1000)
        return listOf(Tweets("This is a tweet"))
    }

    suspend fun getUserDetails(): Details = coroutineScope {
        val name = async { getUserName() }
        val follwersNumber = async { getFollowersNumber() }
        Details(name.await(), follwersNumber.await())
    }

    @Test
    fun `131 페이지`() = runBlocking {
        val details = try {
            getUserDetails()
        } catch (e: ApiException) {
            println("Error: ${e.message}")
            null
        }

        val tweets = async { getTweets() }
        println("User: $details")
        println("Tweets: ${tweets.await()}")
    }

    @Test
    fun test() = runBlocking { // 이것은 최상위 부모 코루틴입니다.
        launch { // 부모 코루틴 내에서 새로운 자식 코루틴을 시작합니다.
            delay(1000L) // 1초 동안 지연
            println("World!") // 지연 후 메시지 출력
        }
        println("Hello") // 자식 코루틴이 완료되기 전에 바로 출력됩니다.
    }

    
}
