# 코루틴 딥다이브(마르친 모스카와)

---

# 11장 - 코루틴 스코프 함수

코루틴에서 GlobalScope를 사용하여 async를 호출하면, 생성된 코루틴은 부모 코루틴과 아무런 관계가 없습니다. 즉, GlobalScope로 시작된 코루틴은 애플리케이션의 전체 수명 주기와 연결되어 있으며,
특정 부모 코루틴에 속하지 않습니다.

부모코루틴이 취소되어도 'GlobalScope' 로 시작된 코루틴은 취소되지 않습니다.
GlobalScope로 시작된 코루틴은 애플리케이션의 생명 주기와 직접 연결되므로, 애플리케이션이 종료될 때까지 종료되지 않을 수 있습니다.
에러 처리가 부모-자식 관계에 의존하는 일반적인 코루틴 구조와는 다르게, GlobalScope 코루틴에서 발생하는 에러는 자동으로 부모에게 전파되지 않습니다.

 

suspend fun getUserProfile(scope: CoroutineScope): UserProfileData {
val user = scope.async { getUserData() }
val notifucation = scope.async { getNotifications() }

    return UserprofileData(user.await(), notification.await())

}

위에 코드에서 scope.async 를 사용하는 것은 구조화된 동시성 원칙을 위반합니다.
코루틴의 구조화된 동시성 원칙에 따르면, 코루틴은 항상 특정한 스코프 내에서 실행되어야 하며, 해당 스코프는 코루틴의 생명 주기를 관리합니다.
scope.async 를 사용하면, 해당 코루틴들은 getUserProfile 함수가 종료된 후에도 계속 실행될 수 있으며, 이믄 리소스 누수를 일으킬 수 있다.

@Test
fun `128 페이지`() {
runBlocking {
val details = try {
getUserDetails()
} catch (e: Exception) {
null
}

        val tweets = async { getTweets() }
        println("User: $details")
        println("Tweets: ${tweets.await()}")
        println(details)
    }

}

data class Details(val name: String, val followers: Int)
data class Tweet(val text: String)

fun getFollowersNumber(): Int = throw Error("Service exception")
suspend fun getUserName(): String {
delay(500)
return "JeongInjin"
}

suspend fun getTweets(): List<Tweet> {
delay(1000)
return listOf(Tweet("Hello, World"))
}

suspend fun CoroutineScope.getUserDetails(): Details {
val name = async { getUserName() }
val tweets = async { getFollowersNumber() }
return Details(name.await(), tweets.await())
}

위 코드를 보면 사용자 세부사항을 들고 오는 데 문제가 있더라도 최소한 Tweets 는 볼 수 있을 것 같지만, getFollowersNumber 에서 발생한 예외가 async 를 종료시키고 전체 스코프가 종료되는
걸로 이어져 프로그램이 끝나 버린다
예외가 발생하면 종료되는 대신 예외를 그대로 던지는 함수가 더 낫다
CoroutineScope

코루틴을 구성하고 관리하는 기본적인 단위입니다.
코루틴 스코프는 코루틴의 생명주기를 정의하며, 코루틴의 실행을 제어하는데 사용됩니다.
코루틴 스코프 내에서 시작된 모든 코루틴은 해당 스코프에 속하게 되며, 스코프가 취소되면, 그 안에 있는 모든 코루틴도 함께 취소됩니다.
특징

CoroutineScope 는 내부적으로 쿠리틴의 생명 주기를 관리합니다. 스코프가 취소되면 그 안에 있는 모든 코루틴도 취소됩니다.
CoroutineScope 를 사용하면 구조회된 동시성을 통해 코드를 더 안전하고 관리하기 쉽게 만들 수 있다. 이는 코루틴이 시작되고 종료되는 것을 명확하게 제어할 수 있게 해주며, 리소스 누수를 방지하고 예외
처리를 좀 더 쉽게 만듭니다.
CoroutineScope 는 CoroutineContext 를 통해 코루틴의 실행 환경을 정의합니다.

