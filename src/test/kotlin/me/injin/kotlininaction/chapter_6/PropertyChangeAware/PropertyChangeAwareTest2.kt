package me.injin.kotlininaction.chapter_6.PropertyChangeAware

import org.junit.jupiter.api.Test
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

//공통 모듈을 뽑아내서 재사용성 증가 시키기
class PropertyChangeAwareTest2 {

    @Test
    fun mainTest() {
        val myPerson = MyPerson2("Jayden", 26, 1234)
        myPerson.addPropertyChangeListener(
            PropertyChangeListener { event ->
                println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
            }
        )

        myPerson.age = 30
        myPerson.salary = 5500
    }
}
class ObservableProperty2(
    private val propName: String,
    private var propValue: Int,
    private val changeSupport: PropertyChangeSupport
) {
    fun getValue(): Int = propValue
    fun setValue(newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(propName, oldValue, newValue)
    }
}

class MyPerson2(
    val name: String, age: Int, salaray: Int
) : PropertyChangeAware() {
    val _age = ObservableProperty2("age", age, changeSupport)
    var age: Int
        get() = _age.getValue()
        set(value) = _age.setValue(value)

    val _salary = ObservableProperty2("salary", salaray, changeSupport)
    var salary: Int
        get() = _salary.getValue()
        set(value) = _salary.setValue(value)
}