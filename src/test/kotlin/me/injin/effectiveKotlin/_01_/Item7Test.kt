package me.injin.effectiveKotlin._01_

import org.junit.jupiter.api.Test

class Item7Test {

//    @Test
//    fun nullOrFailureTest() {
//        val result = someMethod(1)
//        when (result) {
//            is Success -> println("is Success")
//            is Failure -> println("is Failure")
//        }
//        println("====================")
//        val result2 = someMethod(2)
//        when (result2) {
//            is Success -> println("is Success")
//            is Failure -> println("is Failure")
//        }
//    }
//
//    val random = Random()
//
//    fun someMethod(num: Int): Result<String> {
////        val num = random.nextInt(5)
//        return if (num == 1) {
//            println("Failure(IllegalStateException())")
//            Failure(IllegalStateException())
//        } else {
//            Success("직업 성공")
//        }
//    }
//
//    sealed class Result<out T>
//    class Success<out T>(val result: T) : Result<T>()
//    class Failure(val throwable: Throwable) : Result<Nothing>()

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Failure(val exception: Exception) : Result<Nothing>()
    }

    fun calculateSum(a: Int, b: Int): Result<Int> {
        return try {
            Result.Success(a + b)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    @Test
    fun mainResult() {
        val result = calculateSum(50, 51)
        when (result) {
            is Result.Success -> println("Sum is ${result.data}")
            is Result.Failure -> println("Error: ${result.exception.message}")
        }
    }

    sealed class ProcessResult<out T> {
        data class Success<out T>(val data: T) : ProcessResult<T>()
        sealed class Failure : ProcessResult<Nothing>() {
            object FileNotFound : Failure()
            object PermissionDenied : Failure()
            data class ParsingError(val error: String) : Failure()
            data class ApiError(val code: Int, val message: String) : Failure()
        }
    }

    fun processFile(fileName: String): ProcessResult<String> {
        // 파일 처리 로직 구현
        return ProcessResult.Failure.FileNotFound // 예시
    }

    @Test
    fun sealedResultTest() {
        val result = processFile("example.txt")

        when (result) {
            is ProcessResult.Success -> println("File processed: ${result.data}")
            is ProcessResult.Failure.FileNotFound -> println("File not found")
            is ProcessResult.Failure.PermissionDenied -> println("Permission denied")
            is ProcessResult.Failure.ParsingError -> println("Parsing error: ${result.error}")
            is ProcessResult.Failure.ApiError -> println("API error ${result.code}: ${result.message}")
        }
    }

    @Test
    fun listTest() {
        val list = listOf(1, 2, 3, 4, 5)

        val get = list[0] // list.get(0)
        println("get: $get")

        // 리스트에서 특정 위치의 요소를 반환하지만, 요청한 인덱스가 범위를 벗어날 경우 null을 반환합니다.
        val orNull = list.getOrNull(10) // list.get(10) or null
        println("orNull: $orNull")
        list.getOrElse(0) { 0 } // list.get(0) or 0

        // Elvis 연산자 사용
        val first = list.firstOrNull() ?: 0
        println("first: $first")

        val last = list.lastOrNull() ?: 0
        println("last: $last")

        // get: 1
        // orNull: null
        // first: 1
        // last: 5
    }
    
}


