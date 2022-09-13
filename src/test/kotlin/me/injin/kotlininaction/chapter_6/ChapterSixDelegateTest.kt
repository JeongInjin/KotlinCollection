package me.injin.kotlininaction.chapter_6

import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty


class ChapterSixDelegateTest {

    @Test
    fun delegateTest() {
        val p = PersonDelegate("Monguse", 20, 2000)
        p.age = 21
        p.salary = 2100
        println("${p.name} - age: ${p.age}, salary: ${p.salary}")
    }

    @Test
    fun byLazyTest() {
        var emailTest = PersonEmail("jij")
        emailTest.emails
    }


}

class Delegator(var value: Int) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("${property.name} get! $value")
        return value
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, newValue: Int) {
        println("${property.name} set! $newValue")
        value = newValue
    }
}

class PersonDelegate(val name: String, age: Int, salary: Int) {
    var age: Int by Delegator(age)
    var salary: Int by Delegator(salary)
}

//프로퍼티 초기화 지연 by lazy()
class PersonEmail(val name: String){
    val emails by lazy { loadEmails(this)}

    fun loadEmails(personEmail: PersonEmail): List<Email> {
        println("${personEmail.name} 의 메일을 가져옵니다.")
        return listOf(Email())
    }
}
class Email{}



