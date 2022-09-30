package me.injin.kotlininaction.etc

import org.junit.jupiter.api.Test

class RangeTest {

    @Test
    fun rangeTest() {
        val naturalNumbers = 1..100
        val r = 1..(99+1)
        val r2 = IntRange(1, 100)

        val arr1: IntArray = r.toList().toIntArray()
        val arr2:Array<Int> = r.toList().toTypedArray()

        val r3 = 100..200
//        val r4 = 100..200-1 //rangeTo
        val r4 = 100 until 200 // 100..199
        var r5 = (200 downTo 100).step(20)
        println(r5.joinToString(", "))
    }

    @Test
    fun array2rdTest() {
        val numbersOrdered = Array(3, {i -> IntArray(4, { j -> j + i * 4})})
        val numbersOrdered2 = Array(3) { i -> IntArray(4) { j -> j + i * 4 } }

        for ((i, row) in numbersOrdered.withIndex()) {
            for ((j, column) in row.withIndex()) {
                println("[$i,$j] => $column\t")
            }
            println()
        }
    }
}