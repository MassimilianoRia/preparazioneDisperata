package a02a.e2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LogicsImpl {

    private final Map<Position, Integer> cells = new LinkedHashMap<>();
    private final Random rnd = new Random();
    private final List<Position> obstacles = new ArrayList<>();
    private boolean step = true;

    public LogicsImpl() {
        for (int i = 0; i < 3; i++) {
            this.obstacles.add(null);
        }
    }

    public void addCell(final Position pos) {
        this.cells.put(pos, 0);
    }

    public int getValue(final Position pos) {
        return this.cells.get(pos);
    }

    public void initGrid() {
        for (int i = 0; i < 3; i++) {
            this.cells.put(this.rndPos(), 2);
        }
        final Position origin = new Position(0, 0);
        this.cells.put(origin, 1);
    }

    private Position rndPos() {
        final List<Position> path = new ArrayList<>(this.cells.keySet());
        Position pos;
        do {
            pos = path.get(this.rnd.nextInt(1, path.size()));
        }
        while (this.obstacles.contains(pos));
        this.obstacles.set(this.obstacles.indexOf(null), pos);
        return pos;
    }

    public boolean nextStep() {
        final List<Position> path = new ArrayList<>(this.cells.keySet());
        for (final var el : path) {
            if (this.cells.get(el) == 1) {
                final int move = step ? 3 : 4;
                step = !step;
                this.cells.put(el, 0);
                final Position nextPos = path.get((path.indexOf(el) + move) % path.size());
                if (this.cells.get(nextPos) == 2) {
                    this.obstacles.set(this.obstacles.indexOf(nextPos), null);
                    this.cells.put(nextPos, 0);
                    this.cells.put(new Position(0, 0), 1);
                } else {
                    this.cells.put(nextPos, 1);
                }
                break;
            }
        }
        return this.isOver();
    }

    private boolean isOver() {
        int n = 0;
        for (int i = 0; i < 3; i++) {
            if (this.obstacles.get(i) == null) {
                n++;
            }
        }
        return n == 3;
    }

}
