package me.injin.study

fun main(args: Array<String>) {
//    val outer = Outer()
//    println("outer: $outer")
//    outer.Inner()
//    val nested = Outer.Nested()

    Outer.companionObjectFunction()
    var test = Outer()
    test.Inner().innerFunction()


}


class Outer {
    private  val TEST = "TEST"

    init {
        println("1 - Initializing class Outer, where TEST = $TEST")
    }
    constructor() {
        println("constructor 진입")
    }

    companion object {
        init {
            println("companion object init")
        }

        val companionObject = "companionObject"

        fun companionObjectFunction() {
            println("companionObjectFunction")
        }
    }

    class Nested {
        init {
            println("Nested init")
        }
        constructor() {
            println("Nested constructor")
        }

        val nested = "nested"

        fun nestedFunction() {
            println("nestedFunction")
        }

    }

    inner class Inner {
        init {
            println("Inner init")
        }
        constructor() {
            println("Inner constructor")
        }

        fun innerFunction() {
            println("constructorFunction: $TEST")
        }
    }

    private object InnerObject {
        init {
            println("4 - Initializing class Inner")
        }

        fun info(): String {
            return "I'm a method in Inner"
        }
    }
}