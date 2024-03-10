package me.injin.effectiveKotlin._03_

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Item20Test {

    @Test
    fun coerceInTest() {
        //범위제한 확장함수
        val coerceIn1 = (-1).coerceIn(0, 100) // 0
        val coerceIn2 = 0.coerceIn(0, 100) // 0
        val coerceIn3 = 1.coerceIn(0, 100) // 1
        val coerceIn4 = 100.coerceIn(0, 100) // 100
        val coerceIn5 = 101.coerceIn(0, 100) // 100

        assertThat(coerceIn1).isEqualTo(0)
        assertThat(coerceIn2).isEqualTo(0)
        assertThat(coerceIn3).isEqualTo(1)
        assertThat(coerceIn4).isEqualTo(100)
        assertThat(coerceIn5).isEqualTo(100)
    }

    @Test
    fun extensionFunctionTest() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)

        println("numbers.map { x -> x * 2 }: ${numbers.map { x -> x * 2 }}")
        println("numbers.map { it * 3 }: ${numbers.map { it * 3 }}")

        println("numbers.first(): ${numbers.first()}")
        println("numbers.last(): ${numbers.last()}")
        println("numbers.first { it % 2 == 0 }: ${numbers.first { it % 2 == 0 }}")
        println("numbers.last { it % 2 != 0 }: ${numbers.last { it % 2 != 0 }}")
    }

    /**
    associateBy와 groupBy의 차이점은 동일한 키로 개체를 처리하는 방법입니다.
    associateBy는 적합한 요소들 중 마지막 요소만을 값으로 사용합니다.

    groupBy는 모든 적절한 요소들을 리스트로 만들어 값에 넣습니다.
     */
    @Test
    fun associateByAndGroupByTest() {
        val people = listOf(
            Person("John", "Boston", "+1-888-123456"),
            Person("Sarah", "Munich", "+49-777-789123"),
            Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
            Person("Vasilisa", "Saint-Petersburg", "+7-999-123456")
        )

        val phoneBook = people.associateBy { it.phone }
        val cityBook = people.associateBy(Person::phone, Person::city)
        val peopleCities = people.groupBy(Person::city, Person::name)
        val lastPersonCity = people.associateBy(Person::city, Person::name)
    }

    @Test
    fun associateByAndGroupByTest2() {
        val numbers = listOf("one", "two", "three", "four")
        val mapByFirstChar = numbers.associateBy { it.first() }
        println(mapByFirstChar)  // 결과: {o=one, t=three, f=four}

        val numbersGroupedByFirstChar = numbers.groupBy { it.first() }
        println(numbersGroupedByFirstChar) // 결과: {o=[one], t=[two, three], f=[four]}
    }

    /**
     * 파티션 함수는 주어진 조건을 사용하여 원래 컬렉션을 한 쌍의 리스트로 분할합니다.
     */
    @Test
    fun partitionTest() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)

        val evenOdd = numbers.partition { it % 2 == 0 }
        val (positives, negatives) = numbers.partition { it > 0 }
    }

    /**
     *  flatMap은 컬렉션의 각 요소를 반복 가능한 개체로 변환하고 변환 결과들을 단일 리스트로 작성합니다.
     */
    @Test
    fun flatMapTest() {
        val fruitsBag = listOf("apple", "orange", "banana", "grapes")
        val clothesBag = listOf("shirts", "pants", "jeans")
        val cart = listOf(fruitsBag, clothesBag) // [[apple, orange, banana, grapes], [shirts, pants, jeans]]
        val mapBag = cart.map { it } // [[apple, orange, banana, grapes], [shirts, pants, jeans]]

        val flatMapBag = cart.flatMap { it } // [apple, orange, banana, grapes, shirts, pants, jeans]
        val flattenBag = cart.flatten() // [apple, orange, banana, grapes, shirts, pants, jeans]
    }


}


data class Person(val name: String, val city: String, val phone: String)
