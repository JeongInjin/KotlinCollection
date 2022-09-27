package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test

class Item3Test {

    @Test
    fun missMatchTest() {
        var animal = Zebra()
//    animal = Animal() //오류: Type missmatch
    }

    @Test
    fun inferredTypeTest() {
        var animal: Animal = Zebra()
        animal = Animal()
    }

}

open class Animal
class Zebra:Animal()
