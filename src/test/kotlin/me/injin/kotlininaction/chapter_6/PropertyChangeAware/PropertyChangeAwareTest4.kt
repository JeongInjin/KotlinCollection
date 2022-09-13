package me.injin.kotlininaction.chapter_6.PropertyChangeAware

import org.junit.jupiter.api.Test
import java.beans.PropertyChangeListener
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class PropertyChangeAwareTest4 {

    @Test
    fun mainTest() {
        val myPerson = MyPerson4( 26, 1234)
        myPerson.addPropertyChangeListener(
            PropertyChangeListener { event ->
                println("Property ${event.propertyName} changed from ${event.oldValue} to ${event.newValue}")
            }
        )

        myPerson.age = 30
        myPerson.salary = 5500
    }
}

class MyPerson4(
    age: Int, salary: Int
) : PropertyChangeAware() {
    /** observer를 정의하고 코틀린 위임 객체에 넘겨주면 된다.
     *  - 사실 위임 객체의 방법은 보이지 않는 접근자들을 만들어 주는 것이다.
     *  - get() -> <delegate>.getValue(v, <property>)
     *  - set() -> <delegate>.setValue(c, <property>, x)
     *  - 단순한 방법이지만 프로퍼티가 저장될 값을 맵, 디비 등으로 바꿀 수 있고, 프로퍼티를 읽거나 쓸 때 이벤트들을 추가하는 방식들을 간결하게 구현할 수 있다.
     */

    private val observer = { prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)
}