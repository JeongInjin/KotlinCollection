package me.injin.cookbook._06_

import org.junit.jupiter.api.Test

class testCode {

    @Test
    fun `6_1 지연 시퀀스 사용하기`() {
        // 6-1 3으로 나누어지는 첫 번재 배수 찾기(버전1)
        // 끔찍하게 비효율 적이다.
        val version1 = (100 until 200).map { it * 2 }
            .filter { it % 3 == 0 }
            .first()
        println("version1: $version1")

        // 6-2 3으로 나누어지는 첫 번재 배수 찾기(버전2)
        val version2 = (100 until 200).map { it * 2 }
            .first { it % 3 == 0 }
        println("version2: $version2")

        println("===============================")
        // 6-3 3으로 나누어지는 첫 번재 배수 찾기(최적)
        val version3 = (100 until 2_000_000).asSequence()
            .map { println("dpubling $it"); it * 2 }
            .filter { println("filtering $it"); it % 3 == 0 }
            .first()

        println("version3: $version3")

    }
}