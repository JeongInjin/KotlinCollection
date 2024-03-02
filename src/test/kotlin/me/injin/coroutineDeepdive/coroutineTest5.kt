package me.injin.coroutineDeepdive

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.junit.jupiter.api.Test


class CoroutineTest5 {

    @Test
    fun `227 페이지 불완전한 코드`() = runBlocking {
        main1()
    }

    @Test
    fun `228 페이지 개선된 코드`() = runBlocking {
        main2()
    }

    @Test
    fun `229 페이지 produce 사용으로 좀 더 개선된 코드`() = runBlocking {
        main3()
    }

    @Test
    fun `230 페이지`() = runBlocking {
        main4()
    }

    @Test
    fun `231 페이지`() = runBlocking {
        main5()
    }

    @Test
    fun `232 페이지`() = runBlocking {
        main6()
    }

    @Test
    fun `233 페이지`() = runBlocking {
        main7()
    }

    @Test
    fun `234 페이지`() = runBlocking {
        main8()
    }

    @Test
    fun `236 ~ 237 페이지`() = runBlocking {
        main9()
    }

    @Test
    fun `238 페이지`() = runBlocking {
        main10()
    }

    @Test
    fun `239 페이지`() = runBlocking {
        main11()
    }

}

// 227 페이지 - 불완전한 코드
suspend fun main1(): Unit = coroutineScope {
    val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next one")
            channel.send(index * 2)
        }
    }

    launch {
        repeat(5) {
            val received = channel.receive()
            println("Received: $received")
        }
    }
}

// 228 페이지 - 개선된 코드
suspend fun main2(): Unit = coroutineScope {
    val channel = Channel<Int>()
    launch {
        repeat(5) { index ->
            delay(1000)
            println("Producing next one")
            channel.send(index * 2)
        }
        channel.close()
    }

    launch {
        for (element in channel) {
            println("Received element: $element")
        }

        // 또는
//        channel.consumeEach { element ->
//            println("Received element: $element")
//        }
    }
}

// 229 페이지 - ReceiveChannel 을 반환하는 코루틴 빌더인 produce 함수 사용
fun CoroutineScope.produceNumbers(max: Int): ReceiveChannel<Int> = produce {
    var x = 0
    while (x < 5) {
        send(x++)
    }
}

// 229 페이지 - produce 사용으로 좀 더 개선된 코드
suspend fun main3(): Unit = coroutineScope {
    val channel = produce {
        repeat(5) { index ->
            println("Producing next one")
            delay(1000)
            send(index * 2)
        }
    }

    for (element in channel) {
        println("Received element: $element")
    }

}

// 230 페이지 - Channel.UNLIMITED
suspend fun main4(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.UNLIMITED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    for (element in channel) {
        println("Received element: $element")
        delay(1000)
    }
}

// 231 페이지 - 정해진 용량
suspend fun main5(): Unit = coroutineScope {
    val channel = produce(capacity = 3) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    for (element in channel) {
        println("Received element: $element")
        delay(1000)
    }
}

// 232 페이지 - 기본(랑데뷰) 채널
suspend fun main6(): Unit = coroutineScope {
    val channel = produce {
        // 또는 produce(capacity = Channel.RENDEZVOUS)
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    for (element in channel) {
        println("Received element: $element")
        delay(1000)
    }
}

// 233 페이지 - 융합(CONFLATED) 채널
suspend fun main7(): Unit = coroutineScope {
    val channel = produce(capacity = Channel.CONFLATED) {
        repeat(5) { index ->
            send(index * 2)
            delay(100)
            println("Sent")
        }
    }

    delay(1000)
    for (element in channel) {
        println("Received element: $element")
        delay(1000)
    }
}

// 234 페이지 - 버퍼 오퍼플로일 때
suspend fun main8(): Unit = coroutineScope {
    val channel = Channel<Int>(
        capacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    launch {
        repeat(5) { index ->
            channel.send(index * 2)
            delay(100)
            println("Sent")
        }
        channel.close()
    }

    delay(1000)
    for (element in channel) {
        println("Received element: $element")
        delay(1000)
    }
}

fun CoroutineScope.produceNumbers2() = produce {
    repeat(10) {
        delay(100)
        send(it)
    }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
    for (msg in channel) {
        println("Processor #$id received $msg")
    }
}

// 236 ~ 237 페이지 - 팬아웃
suspend fun main9(): Unit = coroutineScope {
    val channel = produceNumbers2()
    repeat(3) { id ->
        delay(10)
        launchProcessor(id, channel)
    }
}

// 238 페이지 - 팬인
suspend fun sendString(
    channel: SendChannel<String>,
    text: String,
    time: Long
) {
    while (true) {
        delay(time)
        channel.send(text)
    }
}

fun main10() = runBlocking {
    val channel = Channel<String>()
    launch { sendString(channel, "foo", 200L) }
    launch { sendString(channel, "BAR!", 500L) }
    repeat(50) {
        println(channel.receive())
    }
    coroutineContext.cancelChildren()
}

// 239 페이지 - 파이프라인
fun CoroutineScope.numbers(): ReceiveChannel<Int> =
    produce {
        repeat(3) { num ->
            send(num + 1)
        }
    }

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> =
    produce {
        for (num in numbers) {
            send(num * num)
        }
    }


suspend fun main11(): Unit = coroutineScope {
    val numbers = numbers()
    val squares = square(numbers)
    for (square in squares) {
        println(square)
    }
}