data class Details(val name: String, val follwers: Int)
data class Tweets(val text: String)
class ApiException(val code: Int, message: String) : Throwable(message)

fun getFollowersNumber(): Int {
throw ApiException(500, "Service unavailable")
}

suspend fun getUserName(): String {
delay(500)
return "Kotlin"
}

suspend fun getTweets(): List<Tweets> {
delay(1000)
return listOf(Tweets("This is a tweet"))
}

suspend fun getUserDetails(): Details = coroutineScope {
val name = async { getUserName() }
val follwersNumber = async { getFollowersNumber() }
Details(name.await(), follwersNumber.await())
}

@Test
fun `131 페이지`() = runBlocking {
val details = try {
getUserDetails()
} catch (e: ApiException) {
println("Error: ${e.message}")
null
}

    val tweets = async { getTweets() }
    println("User: $details")
    println("Tweets: ${tweets.await()}")

}

coroutineScope 함수는 기존의 중단 컨텍스트에서 벗어나 새로운 스코프를 만듭니다. 부모로부터 스코프를 상속받고 구조화된 동시성을 지원합니다.

coroutineScope 함수는 호출된 위치의 현재 코루틴 컨텍스트(ex: 부모 코루틴 컨텍스트)를 기반으로 새로운 코루틴 스코프를 생성합니다.즉 coroutineScope 사용하면, 이미 실행 중인 코루틴 내에서
새로운 코루틴 작업을 그룹화하고 관리할 수 있는 새로운 범위를 만들 수 있다는 뜻 하지만 새로운 범위는 여전히 외부(부모) 코루틴 컨텍스트를 삭속받는다.즉 스코프는 독립적으로 작동하지만 부모 코루틴의 컨텍스트 설정을
사용한다.
부모 코루틴이 취소되면 coroutineScope 내부의 모든 코루틴도 함깨 취소된다.

구조화된 동시성(Sturctured Concurrency)

코루틴을 관리하는 코틀린의 중요한 개념 중 하나. 코드 내에서 동시에 실행되는 작업들을 체계적이고 안전하게 관리할 수 있도록 설계되었다. 구조화된 동시성의 주요 목표는 코루틴의 생명 주기를 그들이 실행되는 코드의
구조에 맞추어 관리하는 것
코루틴과 같은 동시 실행 작업을 안전하고 예측 가능한 방식으로 관리하는 프로그래밍 패턴입니다. 간단히 말해, 코드의 구조 내에서 동시에 실행되는 모든 작업을 체계적으로 구성하여, 부모 작업이 자식 작업들이 모두
완료될 때까지 종료되지 않도록 함으로써, 리소스 누수를 방지하고 에러를 쉽게 처리할 수 있게 합니다.
부모 자식 관계

구조화된 동시성에서는 모든 코루틴은 부모 코루틴과 연결된다.자식 코루틴이 시작되면, 자동적으로 부모 코루틴의 생명 주기에 바인딩된다. 부모 코루틴이 취소되거나 종료되면 모든 자식 코루틴도 함께 취소되거나 종료된다.

코루틴의 종료 대기

부모 코루틴은 자식 코루틴들이 모두 완료될 때까지 종료되지 않습니다.부모 코루틴이 자식 코루틴들의 완료를 자동으로 기다린다는 것을 의미.이로 인해 동시에 실행되는 여러 작업을 더 쉽게 추적하고 관리할 수 있다.

에러 처리

자식 코루틴에서 에러가 발생하면, 해당 에러는 부모 코루틴으로 전파된다. 이를 통해 에러를 캡처하고 적절하게 처리할 수 있으며, 하나의 코루틴에서 발생한 문제가 전체 코루틴 트리에 영향을 미치는 것을 방지할 수
있다.

