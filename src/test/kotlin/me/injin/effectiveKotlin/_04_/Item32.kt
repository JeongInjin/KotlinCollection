package me.injin.effectiveKotlin._04_

import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

class Item32


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