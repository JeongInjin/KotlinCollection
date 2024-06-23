package me.injin.effectiveKotlin._03_

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Item20Test {

    @Test
    fun coerceInTest() {
        // coerceIn 확장함수
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

        // coerceAtLeast 확장함수
        val coerceAtLeast1 = (-1).coerceAtLeast(0) // 0
        val coerceAtLeast2 = 0.coerceAtLeast(0) // 0
        val coerceAtLeast3 = 1.coerceAtLeast(0) // 1
        val coerceAtLeast4 = 100.coerceAtLeast(0) // 100
        val coerceAtLeast5 = 101.coerceAtLeast(0) // 101
        val coerceAtLeast6 = 101.coerceAtLeast(100) // 101
        assertThat(coerceAtLeast1).isEqualTo(0)
        assertThat(coerceAtLeast2).isEqualTo(0)
        assertThat(coerceAtLeast3).isEqualTo(1)
        assertThat(coerceAtLeast4).isEqualTo(100)
        assertThat(coerceAtLeast5).isEqualTo(101)
        assertThat(coerceAtLeast6).isEqualTo(101)

        // coerceAtMost 확장함수
        val coerceAtMost1 = (-1).coerceAtMost(100) // -1
        val coerceAtMost2 = 0.coerceAtMost(100) // 0
        val coerceAtMost3 = 1.coerceAtMost(100) // 1
        val coerceAtMost4 = 100.coerceAtMost(100) // 100
        val coerceAtMost5 = 101.coerceAtMost(100) // 100
        val coerceAtMost6 = 101.coerceAtMost(100) // 100

        assertThat(coerceAtMost1).isEqualTo(-1)
        assertThat(coerceAtMost2).isEqualTo(0)
        assertThat(coerceAtMost3).isEqualTo(1)
        assertThat(coerceAtMost4).isEqualTo(100)
        assertThat(coerceAtMost5).isEqualTo(100)
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

    associateBy는 각 키에 대해 하나의 값만을 가지는 Map을 생성합니다.
    groupBy는 각 키에 대해 여러 값을 가지는 Map을 생성합니다.

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

        assertThat(phoneBook).isEqualTo(
            mapOf(
                "+1-888-123456" to Person("John", "Boston", "+1-888-123456"),
                "+49-777-789123" to Person("Sarah", "Munich", "+49-777-789123"),
                "+7-999-456789" to Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
                "+7-999-123456" to Person("Vasilisa", "Saint-Petersburg", "+7-999-123456")
            )
        )

        assertThat(cityBook).isEqualTo(
            mapOf(
                "+1-888-123456" to "Boston",
                "+49-777-789123" to "Munich",
                "+7-999-456789" to "Saint-Petersburg",
                "+7-999-123456" to "Saint-Petersburg"
            )
        )

        assertThat(peopleCities).isEqualTo(
            mapOf(
                "Boston" to listOf("John"),
                "Munich" to listOf("Sarah"),
                "Saint-Petersburg" to listOf("Svyatoslav", "Vasilisa")
            )
        )

        assertThat(lastPersonCity).isEqualTo(
            mapOf(
                "Boston" to "John",
                "Munich" to "Sarah",
                "Saint-Petersburg" to "Vasilisa"
            )
        )
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
     * partition 함수는 주어진 조건에 따라 컬렉션을 두 개의 리스트로 분리합니다.
     * 결과는 Pair로 반환되며, 첫 번째 리스트는 조건을 만족하는 요소들, 두 번째 리스트는 조건을 만족하지 않는 요소들입니다.
     */
    @Test
    fun partitionTest() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)

        val evenOdd = numbers.partition { it % 2 == 0 }

        // 양수와 음수로 분할
        val (positives, negatives) = numbers.partition { it > 0 }

        assertThat(evenOdd).isEqualTo(
            Pair(
                listOf(-2, -4, -6),
                listOf(1, 3, 5)
            )
        )

        assertThat(positives).isEqualTo(listOf(1, 3, 5))
        assertThat(negatives).isEqualTo(listOf(-2, -4, -6))
    }

    /**
     * map: 컬렉션의 각 요소를 변환하지만, 리스트의 구조는 유지합니다.
     * flatMap: 각 요소를 변환하고, 변환된 결과를 하나의 리스트로 평탄화합니다.
     * flatten: 중첩된 리스트를 평탄화하여 하나의 리스트로 만듭니다.
     *
     * flatMap 과 flatten 의 차이점
     *  - flatMap: 각 요소에 대해 변환 함수를 적용한 후, 결과를 평탄화합니다.
     *  - flatten: 중첩된 컬렉션을 단순히 평탄화합니다. 변환 함수는 적용되지 않습니다.
     */
    @Test
    fun flatMapTest() {
        val fruitsBag = listOf("apple", "orange", "banana", "grapes")
        val clothesBag = listOf("shirts", "pants", "jeans")
        val cart = listOf(fruitsBag, clothesBag) // [[apple, orange, banana, grapes], [shirts, pants, jeans]]
        val mapBag = cart.map { it } // [[apple, orange, banana, grapes], [shirts, pants, jeans]]

        val flatMapBag = cart.flatMap { it } // [apple, orange, banana, grapes, shirts, pants, jeans]
        val flattenBag = cart.flatten() // [apple, orange, banana, grapes, shirts, pants, jeans]

        assertThat(mapBag).isEqualTo(listOf(fruitsBag, clothesBag))
        assertThat(flatMapBag).isEqualTo(listOf("apple", "orange", "banana", "grapes", "shirts", "pants", "jeans"))
        assertThat(flattenBag).isEqualTo(listOf("apple", "orange", "banana", "grapes", "shirts", "pants", "jeans"))
    }

}


data class Person(val name: String, val city: String, val phone: String)
