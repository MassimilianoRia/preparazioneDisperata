package a03b.e2;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LogicsImpl {

    private final Map<Position, Integer> cells = new HashMap<>();
    private Set<Position> arrows = new LinkedHashSet<>();
    private int arrowsIndex = 0;

    public void addCell(final Position pos) {
        this.cells.put(pos, 0);
    }

    public void hit(final Position pos) {
        if (this.arrows.size() < 5) {
            this.arrows.add(pos);
            final var isValueEven = this.getValue(pos) % 2 == 0;
            final var newValue = isValueEven ? 1 : 2;
            this.cells.put(pos, newValue);
        } else {
            final List<Position> arrowsList = new LinkedList<>(this.arrows);
            for (int i = 0; i < 5; i++) {
                final int index = (this.arrowsIndex + i) % this.arrows.size();
                final Position currPosition = arrowsList.get(index);
                final var currValue = this.getValue(currPosition);
                final var step = currValue == 1 ? -1 : 1;
                final Position newPosition = new Position(currPosition.x(), currPosition.y() + step);
                if (this.cells.containsKey(newPosition)) {
                    if (this.getValue(newPosition) == 0) {
                        this.arrows.remove(currPosition);
                        this.arrows.add(newPosition);
                        this.cells.put(currPosition, 0);
                        this.cells.put(newPosition, currValue);
                        this.arrowsIndex = (arrowsIndex + 1) % this.arrows.size();
                        return;
                    }
                }
            }
            this.isOver();
        }
    }

    private void isOver() {
        this.arrows.clear();
        for (final var pos : this.cells.keySet()) {
            this.cells.put(pos, 0);
        }
    }

    public int getValue(final Position pos) {
        return this.cells.get(pos);
    }

}