코루틴 빌더는(launch, async) 는 새로운 코루틴을 시작하는 데 사용되며, 주로 작업의 실행을 위해 사용된다. 이들은 부모 코루틴의 생명주기에 종속되며, 부모 코루틴이 취소되면 자식 코루틴도 함께 취소된다.
코루틴 스코프 함수(coroutineScope, supervisorScope) 는 현재 코루틴의 실행을 구조화하고 관리하는 데 사용된다. 이들은 새로운 코루틴을 시작하는 것이 아니라 현재 코루틴의 범위 내에서 여러
비동기 작업을 조작하고 관리하는 역할을 한다.coroutineScope 는 모든 자식 코루틴이 완료될 떄까지 기다리며, supervisorScope 는 자식 코루틴 간에 독십성을 제공합니다.

coroutineScope:모든 자식 코루틴이 함께 성공하거나 함께 실패합니다. 하나의 자식 코루틴에서 예외가 발생하면, 모든 자식 코루틴이 취소되고 예외가 상위로 전파됩니다.supervisorScope:자식
코루틴들은 서로 독립적으로 실행됩니다. 한 자식 코루틴에서 예외가 발생해도, 다른 자식 코루틴들은 영향을 받지 않고 계속 실행됩니다.

runBlocking 또한 함수 본체를 곧바로 호출하고 그 결과를 반환합니다. 가장 큰 차이점은 runBlocking 은 블로킹 함수지만 코루틴 스코프 함수는 중단 함수라는 것입니다. 따라서 runBlocking 은
코루틴 계층에서 가장 상위에 있으며, 코루틴 스코프 함수는 계층 중간에 있는 것입니다.

runBlocking

블로킹 함수: 호출한 스레드를 차단 합니다. runBlocking 내부의 코루틴 작업이 모두 완료될 때까지 현재 스레드의 실행을 멈춥니다.주로 테스트 환경이나, 메인 함수와 같이 코루틴 환경외부에서 코루틴 코드를
실행할 때 사용됨
계층에서의 위치: runBlockung 은 코루틴 계층에서 가장 상위에 위치합니다. runBlocking 이 새로운 코루틴 스코프를 만들고, 이 스코프 내에서 실행된 모든 코루틴이 완료될 때까지 기다린다는 의미.
일반적으로 runBlocking 은 권장되지 않는다.

withContext

withContext 함수는 coroutineScope 와 비슷하지만 스코프의 컨텍스트를 변경할 수 있다는 점에서 다르다.
withContext 의 인자로 컨텍스트를 제공하면(코루틴 빌더와 같은 방식으로) 부모 스코프의 컨텍스트를 대체한다

fun CoroutineScope.log(test: String) {
val name = this.coroutineContext[CoroutineName]?.name
println("$name : $test")
}

@Test
fun `135 페이지`() = runBlocking(CoroutineName("Parent")) {
log("Before")

    withContext(CoroutineName("Child 1")) {
        delay(1000)
        log("Hello 1")
    }

    withContext(CoroutineName("Child 2")) {
        delay(1000)
        log("Hello 2")
    }

    log("After")

}
// Parent : Before
// Child 1 : Hello 1
// Child 2 : Hello 2
// Parent : After

withContext 함수는 기존 스코프와 컨텍스트가 다른 코루틴 스코프를 설정하기 위해 주로 사용된다.
특징

컨텍스트 변경: 첫번째 인자로 CoroutineContext 를 받는다. withContext 블록 내에서 실행되는 코루틴의 컨텍스트를 임시로 변경합니다. 예를들어 다른 Dispatcher 를 사용하여 코루틴을 다른
스레드에서 실행하게 할 수 있습니다.
부모 스코프의 컨텍스트 대체: withContext 에 제공된 컨텍스트는 부모 코루틴 스코프의 컨텍스트를 임시로 대체합니다. 즉 withContext 블록내에서는 전달된 새 컨텍스트가 적용되며, 블록이 종료되면
원래의 부모 스코프 컨텍스트로 돌아간다.
결과 반환: withContext 는 블록의 마지막 표현식의 결과를 반환한다. 이는 coroutineScope 와 유사하지만 다른 코루틴 빌더(laungch, async) 와는 다르다.이는 withContext 를
사용하여 결과를 직접 반환할 수 있음을 의미한다

