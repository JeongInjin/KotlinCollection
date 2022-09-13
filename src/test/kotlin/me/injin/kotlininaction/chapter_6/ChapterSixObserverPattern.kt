package me.injin.kotlininaction.chapter_6

import org.junit.jupiter.api.Test

class ChapterSixObserverPattern {

    @Test
    fun test() {
        EventPrinter().start()
    }
}

interface EventListener {
    fun onEvent(count: Int)
}

class EventPrinter : EventListener {
    override fun onEvent(count: Int) {
        println("count : $count")
    }

    fun start() {
        val counter  = Counter(this)
        counter.count()
    }

}

class Counter(var listener: EventListener) {
    fun count() {
        for (i in 1..100) {
            if(i % 5 == 0) listener.onEvent(i)
        }
    }
}

