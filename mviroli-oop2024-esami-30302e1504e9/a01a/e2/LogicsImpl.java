package a01a.e2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LogicsImpl implements Logics {

    final private Map<Position, Boolean> cells;

    public LogicsImpl() {
        this.cells = new HashMap<>();
    }

    @Override
    public void add(final Position pos) {
        this.cells.put(pos, false);
    }

    @Override
    public boolean firstHit(final Position pos) {
        final boolean newBoolean = !this.cells.get(pos);
        if (newBoolean) {
            this.cells.put(pos, newBoolean);
        }
        return newBoolean;
    }

    @Override
    public boolean isBorder(final Position pos) {
        return !(pos.x() == 0
            || pos.y() == 0
            || pos.x() == Math.sqrt(cells.size())-1 
            || pos.y() == Math.sqrt(cells.size())-1);
    }

    @Override
    public Set<Position> computeRectangleVertex() {
        int left = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int up = Integer.MAX_VALUE;
        int down = Integer.MIN_VALUE;
        for (final var entry : this.cells.entrySet()) {
            if (entry.getValue()) {
                if (entry.getKey().x() < left) {
                    left = entry.getKey().x();
                }
                if (entry.getKey().x() > right) {
                    right = entry.getKey().x();
                }
                if (entry.getKey().y() < up) {
                    up = entry.getKey().y();
                }
                if (entry.getKey().y() > down) {
                    down = entry.getKey().y();
                }
            }
        }
        final Set<Position> vertices = new LinkedHashSet<>();
        vertices.add(new Position(left-1, down+1));
        vertices.add(new Position(left-1, up-1));
        vertices.add(new Position(right+1, up-1));
        vertices.add(new Position(right+1, down+1));
        return vertices;
    }

    @Override
    public Set<Position> computeRectangleEdges() {
        final Set<Position> rectangleCells = new LinkedHashSet<>();
        final Iterator<Position> it = this.computeRectangleVertex().iterator();
        final Position vertex1 = it.next();
        final Position vertex2 = it.next();
        final Position vertex3 = it.next();
        final Position vertex4 = it.next();
        for (int i = vertex1.y()-1; i > vertex2.y(); i--) {
            rectangleCells.add(new Position(vertex1.x(), i));
            rectangleCells.add(new Position(vertex4.x(), i));
        }
        for (int i = vertex2.x()+1; i < vertex3.x(); i++) {
            rectangleCells.add(new Position(i, vertex1.y()));
            rectangleCells.add(new Position(i, vertex2.y()));
        }
        return rectangleCells;
    }

    @Override
    public void reset() {
        for (final var pos: this.cells.keySet()) {
            this.add(pos);
        }
    }

}
