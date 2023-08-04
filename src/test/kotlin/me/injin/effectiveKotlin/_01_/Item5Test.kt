package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test


class Item5Test {

    @Test
    fun testFunction() {
        println(factorialRequireTest(5))
        findClustersRequireTest(listOf("a", "b"))
        sendEmailRequireTest(User("abc", "def"), "messageTest")
    }

    @Test
    fun factorialRequireTest(n: Int): Long {
        require(n >= 0) {
            "Cannot calculate factorial of $n because it is smaller than 0"
        }
        return if (n <= 1) 1 else factorialRequireTest(n - 1) * n
    }

    @Test
    fun findClustersRequireTest(points: List<String>) {
        require(points.isNotEmpty())

        println("points size: ${points.size}")
        //...
    }

    @Test
    fun sendEmailRequireTest(user: User, message: String) {
        requireNotNull(user.name)
        require(isValidName(user.name))

        println("user ok")
        //...
    }

    private fun isValidName(name: String): Boolean {
        //validation check....
        return true
    }


}