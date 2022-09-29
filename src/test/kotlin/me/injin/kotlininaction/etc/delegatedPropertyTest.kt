package me.injin.kotlininaction.etc

import java.io.BufferedReader
import java.io.InputStreamReader

class delegatedPropertyTest {

}

fun main() {
    val p1: Player = Player()
    p1.nick = "guest${(Math.random() * 100000).toInt()}"
    p1.leadMonster = Monster.FireMon
    println(p1.nick)
    p1.nick = "guest${(Math.random() * 100000).toInt()}"
    println(p1.nick)
    println(p1.leadMonster)
    p1.clan // 1버만 호출.
    println(p1.clan)
}
class PersonTest {
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }
    // 위임 프로퍼티를 맵에 사용
    val name: String by _attributes
}

enum class Monster {
    WaterMon,
    FireMon,
    EarthMon,
    WinMon
}

enum class Clan {
    DarkSide, LightSide
}

class Player {
    lateinit var nick: String

    lateinit var leadMonster: Monster

    val clan: Clan by lazy {
        this.selectClan()
    }


    private fun selectClan(): Clan {
        var mySide:Clan = Clan.DarkSide

        runCatching {
            Clan.values().forEach {
                println("${it.ordinal}: ${it.name} 진영 \t")
            }
            println(">> player 진영을 선택")

            var br: BufferedReader = BufferedReader(
                InputStreamReader(System.`in`)
            )
            val sel = br.readLine()
            if (sel == "1") mySide = Clan.LightSide
        }.onFailure { e ->
            e.printStackTrace()
        }

        return mySide
    }

    override fun toString(): String {
        return "Player(nick='$nick', leadMonster=$leadMonster, clan=$clan)"
    }
}

