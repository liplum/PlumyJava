package plumy.path;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract interface to find a path in a directed graph or an undirected graph.
 *
 * @author EBwilson
 * @since 1.0
 */
public interface PathFinder<Path extends IPath<Vert>, Vert extends Vertex<Vert>> {
    /**
     * Check whether the subject is the destination.
     *
     * @param start   the start point
     * @param subject a vertex to be checked if it's the destination.
     * @return whether the subject is the destination of the start point.
     */
    boolean isDestination(@NotNull Vert start, @NotNull Vert subject);

    /**
     * Find all paths between the {@code start} point and destination.
     * The concrete finding behavior is implemented by the subclass.
     *
     * @param start    the start pointer
     * @param pathCons which consumes the path and destination vertex then decides whether to continue finding
     */
    void findPath(@NotNull Vert start, @NotNull PathConsumer<Path, Vert> pathCons);

    /**
     * Iterate all vertices started with the start point given.
     *
     * @param start    the start pointer
     * @param vertCons which consumes every vertex
     */
    void forEachVertices(@NotNull Vert start, @NotNull VertexConsumer<Vert> vertCons);
}
