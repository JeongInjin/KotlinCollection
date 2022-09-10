package me.injin.kotlininaction.chapter_4

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChapterFourTest {

    @Test
    fun `delegate pattern`() {
        val injin = Injin(DroidKnights())
        println(injin.say())
    }

    @Test
    fun `클래스 위임 사용하기 by 키워드`() {
        val injinBy = InjinBy(DroidKnights())
        injinBy.say()
    }

class CountingSet<T>(val innerSet: MutableCollection<T> = HashSet<T>()) :
    MutableCollection<T> by innerSet { //MutableCollection 의 구현을 innserSet 에게 위임한다.
    var objectsAdded = 0

    override fun add(element: T): Boolean { //메소드는 위임하지 않고 새로운 구현을 제공한다
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean { //메소드는 위임하지 않고 새로운 구현을 제공한다
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}

class CountingSet_Test<T>(val innerSet: MutableCollection<T> = HashSet<T>()) : MutableCollection<T> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<T> {
        TODO("Not yet implemented")
    }

    override fun remove(element: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        TODO("Not yet implemented")
    }

}

/**
 * delegate pattern
 */
interface Speaker {
    val subject: String
    val script: String
    fun say()
}

class DroidKnights : Speaker {
    override val subject = "Jetpack Compose 내부 작동 원리"
    override val script = """
        안녕하세요, 저는 $subject 에 대해 발표할 정인진 입니다.
        Jetpack Compose 는 작년 7월달에 stable 로 출시되었습니다.
        ...
    """.trimIndent()

    override fun say() {
        println("[$subject] $script")
    }
}

class Injin(private val presentation: Speaker) : Speaker {
    override val subject = presentation.subject
    override val script = presentation.script
    override fun say() {
        presentation.say()
    }
}

    //by 키워드를 이용한 delegate pattern 과 비슷한..효과?
    class InjinBy(presentation: Speaker) : Speaker by presentation
}
