package a01c.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogicsImpl implements Logics {

    private final Map<Position, Integer> cells = new HashMap<>();
    private final List<Position> vertices = new ArrayList<>();

    public LogicsImpl() {
        for (int i = 0; i < 4; i++) {
            this.vertices.add(null);
        }
    }

    @Override
    public void addToLogicsGrid(final Position pos) {
        this.cells.put(pos, 0);
    }

    @Override
    public int getValueFromLogics(final Position pos) {
        return this.cells.get(pos);
    }

    @Override
    public void hit(Position pos) {
        final var v1 = this.vertices.get(0);
        final var v2 = this.vertices.get(1);
        final var v3 = this.vertices.get(2);
        final var v4 = this.vertices.get(3);

        if (v1 == null) {
            this.vertices.set(0, pos);
            this.cells.put(pos, 1);
            return;
        }

        if (v2 == null) {
            if (v1.x() < pos.x() && v1.y() == pos.y()) {
                this.vertices.set(1, pos);
                this.cells.put(pos, 2);
            } else {
                this.reset();
            }
            return;
        }

        if (v3 == null) {
            if (v2.x() == pos.x() && v2.y() < pos.y()) {
                this.vertices.set(2, pos);
                this.cells.put(pos, 3);
            } else {
                this.reset();
            }
            return;
        }

        if (v4 == null) {
            if (v3.y() == pos.y() && v1.x() == pos.x()) {
                this.vertices.set(3, pos);
                this.cells.put(pos, 4);
                this.computeRectangle();  
            } else {
                this.reset();
            }
            return;
        }
        this.reset();
    }
    
    private void reset() {
        for (final var pos : this.cells.keySet()) {
            this.cells.put(pos, 0);
        }
        this.vertices.clear();
        for (int i = 0; i < 4; i++) {
            this.vertices.add(null);
        }
    }

    private void computeRectangle() {
        final var v1 = this.vertices.get(0);
        final var v3 = this.vertices.get(2);
        final var v4 = this.vertices.get(3);
        for (int col = v4.x()+1; col < v3.x(); col++) {
            for (int row = v4.y()-1; row > v1.y(); row--) {
                final Position rectPos = new Position(col, row);
                this.cells.put(rectPos, 5);
            }
        }
    }

}
