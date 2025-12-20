package a01c.e2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogicsImpl {

    private final Map<Position, Integer> cells = new HashMap<>();
    private final List<Position> vertices = new ArrayList<>();
    /*private Position altosx = null;
    private Position altodx = null;
    private Position bassodx = null;
    private Position bassosx = null;*/

    public LogicsImpl() {
        for (int i = 0; i < 4; i++) {
            this.vertices.add(null);
        }
    }

    public void addToLogicsGrid(final Position pos) {
        this.cells.put(pos, 0);
    }

    public int getValueFromLogics(final Position pos) {
        return this.cells.get(pos);
    }

    public void hit(Position pos) {
        if (this.vertices.get(3) != null) {
            this.reset();
        } else {
            if (this.vertices.get(0) == null) {
                this.vertices.set(0, pos);
                this.cells.put(pos, 1);
            } else
            if (this.vertices.get(1) == null) {
                this.vertices.set(1, pos);
                this.cells.put(pos, 2);
            } else
            if (this.vertices.get(2) == null) {
                this.vertices.set(2, pos);
                this.cells.put(pos, 3);
            } else
            if (this.vertices.get(3) == null) {
                this.vertices.set(3, pos);
                this.cells.put(pos, 4);
                this.computeRectangle();
            }
        }
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
        this.cells.put(new Position(0, 0), 5);
    }

}
