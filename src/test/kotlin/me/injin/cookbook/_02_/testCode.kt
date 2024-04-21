package me.injin.cookbook._02_

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test
import kotlin.math.pow

class testCode {

    @Test
    fun `레시피 2_6 숫자를 거듭제곱하기_1`() {
        val number: Int = 2
        val result = number * number
        println(result)
        assertThat(result).isEqualTo(4)

        val pow1 = number.toDouble().pow(2).toInt()
        val pow2 = number.toDouble().pow(3).toInt()

        assertThat(pow1).isEqualTo(4)
        assertThat(pow2).isEqualTo(8)
    }

    @Test
    fun `레시피 2_6 숫자를 거듭제곱하기_2`() {
        val number: Int = 2
        val result = number * number
        println(result)
        assertThat(result).isEqualTo(4)

        val pow1 = number `**` 2
        val pow2 = number `**` 3

        assertThat(pow1).isEqualTo(4)
        assertThat(pow2).isEqualTo(8)

        val pow3 = number.pow(2)
        val pow4 = number.pow(3)
        assertThat(pow3).isEqualTo(4)
        assertThat(pow4).isEqualTo(8)
    }

    @Test
    fun closeTo_test() {
        val a = 2.0 + 1.0
        assertThat(a).isCloseTo(2.9, Offset.offset(0.2))

        val b = 1.0 / 3.0
        assertThat(b).isCloseTo(0.333, Offset.offset(0.001))
    }

    @Test
    fun `비트 시프트 연산자 사용`() {
        val number = 8
        val shiftLeft = number shl 1
        val shiftRight = number shr 1

        assertThat(shiftLeft).isEqualTo(16)
        assertThat(shiftRight).isEqualTo(4)
    }

    @Test
    fun `비트 연산자 shl 사용`() {
        // 1 = 0000_0001
        val shiftLeft1 = 1 shl 1 // 00000_0010
        val shiftLest2 = 1 shl 2 // 00000_0100
        val shiftLest3 = 1 shl 3 // 00000_1000
        val shiftLest4 = 1 shl 4 // 00001_0000
        val shiftLest5 = 1 shl 5 // 00010_0000
        val shiftLest6 = 1 shl 6 // 00100_0000
        val shiftLest7 = 1 shl 7 // 01000_0000

        assertThat(shiftLeft1).isEqualTo(2)
        assertThat(shiftLest2).isEqualTo(4)
        assertThat(shiftLest3).isEqualTo(8)
        assertThat(shiftLest4).isEqualTo(16)
        assertThat(shiftLest5).isEqualTo(32)
        assertThat(shiftLest6).isEqualTo(64)
        assertThat(shiftLest7).isEqualTo(128)
    }

    @Test
    fun `비트 연산자 shr 사용`() {
        // 235 = 1110_1011
        val shiftRight1 = 235 shr 1 //0111_0101
        val shiftRight2 = 235 shr 2 //0011_1010
        val shiftRight3 = 235 shr 3 //0001_1101
        val shiftRight4 = 235 shr 4 //0000_1110
        val shiftRight5 = 235 shr 5 //0000_0111
        val shiftRight6 = 235 shr 6 //0000_0011
        val shiftRight7 = 235 shr 7 //0000_0001

        assertThat(shiftRight1).isEqualTo(117)
        assertThat(shiftRight2).isEqualTo(58)
        assertThat(shiftRight3).isEqualTo(29)
        assertThat(shiftRight4).isEqualTo(14)
        assertThat(shiftRight5).isEqualTo(7)
        assertThat(shiftRight6).isEqualTo(3)
        assertThat(shiftRight7).isEqualTo(1)

        println(117 shl 1)
    }

    @Test
    fun `비트 연산자 usr 과 ushr 비교`() {
        val n1 = 5
        val n2 = -5
        println(n1.toString(2)) // 101
        println(n2.toString(2)) // -101

        val shiftRight1 = n1 shr 1
        val shiftRight2 = n1 ushr 1

        println(shiftRight1.toString(2)) // 10
        println(shiftRight2.toString(2)) // 10

        val shiftRight3 = n2 shr 1
        val shiftRight4 = n2 ushr 1

        println(shiftRight3.toString(2)) // -10
        println(shiftRight4.toString(2)) // 1111111111111111111111111111101
    }


}

infix fun Int.`**`(x: Int) = toDouble().pow(x).toInt()
infix fun Long.`**`(x: Int) = toDouble().pow(x).toLong()
infix fun Float.`**`(x: Int) = this.toDouble().pow(x.toDouble()).toFloat()
infix fun Double.`**`(x: Int) = pow(x.toDouble()).toInt()

// Float 과 Double 에 존재하는 함수와 비슷한 패턴
fun Int.pow(x: Int): Int = `**`(x)
fun Long.pow(x: Int): Long = `**`(x)
