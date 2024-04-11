package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test
import java.util.*

class Item17test {

    @Test
    fun `p97_1`() {
        val text = (1..10).joinToString { "|" }

        val text2 = (1..10).joinToString(separator = "|")

        val seperator = "|"
        val text3 = (1..10).joinToString(seperator)

        val test4 = (1..10).joinToString(separator = seperator)
    }

    @Test
    fun `함수 타입의 파라미터`() {
        performOperation(startMessage = "Starting operation.ㅈ..") {
            greet(message = "Hello, World!", isLoud = true)
        }
    }

    fun greet(message: String, isLoud: Boolean = false) {
        if (isLoud) {
            println(message.uppercase(Locale.getDefault()))
        } else {
            println(message)
        }
    }

    fun setUpWindow(title: String, width: Int, height: Int) {
        println("Setting up window: $title, Width: $width, Height: $height")
    }

    fun performOperation(startMessage: String, operation: () -> Unit) {
        println(startMessage)
        operation()
    }

}
