package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test

class Item15Test {

    @Test
    fun Test() {
        val node = Node("parent")
        node.makeChild("child")
    }
}

class Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
//            .apply { print("Created ${this?.name}") }
            .let { print("Created ${it?.name}") }

    private fun create(name: String): Node? = Node(name)
}