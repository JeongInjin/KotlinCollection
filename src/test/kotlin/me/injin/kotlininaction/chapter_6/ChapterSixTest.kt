package me.injin.kotlininaction.chapter_6

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ChapterSixTest {

    @Test
    fun asCastTest() {
        val p1 = Person("Dmitry", "Jemerov")
        val p2 = Person("Dmitry", "Jemerov")
        println(p1 == p2)
        println(p1.equals(42))
    }

    @Test
    fun letTest() {
        var email: String? = "injin@example.com"
        email?.let { sendEmailTo(it) }
        email = null
        email?.let { sendEmailTo(it) }
    }
}

class Person(val firstName: String, val lastName: String) {
    override fun equals(o: Any?): Boolean {
        val otherPerson = o as? Person ?: return false

        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }

    override fun hashCode(): Int =
        firstName.hashCode() * 37 + lastName.hashCode()
}

fun sendEmailTo(email: String) {
    println("Sending email to $email")
}

class MyService {
    fun performAction(): String = "foo"
}

class MyTest {
    private lateinit var myService: MyService // 초기화하지 않고 널이 될 수 없는 프로퍼티를 선언한다.

    @BeforeEach fun setUp() {
        myService = MyService()
    }

    @Test fun testAction() {
        Assertions.assertThat("foo").isEqualTo(myService.performAction())// 널 검사를 수행하지 않고 프로퍼티를 사용한다.
    }
}
