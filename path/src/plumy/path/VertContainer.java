package plumy.path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A container of vertices, for various pathfinder algorithm.
 * To Implement this interface and provide some essential methods as back-end fields
 *
 * @author EBwilson, Liplum
 * @since 1.0
 */
public interface VertContainer
        <Path extends IPath<Vert>, Vert extends Vertex<Vert>> {
    /**
     * Reset all states and caches.
     * For example the stack used by {@linkplain VertContainer#pushCache(Vertex)}
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
     * Check whether the subject is the destination.
     *
     * @param start   the start point
     * @param subject a vertex to be checked if it's the destination.
     * @return whether the subject is the destination of the start point.
     */
    boolean isDestination(@NotNull Vert start, @NotNull Vert subject);

    /**
     * Get the pointer linked to this vertex
     *
     * @param vert the vertex
     * @return Its corresponding pointer
     */
    @NotNull
    Pointer<Vert> getLinkedPointer(Vert vert);

    /**
     * Pop this cache. It can be used as a FILO stack.<br/>
     * It may be used in DFS.
     *
     * @return the popped or null if cache is empty
     */
    @Nullable
    Vert popCache();

    /**
     * Push a vertex into this cache. It can be used as a FILO stack.<br/>
     * It may be used in DFS.
     *
     * @param newVertex a vertex to be pushed
     */
    void pushCache(Vert newVertex);

    /**
     * Poll a vertex from cache. It can be used as a FIFO queue.<br/>
     * It may be used in BFS.
     *
     * @return the polled or null if cache is empty
     */
    @Nullable
    Vert pollCache();

    /**
     * Add a vertex into this cache at tail. It can be used as a FIFO queue.<br></br>
     * It may be used in BFS.
     *
     * @param newVertex a vertex to be added
     */
    void addCache(@NotNull Vert newVertex);

    /**
     * Create a new path.
     *
     * @return the path object
     * @see LinkedPath
     */
    @NotNull
    Path createPath();
}
