package plumy.pathkt

import java.util.*

/**
 * An easy Breadth-first pathfinder.
 * NOTE: Non-thread-safe
 * @author Liplum
 * @since 1.0
 */
open class EasyBFS<Path, Vert>(
    val pointerCtor: () -> BFS.IPointer<Vert>,
    val pathCtor: () -> Path,
) : BFS<Path, Vert>
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    val stack = LinkedList<Vert>()
    val seen = HashSet<BFS.IPointer<Vert>>()
    val vert2Pointer = HashMap<Vert, BFS.IPointer<Vert>>()
    var currentDestination: Vert? = null
    override fun reset() {
        seen.clear()
        stack.clear()
        vert2Pointer.clear()
    }

    override fun tryLinkNewPointer(linked: Vert, itsPrevious: BFS.IPointer<Vert>?): Boolean =
        if (linked in vert2Pointer) false
        else {
            vert2Pointer[linked] = pointerCtor().apply {
                self = linked
                previous = itsPrevious
            }
            true
        }

    override fun getLinkedPointer(vert: Vert): BFS.IPointer<Vert> =
        vert2Pointer[vert] ?: throw UnlinkedPointerException(vert)

    override fun popCacheStack(): Vert? =
        if (stack.isEmpty()) null
        else stack.pop()

    override fun pushCacheStack(newVertex: Vert) {
        stack.push(newVertex)
    }

    override fun isDestination(start: Vert, subject: Vert): Boolean =
        (currentDestination ?: throw NoDestinationException(this)) == subject

    override fun createPath(): Path =
        pathCtor()
}