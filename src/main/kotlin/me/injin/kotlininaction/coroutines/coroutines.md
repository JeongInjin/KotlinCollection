#Coroutine
- CoroutineScope?
  - Coroutine 사용 범위 정의
  - 모든 Coroutine 은 CoroutineScope 상속

- suspend: 일시중단이 가능한 함수
  - 코루틴 내부에서 수행되어야만 한다.(소스자체가)
- delay(): Thread.sleep 이랑은 다르다 코루틴 안에서 대기하는것과, 쓰레드 자체가 전체 멈추는것과는 다르다. 
  - 다른 코루틴으로 넘어간다.

  - Thread 에 비해 훨씬 가볍다 - 부하가 적다      
```kotlin
//launch 형태가 훨씬 가볍다. 부하가 적다
    @Test
    fun repeatCoroutineTests() = runBlocking {
        repeat(100_000) {
            launch {
                delay(1000L)
                print(".")
            }
        }

        repeat(100_000) {
            thread {
                Thread.sleep(1000L)
                print(".")
            }.run()
        }
    }
```

- GlobalScope 안에서는 메인 쓰레드가 끝나면 하위 launch 가 돌고있어도 종료되버린다.
  - 라이프싸이클의 전체를 뜻한다.
- cancel(), cancelAndJoin()
  - cancel() 은 그냥 종료시킬 수 있음.
  - cancelAndJoin()
    - yield() 같은키워드로 suspend 키워드 함수를 호출 후 재개될때 익셉션을 발생시켜 종료 시킬 수 있다.
      - 주기적으로 suspend 키워드 호출 하는방법 -> 강제로 익셉션 발생
    - isActive 로 상태를 파악하는방법
      - ex) while(isActive) 같은 조건을 줘서 체크하는 방법 -> 익셉션이 발생하지않는다
    - TimeOut 을 이용한 방법
      - withTimeOut(1000L) {...}
```text
- 이 상태 변수들은 다음과 같이 설명될 수 있다.
->확장 프로퍼티
isActive: Job 이 실행중인지 여부를 표시
isCancelled: Job cancel 이 요청되었는지 여부를 표시
isCompleted: Job 의 실행이 완료되었거나 cancel이 완료었는지를 표시
```
- 리소스 반환방식
  - try finally 를 이용해서 리소스 해제를 하자.
- TimeOut
  - withTimeOut 시간이 지나면 종료된다.(익셉션 발생)
- withTImeOutOrNull -> 타임아웃이 났을때 익셉션이 아니라 null 을 반환함