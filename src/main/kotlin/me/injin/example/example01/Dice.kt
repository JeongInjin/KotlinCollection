package me.injin.example.example01

import java.util.*


fun getNumberInt(max: Int) = (Math.random() * max).toInt() + 1
fun getNumberInt2(max: Int) = Random().nextInt(max) + 1
fun getNumberStream(max: Int) = Random().ints(1, max).findFirst().asInt

fun getNumberHedron(hed: Hedron) = (Math.random() * hed.faces).toInt() + 1

enum class Hedron(val faces: Int) {
    Tetrahedron(4),
    Cube(6),
    Octahedron(8),
    Dodecahedron(12)
}

typealias HedronToInt = (Hedron) -> Int // 함수타입을 typealias
typealias Random = (Hedron) -> Int // 함수타입을 typealias

class Dice(val sides: Hedron) { //random func
    fun cast(ranFun: (Int) -> Int) = ranFun(sides.faces)
//    fun castHedron(ranFun: (Hedron) -> Int) = ranFun(sides)
    fun castHedron(ranFun: HedronToInt) = ranFun(sides)

    override fun toString(): String {
//        return "Dice(${sides.name}) casts ${getNumberInt(sides.faces)}"
//        return "Dice(${sides.name}) casts ${cast( fun(n: Int) = (Math.random() * n).toInt() + 1)}" //익명함수
//        return "Dice(${sides.name}) casts ${cast { (Math.random() * sides.faces).toInt() + 1 }}" //람다식(람다블록)
//        return "Dice(${sides.name}) casts ${cast(::getNumberInt)}" //함수 레퍼런스
//        return "Dice(${sides.name}) casts ${cast(::getNumberInt2)}" //함수 레퍼런스
//        return "Dice(${sides.name}) casts ${cast(::getNumberStream)}" //함수 레퍼런스
        return "Dice(${sides.name}) casts ${castHedron(::getNumberHedron)}" //함수 레퍼런스
    }
}

fun main() {
    val dices = listOf(
        Dice(Hedron.Tetrahedron),
        Dice(Hedron.Cube),
        Dice(Hedron.Octahedron),
        Dice(Hedron.Dodecahedron),
    )
    println("\n 0. for loop iteration")
    for (dice in dices) {
        println(dice.toString())
    }
    //
    println("\n 1. anonymous function parameter")
    dices.forEach( fun(dice: Dice) { println(dice.toString()) })
    //
    println("\n 2. lambda expression parameter")
    dices.forEach({ dice: Dice -> println(dice.toString()) })
    //
    println("\n 3. last parameter to external lambda block")
    dices.forEach() { dice: Dice -> println(dice.toString()) }
    //
    println("\n 4. () removing for single argument lambda block")
    dices.forEach { dice: Dice -> println(dice.toString()) }
    //
    println("\n 5. single argument 'it' for lambda expression")
    dices.forEach { println(it) }
    //
    println("\n 6. function reference of kotlin.io::println")
    dices.forEach (::println)
    //
    println("\n 7. custom function reference of ::printDice")
    dices.forEach (::printDice)
    //
    println("\n 8. function reference of System.out::println (JVM)")
    dices.forEach (System.out::println)
}

fun printDice(d: Dice) = println(d)