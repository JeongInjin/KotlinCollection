package me.injin.desighPattern.factory_method

open class Ship {
    lateinit var name: String
    lateinit var color: String
    lateinit var logo: String

    override fun toString(): String {
        return "Ship(name='$name', color='$color', logo='$logo')"
    }
}