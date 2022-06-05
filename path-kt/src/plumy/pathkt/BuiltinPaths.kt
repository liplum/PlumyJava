package plumy.pathkt

import java.util.*

open class LinkedPath<Vert>(
    val path: LinkedList<Vert> = LinkedList()
) : IPath<Vert> where Vert : IVertex<Vert> {
    override fun iterator() = path.iterator()
    override fun addFirst(head: Vert) {
        path.addFirst(head)
    }

    override fun addLast(tail: Vert) {
        path.addLast(tail)
    }
    val size :Int
        get() = path.size
    override val start: Vert
        get() = path.first
    override val destination: Vert
        get() = path.last
}

open class ArrayPath<Vert>(
    val path: ArrayList<Vert> = ArrayList()
) : IPath<Vert> where Vert : IVertex<Vert> {
    constructor(capacity: Int) : this(ArrayList(capacity))

    override fun iterator() = path.iterator()
    override fun addFirst(head: Vert) {
        path.add(0, head)
    }

    override fun addLast(tail: Vert) {
        path.add(tail)
    }
    val size :Int
        get() = path.size
    override val start: Vert
        get() = path.first()
    override val destination: Vert
        get() = path.last()
}