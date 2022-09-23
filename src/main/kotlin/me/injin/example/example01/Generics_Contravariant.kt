package me.injin.example.example01_03

class Generics_Invariant {
}

/**
    1. 불변성(무공변성, invariant)
    상속 관계에 상관없이 자신의 타입만 허용하는 것을 뜻한다. Kotlin 에서는 따로 지정해주지 않으면 기본적으로 모든 Generic Class 는 무공변이다. Java 에서의 <T>와 같다.
 */
open class Alcohol
class Soju : Alcohol()

interface Drinker<T> {
    fun drink()
}


//소주가 Alcohol 을 상속받았지만 별도의 공변성을 지정하지 않았기에 "무공변" 이다.
fun varianceTest(input: Drinker<Alcohol>){
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
//    println(varianceTest(soju)) // Type mismatch: inferred type is Drinker<Soju> but Drinker<Alcohol> was expected

}