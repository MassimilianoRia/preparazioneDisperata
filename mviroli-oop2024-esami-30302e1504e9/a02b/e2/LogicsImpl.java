package a02b.e2;

import java.util.LinkedHashMap;
import java.util.Map;

public class LogicsImpl implements Logics {

    private final Map<Position, Integer> colCounters = new LinkedHashMap<>();
    private final Map<Position, Integer> rowCounters = new LinkedHashMap<>();
    private final Map<Position, Integer> cells = new LinkedHashMap<>();

    @Override
    public void addColCounter(final Position pos) {
        this.colCounters.put(pos, 0);
    }

    @Override
    public void addRowCounter(final Position pos) {
        this.rowCounters.put(pos, 0);
    }

    @Override
    public void addCell(final Position pos) {
        this.cells.put(pos, 0);
    }

    @Override
    public void hit(final Position pos) {
        if (this.cells.get(pos) != 1) {
            this.cells.put(pos, 1);
            final Position colCell = new Position(pos.x(), pos.x());
            final int updatedColValue = this.colCounters.get(colCell) + 1;
            this.colCounters.put(colCell, updatedColValue);
            final Position rowCell = new Position(9, pos.y());
            final int updatedRowValue = this.rowCounters.get(rowCell) + 1;
            this.rowCounters.put(rowCell, updatedRowValue);
        }
    }

    @Override
    public int getValue(final Position pos) {
        if (this.colCounters.containsKey(pos)) {
            return this.colCounters.get(pos);
        }
        if (this.rowCounters.containsKey(pos)) {
            return this.rowCounters.get(pos);
        }
        if (this.cells.containsKey(pos)) {
            return this.cells.get(pos);
        }
        throw new IllegalStateException();
    }

    @Override
    public void reset() {
        for (final var pos : this.colCounters.keySet()) {
            this.addColCounter(pos);
        }
        for (final var pos : this.rowCounters.keySet()) {
            this.addRowCounter(pos);
        }
        for (final var pos : this.cells.keySet()) {
            this.addCell(pos);
        }
    }

}
