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

val name: String?  = "M"
val name2: String?  = "M"

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
