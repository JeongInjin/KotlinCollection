package me.injin.effectiveKotlin._01_


class item1 {
}

fun main() {
    val account = BankAccount()
    println(account.balance) // 0.0
    account.deposit(100.0)
    println(account.balance) // 100.0
    account.deposit(50.0)
    println(account.balance) // 50.0
}
class InsufficientFunds : Exception()
class BankAccount {
    var balance = 0.0
        private set

    fun deposit(depositAmount: Double) {
        balance += depositAmount
    }

    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double) {
        if (balance < withdrawAmount) {
            throw InsufficientFunds()
        }
        balance -= withdrawAmount
    }
}