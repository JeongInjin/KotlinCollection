package me.injin.effectiveKotlin._03_

class Item28 {

}

@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
annotation class ExperimentalAPI

@ExperimentalAPI
class ExperimentalFeature {
    fun experimentalMethod() {
        println("This is an experimental method")
    }
}

@Deprecated("Use newFunction() instead", ReplaceWith("newFunction()"))
fun oldFunction() {
    println("This is the old function")
}

fun newFunction() {
    println("This is the new function")
}

fun main() {
    // 옵트인 없이 실험적 기능을 사용하면 경고 메시지가 표시됩니다.
    val feature = ExperimentalFeature()
    feature.experimentalMethod()

    // 대체 기능이 제공된 사용 중단된 기능을 호출하면 경고 메시지가 표시됩니다.
    oldFunction()

    // 올바른 사용 예
    newFunction()

    // 옵트인하여 실험적 기능을 사용하는 예
    @OptIn(ExperimentalAPI::class)
    fun useExperimentalFeature() {
        val feature = ExperimentalFeature()
        feature.experimentalMethod()
    }

    useExperimentalFeature()
}
