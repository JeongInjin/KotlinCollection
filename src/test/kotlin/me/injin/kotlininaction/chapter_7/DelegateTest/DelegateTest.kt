package me.injin.kotlininaction.chapter_7.DelegateTest

import org.junit.jupiter.api.Test

class DelegateTest {

    @Test
    fun mainTest() {
        val file = "File1.mkv"
        val mediaFile = MediaFile(fileDownLoader(file), FilePlayer(file))
        mediaFile.download()
        mediaFile.play()
    }
}

interface DownLoader {
    fun download()
}

interface Player {
    fun play()
}

class MediaFile(private val downLoader: DownLoader, private val player: Player): DownLoader by downLoader, Player by player
//{
//
//    override fun download() {
//        downLoader.download()
//    }
//
//    override fun play() {
//        player.play()
//    }
//}

class fileDownLoader(private val file: String): DownLoader {
    override fun download() {
        println("$file Downloaded")
    }
}

class FilePlayer(private val file: String) : Player {
    override fun play() {
        println("$file Playing")
    }

}
