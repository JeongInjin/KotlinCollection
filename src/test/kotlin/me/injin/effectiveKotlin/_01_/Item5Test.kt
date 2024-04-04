package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test

class Item5Test {

    @Test
    fun testFunction() {
        println(factorialRequireTest(5))
        findClustersRequireTest(listOf("a", "b"))
        sendEmailRequireTest(User("abc", "def"), "messageTest")
    }


    fun factorialRequireTest(n: Int): Long {
        require(n >= 0) {
            "Cannot calculate factorial of $n because it is smaller than 0"
        }
        return if (n <= 1) 1 else factorialRequireTest(n - 1) * n
    }


    fun findClustersRequireTest(points: List<String>) {
        require(points.isNotEmpty())

        println("points size: ${points.size}")
        //...
    }


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

    fun printLength(input: String?) {
        val nonNullInput = requireNotNull(input) { "input cannot be null" }
        // 이 시점부터 nonNullInput 은 non-null 타입으로 캐스트됩니다.
        println(nonNullInput.length)
    }

    @Test
    fun requireNotNullTest() {
        printLength("Hello") // 5를 출력
        printLength(null) // 예외 발생: input cannot be null
    }

    fun acceptOnlyTwo(num: Int) {
        // 원하는것만 적는다
        require(num == 2) { "2만 허용!" }

        // 원하는게 아닐경우를 적는다
        if (num != 2) {
            throw IllegalArgumentException("2만 허용!")
        }
    }

    // check: 상태혹은 멤버 변수를 검증
    class Person {
        val email: String? = null
        val status: PersonStatus = PersonStatus.PLAYING

        fun sleep() {
            check(this.status == PersonStatus.PLAYING) { "에러 메시지!" }

            if (this.status != PersonStatus.PLAYING) {
                throw IllegalStateException("에러 메시지!")
            }
        }

        enum class PersonStatus {
            PLAYING, SLEEPING
        }
    }

    fun sendMail(person: Person, text: String) {
        val email: String = person.email ?: run {
            println("email is null")
            return
        }
    }

    @Test
    fun testCalculation() {
        mainResult()
    }
}

fun calculateSum(a: Int, b: Int): Result<Int> {
    return if (a + b > 100) {
        Result.failure(Exception("Sum is greater than 100"))
    } else {
        Result.success(a + b)
    }
}

fun mainResult() {
    val result = calculateSum(50, 51)
    when {
        result.isSuccess -> println("Sum is ${result.getOrNull()}")
        result.isFailure -> println("Error: ${result.exceptionOrNull()?.message}")
    }
}
