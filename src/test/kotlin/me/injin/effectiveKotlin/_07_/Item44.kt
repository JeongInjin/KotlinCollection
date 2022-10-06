package me.injin.effectiveKotlin._07_

import java.math.BigInteger
import kotlin.system.measureTimeMillis

class Item44 {
}

//sealed class LinkedList<T>
//
//class Node<T>(
//    val head: T,
//    val tail: LinkedList<T>
//): LinkedList<T>()
//
//class Empty<T>: LinkedList<T>()
//
//fun main() {
//    val list: LinkedList<Int> =
//        Node(1, Node(2, Node(3, Empty())))
//    val list2: LinkedList<String> =
//        Node("a", Node("b", Empty()))
//}
/**
 * 위 예제의 문제점은 매번 Empty 인스턴스를 새로 만들어야 한다는 점
    Empty 인스턴스를 미리 하나만 만들고, 다른 리스트에서 활용할 수 있게 한다하여도 제네릭 타입이 일치하지 않아서 문제가 될 수 있다
 */

//=====================================================================================================

sealed class LinkedList<out T>

class Node<T>(
    val head: T,
    val tail: LinkedList<T>
): LinkedList<T>()

object EmptyWithNothing: LinkedList<Nothing>()

fun main() {
    val list1: LinkedList<Int> =
        Node(1, Node(2, EmptyWithNothing))

    val list2: LinkedList<String> =
        Node("a", EmptyWithNothing)

    //재귀
    println(measureTimeMillis { fibonacci(10) }) // 약 500 밀리초
//    println(measureTimeMillis { fibonacci(45) }) // 약 5.5 초


    println("===")
    println(measureTimeMillis { fib(40) })
    println(measureTimeMillis { fib(45) })
    println(measureTimeMillis { fib(500) })

    println("===")
    val list:Iterable<Int> = Array(10000){ it }.toList()

    println("time1 = ${measureTimeMillis {
        println("result1 = ${list.countMax1()}")
    }}")
    println("time2 = ${measureTimeMillis {
        println("result2 = ${list.countMax2()}")
    }}")
}

//단순한 재귀 함수
//0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597
fun fibonacci(n: Int): Long = when (n) {
    0, 1 -> 1L
    else -> fibonacci(n - 1) + fibonacci(n - 2)
}


//메모이제이션 사용
private val FIB_CACHE = mutableMapOf<Int, BigInteger>()

fun fib(n: Int): BigInteger = FIB_CACHE.getOrPut(n) {
    if(n <= 1) BigInteger.ONE else fib(n -1) + fib(n-2)
}


//무거운 객체를 외부 스코프로 보내기
fun Iterable<Int>.countMax1():Int{
    val max = this.maxOrNull()
    return count {it == max}
}
fun Iterable<Int>.countMax2():Int=count {it == this.maxOrNull()}
