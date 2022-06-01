package plumy.path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Non-thread-safe Breadth-First search.
 * To Implement this interface and provide some essential methods as back-end fields
 *
 * @author EBwilson
 * @since 1.0
 */
public interface BFS<Path extends IPath<Vert>, Vert extends Vertex<Vert>> extends PathFinder<Path, Vert> {
    /**
     * Reset all states and caches.
     * For example the stack used by {@linkplain BFS#pushCacheStack(Vertex)}
     */
    void reset();

    /**
     * Try to link this vertex to a new {@linkplain Pointer pointer} by its previous pointer.
     *
     * @param linked      the vertex to be linked to a new pointer
     * @param itsPrevious the {@linkplain Pointer#previous} pointer of this new pointer
     * @return whether the link is built during this calling.
     */
    boolean tryLinkNewPointer(@NotNull Vert linked, @Nullable Pointer<Vert> itsPrevious);

    /**
     * Get the pointer linked to this vertex
     *
     * @param vert the vertex
     * @return Its corresponding pointer
     */
    @NotNull
    Pointer<Vert> getLinkedPointer(Vert vert);

    /**
     * Pop this FIFO stack.
     *
     * @return the popped or null if stack is empty
     */
    @Nullable
    Vert popCacheStack();

    /**
     * Push a vertex into this FIFO stack.
     *
     * @param newVertex a vertex to be pushed
     */
    void pushCacheStack(Vert newVertex);

    /**
     * Create a new path.
     *
     * @return the path object
     * @see GenericPath
     */
    @NotNull
    Path createPath();

    /**
     * Find all paths between the {@code start} point and destination.
     *
     * @param start    the start pointer
     * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
     */
    @Override
    default void findPath(@NotNull Vert start, @NotNull PathConsumer<Path, Vert> pathCons) {
        reset();
        pushCacheStack(start);
        tryLinkNewPointer(start, null);

        Vert next;
        while ((next = popCacheStack()) != null) {
            Pointer<Vert> pointer = getLinkedPointer(next);
            for (Vert vert : next.getLinkedVertices()) {
                if (tryLinkNewPointer(vert, pointer)) {
                    pushCacheStack(vert);
                }
            }
            // Check if current pointer is the destination.
            if (isDestination(start, next)) {
                Pointer<Vert> tracePointer = pointer;
                Path path = createPath();
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
     * Iterate all vertices started with the start point given.
     *
     * @param start    the start pointer
     * @param vertCons which consumes every vertex
     */
    @Override
    default void forEachVertices(@NotNull Vert start, @NotNull VertexConsumer<Vert> vertCons) {
        reset();
        pushCacheStack(start);
        tryLinkNewPointer(start, null);

        Vert v;
        while ((v = popCacheStack()) != null) {
            for (Vert vert : v.getLinkedVertices()) {
                if (tryLinkNewPointer(vert, null)) {
                    pushCacheStack(vert);
                }
            }
            vertCons.accept(v);
        }
    }

    /**
     * The linked list-like pointers. The destination's pointer can trace back the start point.
     */
    class Pointer<Vert> {
        /**
         * The Previous pointer or null if {@linkplain Pointer#self self} is a start point.
         */
        @Nullable
        public Pointer<Vert> previous;
        /**
         * The vertex saved
         */
        @NotNull
        public Vert self;

        /**
         * @param self     the vertex saved
         * @param previous its previous pointer or null if this is a start point.
         */
        public Pointer(@NotNull Vert self, @Nullable Pointer<Vert> previous) {
            this.previous = previous;
            this.self = self;
        }
    }
}
