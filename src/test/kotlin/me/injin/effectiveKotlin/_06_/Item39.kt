package me.injin.effectiveKotlin._06_

class Item39 {
}

class ValueMatcher<T> private constructor(
    private val value: T? = null,
    private val matcher: Matcher
) {

    companion object {
        fun <T> equal(value: T) = ValueMatcher<T>(value = value, matcher = Matcher.EQUAL)

        fun <T> notEqual(value: T) = ValueMatcher<T>(value = value, matcher = Matcher.NOT_EQUAL)

        fun <T> emptyList(value: T) = ValueMatcher<T>(value = value, matcher = Matcher.LIST_EMPTY)

        fun <T> notEmptyList(value: T) = ValueMatcher<T>(value = value, matcher = Matcher.LIST_NOT_EMPTY)
    }

    enum class Matcher {
        EQUAL,
        NOT_EQUAL,
        LIST_EMPTY,
        LIST_NOT_EMPTY
    }

    fun match(value: T?) = when(matcher) {
        Matcher.EQUAL -> value == this.value
        Matcher.NOT_EQUAL -> value != this.value
        Matcher.LIST_EMPTY -> value is List<*> && value.isEmpty()
        Matcher.LIST_NOT_EMPTY -> value is List<*> && value.isNotEmpty()
    }

}

/**
 * sealed 한정자를 붙여서 서브클래스 제한으로 변경.
 */
sealed class ValueMatcher2<T> {
    abstract fun match(value: T): Boolean

    class Equal<T>(private val value: T) : ValueMatcher2<T>() {
        override fun match(value: T): Boolean {
            return value == this.value
        }
    }

    class NotEqual<T>(private val value: T) : ValueMatcher2<T>() {
        override fun match(value: T): Boolean {
            return value != this.value
        }
    }

    class EmptyList<T>(private val value: T) : ValueMatcher2<T>() {
        override fun match(value: T): Boolean {
            return value is List<*> && value.isEmpty()
        }
    }

    class NotEmptyList<T>(private val value: T) : ValueMatcher2<T>() {
        override fun match(value: T): Boolean {
            return value is List<*> && value.isNotEmpty()
        }
    }
}

fun <T> ValueMatcher2<T>.reversed(): ValueMatcher2<T> =
    when (this) {
        is ValueMatcher2.EmptyList -> TODO()
        is ValueMatcher2.Equal -> TODO()
        is ValueMatcher2.NotEmptyList -> TODO()
        is ValueMatcher2.NotEqual -> TODO()
    }

