package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test

class Item11Class(
    var name: String? = null,
) {
    var innerClass: Item11Class? = null
    fun printName() {
        if (innerClass != null) {
            println(innerClass?.name)
        }

        innerClass?.let {
            println(it.name)
        }
    }
}

class Item11Test {
    @Test
    fun `p74_1`() {
        var item11 = Item11Class()
        item11.printName()
        item11.innerClass = Item11Class("injin")
        item11.printName()
    }

}
