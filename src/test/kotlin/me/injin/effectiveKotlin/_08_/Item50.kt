package me.injin.effectiveKotlin._08_

class Item50 {
}

class Student(val name: String?)


//작동은 합니다.
fun List<Student>.getNames1(): List<String> = this
    .map { it.name }
    .filter { it != null }
    .map { it!! }

//더 좋습니다.
fun List<Student>.getNames2(): List<String> = this
    .map { it.name }
    .filterNotNull()

//가장 좋습니다.
fun List<Student>.getNames3(): List<String> = this
    .mapNotNull { it.name }
