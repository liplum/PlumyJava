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

    override val start: Vert
        get() = path.first
    override val destination: Vert
        get() = path.last
}