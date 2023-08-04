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
    fun p17_1() {
        var names by Delegates.observable(listOf<String>()) { _, old, new ->
            println("Names changed from $old to $new")
        }

        names += "Fabio" // Names changed from [] to [Fabio]
        names += "Bill" // Names changed from [Fabio] to [Fabio, Bill]
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