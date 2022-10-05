package me.injin.effectiveKotlin._06_

import org.junit.jupiter.api.Test

class Item36 {

    @Test
    fun counterSetTest() {
        val counterList = CounterSet<String>()
        counterList.addAll(listOf("A", "B", "C"))
        println("counterList.elementsAdded = ${counterList.elementsAdded}") // 6 이 나옴.

        val counterList2 = CountSet<String>()
        counterList2.addAll(listOf("A", "B", "C"))
        println("counterList2.elementsAdded = ${counterList2.elementsAdded}") // 3 이 나옴.
    }
}

/**
 * 상속의 단점.
 */
abstract class Dog {
    open fun bark(){}
    open fun sniff(){}
}

abstract class Robot {
    open fun calculate(){}
}

class Labrador: Dog()

class RobotDog : Dog() {
    override fun sniff() {
        throw Error("Operation not supported") // ISP, LSP 원칙에 위반됨
    }
}

/**
 * 캡슐화를 깨는 상속
 */
class CounterSet<T> : HashSet<T>() {
    var elementsAdded: Int = 0
    private set

    override fun add(element: T): Boolean {
        elementsAdded++
        return super.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elementsAdded += elements.size
        return super.addAll(elements)
    }
}

/**
 * 컴포지션을 활용해 변경
 * 이렇게 수정했을 때 문제가 하나 있는데 다형성이 사라진다는것
 * 이는 위임패턴으로 해결할 수 있다.
 */
class CountSet<T> {
    private val innerSet = HashSet<T>()
    var elementsAdded: Int = 0
        private set

    fun add(element: T) {
        elementsAdded++
        innerSet.add(element)
    }

    fun addAll(elements: Collection<T>) {
        elementsAdded += elements.size
        innerSet.addAll(elements)
    }
}

/**
 * 인터페이스 위임이 활용
 */
class CountSetDele<T>(
    private val innerSet: MutableSet<T> = mutableSetOf()
) : MutableSet<T> by innerSet {
    var elementsAdded: Int = 0
        private set

    override fun add(element: T): Boolean {
        elementsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elementsAdded += elements.size
        return innerSet.addAll(elements)
    }
}

/**
 * 어떤 이유로 상속은 허용하지만, 메서드는 오버리이드하지 못하게 만들고 싶은 경우에는 open 이라는 키워드를 사용하자
 * open 클래스는 open 메서드만 오버라이드할 수 있다.
 */

open class Parent {
    fun a() {}
    open fun b() {}
}

class Child : Parent() {
    //오류
//    override fun a() {
//        super.a()
//    }
    override fun b() {

    }
}