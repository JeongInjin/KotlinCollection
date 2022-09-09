package me.injin.kotlininaction.chapter_2

import me.injin.kotlininaction.chapter_2.Color.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class chapterTwoTest {

    @Test
    fun test1() {
        println(max1(1,2))
        println(max2(4,3))
    }

    fun max1(a: Int, b: Int): Int = if(a > b) a else b
    //반환값을 추론할 수 있어 반환값을 명시하지않아도 가능. 코틀린의 장점인 "타입 추론"
    fun max2(a: Int, b: Int) = if(a > b) a else b

    //enum
    @Test
    fun enumTest() {
        println(BLUE.rgb())
        println(enumWhenTest(BLUE))
        println(mix(BLUE,YELLOW))
        println(minxOptimizedc1(BLUE,YELLOW))
    }

    fun enumWhenTest(color: Color) =
        when (color) {
//            Color.RED -> "Richard"
//            Color.ORANGE -> "Of"
//            Color.YELLOW -> "York"
//            Color.BLUE, Color.GREEN -> "Battle"
//            Color.INDIGO, Color.VIOLE -> "Cold"
        //static import 시
            RED -> "Richard"
            ORANGE -> "Of"
            YELLOW -> "York"
            BLUE, GREEN -> "Battle"
            INDIGO, VIOLET -> "Cold"
        }

    //when 과 임의의 객체를 함께 사용
    //해당 함수가 아주 자주 사용된다면 Set 인스턴스가 자주 발생하여 불필요한 가비지 객체가 늘어남에 따라 아래와 같이 함수를 고치자(minxOptimized)
    fun mix(c1: Color, c2: Color) =
        when (setOf(c1, c2)) {
            setOf(RED, YELLOW) -> ORANGE
            setOf(YELLOW, BLUE) -> GREEN
            setOf(BLUE, VIOLET) -> INDIGO
            else -> throw Exception("Dirty color")
        }

    fun minxOptimizedc1(c1: Color, c2: Color) =
        when {
            (c1 == RED && c2 == YELLOW )|| (c1 == YELLOW && c2 == RED) -> ORANGE
            (c1 == YELLOW && c2 == BLUE )|| (c1 == BLUE && c2 == YELLOW) -> GREEN
            (c1 == BLUE && c2 == VIOLET )|| (c1 == VIOLET && c2 == BLUE) -> INDIGO
            else -> throw Exception("Dirty color")
        }


}

