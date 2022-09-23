package me.injin.example.example01_01

class Generics_Invariant {

}

/**
    3. 반공변성(contravariant)
    공변성의 반대 - 자기 자신과 부모 객체만 허용한다. Java 에서의 <? super T>와 같다. Kotlin 에서는 in 키워드를 사용해서 표현한다.
 */
open class Alcohol
class Soju : Alcohol()

interface Drinker<T> {
    fun drink()
}

fun varianceTest(input: Drinker<in Alcohol>){ // in keyword 추가
    input.drink()
}

fun main() {

    val alchol: Drinker<Alcohol> = object:Drinker<Alcohol>{
        override fun drink(){
            println("Drink!")
        }
    }

    val soju: Drinker<Soju> = object:Drinker<Soju>{
        override fun drink(){
            println("Drink Soju!")
        }
    }

    val any: Drinker<Any> = object:Drinker<Any>{
        override fun drink(){
            println("Drink Any!")
        }
    }

    // Success
    println(varianceTest(alchol)) // Drink!

    // Error
//    println(varianceTest(soju)) // Type mismatch: inferred type is Drinker<Soju> but Drinker<in Alcohol> was expected

    // Success
    println(varianceTest(any)) // Drink Any!

}