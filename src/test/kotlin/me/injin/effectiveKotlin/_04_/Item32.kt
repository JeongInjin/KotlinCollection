package me.injin.effectiveKotlin._04_

import org.junit.jupiter.api.Test
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class Item32 {
    @Test
    fun p_203_Test() {
        val set = mutableSetOf(Item32_Id(1))
        set.add(Item32_Id(1))
        set.add(Item32_Id(1))
        println("set.size: ${set.size}") // 3
    }
}

/**
 * equals 메서드는 오버라이드되었지만, hashCode 메서드는 오버라이드되지 않았습니다.
 * Set은 중복 요소를 허용하지 않지만, 중복 여부를 판단하기 위해 equals와 hashCode를 모두 사용합니다.
 */
class Item32_Id(val id: Int) {
    override fun equals(other: Any?): Boolean {
        return other is Item32_Id && other.id == id
    }

//    override fun hashCode(): Int {
//        return id
//    }
}

/**
 * 리플렉션을 활용하여 클래스 외부에서 private 함수를 호출하는 예.
 */
class Employee {

    private val id: Int = 2
    override fun toString(): String {
        return "User(id=$id)"
    }

    private fun privateFunction() {
        println("Private function called")
    }

}

fun callPrivateFunction(employee: Employee) {
    employee::class.declaredFunctions
        .first { it.name == "privateFunction" }
        .apply { isAccessible = true }
        .call(employee)
}

fun changeEmployeeId(employee: Employee, newId: Int) {
    employee::class.java.getDeclaredField("id")
        .apply { isAccessible = true }
        .set(employee, newId)
}

fun main() {
    val employee = Employee()
    callPrivateFunction(employee)
    println(employee)
    println("=====")
    changeEmployeeId(employee, 1)
    println(employee)
}
