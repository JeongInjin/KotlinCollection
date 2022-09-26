package me.injin.desighPattern.singleton

object Singleton {
    val test = "test"
}

class NotSingleton {
    val test = "test"
}

fun main() {
    var a = NotSingleton()
    var b = NotSingleton()
    println("a == b: ${a == b}")
    println("a === b: ${a === b}")

    println("=====================")

    var c = Singleton
    var d = Singleton
    println("c == d: ${c == d}")
    println("c === d: ${c === d}")
}