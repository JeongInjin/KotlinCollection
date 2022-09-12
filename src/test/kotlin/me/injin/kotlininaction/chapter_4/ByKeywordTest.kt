package me.injin.kotlininaction.chapter_4

import org.junit.jupiter.api.Test

class ByKeywordTest {

    @Test
    fun `by 키워드 테스트`() {
        val myAirbus330 = CarOrAirplane("Lamborghini", CarImpl("Seoul"))
        val myBoeing337 = CarOrAirplane("Boeing 337", AirplaneImpl("Seoul"))

        myAirbus330.tellMeYourTrip()
        myBoeing337.tellMeYourTrip()
    }
}
interface Vehicle {
    fun go(): String
}
class CarImpl(val where: String): Vehicle {
    override fun go() = "is going to $where"
}
class AirplaneImpl(val where: String): Vehicle {
    override fun go() = "is flying to $where"
}
class CarOrAirplane(val model: String, impl: Vehicle): Vehicle by impl {
    fun tellMeYourTrip() {
        println("$model ${go()}")
    }
}