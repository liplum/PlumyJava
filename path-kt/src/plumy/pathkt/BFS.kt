package plumy.pathkt

/**
 * Non-thread-safe Breadth-First search.
 * To Implement this interface and provide some essential methods as back-end fields
 *
 * @author Liplum
 * @since 1.0
 */
interface BFS<Vert, Path>
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    /**
     * Reset all states and caches.
     * For example the stack used by [BFS.pushCacheStack]
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
     * Pop this FIFO stack.
     *
     * @return the popped or null if stack is empty
     */
    fun popCacheStack(): Vert?
    /**
     * Push a vertex into this FIFO stack.
     *
     * @param newVertex a vertex to be pushed
     */
    fun pushCacheStack(newVertex: Vert)
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
}
/**
 * Iterate all vertices started with the start point given.
 *
 * @param start    the start pointer
 * @param vertCons which consumes every vertex
 */
inline fun <Vert, Path> BFS<Vert, Path>.forEachVertices(start: Vert, vertCons: (Vert) -> Unit)
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    pushCacheStack(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val v = popCacheStack() ?: break
        for (vert in v.linkedVertices) {
            if (tryLinkNewPointer(vert, null)) {
                pushCacheStack(vert)
            }
        }
        vertCons(v)
    }
}
/**
 * Find all paths between the `start` point and destination.
 * The concrete finding behavior is implemented by the subclass.
 *
 * @param start    the start pointer
 * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
 */
inline fun <Vert, Path> BFS<Vert, Path>.findPath(start: Vert, pathCons: (Vert, Path) -> Boolean)
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    pushCacheStack(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val next = popCacheStack() ?: break
        val pointer: BFS.IPointer<Vert> = getLinkedPointer(next)
        for (vert in next.linkedVertices) {
            if (tryLinkNewPointer(vert, pointer)) {
                pushCacheStack(vert)
            }
        }
        // Check if current pointer is the destination.
        if (isDestination(start, next)) {
            var tracePointer: BFS.IPointer<Vert>? = pointer
            val path = createPath()
            path.addFirst(pointer.self)
            while (true) {
                tracePointer = tracePointer?.previous
                if (tracePointer == null) break
                path.addFirst(tracePointer.self)
            }
            // If the consumer thinks the finding is ended, stop this finding
            if (pathCons(next, path)) break
        }
    }
}
/**
 * Find all paths between the `start` point and destination.
 * The concrete finding behavior is implemented by the subclass.
 *
 * @param start    the start pointer
 * @param destination the destination of path
 * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
 */
inline fun <Vert, Path> BFS<Vert, Path>.findPath(
    start: Vert,
    destination: Vert,
    pathCons: (Path) -> Boolean
) where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    pushCacheStack(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val next = popCacheStack() ?: break
        val pointer: BFS.IPointer<Vert> = getLinkedPointer(next)
        for (vert in next.linkedVertices) {
            if (tryLinkNewPointer(vert, pointer)) {
                pushCacheStack(vert)
            }
        }
        // Check if current pointer is the destination.
        if (next == destination) {
            var tracePointer: BFS.IPointer<Vert>? = pointer
            val path = createPath()
            path.addFirst(pointer.self)
            while (true) {
                tracePointer = tracePointer?.previous
                if (tracePointer == null) break
                path.addFirst(tracePointer.self)
            }
            // If the consumer thinks the finding is ended, stop this finding
            if (pathCons(path)) break
        }
    }
}