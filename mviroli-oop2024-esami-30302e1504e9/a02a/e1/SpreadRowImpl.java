package a02a.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;

public class SpreadRowImpl implements SpreadRow {

    private static record Cell(
        int base,
        Optional<BinaryOperator<Integer>> op, 
        Optional<Set<Integer>> refs
    ) {}

    private final List<Cell> cells;

    public SpreadRowImpl(final int size) {
        this.cells = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            this.cells.add(null);
        }
    }

    @Override
    public int size() {
        return this.cells.size();
    }

    @Override
    public boolean isFormula(int index) {
        return !isEmpty(index) && this.cells.get(index).op().isPresent();
    }

    @Override
    public boolean isNumber(int index) {
        return !isEmpty(index) && !isFormula(index);
    }

    @Override
    public boolean isEmpty(int index) {
        return this.cells.get(index) == null;
    }

    @Override
    public List<Optional<Integer>> computeValues() {
        final List<Optional<Integer>> values = new LinkedList<>();
        for (final var cell : this.cells) {
            if (isFormula(this.cells.indexOf(cell))) {
                final List<Integer> currRefs = new LinkedList<>(cell.refs().get());
                int result = cell.base();
                for (int i = 0; i < currRefs.size(); i++) {
                    result = cell.op().get().apply(result, values.get(currRefs.get(i)).get());
                }
                values.add(Optional.of(result));
            } else if (isNumber(this.cells.indexOf(cell))) {
                values.add(Optional.of(cell.base()));
            } else {
                values.add(Optional.empty());
            }
        }
        return values;
    }

    @Override
    public void putNumber(int index, int number) {
        this.cells.set(index, new Cell(number, Optional.empty(), Optional.empty()));
    }

    @Override
    public void putSumOfTwoFormula(int resultIndex, int index1, int index2) {
        this.cells.set(resultIndex, new Cell(0, Optional.of((n1, n2) -> n1 + n2), Optional.of(Set.of(index1, index2))));
    }

    @Override
    public void putMultiplyElementsFormula(int resultIndex, Set<Integer> indexes) {
        this.cells.set(resultIndex, new Cell(1, Optional.of((n1, n2) -> n1 * n2), Optional.of(indexes)));
    }

}
