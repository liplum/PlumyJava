package plumy.pathkt

import java.util.*

/**
 * A path using [LinkedList].
 * @author Liplum
 * @since 1.0
 */
open class LinkedPath<Vert>(
    val path: LinkedList<Vert> = LinkedList(),
) : ISizedPath<Vert> where Vert : IVertex<Vert> {
    override fun iterator() = path.iterator()
    fun descendingIterator(): MutableIterator<Vert> = path.descendingIterator()
    fun listIterator(): MutableListIterator<Vert> = path.listIterator()
    override fun addFirst(head: Vert) {
        path.addFirst(head)
    }

    override fun addLast(tail: Vert) {
        path.addLast(tail)
    }

    operator fun get(index: Int) =
        path[index]

    operator fun set(index: Int, vert: Vert) {
        path[index] = vert
    }

    override val size: Int
        get() = path.size
    override val start: Vert
        get() = path.first
    override val destination: Vert
        get() = path.last
    override fun toString() = path.toString()
}
/**
 * A path using [ArrayList].
 * @author Liplum
 * @since 1.0
 */
open class ArrayPath<Vert>(
    val path: ArrayList<Vert> = ArrayList(),
) : ISizedPath<Vert> where Vert : IVertex<Vert> {
    constructor(capacity: Int) : this(ArrayList(capacity))

    override fun iterator() = path.iterator()
    fun listIterator() = path.listIterator()
    override fun addFirst(head: Vert) {
        path.add(0, head)
    }

    override fun addLast(tail: Vert) {
        path.add(tail)
    }

    operator fun get(index: Int) =
        path[index]

    operator fun set(index: Int, vert: Vert) {
        path[index] = vert
    }

    override val size: Int
        get() = path.size
    override val start: Vert
        get() = path.first()
    override val destination: Vert
        get() = path.last()
    override fun toString() = path.toString()
}
/**
 * An array path but [addFirst] is swap with [addLast].
 * It's useful when some algorithms often use [addFirst]
 * @author Liplum
 * @since 1.0
 */
open class ReversedArrayPath<Vert>(
    path: ArrayList<Vert> = ArrayList(),
) : ArrayPath<Vert>(path) where Vert : IVertex<Vert> {
    constructor(capacity: Int) : this(ArrayList(capacity))

    override fun addFirst(head: Vert) {
        path.add(head)
    }

    override fun addLast(tail: Vert) {
        path.add(0, tail)
    }
    /**
     * Reverse this path.
     */
    fun reverse() {
        path.reverse()
    }

    override fun toString() = path.toString()
}