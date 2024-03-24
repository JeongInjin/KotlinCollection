package me.injin.effectiveKotlin._01_;

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class Item1KtTest {

    @Test
    fun p6_1() {

        val list = mutableListOf(1, 2, 3)
//        list = mutableListOf(4, 5, 6) //에러


        var num = 0
        repeat(1000) {
            thread {
                Thread.sleep(10)
                num += 1
            }
        }
        Thread.sleep(5000)
        println(num) //1000 이 아닐 확률이 매우 높다.
    }

    suspend fun p6_2_function() {
        var num = 0
        coroutineScope {
            for (i in 1..1000) {
                launch {
                    delay(10)
                    num += 1
                }
            }
        }
        println(num) //실행할 때마다 다른 숫자가 나온다.
    }

    @Test
    fun p6_3() {
        val lock = Any()
        var num = 0
        repeat(1000) {
            thread {
                Thread.sleep(10)
                synchronized(lock) { num += 1 }
            }
        }
        Thread.sleep(1000)
        println(num)
    }

    @Test
    fun p8_1() {
        val list2 = mutableListOf(1, 2, 3)
        list2.add(4)
    }


    var name: String = "InJin"
    var surname: String = "Jeong"
    private val fullName
        get() = "$name $surname" //지우면 안됨

    @Test
    fun p8_2() {
        println(fullName) // Jeong InJin
        name = "Kim"
        println(fullName) // Kim InJin
    }

    // p9_1
    interface Element {
        val active: Boolean
    }

    // 함수 본문에서 override
    class ActualElement : Element {
        override var active: Boolean = false
    }

    // 생성자에서 override
    class ActualElement2(override val active: Boolean) : Element

    @Test
    fun p9_1() {
        val element = ActualElement()
        element.active = true
        println(element.active)

        val element2 = ActualElement2(true)
        println(element2.active)
    }

    @Test
    fun p10_1() {
        fun printLength(obj: Any) {
            if (obj is String) {
                // obj는 자동으로 String 타입으로 캐스트됩니다.
                println(obj.length) // 스마트 캐스트
            }
        }
    }

    @Test
    fun p12_1() {
        val list = listOf(1, 2, 3)
        if (list is MutableList) {
            assertThrows(UnsupportedOperationException::class.java) { list.add(4) }
//            list.add(4)
        }

        //읽기전용에서 mutable 로 변경해야 한다면 복제를 통해서 새로운 mutable 컬렉션을 만들어야 한다.
        val mutableList = list.toMutableList()
        mutableList.add(4)
        assertThat(mutableList).hasSize(4)
    }

    @Test
    fun p13_1() {
        val list = listOf(1, 2, 3)
        val mutableList = list.toMutableList()
        mutableList.add(4)
        assertThat(mutableList).hasSize(4)
        assertThat(list).hasSize(3)
    }

    @Test
    fun p12_2() {
        val list = listOf(1, 2, 3)
        // list.add(4) - 에러
        if (list is MutableList) {
            list.add(4) // 이렇게 하지마시오
        }
    }

    data class Person_p13(val name: String, val surname: String)

    @Test
    fun `p13 4번 - 방어적복사 + 깊은복사 필요가없다`() {
        val originPerson = Person_p13("Injin", "Jeong")
        val copyPerson = originPerson
//        originPerson.name = "minsu" // 에러
//        originPerson.surname = "Kim" // 에러
        assertThat(originPerson).isEqualTo(copyPerson)

        val sharedPerson = originPerson
//        sharedPerson.name = "minsu" // 에러
//        sharedPerson.surname = "Kim" // 에러
        assertThat(originPerson).isEqualTo(sharedPerson)
    }

    data class PersonWithFriends(
        val name: String,
        val surname: String,
        val friends: MutableList<String> = mutableListOf()
    )

    @Test
    fun `p13 4번 - 방어적복사 + 깊은복사 예외케이스`() {
        val originPerson = PersonWithFriends("Injin", "Jeong")
        originPerson.friends.add("minsu")

        val copyPerson = originPerson.copy(friends = originPerson.friends.toMutableList())
        copyPerson.friends.add("suchan")

        assertThat(originPerson.friends).hasSize(1)
        assertThat(copyPerson.friends).hasSize(2)
    }

    data class MutablePerson(var name: String)

    @Test
    fun `p14 6번 예시`() {
        val person = MutablePerson("Injin Jeong")
        val map = mutableMapOf(person to "Developer")

        println(map[person]) // "Developer" 출력
        assertThat(map[person]).isEqualTo("Developer")

        // 객체 상태 변경
        person.name = "Jane"

        // 상태 변경 후 해시 코드가 변경됨
        println(map[person]) // null 출력
        assertThat(map[person]).isNull()
    }


    @Test
    fun p15_1() {
        var user = User("Jeong", "Injin")
        user = user.copy(surname = "momo")
        val testUser = user.copy()
        val testUser2 = user.copy(name = "test", surname = "name")
        println(user)
        println(testUser)
        println(testUser2)
//        user.name = "asd" 안됨
    }

    @Test
    fun p16_1() {
        /**
         * 1번 코드는 구체적인 리스트 구현 내부에 변경 가능 지점이 있다.
         *      -> 멀티스레드 처리가 이루어질 경우, 내부적으로 적절한 동기화가 되어 있는지 확실하게 알 수 없으므로 위험하다.
         * 2번 코드는 프러퍼티 자체가 변경 가능 지점이다.
         *      따라서 멀티스레드 처리의 안정성이 더 좋다고 할 수 있다(물론 잘못 만들면 손실될 수도 있음)
         */
        val list1: MutableList<Int> = mutableListOf()//1번
        var list2: List<Int> = listOf()//2번

        list1.add(1)
        list2 = list2 + 1

        println(list1)
        println(list2)

        list1 += 2 // list1.plusAssign(2)로 변경
        list2 += 2 // list2 = list2.plus(2)로 변경

        var list9: List<String> = listOf()
        list9 += "asd"
        list9 += "efg"

        println(list1)
        println(list2)
        println(list9)
        list9 -= "efg"
        println(list9)

        println("===")
        var list3 = mutableListOf<Int>()
        list3 += 1
        println(list3)
        list3.add(2)
        println(list3)
        list3 += 3
        println(list3)

        var list4 = mutableListOf<String>()
        list4 += "1"
        println(list4)
        list4.add("2")
        println(list4)
        list4 += "3"
        println(list4)

        //이렇게 하지 마세요
        var list5 = mutableListOf<Int>()
        list5 += 1

    }

    @Test
    fun p16_1_again() {
        val list1: MutableList<Int> = mutableListOf() // mutable list
        var list2: List<Int> = listOf() // var 프로퍼티 선언

        // 두 가지 모두 변경할 수 있다.
        list1.add(1)
        list2 = list2 + 1

        // 두 가지 모두 다음과 같이 += 연산자를 사용할 수 있다. 실질적인 동작은 다르다.
        list1 += 1 // list1.plusAssign(1)로 변경
        list2 += 1 // list2 = list2.plus(1)로 변경
    }

    @Test
    fun p16_2() {
        var list = listOf<Int>()
        for (i in 1..1000) {
            thread {
                list = list + i
            }
        }
        Thread.sleep(1000)
        println(list.size) // 1000 이 아닐 확률이 높다.

        val list2 = mutableListOf<Int>()
        for (i in 1..123456) {
            thread {
//                list2 += i
                list2.add(i)
            }
        }
        Thread.sleep(1000)
        println(list2.size)
    }

    @Test
    fun `16_2_test`() {
        val list2 = mutableListOf<Int>()
        val threads = mutableListOf<Thread>()

        for (i in 1..12345) {
            val thread = thread {
                list2.add(i)
            }
            threads.add(thread)
        }

        threads.forEach { it.join() } // 모든 스레드가 작업을 마칠 때까지 대기

        println("Expected size: 1000")
        println("Actual size: ${list2.size}")

    }

    @Test
    fun p17_1() {
        var names by Delegates.observable(listOf<String>()) { _, old, new ->
            println("====================================")
            println("Names changed from $old to $new")
        }

        names += "Fabio" // Names changed from [] to [Fabio]
        names += "Bill" // Names changed from [Fabio] to [Fabio, Bill]
        println("====================================")

        /**
         * Delegates.observable은 프로퍼티의 참조나 값 자체의 변경을 감지하는 용도로 유용하지만,
         * 리스트와 같은 컬렉션의 내용 변경을 직접 감지하지는 않습니다.
         * 컬렉션의 내용 변경을 감지하려면 컬렉션을 래핑하거나 컬렉션의 변경 가능한 메서드를
         * 오버라이딩하는 등의 추가적인 구현이 필요합니다.
         */
        var myList: MutableList<Int> by Delegates.observable(mutableListOf()) { prop, old, new ->
            println("List has changed from $old to $new")
        }

        myList.add(1) // 리스트 변경
        myList = mutableListOf(1, 2, 3) // 리스트 재할당
    }

    @Test
    fun p18_1() {
        val userRepository = UserRepository()
        val storedUsers = userRepository.loadAll()
        storedUsers[4] = "sososososo"

        println(storedUsers)

        println("===")

        val storedUsers2 = userRepository.loadAll2()
//        storedUsers2[4] = "sososososo" //에러
    }

    @Test
    fun `Banking Property 패턴`() {
        val inventory = Inventory()
        val items = inventory.items
//        items.add(Item("Sword")) // 에러
        inventory.addItem(Item("Sword"))
        inventory.addItem(Item("Shield"))
        inventory.addItem(Item("Potion"))

        println(inventory.items)
        inventory.removeItem(inventory.items[1])
        println(inventory.items)
    }

}

class Item(val name: String)

class Inventory {
    // 클래스 내부에서 사용될 가변 리스트
    private val _items = mutableListOf<Item>()

    // 외부에 노출될 읽기 전용 리스트
    val items: List<Item> get() = _items

    // Item을 추가하는 메서드
    fun addItem(item: Item) {
        _items.add(item)
    }

    // Item을 제거하는 메서드
    fun removeItem(item: Item) {
        _items.remove(item)
    }
}

data class User(val name: String, val surname: String)
data class User2(val name: String)

class UserRepository {
    private val storedUsers: MutableMap<Int, String> = mutableMapOf()

    fun loadAll(): MutableMap<Int, String> {
        return storedUsers
    }

    fun loadAll2(): Map<Int, String> {
        return storedUsers
//        return storedUsers.toMap()
    }
}

class p10Class {
    val name: String? = "Injin"
    val surname: String? = "Jeong"

    val fullName: String?
        get() = name?.let { "$it $surname" }

    val fullName2: String? = name?.let { "$it $surname" }
}

fun p10Main() {
    val p10 = p10Class()
//    if (p10.fullName != null) {
//        println(p10.fullName.length)
//    }

    if (p10.fullName2 != null) {
        println(p10.fullName2.length)
    }
}
