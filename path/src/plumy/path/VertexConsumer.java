package plumy.path;

import org.jetbrains.annotations.NotNull;

/**
 * Consume a vertex in a graph.
 *
 * @author EBwilson
 */
@FunctionalInterface
public interface VertexConsumer<Vert extends Vertex<Vert>> {
    /**
     * Accept a vertex in a graph.
     *
     * @param vert a vertex
     */
    void accept(@NotNull Vert vert);
}