package plumy.pathkt

import java.util.*

/**
 * An easy vertex container.
 * NOTE: Non-thread-safe
 * @author Liplum
 * @since 1.0
 */
open class EasyContainer<Vert, Path>(
    val pointerCtor: () -> IPointer<Vert>,
    val pathCtor: () -> Path,
) : VertContainer<Vert, Path>
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    /**
     * It used to customize the clear behavior.
     * Useful when you're using object pool.
     */
    var clearSeen: EasyContainer<Vert, Path>.() -> Unit = {
        seen.clear()
    }
    val stack = LinkedList<Vert>()
    val seen = HashSet<IPointer<Vert>>()
    val vert2Pointer = HashMap<Vert, IPointer<Vert>>()
    var currentDestination: Vert? = null
    override fun reset() {
        stack.clear()
        clearSeen()
        vert2Pointer.clear()
    }

    override fun tryLinkNewPointer(linked: Vert, itsPrevious: IPointer<Vert>?): Boolean =
        if (linked in vert2Pointer) false
        else {
            vert2Pointer[linked] = pointerCtor().apply {
                self = linked
                previous = itsPrevious
            }
            true
        }

    override fun getLinkedPointer(vert: Vert): IPointer<Vert> =
        vert2Pointer[vert] ?: throw UnlinkedPointerException(vert)

    override fun isDestination(start: Vert, subject: Vert): Boolean =
        (currentDestination ?: throw NoDestinationException(this)) == subject

    override fun createPath(): Path =
        pathCtor()

    override fun popCache(): Vert? =
        if (stack.isEmpty()) null
        else stack.pop()

    override fun pushCache(newVertex: Vert) {
        stack.push(newVertex)
    }

    override fun pollCache(): Vert? =
        if (stack.isEmpty()) null
        else stack.poll()
}