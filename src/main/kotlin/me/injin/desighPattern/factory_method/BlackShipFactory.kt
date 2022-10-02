package me.injin.desighPattern.factory_method

class BlackShipFactory: ShipFactory {
    override fun createShip(): Ship {
        return BlackShip()
    }
}