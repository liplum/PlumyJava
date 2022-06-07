package plumy.pathkt

/**
 * Iterate all vertices started with the [start] point given.
 *
 * @param start    the start pointer
 * @param vertCons which consumes every vertex
 * @author Liplum
 * @since 1.0
 */
inline fun <Vert, Path> VertContainer<Vert, Path>.forEachVerticesBFS(start: Vert, vertCons: (Vert) -> Unit)
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    addCache(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val v = pollCache() ?: break
        for (vert in v.linkedVertices) {
            if (tryLinkNewPointer(vert, null)) {
                addCache(vert)
            }
        }
        vertCons(v)
    }
}

/**
 * Find a path between the [start] point and [VertContainer.isDestination].<br/>
 * The finding depends on the [VertContainer.isDestination],
 * if multiple destinations are given, the [pathCons] will be called more than once
 * when a path between the same [start] but into a different destination.
 *
 * @param start    the start pointer
 * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
 * @author Liplum
 * @since 1.0
 */
inline fun <Vert, Path> VertContainer<Vert, Path>.findPathsBFS(start: Vert, pathCons: (Vert, Path) -> Boolean)
        where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    addCache(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val next = pollCache() ?: break
        val pointer: IPointer<Vert> = getLinkedPointer(next)
        for (vert in next.linkedVertices) {
            if (tryLinkNewPointer(vert, pointer)) {
                addCache(vert)
            }
        }
        // Check if current pointer is the destination.
        if (isDestination(start, next)) {
            var tracePointer: IPointer<Vert>? = pointer
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
 * Find a path between the [start] point and [destination].
 *
 * @param start    the start pointer
 * @param destination the destination of path
 * @return the first found path or an empty path(but not null) if there is no path
 * @author Liplum
 * @since 1.0
 */
fun <Vert, Path> VertContainer<Vert, Path>.findPathBFS(
    start: Vert,
    destination: Vert,
): Path where Vert : IVertex<Vert>, Path : IPath<Vert> {
    reset()
    addCache(start)
    tryLinkNewPointer(start, null)
    while (true) {
        val next = pollCache() ?: break
        val pointer: IPointer<Vert> = getLinkedPointer(next)
        for (vert in next.linkedVertices) {
            if (tryLinkNewPointer(vert, pointer)) {
                addCache(vert)
            }
        }
        // Check if current pointer is the destination.
        if (next == destination) {
            var tracePointer: IPointer<Vert>? = pointer
            val path = createPath()
            path.addFirst(pointer.self)
            while (true) {
                tracePointer = tracePointer?.previous
                if (tracePointer == null) break
                path.addFirst(tracePointer.self)
            }
            // If the consumer thinks the finding is ended, stop this finding
            return path
        }
    }
    return createPath()
}
