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
/**
 * An abstract path. To iterate this will get all vertices from start point to destination.
 *
 * @author Liplum
 * @since 1.0
 */
interface ISizedPath<Vert> : IPath<Vert>
        where Vert : IVertex<Vert> {
    val size: Int
}

fun <Vert> ISizedPath<Vert>.isEmpty(): Boolean
        where Vert : IVertex<Vert> =
    size == 0

fun <Vert> ISizedPath<Vert>.isNotEmpty(): Boolean
        where Vert : IVertex<Vert> =
    size > 0