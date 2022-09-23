package me.injin.example.example01

class HighOrderFunction {
}
fun myFun(p: () -> Unit) = p() //invoke

//HOF: highOrderFunction
fun takePrintNameFunction(name: String, pnfun: (String) -> Unit) {
    val local: (String) -> Unit = pnfun
    local(name)
}

fun supplyPrintNameFunction(): (String) -> Unit {
    return {name: String ->
        println("your name is $name")
    } //람다식
}

fun main(args: Array<String>) {
    myFun {
        println("Hello Kotlin FP!!")
    }

    //1. 변수에 담을 수 있음 - 1급 함수
    val printName: (String) -> Unit = fun(name: String) {
        println("your name is $name")
    } //익명함수

    printName("jeong injin")
    printName("eunbi")
    printName("printName.javaClass = ${printName.javaClass}")

    val pName2: (String) -> Unit = printName
    pName2("pretty Daeun")

    val pName3: (String) -> Unit = {name: String ->
        println("your name is $name")
    } //람다식
    pName3("injin")

    println("======================================================================")

    //2. 인자로 전달할 수 있음
    takePrintNameFunction("injin", pName2)
    takePrintNameFunction("injin2", printName)
    takePrintNameFunction("david", fun(n: String) { println("yourName is $n") }
    )
    takePrintNameFunction("Mark"
    ) { x: String -> println("your name is $x") }

    println("======================================================================")

    //3. 반환값으로 리턴할 수 있음.
    takePrintNameFunction("yaho", supplyPrintNameFunction())

    supplyPrintNameFunction()("hahaha")

    val pName4 = supplyPrintNameFunction()
    pName4("Last Man")

    println("======================================================================")
    listOf<Int>(1,2,3,4,5,6,7).showNumbersToString({n -> n % 2 == 1}, concats)
    (1..7).toList().showNumbersToString(fun(n: Int) = n % 2 == 0, concats)
}

//숫자 리스트 확장함수 - 고차함수
fun List<Int>.showNumbersToString( //predicate
    pre: (Int) -> Boolean, con: (List<Int>, String) -> String
) {
    val list: ArrayList<Int> = arrayListOf()
    for (n in this) {
        if(pre(n)) list.add(n)
    }
    println(con(this, ", "))
}

val concats = fun(ints: List<Int>, sep: String): String {
    var result = ""
    for ((i, n) in ints.withIndex()) {
        if(i < ints.lastIndex) result += "$n$sep"
        else result += n
    }
    return result
}