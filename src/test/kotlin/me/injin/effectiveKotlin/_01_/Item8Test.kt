package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.properties.Delegates

class Item8Test {

    @Test
    fun `적절하게 null을 처리하라`() {
        val toIntOrNull1 = "af".toIntOrNull()
        println("toIntOrNull1: :$toIntOrNull1")
        val toIntOrNull2 = "123".toIntOrNull()
        println("toIntOrNull2: :$toIntOrNull2")
    }

    @Test
    fun `52_1`() {
        var name: String = "M"
        // String 을 Int 로 변환할 수 없을 경우 null 을 반환
        val toIntOrNull = name.toIntOrNull()
        println("toIntOrNull: $toIntOrNull")

        val list = listOf(1, 2, 3)
        // 조건에 맞는 첫 번째 요소를 반환하거나 없으면 null 을 반환
        val firstOrNull = list.firstOrNull { it % 4 == 0 }
        println("firstOrNull: $firstOrNull")

        // toIntOrNull: null
        // firstOrNull: null
    }

    @Test
    fun `53_1`() {
        val name: String? = "injin Jeong"
        name?.println()

        val nameIsNull: String? = null
        nameIsNull?.println()

        if (nameIsNull != null) {
            nameIsNull.println()
        }

        nameIsNull?.println() ?: "nameIsNull is null".println()

        // injin Jeong
        // nameIsNull is null
    }

    @Test
    fun `53_2`() {
        val list: List<Int>? = null

        val notNullList: List<Int> = list.orEmpty()

        println("list: $list") // list: null
        println("notNullList: $notNullList") // notNullList: []
    }

    @Test
    fun `55_1`() {
        val name: String? = null
        assertThrows<IllegalArgumentException> {
            name ?: throw IllegalArgumentException("name is null")
        }

        assertThrows<NullPointerException> {
            name!!.println()
        }

        assertThrows<IllegalArgumentException> {
            requireNotNull(name) { "name is null" }
        }
    }

    @Test
    fun `58_lateinit_test`() {
        val myClass = MyClass()
        println("isInitialized: ${myClass.isInitialized()}") // false
        myClass.initializeProperty() // 프로퍼티 초기화
        println("isInitialized: ${myClass.isInitialized()}") // true
        println(myClass.myProperty) // 이제 안전하게 접근 가능
    }

    @Test
    fun `58_Delegates_test`() {
        val myClass = MyClass2()
        myClass.initializeProperty() // 프로퍼티 초기화
        println(myClass.myProperty) // 이제 안전하게 접근 가능
    }

    @Test
    fun `58_Enum`() {
        val currentStatus = ProcessStatus.NONE
        reportStatus(currentStatus) // 프로세스가 시작되지 않았거나 상태가 유효하지 않습니다.
    }


}

fun String.println() {
    println(this)
}

class MyClass {
    lateinit var myProperty: String
    

    // 초기화 여부 확인
    fun isInitialized(): Boolean = ::myProperty.isInitialized
    fun initializeProperty() {
        myProperty = "Hello, World!"
    }
}


class MyClass2 {
    var myProperty: String by Delegates.notNull<String>()

    fun initializeProperty() {
        myProperty = "Hello, Kotlin!"
    }
}

enum class ProcessStatus {
    STARTED,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    NONE // 어떤 프로세스 상태도 아닌, 초기 상태나 유효하지 않은 상태를 나타내는 멤버
}

fun reportStatus(status: ProcessStatus) {
    when (status) {
        ProcessStatus.NONE -> println("프로세스가 시작되지 않았거나 상태가 유효하지 않습니다.")
        ProcessStatus.STARTED -> println("프로세스가 시작되었습니다.")
        ProcessStatus.IN_PROGRESS -> println("프로세스가 진행 중입니다.")
        ProcessStatus.COMPLETED -> println("프로세스가 완료되었습니다.")
        ProcessStatus.FAILED -> println("프로세스가 실패했습니다.")
    }
}
