package me.injin.desighPattern.factory_method

interface ShipFactory {

    fun orderShip(name: String, email: String): Ship {
        validate(name, email)
        prepareFor(name)
        val ship: Ship = createShip();
        sendEmailTo(email, ship)

        return ship
    }

    fun createShip(): Ship

    fun validate(name: String, email: String) {
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("배 이름을 지어주세요.")
        }

        if (email.isNullOrEmpty()) {
            throw IllegalArgumentException("연락처를 남겨주세요.")
        }
    }

    private fun prepareFor(name: String) {
        println("$name 만들 준비 중")
    }

    private fun sendEmailTo(email: String, ship: Ship) {
        println(ship.name + " 다 만들었습니다.")
    }


}