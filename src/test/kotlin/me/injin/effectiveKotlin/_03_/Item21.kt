package me.injin.effectiveKotlin._03_

import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class Item21 {

    @Test
    fun byLazyTest() {
        val area = Area(59)
        println(area.test)
        println(area.test)
    }

    @Test
    fun windowTest() {
        val window: IWindow = TransparentWindow()
        val ui = UI(window)
        println("Width : ${ui.getWidth()}, height: ${ui.getHeight()}")
    }
}

class Rectangle {
    lateinit var area: Area
    fun initArea(param: Area): Unit {
        this.area = param
    }
}

class Area(val value: Int) {
    val test: String by lazy { delegatesFunction() }

    private fun delegatesFunction(): String {

        println("delegatesFunction 호출")
        return "abc"
    }
}

fun main() {
    val rectangle = Rectangle()
    rectangle.initArea(Area(10))
    println(rectangle.area.value)

    var nonNullString: String by Delegates.notNull<String>()
    nonNullString = "Hello World"
    println("Non null value is: ${nonNullString}")
//    nonNullString = null  // 컴파일 에러, non-null 타입에 null을 넣을 수 없음
}

interface IWindow {
    fun getWidth() : Int
    fun getHeight() : Int
}

open class TransparentWindow : IWindow {
    override fun getWidth(): Int {
        return 100
    }

    override fun getHeight() : Int{
        return 150
    }
}

//class UI(window: IWindow) : IWindow {
//    val mWindow: IWindow = window
//
//    override fun getWidth(): Int {
//        return mWindow.getWidth()
//    }
//
//    override fun getHeight(): Int {
//        return mWindow.getHeight()
//    }
//}
class UI(window: IWindow) : IWindow by window