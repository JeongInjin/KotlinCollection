package me.injin.coroutineDeepdive

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test


class CoroutineTest29 {

    @Test
    fun `29_1 test`() {
        main_29_1()
        /*
        send: Notificationabc(message=Message #4)
        send: Notificationabc(message=Message #3)
        send: Notificationabc(message=Message #0)
        send: Notificationabc(message=Message #2)
        send: Notificationabc(message=Message #1)
        NotificationsSender를 통해 모든 알림이 전송되었습니다.
        */
    }

    @Test
    fun `29_2 test`() {
        main_29_2()
        /*
        send: Notificationabc(message=Message #0)
        send: Notificationabc(message=Message #1)
        send: Notificationabc(message=Message #2)
        send: Notificationabc(message=Message #3)
        send: Notificationabc(message=Message #4)
        NotificationsSender2를 통해 모든 알림이 전송되었습니다.
        */
    }
}

// 코루틴 스코프 객체에서 실행되는 일반 함수
// launch: 코루틴을 시작하는 함수로, 새로운 코루틴을 생성하고 즉시 반환합니다. 이렇게 생성된 코루틴은 비동기적으로 실행됩니다.
// supervisorScope: 코루틴 스코프를 생성하는 함수로, 이 스코프 내에서 시작된 모든 코루틴은 부모 코루틴의 생명주기에 종속됩니다.
// supervisorScope 는 자식 코루틴 중 하나에서 발생한 예외가 다른 자식 코루틴에 영향을 주지 않도록 합니다.
class NotificationsSender(
        private val client: NotificationClient,
        private val notificationScope: CoroutineScope,
) {

    // 시작한 코루틴을 기다리지 않습니다.
    // 예외는 스코프에서 처리됩니다.
    // 스코프로부터 컨텍스트를 받으며, 스코프와 관계를 유지합니다.
    fun sendNotification(notifications: List<Notificationabc>) {
        for (n in notifications) {
            notificationScope.launch {
                client.send(n)
            }
        }
    }
}

// 중단 함수
class NotificationsSender2(private val client: NotificationClient) {

    // 코루틴을 기다립니다.
    // 예외를 처리합니다.
    // 컨텍스트를 받고 시작한 코루틴과 관계를 유지합니다.
    suspend fun sendNotifications(notificationabcs: List<Notificationabc>) = supervisorScope {
        for (n in notificationabcs) {
            launch {
                client.send(n)
            }
        }
    }

    suspend fun sendNotifications_try_catch(notificationabcs: List<Notificationabc>) = supervisorScope {
        for ((index, n) in notificationabcs.withIndex()) {
            launch {
                try {
                    if (index == 2) throw Error("Error at index $index")
                    client.send(n)
                } catch (e: Exception) {
                    println("Caught an exception in coroutine $index: ${e.message}")
                    // 여기에서 예외를 처리하거나, 필요한 복구 로직을 수행합니다.
                }
            }
        }
    }
}

data class Notificationabc(val message: String)
class NotificationClient {
    fun send(n: Notificationabc) {
        println("send: $n")
    }
}

fun main_29_1() = runBlocking {
    val client = NotificationClient()
    val notificationScope = CoroutineScope(Dispatchers.Default)
    val notificationsSender = NotificationsSender(client, notificationScope)

    val notificationabcs = List(5) { Notificationabc("Message #$it") }
    notificationsSender.sendNotification(notificationabcs)

    delay(1000L) // 코루틴이 완료될 시간 기다림
    println("NotificationsSender를 통해 모든 알림이 전송되었습니다.")
}

fun main_29_2() = runBlocking {
    val client = NotificationClient()
    val notificationsSender2 = NotificationsSender2(client)

    val notificationabcs = List(5) { Notificationabc("Message #$it") }
    notificationsSender2.sendNotifications(notificationabcs)

    println("NotificationsSender2를 통해 모든 알림이 전송되었습니다.")
}