135p.구조화된 동시성의 원칙코틀린 코루틴의 '구조화된 동시성'원칙은 코루틴의 실행과 생명 주기를 명확하게 관리하기 위한 개념입니다. 이 원칙에 따르면, 코루틴은 항상 어떤 부모 코루틴의 컨텍스트 내에서 실행되어야
하며, 부모 코루틴은 자식 코루틴의 완료를 기다리고, 자식 코루틴에서 발생하는 예외를 적절히 처리할 책임이 있습니다.

여러 async 호출 후 즉시 await()사용의 문제점

여러 async를 호출한 후 각각에 대해 즉시 await()을 호출하는 방식은, async로 시작된 각 작업이 독립적으로 처리됩니다. 만약 async 중 하나에서 예외가 발생하면 다음 상황이 발생할 수 있습니다:
예외가 발생한 async 작업에 대한 await()가 아직 호출되지 않았다면, 프로그램은 다른 async 작업의 결과를 기다리는 동안 계속 실행됩니다. 이는 예외가 발생한 시점에 나머지 작업을 적절히 처리하지 못하고
계속 진행되게 할 수 있습니다.
다른 async 작업이 이미 실행 중이라면, 예외가 발생한 작업의 await()을 호출할 때까지 다른 작업들은 취소되거나 중단되지 않습니다. 이는 리소스 누수나 불필요한 작업 실행을 초래할 수 있습니다.

// 여러 async 호출 후 즉시 await() 사용

suspend fun fetchData(): String {
delay(1000) // 네트워크 호출을 시뮬레이션
return "Data"
}

suspend fun processMultipleRequests() = runBlocking {
val data1 = async { fetchData() }
val data2 = async { fetchData() }

    println(data1.await()) // 첫 번째 작업의 결과를 기다림
    println(data2.await()) // 두 번째 작업의 결과를 기다림

}
위 코드에서는 두 개의 fetchData() 호출이 병렬로 실행되고, 각각의 결과를 바로 기다립니다. 하지만 여기서 한 작업에서 예외가 발생하면, 다른 하나는 여전히 실행됩니다.

// coroutineScope 사용
// coroutineScope를 사용하는 방식에서는 모든 async 작업이 같은 스코프 내에서 실행되며, 모든 작업이 완료될 때까지 기다립니다.

suspend fun processMultipleRequestsStructured() = runBlocking {
coroutineScope { // 새로운 코루틴 스코프 생성
val data1 = async { fetchData() }
val data2 = async { fetchData() }

        println(data1.await()) // 첫 번째 작업의 결과를 기다림
        println(data2.await()) // 두 번째 작업의 결과를 기다림
    }

}

이 경우, coroutineScope 내에서 시작된 모든 코루틴은 해당 스코프가 종료될 때까지 완료되어야 합니다. 만약 어떤 async 작업에서 예외가 발생하면, coroutineScope 내의 다른 모든 코루틴은
자동으로 취소됩니다. 그리고 예외는 runBlocking으로 전파되어 적절히 처리될 수 있습니다.

coroutineScope 사용의 주요 이점은 모든 자식 코루틴의 완료를 보장하고, 예외 발생 시 모든 코루틴을 적절히 취소하며, 예외를 상위 스코프로 전파하여 처리할 수 있다는 것입니다. 이는 코드를 더 안전하고
관리하기 쉽게 만듭니다.

withTimeout

    @Test
    fun `140 페이지`(): Unit = runBlocking {
        launch {
            launch {
                delay(2000)
                println("Will not be printed")
            }
            withTimeout(1000) {
                delay(1500)
                println("Will not be printed")
            }
            launch {
                println("Will not be printed")
            }
        }
        launch {
            delay(2000)
            println("Will be printed")
        }
    }
    // Will be printed

