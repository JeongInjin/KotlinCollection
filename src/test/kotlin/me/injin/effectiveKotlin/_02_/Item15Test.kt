package me.injin.effectiveKotlin._02_

import org.junit.jupiter.api.Test

class Item15Test {

    @Test
    fun Test() {
        val node = Node("parent")
        node.makeChild("child")
    }

    @Test
    fun alsoTest() {
        var hello = "Hello"
        val result = hello.also {
            println("Original: $it")
            it.reversed() // 이 연산의 결과는 무시됩니다.
        } // result: String ("Hello", 원본 문자열을 반환)
        println("Result: $result")
        println("Hello: $hello")
    }
}

class Node(val name: String) {
    fun makeChild(childName: String) =
        create("$name.$childName")
//            .apply { println("Created $name") } // Created parent
//            .apply { println("Created ${this?.name}") } // Created parent.child (좋은방법은 아님)
//            .also { println("Created ${it?.name}") } // Created parent.child
//            .let { println("Created ${it?.name}") } // Created parent.child

    private fun create(name: String): Node? = Node(name)
}

class User(var name: String, var age: Int) {
    fun birthday() {
        age++ // 여기서 'age'는 명시적인 리시버 참조 없이 사용됨
    }
}

fun User.printInfo() {
    println("Name: $name, Age: $age") // 확장 함수 내에서 'this' 생략 가능
}

val user = User("injin Jeong", 10).apply {
    name = "injin Jeong" // 'apply' 내에서 'this'를 생략하고 리시버의 속성에 접근
    birthday() // 'this' 생략하고 리시버의 함수 호출
}


class OuterTest {
    val outerProperty = "Outer property"

    fun outerFunction() {
        println("This is from the outer function.")
    }

    inner class Inner {
        val innerProperty = "Inner property"

        fun nestedFunction() {
            // 여기서 this는 Inner 객체를 가리킵니다.
            println(this.innerProperty) // "Inner property" 출력

            this@OuterTest.outerFunction() // OuterTest의 함수 호출
            println(this@OuterTest.outerProperty) // OuterTest의 프로퍼티 접근
        }

        fun outerFunction() {
            println("This is from the outer function.")
        }

        fun test() {
            // 직접적으로 outerFunction 호출 시, Inner의 outerFunction이 호출됩니다.
            println(outerFunction())
            // OuterTest의 함수를 호출하려면, 리시버를 명시적으로 지정해야 합니다.
            println(this@OuterTest.outerFunction())
        }
    }

    fun doSomethingWithInner() {
        Inner().nestedFunction() // Inner의 확장 함수 호출
        Inner().test()
    }
}
