package me.injin.kotlininaction.chapter_7.PropertyChangeAware

import org.junit.jupiter.api.Test
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.reflect.KProperty

//위임 프로퍼티 구현 후 적용
class PropertyChangeAwareTest3 {

    @Test
    fun mainTest() {
        val myPerson = MyPerson3( 26, 1234)
        myPerson.addPropertyChangeListener(
            PropertyChangeListener { event ->
                println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
            }
        )

        myPerson.age = 30
        myPerson.salary = 5500
    }
}
class ObservableProperty3(
    private var propValue: Int,
    private val changeSupport: PropertyChangeSupport
) {
    // 위임을 위해 코틀린 관례에 맞게 operator를 붙이고, 해당 객체를 프로퍼티를 넘겨줘야 한다.
    operator fun getValue(p: MyPerson3, prop: KProperty<*>): Int = propValue
    operator fun setValue(p: MyPerson3, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

class MyPerson3(
    age: Int, salary: Int
) : PropertyChangeAware() {
    // by 오른쪽의 객체를 **위임 객체**라고 부른다.
    // 코틀린은 위임 객체를 감춰진 프로퍼티에 저장하고, 주 객체의 프로퍼티를 읽거나 쓸때마다 위임 객체의 getValue, setValue를 호출해준다.
    var age: Int by ObservableProperty3(age, changeSupport)
    var salary: Int by ObservableProperty3(salary, changeSupport)
}