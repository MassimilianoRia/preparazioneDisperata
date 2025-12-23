package a01c.e1;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class SimpleIteratorFactoryImpl implements SimpleIteratorFactory {

    @Override
    public SimpleIterator<Integer> naturals() {
        return new SimpleIterator<Integer>() {

            int n = 0;

            @Override
            public Integer next() {
                return n++;
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> circularFromList(List<X> list) {
        return new SimpleIterator<X>() {

            int i = 0;

            @Override
            public X next() {
                final X getted = list.get(i++);
                if (i == list.size()) {
                    i = 0;
                }
                return getted;
            }

        };
    }

    @Override
    public <X> SimpleIterator<X> cut(int size, SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<X>() {

            int c = 0;

            @Override
            public X next() {
                if (c < size) {
                    c++;
                    return simpleIterator.next();
                }
                throw new NoSuchElementException();
            }
            
        };
    }

    @Override
    public <X> SimpleIterator<Pair<X, X>> window2(SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<Pair<X,X>>() {

            private Pair<X, X> old;

            @Override
            public Pair<X, X> next() {
                final Pair<X, X> pair;
                if (this.old == null) {
                    pair = new Pair<X,X>(simpleIterator.next(), simpleIterator.next());
                } else {
                    pair = new Pair<X,X>(old.get2(), simpleIterator.next());
                }
                old = pair;
                return pair;
            }
            
        };
    }

    @Override
    public SimpleIterator<Integer> sumPairs(SimpleIterator<Integer> simpleIterator) {
        return new SimpleIterator<Integer>() {

            private Integer old;

            @Override
            public Integer next() {
                if (old == null) {
                    int first = simpleIterator.next();
                    int second = old = simpleIterator.next();
                    return first+second;
                }
                int next = simpleIterator.next();
                int res = next+old;
                old = next;
                return res;
            }
            
        };
    }

    @Override
    public <X> SimpleIterator<List<X>> window(int windowSize, SimpleIterator<X> simpleIterator) {
        return new SimpleIterator<List<X>>() {

            private List<X> buffer = new LinkedList<>();

            @Override
            public List<X> next() {
                if (this.buffer.isEmpty()) {
                    for (int i = 0; i < windowSize; i++) {
                        this.buffer.add(simpleIterator.next());
                    }
                } else {
                    this.buffer.remove(0);
                    this.buffer.add(simpleIterator.next());
                }
                return List.copyOf(this.buffer);
            }
            
        };
    }

}
