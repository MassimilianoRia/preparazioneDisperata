package a03a.e1;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

public class CellsFactoryImpl implements CellsFactory {

    private static class CellImpl<E> implements Cell<E> {

        private E result;
        private final boolean modifiable;
        private final Set<Cell<E>> dependencies;
        private final BinaryOperator<E> op;

        public CellImpl (final E result, final boolean modifiable, final Set<Cell<E>> dependencies, final BinaryOperator<E> op) {
            this.result = result;
            this.modifiable = modifiable;
            this.dependencies = dependencies;
            this.op = op;
        }

        @Override
        public E getResult() {
            if (modifiable) {
                return this.result;
            } else {
                return this.dependencies.stream().map(cell -> cell.getResult()).reduce(op).get();
            }
        }

        @Override
        public boolean isModifiable() {
            return this.modifiable;
        }

        @Override
        public Set<Cell<E>> dependsFrom() {
            return this.dependencies;
        }

        @Override
        public void write(E value) {
            if (this.modifiable) {
                this.result = value;
            } else {
                throw new UnsupportedOperationException();
            }
        }

    }

    private final List<Cell<?>> cells = new LinkedList<>();

    @Override
    public <E> Cell<E> mutableValueCell(E initial) {
        final var cell = new CellImpl<E>(initial, true, Set.of(), null);
        this.cells.add(cell);
        return cell;
    }

    @Override
    public Cell<Integer> sumOfTwo(Cell<Integer> c1, Cell<Integer> c2) {
        final var cell = new CellImpl<>(null, false, Set.of(c1, c2), (x, y) -> x + y);
        this.cells.add(cell);
        return cell;
    }

    @Override
    public Cell<String> concatOfThree(Cell<String> c1, Cell<String> c2, Cell<String> c3) {
        final var cell = new CellImpl<>(null, false, new LinkedHashSet<>(List.of(c1, c2, c3)), (x, y) -> x + y);
        this.cells.add(cell);
        return cell;
    }

    @Override
    public List<Cell<Integer>> cellsWithSumOnLast(List<Integer> values) {
        final List<Cell<Integer>> newCells = new LinkedList<>();
        values.stream().map(i -> new CellImpl<>(i, true, Set.of(), null)).forEach(newCells::add);
        newCells.add(new CellImpl<>(null, false, new LinkedHashSet<>(newCells), (x, y) -> x + y));
        this.cells.addAll(newCells);
        return newCells;
    }

    @Override
    public List<Cell<String>> addConcatenationOnAll(List<Cell<String>> cellList) {
        final List<Cell<String>> newCells = new LinkedList<>(cellList);
        newCells.add(new CellImpl<>(null, false, new LinkedHashSet<>(newCells), (x, y) -> x + y));
        this.cells.addAll(newCells);
        return newCells;
    }

    @Override
    public <E> Cell<E> fromReduction(List<Cell<E>> cellList, BinaryOperator<E> op) {
        final List<Cell<E>> newCells = new LinkedList<>(cellList);
        final var cell = new CellImpl<E>(null, false, new LinkedHashSet<>(newCells), op);
        newCells.add(cell);
        this.cells.addAll(newCells);
        return cell;
    }

}
