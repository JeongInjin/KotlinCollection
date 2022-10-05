package me.injin.effectiveKotlin._06_

class Item38 {
}

fun setOnClickListener(listener: ()->Unit){
//..
    listener()
}

class ClickListener: ()->Unit{
    override fun invoke() {
        //
        println("invoke")
    }

}

fun main() {
    //람다 or 익명함수
    setOnClickListener{}
    setOnClickListener{ println("abcccc") }
    setOnClickListener(fun (){})

    //레퍼런스 전달
    setOnClickListener(::println)

    //선언된 함수 타입을 구현한 객체로 전달
    setOnClickListener(ClickListener())
}


