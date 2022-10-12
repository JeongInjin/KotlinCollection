package me.injin.effectiveKotlin._07_

class Item47 {

}

@JvmInline
value class Minutes(val minutes: Int) {
    fun toMillis(): Millis = Millis(minutes * 60 * 1000)
}

@JvmInline
value class Millis(val milliseconds: Int) {
}

interface User {
    fun decideAboutTime(): Minutes
    fun wakeUp()
}

interface Timer {
    fun callAfter(time: Millis, callback: () -> Unit)
}

fun setUpUserWakeUpUser(user: User, timer: Timer) {
    val time: Minutes = user.decideAboutTime()
//    timer.callAfter(time) { Type missmatch
//        user.wakeUp()
//    }

        timer.callAfter(time.toMillis()) {
        user.wakeUp()
    }
}

fun gradesTest() {
    Grades(StudentId(13))
}

@JvmInline
value class StudentId(val studentId: Int)

class Grades(
    val studentId: StudentId
)

typealias NewName = Int
typealias hours = Int

val nano: NewName = 10
val hour: hours = 10