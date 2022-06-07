package plumy.path;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract path. To iterate this will get all vertices from start point to destination.
 *
 * @author EBwilson
 * @see LinkedPath
 * @see Vertex
 * @since 1.0
 */
public interface IPath<Vert extends Vertex<Vert>> extends Iterable<Vert> {
    /**
     * Insert a new vertex at head.
     *
     * @param head a vertex to be inserted
     */
    void addFirst(@NotNull Vert head);

    /**
     * Append a new vertex at tail.
     *
     * @param tail a vertex to be appended
     */
    void addLast(@NotNull Vert tail);

    /**
     * Get the start point.
     */
    @NotNull
    Vert getStart();

    /**
     * Get the destination.
     */
    @NotNull
    Vert getDestination();
}
