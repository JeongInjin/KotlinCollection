package me.injin.effectiveKotlin._03_

class Item31 {
}

/**
 * 요소가 추가된 횟수를 기록하는 세트.
 *
 * @param T 세트에 포함된 요소의 타입
 * @property elementsAdded 세트에 추가된 요소의 수
 */
class CounterSetKDoc<T>(
    private val innerSet: MutableSet<T> = mutableSetOf()
) : MutableSet<T> by innerSet {
    /**
     * 세트에 추가된 요소의 수.
     * 중복된 요소나 이미 존재하는 요소를 추가하려고 시도한 경우도 포함됩니다.
     */
    var elementsAdded: Int = 0
        private set

    /**
     * 지정된 요소를 세트에 추가합니다.
     *
     * @param element 추가할 요소
     * @return 요소가 세트에 이미 존재하지 않아 추가된 경우 `true`, 그렇지 않으면 `false`
     */
    override fun add(element: T): Boolean {
        elementsAdded++
        return innerSet.add(element)
    }

    /**
     * 지정된 컬렉션의 모든 요소를 세트에 추가합니다.
     *
     * @param elements 추가할 요소들이 포함된 컬렉션
     * @return 세트가 변경된 경우 `true`, 그렇지 않으면 `false`
     */
    override fun addAll(elements: Collection<T>): Boolean {
        elementsAdded += elements.size
        return innerSet.addAll(elements)
    }
}
