package a03a.e2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics {

    private final Map<Position, Integer> cells = new HashMap<>();
    private final List<Position> stars = new LinkedList<>();
    private int starIndex = -1;
    private int starCounter = 0;

    @Override
    public void addCell(final Position pos) {
        this.cells.put(pos, 0);
    }
    
    @Override
    public void init() {
        final Random rnd = new Random();
        final Set<Position> obstacles = new HashSet<>();
        while (obstacles.size() < 3) {
            final Position obstPos = new Position(rnd.nextInt(10), rnd.nextInt(10));
            obstacles.add(obstPos);
        }
        for (final var obstacle : obstacles) {
            this.cells.put(obstacle, 2);
        }
    }

    @Override
    public void hit(final Position pos) {
        if (this.starCounter < 5) {
            final int value = this.cells.get(pos);
            if (value != 1 && value != 2) {
                this.cells.put(pos, 1);
                this.stars.add(pos);
                this.starCounter++;
            }
        } else {
            Position starPos;
            Position nextStarPos;
            int newValue;
            int isOverCounter = 0;
            do {
                isOverCounter++;
                this.starIndex = (this.starIndex + 1) % this.stars.size();
                starPos = this.stars.get(starIndex);
                nextStarPos = new Position(starPos.x(), starPos.y() + 1);
                if (this.cells.containsKey(nextStarPos)) {
                    newValue = this.getValue(nextStarPos);
                } else {
                    newValue = 1;
                }
                if (isOverCounter > 5) {
                    this.isOver();
                    return;
                }
            } while (newValue == 1 || newValue == 2 || Objects.equals(newValue, null));
            this.cells.put(starPos, 0);
            this.cells.put(nextStarPos, 1);
            this.stars.remove(starPos);
            this.stars.add(nextStarPos);
        }
    }

    private void isOver() {
        this.stars.clear();
        for (final var pos : this.cells.keySet()) {
            if (this.cells.get(pos) != 2) {
                this.cells.put(pos, 0);
            }
        }
        this.starCounter = 0;
        this.starIndex = -1;
    }

    @Override
    public int getValue(final Position pos) {
        return this.cells.get(pos);
    }


}
