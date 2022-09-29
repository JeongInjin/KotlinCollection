package me.injin.extTest

import org.junit.jupiter.api.Test

class EtcTest {

    @Test
    fun runCatchingTest() {

        val color = Color.ECT

        val colorName: Result<String> = runCatching {
            when (color) {
                Color.BLUE -> "파란색"
                Color.RED -> "빨간색"
                Color.YELLOW -> "노란색"
                Color.ECT -> throw IllegalArgumentException("처음 들어보는 색")
            }
        }.onSuccess { it:String ->
            //성공시만 실행
            println("onSuccess")
        }.onFailure { it:Throwable ->
            //실패시만 실행 (try - catch 문의 catch 와 유사)
//            println("onFailure: ${it.stackTraceToString()}")

            println("=====")
            when (it) {
                is IllegalArgumentException -> "IllegalArgumentException 발생: ${it.printStackTrace()}"
            }

        }
    }

    @Test
    fun runCatchingTest2() {

    }

}


enum class Color {
    BLUE,
    RED,
    YELLOW,
    ECT
}