package data

import java.util.*

class CircularArray<T> : LinkedHashSet<T>() {

    fun isTheSameOf(target: CircularArray<T>): Boolean {
        val offset = target.indexOf(this.get(0))
        val copy = target.shift(offset)
        return (this == copy)
    }

    fun get(index: Int): T {
        val i = when (index >=0) {
            true -> index % size
            false -> size + index % size
        }
        return this.toList().get(i)
    }

    fun next(element: T): T {
        return get(this.indexOf(element) + 1)
    }

    fun previous(element: T): T {
        return get(this.indexOf(element) - 1)
    }

    private fun shift(offset: Int) : CircularArray<T> {
        val copy = CircularArray<T>()
        for (i in 0 until size) {
            copy.add(this.get(i + offset))
        }
        return copy
    }

}


fun main(args: Array<String>) {
    val uno = CircularArray<Int>()
    uno.addAll(Arrays.asList(1, 1, 2, 3, 4, 5, 6, 7, 8, 9))
    val due = CircularArray<Int>()
    due.addAll(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 1, 1, 2))
    val tre = CircularArray<Int>()
    tre.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 1))
    val quattro = CircularArray<Int>()
    quattro.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 8, 7, 9, 1))
    val cinque = CircularArray<Int>()
    cinque.addAll(Arrays.asList(1, 2, 3, 4, 5, 5, 8, 7, 9, 1))
    println(uno.isTheSameOf(due))
            println(uno.isTheSameOf(quattro))
            println(quattro.next(9))
            println(due.next(1))
            println(tre.previous(1))
}
