package me.injin.effectiveKotlin._03_

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Item20Test {

    @Test
    fun coerceInTest() {
        //범위제한 확장함수
        val coerceIn1 = (-1).coerceIn(0, 100) // 0
        val coerceIn2 = 0.coerceIn(0, 100) // 0
        val coerceIn3 = 1.coerceIn(0, 100) // 1
        val coerceIn4 = 100.coerceIn(0, 100) // 100
        val coerceIn5 = 101.coerceIn(0, 100) // 100

        assertThat(coerceIn1).isEqualTo(0)
        assertThat(coerceIn2).isEqualTo(0)
        assertThat(coerceIn3).isEqualTo(1)
        assertThat(coerceIn4).isEqualTo(100)
        assertThat(coerceIn5).isEqualTo(100)
    }

    @Test
    fun extensionFunctionTest() {
        "string".isEmpty()
    }


}