코루틴 스코프 함수 연결하기

서로 다른 코루틴 스코프 함수의 두 가지 기능이 모두 필요하다면 코루틴 스코프 함수에서 다른 기능을 가지는 코루틴 스코프 함수를 호출해야 합니다.

---

# 12장 - 디스패처

코틀린 코루틴 라이브러리가 제공하는 중요한 기능은 코루틴이 실행되어야 할 스레드를 결정할 수 있다는 것
디스패처를 이용해 이러한 기능을 사용할 수 있다.

 

기본 디스패처

디스패처를 설정하지 않으면 기본적으로 설정되는 디스패처는 CPU 집약적인 연산을 수행하도록 설계된 DIpatchers.Default 입니다.
코드가 실행되는 컴퓨터의 CPI 개수와 동일한 수(최소 두 개 이상)의 스레드 풀을 가지고 있습니다.
runBlocking은 주로 테스트나 메인 함수와 같은 블로킹 환경에서 코루틴 코드를 실행하기 위해 사용됩니다.
runBlocking을 호출하면 호출한 스레드를 블로킹합니다. 즉, runBlocking 블록 내의 모든 코루틴 작업이 완료될 때까지 현재 스레드가 대기 상태가 됩니다.
runBlocking은 자신만의 코루틴 디스패처를 생성하지 않습니다. 특별히 다른 디스패처를 지정하지 않으면, runBlocking 블록 내의 코루틴들은 기본적으로 호출한 스레드(즉, 'main' 스레드나
runBlocking을 호출한 다른 스레드)에서 실행됩니다.

기본 디스패처를 제한하기

Dispatchers.Defaul 의 limitedParallelism 을 사용하면 디스패처가 같은 스레드 풀을 사용하지만 같은 시간에 특정 수 이상의 스레드를 사용하지 못하도록 제한할 수 있습니다.

메인 디스패치

안드로이드에서 메인 스레드는 UI와 상호작용하는 데 사용하는 유일한 스레드이다.
메인스레드가 블로킹되면 전체 애플리케이션이 멈춰 버린다.

IO 디스패처

파일을 읽고 쓰는 경우, 안드로이드의 셰어드 프레퍼런스를 사용하는 경우, 블로킹 함수를 호출하는 경우처럼 I/O 연산으로 스레드를 블로킹할 때 사용하기 위해 설계되었다.
Dispatchers.IO 는 64개 또는 더 많은 코어가 있다면 해당 코어의 수 로 제한이 된다.
Dispatchers.Default 와 Dispatchers.IO 는 같은 스레드 풀을 공유한다.
Dispatchers.IO 를 사용하는 가장 흔한 경우는 라이브러리에서 블로킹 함수를 호출해야 하는 경우이다. 이런경우 withContext(Dispatchers.IO) 로 래핑해 중단 함수를 만드는 것이 가장
좋다.

커스텀 스레드 풀을 사용하는 IO 디스패처

Dispatchers.IO 에는 limitedParallelism 함수를 위해 정의된 특별한 작동 방식이 있습니다.
limitedParallelism 함수는 독립적인 스레드 풀을 가진 새로운 디스패처를 만듭니다.
이렇게 만들어진 풀은 우리가 원하는 만큼 많은 수의 스레드 수를 설정할 수 있으므로 스데르 수가 64개로 제한되지 않습니다.
Dispatchers.IO.limitedParallelism(100)을 사용하여 100개의 병렬 작업을 수행할 수 있는 별도의 디스패처를 생성할 수 있다는 것을 의미합니다. 이렇게 생성된 디스패처는 원본
Dispatchers.IO와 동일한 스레드 풀을 공유할 수 있지만, 동시에 실행될 수 있는 작업의 수는 서로 독립적으로 관리됩니다.단, 이러한 디스패처들은 모두 같은 백엔드 스레드 풀을 공유하기 때문에, 전체적인
시스템 리소스 사용은 여전히 관리되어야 합니다. 즉, Dispatchers.IO의 병렬성 한도를 초과하는 다수의 디스패처를 생성한다 해도, 실제 사용 가능한 스레드 수는 시스템의 자원에 의해 제한됩니다.

