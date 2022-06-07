package plumy.path;

import org.jetbrains.annotations.NotNull;

public class DFS {
    /**
     * Find a path between the {@code start} point and {@linkplain VertContainer#isDestination(Vertex, Vertex) destination}.<br/>
     * The finding depends on the {@linkplain VertContainer#isDestination(Vertex, Vertex) destination},
     * if multiple destinations are given, the {@code pathCons} will be called more than once
     * when a path between the same {@code start} but into a different destination.
     * @param c the container
     * @param start    the start pointer
     * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
     */
    public static <Path extends IPath<Vert>, Vert extends Vertex<Vert>> void findPaths(
            @NotNull VertContainer<Path, Vert> c,
            @NotNull Vert start,
            @NotNull PathConsumer<Path, Vert> pathCons
    ) {
        c.reset();
        c.pushCache(start);
        c.tryLinkNewPointer(start, null);

        Vert next;
        while ((next = c.popCache()) != null) {
            Pointer<Vert> pointer = c.getLinkedPointer(next);
            for (Vert vert : next.getLinkedVertices()) {
                if (c.tryLinkNewPointer(vert, pointer)) {
                    c.pushCache(vert);
                }
            }
            // Check if current pointer is the destination.
            if (c.isDestination(start, next)) {
                Pointer<Vert> tracePointer = pointer;
                Path path = c.createPath();
                path.addFirst(pointer.self);

                while ((tracePointer = tracePointer.previous) != null) {
                    path.addFirst(tracePointer.self);
                }
                // If the consumer thinks the finding is ended, stop this finding
                if (pathCons.accept(next, path))
                    break;
            }
        }
    }

    /**
     * Find a path between the {@code start} point and {@code destination}.
     *
     * @param c the container
     * @param start    the start pointer
     * @return the first found path or an empty path(but not null) if there is no path
     */
    @NotNull
    public static <Path extends IPath<Vert>, Vert extends Vertex<Vert>> Path findPath(
            @NotNull VertContainer<Path, Vert> c,
            @NotNull Vert start,
            @NotNull Vert destination
    ) {
        c.reset();
        c.pushCache(start);
        c.tryLinkNewPointer(start, null);

        Vert next;
        while ((next = c.popCache()) != null) {
            Pointer<Vert> pointer = c.getLinkedPointer(next);
            for (Vert vert : next.getLinkedVertices()) {
                if (c.tryLinkNewPointer(vert, pointer)) {
                    c.pushCache(vert);
                }
            }
            // Check if current pointer is the destination.
            if (c.isDestination(start, next)) {
                Pointer<Vert> tracePointer = pointer;
                Path path = c.createPath();
                path.addFirst(pointer.self);

                while ((tracePointer = tracePointer.previous) != null) {
                    path.addFirst(tracePointer.self);
                }
                // If the consumer thinks the finding is ended, stop this finding
                return path;
            }
        }
        return c.createPath();
    }

    /**
     * Iterate all vertices started with the start point given.
     * @param c the container
     * @param start    the start pointer
     * @param vertCons which consumes every vertex
     */
    public static <Path extends IPath<Vert>, Vert extends Vertex<Vert>> void forEachVertices(
            @NotNull VertContainer<Path, Vert> c,
            @NotNull Vert start,
            @NotNull VertexConsumer<Vert> vertCons
    ) {
        c.reset();
        c.pushCache(start);
        c.tryLinkNewPointer(start, null);

        Vert v;
        while ((v = c.popCache()) != null) {
            for (Vert vert : v.getLinkedVertices()) {
                if (c.tryLinkNewPointer(vert, null)) {
                    c.pushCache(vert);
                }
            }
            vertCons.accept(v);
        }
    }
}
