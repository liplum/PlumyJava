package plumy.pathkt
/**
 * A vertex. It could be in a directed graph or an undirected graph.
 * @author Liplum
 * @since 1.0
 */
interface IVertex<Vert>
        where Vert : IVertex<Vert> {
    /**
     * Get other vertices which this linked to
     * ```
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
     * ```
     */
    val linkedVertices: Iterable<Vert>
}