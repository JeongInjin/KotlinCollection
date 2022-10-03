package me.injin.effectiveKotlin._03_

import org.junit.jupiter.api.Test

class Item22 {

    @Test
    fun shadowingTest() {
        val repo = Repo()
        repo.readFile()
    }
}

interface Tree
class Birch: Tree
class Spruce: Tree

class Forest<T: Tree> {
    fun <T: Tree> addTree(tree: T) {
        println("adding tree ...")
    }
}

fun main() {
    val forest = Forest<Birch>()
    forest.addTree(Birch())
    forest.addTree(Spruce()) //정상동작...
}



//class Forest<T: Tree> {
//    fun addTree(tree: T) {
//        println("adding tree ...")
//    }
//}
//
//fun main() {
//    val forest = Forest<Birch>()
//    forest.addTree(Birch())
//    forest.addTree(Spruce()) // error
//}


fun openFile() {
    println("Opening file (top level)...")
}

class Repo {
    fun openFile() {
        println("Opening file (repository)...")
    }

    fun readFile() {
        openFile()
//        me.injin.effectiveKotlin._03_.openFile()
    }
}
