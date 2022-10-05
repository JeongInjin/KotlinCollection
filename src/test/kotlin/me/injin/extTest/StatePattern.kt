package me.injin.extTest

import org.assertj.core.api.Java6Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

class StatePattern {

    @Test
    fun `State`() {
        val authorizationPresenter = AuthorizationPresenter()

        authorizationPresenter.loginUser("admin")
        println("authorizationPresenter: $authorizationPresenter")

        assertThat(authorizationPresenter.isAuthorized).isTrue
        assertThat(authorizationPresenter.userName).isEqualTo("admin")
        println("===")
        authorizationPresenter.logoutUser()
        println("authorizationPresenter: $authorizationPresenter")
        assertThat(authorizationPresenter.isAuthorized).isFalse
        assertThat(authorizationPresenter.userName).isEqualTo("Unknown")
    }
}

sealed class AuthorizationState{
    object Unauthorized : AuthorizationState()
    class Authorized(val userName: String) : AuthorizationState()
}
class AuthorizationPresenter {

    private var state: AuthorizationState = AuthorizationState.Unauthorized

    val isAuthorized: Boolean
        get() = when (state) {
            is AuthorizationState.Authorized -> true
            is AuthorizationState.Unauthorized -> false
        }

    val userName: String
        get() {
            return when (val state = this.state) { //val enables smart casting of state
                is AuthorizationState.Authorized -> state.userName
                is AuthorizationState.Unauthorized -> "Unknown"
            }
        }

    fun loginUser(userName: String) {
        state = AuthorizationState.Authorized(userName)
    }

    fun logoutUser() {
        state = AuthorizationState.Unauthorized
    }

    override fun toString() = "User '$userName' is logged in: $isAuthorized"
}