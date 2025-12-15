package a06.e1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class FluentParserFactoryImpl implements FluentParserFactory {

    private static class FluentParserImpl<T> implements FluentParser<T> {

        private Iterator<T> sequence;

        private FluentParserImpl(final Iterator<T> sequence) {
            this.sequence = sequence;
        }

        @Override
        public FluentParser<T> accept(T value) {
            if (Objects.equals(value, this.sequence.next())) {
                return new FluentParserImpl<>(this.sequence);
            }
            throw new IllegalStateException("Parsing not coherent. Object invalid.");
        }

    }

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParserImpl<>(Stream.iterate(0, f -> f+1).iterator());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParserImpl<>(Stream.iterate(List.<Integer>of(), list -> {
            return Stream.concat(list.stream(), Stream.of(list.size())).toList();
        }).iterator());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return new FluentParserImpl<>(Stream.iterate(0, f -> f+1).flatMap(n -> Stream.iterate(0, f -> f+1).limit(n)).iterator());
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return new FluentParserImpl<>(Stream.iterate(s, f -> f.concat(s)).flatMap(n -> Stream.iterate(s, f -> f.concat(s)).limit(n.length())).iterator());
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return new FluentParserImpl<>(Stream.iterate(new Pair<>(i0, List.<String>of()), p -> new Pair<>(op.apply(p.getX()), Collections.nCopies(op.apply(p.getX()), s))).iterator());
    }

}
