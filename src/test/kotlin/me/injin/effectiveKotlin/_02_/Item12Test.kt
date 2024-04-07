package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test

class Item12Test {

    @Test
    fun `p80_1`() {
        println("Hello".appendExclamation()) // "Hello!"
        println("Hello" add "World") // "Hello World"
    }

    @Test
    fun `p80_2`() {
        val conetents = Person("contents display")
        val live = Person("live display")

        conetents addFriend live
        conetents.displayFriends() // contents display's friends: live display
    }

}

fun String.appendExclamation(): String = "$this!"

infix fun String.add(other: String): String = "$this $other"

class Person(val name: String) {
    val friends = mutableListOf<Person>()

    infix fun addFriend(friend: Person) {
        friends.add(friend)
    }

    fun displayFriends() {
        println("$name's friends: ${friends.joinToString { it.name }}")
    }
}
