package me.injin.effectiveKotlin._01_

class Chapter01 {

    fun test() {

    }

}

interface Element {
    val active: Boolean
}

class ActualElement : Element {
    override var active: Boolean = false
}

val name: String? = "M"
val name2: String? = "M"

val fullName: String? get() = name?.let { "$it $name2" }

val fullName2: String? = name?.let { "$it $name2" }

fun main() {
    if (fullName != null) {
        println(fullName!!.length)
    }

    if (fullName2 != null) {
        println(fullName2.length)
    }
}

fun main2() {
    val list = listOf(1, 2, 3)

    val mutableList = list.toMutableList()
    mutableList.add(4)
}

data class UserCh01(
    val name: String,
    val surname: String
) {
    fun withSurname(surname: String) = UserCh01(name, surname)
}

fun main3() {
    var user = UserCh01("Maja", "Marlkiewics")
    user = user.copy(surname = "Moskala")
    print(user)
}