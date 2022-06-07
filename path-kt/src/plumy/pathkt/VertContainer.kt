package plumy.pathkt

/**
 * A container of vertices, for various pathfinder algorithm.
 * To Implement this interface and provide some essential methods as back-end fields
 *
 * @author Liplum
 * @since 1.0
 */
interface VertContainer<Vert, Path>
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    /**
     * Reset all states and caches.
     * For example the stack used by [VertContainer.pushCache]
     */
    fun reset()
    /**
     * Try to link this vertex to a new [IPointer] by its previous pointer.
     *
     * @param linked      the vertex to be linked to a new pointer
     * @param itsPrevious the [IPointer.previous] pointer of this new pointer
     * @return whether the link is built during this calling.
     */
    fun tryLinkNewPointer(linked: Vert, itsPrevious: IPointer<Vert>?): Boolean
    /**
     * Get the pointer linked to this vertex
     *
     * @param vert the vertex
     * @return Its corresponding pointer
     */
    fun getLinkedPointer(vert: Vert): IPointer<Vert>
    /**
     * Pop this cache. It can be used as a FILO stack.<br></br>
     * It may be used in DFS.
     *
     * @return the popped or null if cache is empty
     */
    fun popCache(): Vert?
    /**
     * Push a vertex into this cache at head. It can be used as a FILO stack.<br></br>
     * It may be used in DFS.
     *
     * @param newVertex a vertex to be pushed
     */
    fun pushCache(newVertex: Vert)
    /**
     * Poll a vertex from cache. It can be used as a FIFO queue.<br></br>
     * It may be used in BFS.
     *
     * @return the polled or null if cache is empty
     */
    fun pollCache(): Vert?
    /**
     * Add a vertex into this cache at tail. It can be used as a FIFO queue.<br></br>
     * It may be used in BFS.
     *
     * @param newVertex a vertex to be added
     */
    fun addCache(newVertex: Vert)
    /**
     * Check whether the subject is the destination.
     *
     * @param start   the start point
     * @param subject a vertex to be checked if it's the destination.
     * @return whether the subject is the destination of the start point.
     */
    fun isDestination(start: Vert, subject: Vert): Boolean
    /**
     * Create a new path.
     *
     * @return the path object
     */
    fun createPath(): Path
}
/**
 * The linked list-like pointers. The destination's pointer can trace back the start point.
 */
interface IPointer<Vert> {
    /**
     * The vertex saved
     */
    var self: Vert
    /**
     * The Previous pointer or null if [self] is a start point.
     */
    var previous: IPointer<Vert>?
}