정해진 수의 스레드 풀을 가진 디스패처

자신이 스레드 풀을 직접 관리하기를 원하는 니즈가 있을때 자바는 이를 지원해주는 API 제공해준다
close 함수로 반드시 닫아줘야한다.

싱글스레드로 제한된 디스패처

요즘은 주로 Dipatchers.Default.limitedParallelism(1) 식으로 주로 사용함.

# 13장 - 코루틴 스코프 만들기

코루틴 스코프 객체를 만드는 가장 쉬운 방법은 CoroutineScope 팩토리 함수를 사용하는 것입니다

fun main() {
// CoroutineScope 팩토리 함수를 사용하여 코루틴 스코프 생성
val scope = CoroutineScope(Dispatchers.Default)

    // 코루틴 스코프 내에서 코루틴 실행
    scope.launch {
        // 비동기적으로 작업 수행
        delay(1000L)
        println("CoroutineScope Example")
    }

    // 메인 스레드를 1.5초 동안 차단하여 코루틴이 완료되기를 기다림
    Thread.sleep(1500L)

}

Ktor 에서 모든 핸들러는 기본적으로 중단 함수이다.따로 스코프를 만들 필요는 거의 없습니다

https://tech.kakaopay.com/post/spring-and-ktor/

 

Spring 공화국에서 Ktor 사용하기 | 카카오페이 기술 블로그
Spring 공화국에서 Spring 개발자가 Ktor로 개발해본 경험담을 공유합니다.
tech.kakaopay.com

추자적인 연산을 시작하기 위한 스코프를 종종 만들곤 합니다.이런 스코프는 함수나 생성자의 인자를 통해 주로 주입됩니다.스코프를 호출을 중단하기 위한 목적으로만 사용하려는 경우 SupervisorScopre 를
사용하는 것만으로 충분합니다.

// 코루틴 내에서 발생하는 예외를 처리하기 위한 핸들러입니다. 코루틴이 예외로 인해 실패하면,
// 이 핸들러가 호출되며, 여기서는 FirebaseCrashlytics를 사용하여 해당 예외를 기록하고 있습니다.
// 이를 통해 애플리케이션의 예외 상황을 모니터링하고 디버깅할 수 있습니다.
private val exceptionHandler =
CoroutineExceptionHandler { _, throwable ->
FirebaseCrashlytics.getInstance().recordException(throwable)
}

// analyticsScope는 SupervisorJob과 exceptionHandler를 결합하여 생성된 코루틴 스코프입니다.
// 이 스코프 내에서 실행되는 코루틴들은 SupervisorJob의 특성을 가지게 되므로,
// 개별 코루틴의 실패가 다른 코루틴에 영향을 미치지 않습니다.
// 또한, 이 스코프에서 발생하는 예외는 exceptionHandler를 통해 처리됩니다.
val analyticsScope = CoroutineScope(
SupervisorJob() + exceptionHandler
)

remind

SupervisorJob()은 코루틴의 부모-자식 관계에서 중요한 역할을 합니다. 일반적인 Job과는 다르게, SupervisorJob을 사용하면 하나의 자식 코루틴에서 발생한 예외가 다른 자식 코루틴들에게 전파되지
않습니다. 이는 여러 개의 독립적인 작업을 관리할 때 유용합니다. 예를 들어, 여러 개의 네트워크 요청을 병렬로 실행하고 있을 때, 하나의 요청에서 예외가 발생해도 다른 요청들은 영향을 받지 않고 계속 실행될 수
있습니다.

---




