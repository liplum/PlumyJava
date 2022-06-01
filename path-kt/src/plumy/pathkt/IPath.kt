package plumy.pathkt

/**
 * An abstract path. To iterate this will get all vertices from start point to destination.
 *
 * @author Liplum
 * @since 1.0
 */
interface IPath<Vert> : Iterable<Vert>
        where Vert : IVertex<Vert> {
    /**
     * Insert a new vertex at head.
     *
     * @param head a vertex to be inserted
     */
    fun addFirst(head: Vert)
    /**
     * Append a new vertex at tail.
     *
     * @param tail a vertex to be appended
     */
    fun addLast(tail: Vert)
    /**
     * Get the start point.
     */
    val start: Vert
    /**
     * Get the destination.
     */
    val destination: Vert
}