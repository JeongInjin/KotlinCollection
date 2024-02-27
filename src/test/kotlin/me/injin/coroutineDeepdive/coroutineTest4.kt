package me.injin.coroutineDeepdive

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.injin.effectiveKotlin._01_.User
import org.junit.jupiter.api.Test

class CoroutineTest4 {

    @Test
    fun test1() {
        // 원본 MutableList 생성
        val originalUsers = mutableListOf(User("injin", "Jeong"))

        // 원본 리스트에서 복사본 생성
        val copiedUsers = originalUsers.toList()

        // 원본 리스트 변경
        originalUsers.add(User("injin2", "Jeong2"))
        // copiedUsers 는 읽기 전용이므로 추가할 수 없음
        // copiedUsers.add(User("injin3", "Jeong3")) // 에러 발생
        // copiedUsers.removeAt(0) // 에러 발생
        // copiedUsers 를 읽기전용으로만들고 add 하려면 아래와 같이 변경
        val copiedUsers2 = originalUsers.toList().toMutableList()
        copiedUsers2.add(User("injin3", "Jeong3"))

        // 복사본과 원본 리스트 출력
        println(copiedUsers)  // 복사본에는 injin 만 포함
        println(originalUsers) // 원본에는 injin, injin2 포함
    }

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            FirebaseCrashlytics.getInstance().recordException(throwable)
        }

    val analyticsScope = CoroutineScope(
        SupervisorJob() + exceptionHandler
    )

}

private fun Any.recordException(throwable: Throwable) {
    return Unit
}

object FirebaseCrashlytics {
    fun getInstance(): Any {
        return Any()
    }

}

class UserDownloader(private val api: NetworkService) {
    private val users = mutableListOf<User>()

    fun downloaded(): List<User> = users.toList()

    suspend fun fetchUser(id: Int) {
        val newUser = api.fetchUser(id)
        users.add(newUser)
    }
}

class NetworkService {
    fun getUser(): User {
        return User("1", "2")
    }

    fun fetchUser(id: Int): User {
        return User("1", "2")
    }

}
