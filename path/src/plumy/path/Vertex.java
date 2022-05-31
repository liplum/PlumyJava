package plumy.path;

import org.jetbrains.annotations.NotNull;

/**
 * A vertex. It could be in a directed graph or an undirected graph.
 * @author EBwilson
 * @since 1.0
 */
public interface Vertex<Vert extends Vertex<Vert>> {
    /**
     * Get other vertices which this linked to
     * <pre>{@code
     * As the flow graph below.
     * A -> (B,C,D)
     * B -> (E)
     * D -> (F,G
     * ┌───┐   ┌───┐   ┌───┐
     * │ A ├───► B ├───► E │
     * └┬─┬┘   └───┘   └───┘
     *  │ │
     *  │ └────▲───┐   ┌───┐
     *  │      │ D ├───► F │
     * ┌▼──┐   └─┬─┘   └───┘
     * │ C │     │
     * └───┘     │     ┌───┐
     *           └─────► G │
     *                 └───┘
     * }</pre>
     */
    @NotNull
    Iterable<Vert> getLinkedVertices();
}
