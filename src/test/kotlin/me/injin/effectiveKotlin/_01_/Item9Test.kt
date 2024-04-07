package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import java.util.*

private const val path =
    "/Users/injinjeong/injin_workspace/kotlin/KotlinCollection/src/test/kotlin/me/injin/effectiveKotlin/_01_/testText"

class Item9Test {

    @Test
    fun useTest1() {
        use1()
    }

    private fun use1(): Int {
        val reader = BufferedReader(FileReader(path))
        reader.use {
            return reader.lineSequence().sumOf { it.length }
        }
    }

    @Test
    fun useTest2() {
        use2()
    }

    private fun use2(): Int {
        BufferedReader(FileReader(path)).use { reader ->
            return reader.lineSequence().sumOf { it.length }
        }
    }

    //파일을 리소스로 사용하는 경우도 많고, 파일을 한 줄씩 읽어 들이는 경우도 많으므로,
    //코틀린 표준 라이브러리는 파일을 한 줄씩 처리할 때 활용할 수 있는 useLines 함수도 제공함.
    @Test
    fun useTest3() {
        use3()
    }

    private fun use3(): Int {
        File(path).useLines { lines ->
            return lines.sumOf { it.length }
        }
    }

    @Test
    fun useTest4() {
        user4()
    }

    private fun user4() = File(path).useLines { lines ->
        lines.sumOf { it.length }
    }

    @Test
    fun useTest5() {
        readFileWithUse(path)
        // ===== use =====
        // Kotlin은 정적 타입 지정 언어입니다.
        // Kotlin은 JVM에서 실행됩니다.
    }

    @Test
    fun useTest6() {
        readFileWithUseLines(path)
        // ===== useLines =====
        // Kotlin은 정적 타입 지정 언어입니다.
        // ===== useLines =====
        // Kotlin은 JVM에서 실행됩니다.
    }

    @Test
    fun useTest7() {
        readFileWithLinesSequence(path)  // Found an kotlin line: KOTLIN은 정적 타입 지정 언어입니다.
    }

    // use
    fun readFileWithUse(filePath: String) {
        FileInputStream(filePath).use { inputStream ->
            println("===== use =====")
            inputStream.bufferedReader().use { reader ->
                reader.lines().forEach { println(it) }
            }
        }
    }


    fun readFileWithUseLines(filePath: String) {
        File(filePath).useLines { lines ->
            lines.forEach {
                println("===== useLines =====")
                println(it)
            }
        }
    }

    fun readFileWithLinesSequence(filePath: String) {
        val bufferedReader = File(path).bufferedReader()
        bufferedReader.use { reader ->
            val lineSequence = reader.lineSequence()
            val result = lineSequence.map { it.uppercase(Locale.getDefault()) }
                .find { it.startsWith("KOTLIN") }

            if (result != null) {
                println("Found a kotlin line: $result")
            }
        }
    }

}
