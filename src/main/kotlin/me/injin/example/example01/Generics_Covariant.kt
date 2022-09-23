package me.injin.example.example01_02

class Generics_Covariant {
}

/**
    2. 공변성(covariant)
    자기 자신과 자식 객체를 허용한다. Java 에서의 <? extends T>와 같다. Kotlin 에서는 out 키워드를 사용해서 이를 표시한다.
 */
open class Alcohol
class Soju : Alcohol()

interface Drinker<T> {
    fun drink()
}

fun varianceTest(input: Drinker<out Alcohol>){ // out keyword 추가
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

    // Success
    println(varianceTest(soju)) // Drink Soju!

    // Error
//    println(varianceTest(any)) //Type mismatch: inferred type is Drinker<Any> but Drinker<out Alcohol> was expected

}