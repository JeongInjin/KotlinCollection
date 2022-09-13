package me.injin.kotlininaction.chapter_8

import org.junit.jupiter.api.Test

class CallByTest {

    @Test
    fun callByValueTest() {
        /* callByValue
        lambda function
        callByValue function
        */
        println("callByValue(lambda())")
        callByValue(lambda())

        println("==================================")

        /* callByName
        callByName function
        lambda function
        */
        println("callByName(otherLambda)")
        callByName(otherLambda2)


    }
    //callByValue
    fun callByValue(b: Boolean): Boolean {
        println("callByValue function")
        return b
    }

    val lambda: () -> Boolean = {
        println("lambda function")
        true
    }

    //callByName
    fun callByName(b: () -> Boolean): Boolean {
        println("callByName function")
        return b()
    }

    val otherLambda: () -> Boolean = {
        println("otherLambda function")
        true
    }

    val otherLambda2: () -> Boolean = {
        println("otherLambda2 function")
        false
    }

}