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
    var clearVert2Pointer: EasyContainer<Vert, Path>.() -> Unit = {
        vert2Pointer.clear()
    }
    val list = LinkedList<Vert>()
    val vert2Pointer = HashMap<Vert, IPointer<Vert>>()
    var currentDestination: Vert? = null
    override fun reset() {
        list.clear()
        clearVert2Pointer()
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
        if (list.isEmpty()) null
        else list.pop()

    override fun pushCache(newVertex: Vert) {
        list.push(newVertex)
    }

    override fun pollCache(): Vert? =
        if (list.isEmpty()) null
        else list.poll()

    override fun addCache(newVertex: Vert) {
        list.addLast(newVertex)
    }
}