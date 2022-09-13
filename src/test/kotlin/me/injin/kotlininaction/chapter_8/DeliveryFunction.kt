package me.injin.kotlininaction.chapter_8

import org.junit.jupiter.api.Test

class DeliveryFunction {

    @Test
    fun getShippingCostCalculatorTest() {
        val calculator = getShippingCostCalculator(Delivery.EXPEDITED)
        println(calculator(Order(3)))
    }


    fun getShippingCostCalculator(delivery: Delivery): (Order) -> Double { //(Order) -> Double 함수를 반환하는 함수를 선언한다.
        if(delivery == Delivery.EXPEDITED)
            return { order -> 6 + 2.1 * order.itemCount } // 함수에서 람다를 반환한다.

        return { order -> 1.2 * order.itemCount } // 함수에서 람다를 반환한다.
    }

    @Test
    fun durationTest() {
        println(log.averageDurationFor(OS.WINDOWS))
        println(log.averageDurationFor(OS.MAC))

        println("==================")
        //하드코딩
        println(averageMobileDuration)

        println("==================")

        //고차함수
        println(log.averageDurationFor { it.os in setOf(OS.ANDROID, OS.IOS) })
        println(log.averageDurationFor { it.os == OS.IOS && it.path == "/signup" })

    }
}
enum class Delivery { STANDARD, EXPEDITED}

class Order(val itemCount: Int)

// 사이트 방문 데이터 정의
 data class SiteVisit(
    val path: String,
    val duration: Double,
    val os: OS
 )

enum class OS { WINDOWS, LINUX, MAC, IOS, ANDROID}

val log = listOf(
    SiteVisit("/", 34.0, OS.WINDOWS),
    SiteVisit("/", 22.0, OS.MAC),
    SiteVisit("/login", 12.0, OS.WINDOWS),
    SiteVisit("/signup", 8.0, OS.IOS),
    SiteVisit("/", 16.3, OS.ANDROID),
)
fun List<SiteVisit>.averageDurationFor(os: OS) = filter { it.os == os }.map { it.duration }.average()

//복잡하게 하드코딩한 필터를 사용해 데이터 붆석
val averageMobileDuration = log.filter { it.os in setOf(OS.IOS, OS.ANDROID) }.map { it.duration }.average()


//고차 함수를 사용해 중복 제거하기
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) = filter(predicate).map { it.duration }.average()



