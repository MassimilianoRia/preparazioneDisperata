package a06.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class LogicsImpl {

    private final List<List<Integer>> cells;

    public LogicsImpl(final int size) {
        this.cells = new ArrayList<>(size);
        for (int i=0; i<size; i++){
            this.cells.add(new ArrayList<>());
            for (int j=0; j<size; j++){
                this.cells.get(i).add(ThreadLocalRandom.current().nextInt(1, 3));
            }
        }
    }

    public int getValue(final int i, final int j) {
        return this.cells.get(i).get(j);
    }

    public void fire() {
        for (int col = 0; col < this.cells.size(); col++) {
            for (int row = this.cells.size()-1; row > 0; row--) {
                final int current = this.cells.get(row).get(col);
                final int up = this.cells.get(row-1).get(col);
                if (current != 0 && Objects.equals(up, current)) {
                    this.cells.get(row).set(col, current + up);
                    this.cells.get(row-1).set(col, 0);
                    break;
                }
            }
        }
        this.gravity();
    }

    private void gravity() {
        for (int col = 0; col < this.cells.size(); col++) {
            for (int row = this.cells.size()-2; row > 0; row--) {
                final int current = this.cells.get(row).get(col);
                final int up = this.cells.get(row-1).get(col);
                if (current == 0) {
                    final int tmp = Integer.valueOf(current);
                    this.cells.get(row).set(col, up);
                    this.cells.get(row-1).set(col, tmp);
                }
            }
        }       
    }

    public boolean isOver() {
        for (int col = 0; col < this.cells.size(); col++) {
            for (int row = this.cells.size()-1; row > 0; row--) {
                final int current = this.cells.get(row).get(col);
                final int up = this.cells.get(row-1).get(col);
                if (current != 0 && Objects.equals(up, current)) {
                    return false;
                }
            }
        } 
        return true;       
    }

}
