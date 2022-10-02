package me.injin.desighPattern.factory_method

class WhiteShipFactory: ShipFactory {
    override fun createShip(): Ship {
        return WhiteShip()
    }
}