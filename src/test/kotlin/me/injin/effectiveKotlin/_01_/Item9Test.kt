package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class Item9Test {

    @Test
    fun useTest1(): Int {
        val reader = BufferedReader(FileReader("/path"))
        reader.use {
            return reader.lineSequence().sumOf { it.length }
        }
    }

    @Test
    fun useTest2(): Int {
        BufferedReader(FileReader("/path")).use { reader ->
            return reader.lineSequence().sumOf { it.length }
        }
    }

    //파일을 리소스로 사용하는 경우도 많고, 파일을 한 줄씩 읽어 들이는 경우도 많으므로,
    //코틀린 표준 라이브러리는 파일을 한 줄씩 처리할 때 활용할 수 있는 useLines 함수도 제공함.
    @Test
    fun useTest3(): Int {
        File("/path").useLines { lines ->
            return lines.sumOf { it.length }
        }
    }

    @Test
    fun useTest4(): Int =
        File("/path").useLines { lines ->
            lines.sumOf { it.length }
        }


}