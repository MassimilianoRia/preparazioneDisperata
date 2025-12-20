package a01b.e1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;

public class IteratorsCombinersImpl implements IteratorsCombiners {

    @Override
    public <X> Iterator<X> alternate(Iterator<X> i1, Iterator<X> i2) {
        return new Iterator<X>() {

            private boolean fromFirst = false;

            @Override
            public boolean hasNext() {
                return i1.hasNext() || i2.hasNext();
            }

            @Override
            public X next() {
                if (!hasNext()) {
                throw new NoSuchElementException();
                }
                if (!i1.hasNext()) {
                    return i2.next();
                }
                if (!i2.hasNext()) {
                    return i1.next();
                }
                fromFirst = !fromFirst;
                return fromFirst ? i1.next() : i2.next();
            }
            
        };
    }

    @Override
    public <X> Iterator<X> seq(Iterator<X> i1, Iterator<X> i2) {
        return new Iterator<X>() {

            @Override
            public boolean hasNext() {
                return i1.hasNext() || i2.hasNext();
            }

            @Override
            public X next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();      
                }
                return i1.hasNext() ? i1.next() : i2.next();
            }
            
        };
    }

    @Override
    public <X> Iterator<X> map2(Iterator<X> i1, Iterator<X> i2, BinaryOperator<X> operator) {
        return new Iterator<X>() {

            @Override
            public boolean hasNext() {
                return i1.hasNext() && i2.hasNext();
            }

            @Override
            public X next() {
                if (!hasNext()) {
                throw new NoSuchElementException();
                }
                return operator.apply(i1.next(), i2.next());
            }
            
        };
    }

    @Override
    public <X, Y, Z> Iterator<Pair<X, Y>> zip(Iterator<X> i1, Iterator<Y> i2) {
        return new Iterator<Pair<X,Y>>() {

            @Override
            public boolean hasNext() {
                return i1.hasNext() && i2.hasNext();
            }

            @Override
            public Pair<X, Y> next() {
                return new Pair<X,Y>(i1.next(), i2.next());
            }
            
        };        
    }

}
