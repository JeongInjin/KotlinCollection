package me.injin.kotlininaction.chapter_7.PropertyChangeAware

import org.junit.jupiter.api.Test
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

// 리스너의 목록을 관리하고 이벤트가 들어오면 통지한다
//기본 자바 빈즈를 이용해 구현
class PropertyChangeAwareTest {

    @Test
    fun mainTest() {
        val myPerson = MyPerson1("Jayden", 26, 1234)
        myPerson.addPropertyChangeListener(
            PropertyChangeListener { event ->
                println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
            }
        )

        myPerson.age = 30
        myPerson.salary = 5500
    }
}

open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}

class MyPerson1(
    val name: String, age: Int, salaray: Int
) : PropertyChangeAware() {
    var age: Int = age
        set(value) {
            val oldValue = field
            field = value
            changeSupport.firePropertyChange("age", oldValue, value)
        }

    var salary: Int = salaray
        set(value) {
            val oldValue = field
            field = value
            changeSupport.firePropertyChange("salary", oldValue, value)
        }
}

fun main() {
    val myPerson = MyPerson1("Jayden", 26, 1234)
    myPerson.addPropertyChangeListener(
        PropertyChangeListener { event ->
            println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
        }
    )

    myPerson.age = 30
    myPerson.salary = 5500
}