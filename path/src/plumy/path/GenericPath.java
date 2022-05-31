package plumy.path;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A path based on a {@linkplain LinkedList linked list}.
 * @author EBwilson
 * @since 1.0
 */
public class GenericPath<Vert extends Vertex<Vert>> implements IPath<Vert> {
    public final LinkedList<Vert> path = new LinkedList<>();

    @Override
    public void addFirst(@NotNull Vert head) {
        path.addFirst(head);
    }

    @Override
    public void addLast(@NotNull Vert tail) {
        path.addLast(tail);
    }

    @NotNull
    @Override
    public Vert getStart() {
        return path.getFirst();
    }

    @NotNull
    @Override
    public Vert getDestination() {
        return path.getLast();
    }

    @Override
    public Iterator<Vert> iterator() {
        return path.iterator();
    }
}
