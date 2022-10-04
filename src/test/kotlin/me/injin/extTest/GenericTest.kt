package me.injin.extTest

import org.junit.jupiter.api.Test

/**
 * Array 를 List 로 변경했는데 가능한거는 List 인터페이스의 공변성 때문에.
 */
class GenericTest {

    @Test
    fun GenericFruitTest() {
//        receiveFruits(arrayOf(Apple) )
//        val fruits: Array<Fruit> = arrayOf(Apple(), Apple())
        val fruits: List<Apple> = listOf(Apple(), Apple())
        receiveFruits(fruits)
    }
//    private fun receiveFruits(apple: Array<Fruit>) {
//        println("fruits size:")
//    }

    fun receiveFruits(fruits: List<Fruit>) {
        println("fruits size: ${fruits.size}")
    }

    fun <T> useAndClose(input: T) where T: AutoCloseable, T: Appendable {
        input.append("there")
        input.close()
    }

    /**
     * 스타 프로젝션은 타입에 대해 정확히는 알 수 없지만 타입 안정성을 유지하면서 파라미터를 전달할 때 사용됩니다.
     * 스타 프로젝션은 읽는 것만 허용하고 쓰는 것은 허용하지 않습니다. 아래는 스타 프로젝션을 사용한 코드입니다.
     */
    fun printValues(values: Array<*>) {
        for (value in values) {
            println(value)
        }
//        values[0] = values[1] // ERROR
    }
}

open class Fruit
class Apple: Fruit()
class Banana: Fruit()

