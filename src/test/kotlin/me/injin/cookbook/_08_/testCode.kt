package me.injin.cookbook._08_

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.properties.Delegates

class testCode {

    //인자없이 SmartPhone 을 인스턴스화함
    private val smartPhone: SmartPhone = SmartPhone()

    //8_1 대리자를 사용해서 합성 구하기
    @Test
    fun `Dialing delegates to internal phone`() {
        assertThat(smartPhone.dial("555-1234")).isEqualTo("Dialing 555-1234")
    }

    @Test
    fun `Taking picture delegates to internal camera`() {
        assertThat(smartPhone.takePicture()).isEqualTo("Taking picture")
    }

    @Test
    fun `8_3 값이 널이 될 수 없게 만들기`() {
        var shouldNotBeNull: String by Delegates.notNull()

        assertThrows<IllegalStateException> { shouldNotBeNull }

        shouldNotBeNull = "Hello, World!!"
        assertDoesNotThrow { shouldNotBeNull }
        assertThat(shouldNotBeNull).isEqualTo("Hello, World!!")
    }

    @Test
    fun `8_4 observable 과 vetoable 대리자 사용하기`() {
        var warched: Int by Delegates.observable(1) { prop, old, new ->
            println("${prop.name} changed frp, $old to $new")
        }

        var checked: Int by Delegates.vetoable(0) { prop, old, new ->
            println("${prop.name} changed frp, $old to $new")
            new >= 0
        }

        
    }
}

interface Dialable {
    fun dial(number: String): String
}

class Phone : Dialable {
    override fun dial(number: String): String = "Dialing $number"
}

interface Snappable {
    fun takePicture(): String
}

class Camera : Snappable {
    override fun takePicture(): String = "Taking picture"
}

class SmartPhone(
    private val phone: Dialable = Phone(),
    private val camera: Snappable = Camera()
) : Dialable by phone, Snappable by camera
//) : Dialable by phone, Snappable by camera
