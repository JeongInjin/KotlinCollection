package me.injin.kotlininaction.etc

import org.junit.jupiter.api.Test



class BitCalTest {

    @Test
    fun bitCalculatorTest() {
        val cctvA: Int = 0b0000_0001
        val cctvB: Int = 0b0000_0010
        val cctvC: Int = 0b0000_0100
        val cctvD: Int = 0b0000_1000
        val cctvE: Int = 0b0001_0000

        var cctvStatus = 0b0000_0000

        //초기상태 확인
        checkStatus(cctvA, cctvStatus)
        checkStatus(cctvB, cctvStatus)
        checkStatus(cctvC, cctvStatus)
        checkStatus(cctvD, cctvStatus)
        checkStatus(cctvE, cctvStatus)

        println("===========================================")

        cctvStatus = switchOn(cctvA, cctvStatus)
        cctvStatus = switchOn(cctvB, cctvStatus)
        cctvStatus = switchOn(cctvC, cctvStatus)
        cctvStatus = switchOn(cctvD, cctvStatus)
        cctvStatus = switchOn(cctvE, cctvStatus)

        //나중상태 확인
        checkStatus(cctvA, cctvStatus)
        checkStatus(cctvB, cctvStatus)
        checkStatus(cctvC, cctvStatus)
        checkStatus(cctvD, cctvStatus)
        checkStatus(cctvE, cctvStatus)

        println("===========================================")

        cctvStatus = switchOff(cctvC, cctvStatus)
        checkStatus(cctvA, cctvStatus)
        checkStatus(cctvB, cctvStatus)
        checkStatus(cctvC, cctvStatus)
        checkStatus(cctvD, cctvStatus)
        checkStatus(cctvE, cctvStatus)

        println("===========================================")
        println("cctvStatus.toString(2): 0b${cctvStatus.toString(2)}")
        println("cctvStatus.toString(16): 0x${cctvStatus.toString(16)}")
        println("cctvStatus.toString(10): ${cctvStatus.toString(10)}")

    }

    fun switchOn(target: Int, status: Int): Int {
        println("CCTV($target) 스위치 ON")
        return status or target
    }

    fun switchOff(target: Int, status: Int): Int {
        println("CCTV($target) 스위치 OFF")
        return status and target.inv() //해당 카메라만 빝 반전 inv 후 and
    }

    fun checkStatus(target: Int, status: Int) {
        if (status and target == 0b0000_0000)
            println("CCTV($target)은 현재 OFF 꺼져 있습니다.")
        else
            println("CCTV($target)은 현재 ON 켜져 있습니다.")
    }
}