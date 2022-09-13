package me.injin.kotlininaction.chapter_8

import org.junit.jupiter.api.Test
import java.util.concurrent.locks.ReentrantLock

class lmabdaExampleTest {

    var sharable = 1 // 보호가 필요한 공유 자원
    @Test
    fun lockTest() {
        val reLock = ReentrantLock()
        println("sharable1: $sharable")
        lock(reLock, {criticalFunc()})
        println("sharable2: $sharable")
        lock(reLock) { criticalFunc()
        println("sharable3: $sharable")}
        lock(reLock, ::criticalFunc)
        println("sharable4: $sharable")
    }

    fun <T> lock(reLock: ReentrantLock, body: () -> T): T {
        reLock.lock()
        try {
            return body()
        } finally {
            reLock.unlock()
        }
    }

    fun criticalFunc() {
        sharable += 1
    }

}