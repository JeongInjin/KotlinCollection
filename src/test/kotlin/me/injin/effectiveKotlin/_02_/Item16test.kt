package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test

class Item16test {

    @Test
    fun p92_1() {
        val user = UserItem16()
        user.name = "injin"
    }


}

class UserItem16 {
    var name: String = "initial name"
        get() = field // getter에서 백킹 필드에 접근
        set(value) {
            println("Setting name to $value")
//            name = value // stack overflow
            this.name = value // stack overflow
//            field = value // setter에서 백킹 필드에 값을 할당
        }

    val readOnlyProperty: Int = 10
        get() = field

    val readOnlyName: String
        get() = name
}


class UserReadOnly {
    val readOnlyProperty: Int = 10
        get() = field

    val name: String
        get() = name
}


class Rectangle(var width: Int, var height: Int) {
    var area: Int
        get() = width * height
        set(value) {
            width = value / height
        }
}

// 이렇게 하지 마세요
class UserInCorrect {
    private var name: String = ""

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }
}

class UserCorrect {
    var name: String = ""
}
