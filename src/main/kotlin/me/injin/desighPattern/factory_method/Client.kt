package me.injin.desighPattern.factory_method

class Client {
    fun print(shipFactory: ShipFactory, name: String, email: String) {
        println(shipFactory.orderShip(name, email))
        println("=============================================================================")
    }
}

fun main() {
    val client = Client()
    client.print(WhiteShipFactory(), "whiteShip", "injin@emaul.com")
    client.print(BlackShipFactory(), "blackShip", "injin@emaul.com")
}

