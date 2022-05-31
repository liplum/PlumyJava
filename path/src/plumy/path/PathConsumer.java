package plumy.path;


import org.jetbrains.annotations.NotNull;

/**
 * Consume the destination and a path.
 * Use cases
 * <p>
 *     1. When the destination is unknown before, the {@code destination} parameter will be given.
 *     2. When only the first path is needed, the true should be returned once the function is called.
 * </p>
 * @author EBwilson
 * @since 1.0
 */
@FunctionalInterface
public interface PathConsumer<Path extends IPath<Vert>, Vert extends Vertex<Vert>> {
    /**
     * Accept a finding result and decide whether continue to find other paths.
     * @param destination the destination which meets {@linkplain PathFinder#isDestination(Vertex, Vertex)}
     * @param path        the whole path between start point and destination
     * @return whether to stop this path finding.
     */
    boolean accept(@NotNull Vert destination, @NotNull Path path);
}


