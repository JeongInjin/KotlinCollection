package me.injin.kotlininaction.chapter_4

import org.junit.jupiter.api.Test

class DelegatePattern {

    @Test
    fun `delegate pattern test`() {
        var aWindow = TransparentWindow()
        val ui = UI(aWindow)
        println("Width : ${ui.getWidth()}, height: ${ui.getHeight()}")
    }
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

class UI(window: IWindow) : IWindow {
    val myWindow = window
    override fun getWidth(): Int {
        return myWindow.getHeight()
    }

    override fun getHeight(): Int {
        return myWindow.getHeight()
    }

}