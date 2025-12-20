package a01a.e2;

import java.util.Set;

public interface Logics {

    void add(Position pos);

    boolean firstHit(Position pos);

    boolean isBorder(Position pos);

    Set<Position> computeRectangleVertex();

    Set<Position> computeRectangleEdges();

    void reset();

}