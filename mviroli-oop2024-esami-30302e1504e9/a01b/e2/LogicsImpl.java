package a01b.e2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LogicsImpl implements Logics {

    private final Map<Position, Integer> cells;
    private Position firstHitPosition;
    private Position secondHitPosition;

    public LogicsImpl() {
        this.firstHitPosition = null;
        this.secondHitPosition = null;
        this.cells = new HashMap<>();
    }

    @Override
    public void add(final Position pos) {
        this.cells.put(pos, 0);
    }

    @Override
    public void hit(final Position pos) {
        if (this.firstHitPosition != null && this.secondHitPosition != null) {
            for (final var tmp : this.cells.entrySet()) {
                tmp.setValue(0);
            }
            this.firstHitPosition = null;
            this.secondHitPosition = null;
        } else {
            if (Objects.equals(this.firstHitPosition, null)) {
                this.firstHitPosition = pos;
                this.cells.put(pos, 1);
            } else {
                if (Objects.equals(pos.x(), this.firstHitPosition.x()) && Math.abs(this.firstHitPosition.y() - pos.y())%2 == 0) {
                    this.secondHitPosition = pos;
                    this.cells.put(pos, 1);
                    this.computeRhombus();
                } else {
                    this.cells.put(this.firstHitPosition, 0);
                    this.firstHitPosition = null;
                }
            }
        }
    }

    private void computeRhombus() {
        int x = firstHitPosition.x();
        int y1 = firstHitPosition.y();
        int y2 = secondHitPosition.y();

        int distance = Math.abs(y2 - y1);
        int r = distance / 2;
        int yCenter = (y1 + y2) / 2;

        for (int y = yCenter - r + 1; y < yCenter + r; y++) {
            int dx = r - Math.abs(y - yCenter);
            for (int xx = x - dx; xx <= x + dx; xx++) {
                Position p = new Position(xx, y);
                if (this.cells.containsKey(p)) {
                    this.cells.put(p, 2);
                }
            }
        }
    }

    @Override
    public Position getFirstHitPosition() {
        return this.firstHitPosition;
    }

    @Override
    public int getGridValue(final Position pos) {
        return this.cells.get(pos);
    }

}
