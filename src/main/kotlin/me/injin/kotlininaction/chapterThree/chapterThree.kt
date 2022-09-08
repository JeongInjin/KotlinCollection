package me.injin.kotlininaction.chapterThree

class chapterThree {
    //코틀린에서 컬렉션 만들기

    val set = hashSetOf(1, 7, 53)

    val list = arrayListOf(1, 7, 35)

    // 여기서 to 가 언어가 제공하는 특별한 키워드가 아니라 일반 함수라는 점에 유의 하자.
    val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")

}