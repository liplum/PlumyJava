package plumy.path;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The linked list-like pointers. The destination's pointer can trace back the start point.
 */
public class Pointer<Vert> {
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