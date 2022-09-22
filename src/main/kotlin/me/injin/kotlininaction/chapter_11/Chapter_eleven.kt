package me.injin.kotlininaction.chapter_11

class Chapter_eleven

fun main() {

    val a = StringBuilder().append("tst").append("gdgsdg")
    val b = buildString {
        a
    }
    println(a)
    println(b)

    val s = buildString {
        this.append("Hello, ")
        append("World!!")
    }
    println(s)
}

fun buildString(builderAction: StringBuilder.() -> Unit): String {
    println("builderAction: $builderAction")
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}

