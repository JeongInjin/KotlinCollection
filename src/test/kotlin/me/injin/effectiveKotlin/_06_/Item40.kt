package me.injin.effectiveKotlin._06_

import org.junit.jupiter.api.Test
import java.net.URL

class Item40 {

    /**
     * 일반적인 상황에서 두 주소가 같은 IP 주소를 나타내므로 true 를 출력하지만,
     * 인터넷 연결이 끊겨 있으면 false 를 나타낸다.
     * 네트워크 상태에 의존하여 이러한 equals 문제가 발생한다.
     */
    @Test
    fun urlTest() {
        val enWiki = URL("https://en.wikipedia.org/")
        val wiki = URL("https://wikipedia.org/")
        println(enWiki == wiki)
    }
}