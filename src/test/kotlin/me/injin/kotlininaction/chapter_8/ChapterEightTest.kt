package me.injin.kotlininaction.chapter_8

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.KFunction0

internal class ChapterEightTest {

    //고차 함수 테스트
    @Test
    fun mainTest() {
        b(::a) //b가 호춣한 함수 a

        val c: (String) -> Unit = { str -> println("$str 람다함수") }
        b(c) //b가 호춣한 람다함수
    }
    fun a(str: String) = println("$str 함수 a")
    fun b(fuc: (String) -> Unit) = fuc("b가 호춣한")

    //고차 함수 테스트 - 2
    @Test
    fun mainTest2() {
        val sumResult = calculate(4, 5, ::sum)
        println(sumResult)
        val mulResult = calculate(4, 5) {a, b -> a * b}
        println(mulResult)

        val func = operation()
        println(func(2))
    }

    private fun operation(): (Int) -> Int {
        return ::square
    }

    private fun square(x: Int) = x * x

    private fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
        return operation(x, y)
    }

    private fun sum(x: Int, y: Int) = x + y

    //람다 테스트
    @Test
    fun lambdaTest() {
        val upperCase1: (String) -> String = {str:String -> str.uppercase()}
        val upperCase2: (String) -> String = {str -> str.uppercase()}
        val upperCase3 = {str:String -> str.uppercase()}
        val upperCase4: (String) -> String = {it.uppercase()}
        val upperCase5:(String) -> String = String::uppercase

        println("uppserCase1: ${upperCase1("hello")}")
        println("uppserCase2: ${upperCase2("hello")}")
        println("uppserCase3: ${upperCase3("hello")}")
        println("uppserCase4: ${upperCase4("hello")}")
        println("uppserCase5: ${upperCase5("hello")}")
    }

    @Test
    fun twoLambdaTest() {
        twoLambda({a, b -> "first $a $b"}) {"Second $it"}
    }

    fun twoLambda(first: (String, String) -> String, second: (String) -> String) {
        println(first("OneParam", "TwoParam"))
        println(second("OneParam"))
    }

}