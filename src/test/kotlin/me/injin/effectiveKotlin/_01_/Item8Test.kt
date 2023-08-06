package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test

class Item8Test {

    @Test
    fun `적절하게 null을 처리하라`() {
        val toIntOrNull1 = "af".toIntOrNull()
        println("toIntOrNull1: :$toIntOrNull1")
        val toIntOrNull2 = "123".toIntOrNull()
        println("toIntOrNull2: :$toIntOrNull2")
    }

}