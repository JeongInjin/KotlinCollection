package me.injin.effectiveKotlin._01_

import me.injin.extTest.Color
import org.junit.jupiter.api.Test

class Item2Test {

    @Test
    fun `변수를 정의할 때 초기화되는 것이 좋다`() {
        // 나쁜 예
//        val user: User
//        if (hasValue) {
//            user = getValue()
//        } else {
//            user = User()
//        }

        //조금 더 좋은 예
//        val user = User = if (hasValue) {
//            getValue()
//        } else {
//            User()
//        }

    }

    @Test
    fun `구조 분해 선언 테스트 - destructuring declaration`() {
        val updateWeather = updateWeather(4)
        val updateWeatherBad = updateWeatherBadExample(15)
        val (desc, color) = updateWeather(15)
        println("updateWeather.first: ${updateWeather.first}, updateWeather.second: ${updateWeather.second}")
        println("updateWeatherBad.first: ${updateWeatherBad.first}, updateWeatherBad.second: ${updateWeatherBad.second}")
        println("desc: $desc, color: $color")
    }

    //나쁜 예
    fun updateWeatherBadExample(degrees: Int): Pair<String, Color> {
        val description: String
        val color: Color
        if (degrees < 5) {
            description = "cold"
            color = Color.BLUE
        } else if (degrees < 23) {
            description = "mild"
            color = Color.YELLOW
        } else {
            description = "hot"
            color = Color.RED
        }

        return description to color
    }

    // 조금 더 좋은 예
    fun updateWeather(degrees: Int): Pair<String, Color> {
        val (description, color) = when {
            degrees < 5 -> "cold" to Color.BLUE
            degrees < 23 -> "mild" to Color.YELLOW
            else -> "hot" to Color.RED
        }
        println("description: $description, color: $color")
        return (description to color)
    }

    @Test
    fun `에라토스체네스의 체 기본`() {
        var numbers = (2..100).toList()
        val primes = mutableListOf<Int>()
        while (numbers.isNotEmpty()) {
            val prime = numbers.first()
            primes.add(prime)
            numbers = numbers.filter { it % prime != 0 }
        }
        println(primes)
    }

    /**
     * primes.toList() 불려 실행되는 순간 sequence 블록 내부가 실행된다.
     *yield 가 실행되면 sequence 블록 내의 행동이 정지되고, 다시 toList() 가 위치한 main 으로 실행주도권이 넘어감.
     */
    @Test
    fun `에라토스체네스의 체 sequence 이용`() {
        val primes: Sequence<Int> = sequence {
            //2부터 검색하기위해 인수로 2를 주고 1씩 증가하도록 시퀀스 정의
            var numbers = generateSequence(2) { it + 1 }

            while (true) {
                val prime = numbers.first()
                yield(prime)//sequence 에 prime 을 넘겨준다.
                numbers = numbers.drop(1).filter { it % prime != 0 }
            }
        }

        println(primes.take(10).toList())
    }

    @Test
    fun sequenceTest() {
        // 총 205번 연산
        val test = (100..200).map { it * 2 }
            .filter { it % 3 == 0 } // 여기서 101번의 불필요한 연산이 발생하게된다.
            .first()
        println("test: $test")

        // 총 104번 연산
        val test2 = (100..200).map { it * 2 }
            .first { it % 3 == 0 } // 조건을 만족하는 경우 이후 원소는 불필요한 연산을 수행하지 않게된다.
        println("test2: $test2")

        val test3 = (100..2_000_000).asSequence() // 주목할만한 점은 200만으로 올렸음에도 실행속도에 영향이 없다.
            .map { println("doubling $it"); it * 2 }
            .filter { println("filtering $it"); it % 3 == 0 }
//            .first() // 시퀀스가 비었다면 NoSuchElementException이 발생한다.
            .firstOrNull()
        println("test3: $test3")

    }

    @Test
    fun copyTest() {
        var a = CopyTest("a")
        var b = a.copy()
        println(a == b) //true
        println(a === b) //false
    }

    @Test
    fun plusAssignTest() {
        val list1: MutableList<Int> = mutableListOf()
        var list2: List<Int> = listOf()

        list1.add(1)
        list2 += 1

        list1 += 2 // list1.plusAssign(2)로 변경됨.
        list2 += 2 //list2.plus(2)로 변경됨.

        list1.plusAssign(3)
        list2.plus(3)
    }
}

data class CopyTest(val name: String)