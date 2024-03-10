package me.injin.coroutineDeepdive

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class CoroutineTest6 {

    @Test
    fun `252 페이지 핫 콜드`() = runBlocking {
        main6_1()
    }
}

fun main6_1() {
    val l = buildList {
        repeat(3) {
            add("User $it")
            println("L: Added User $it")
        }
    }

    val l2 = l.map {
        println("L2: Processing $it")
        "Processed $it"
    }

    val s = sequence {
        repeat(3) {
            yield("User $it")
            println("S: Added User $it")
        }
    }

    val s2 = s.map {
        println("S2: Processing $it")
        "Processed $it"
    }
}

