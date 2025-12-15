package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComposableParserFactoryImpl implements ComposableParserFactory {

    private static class ComposableParserImpl<T> implements ComposableParser<T> {

        private Set<List<T>> remaining;

        private ComposableParserImpl(final Set<List<T>> remaining) {
            this.remaining = remaining.stream()
                .map(List::copyOf)
                .collect(Collectors.toSet());
        }

        @Override
        public boolean parse(T t) {
            this.remaining = this.remaining.stream()
                .filter(list -> !list.isEmpty() && Objects.equals(list.get(0), t))
                .map(list -> List.copyOf(list.subList(1, list.size())))
                .collect(Collectors.toSet());
            return !remaining.isEmpty();
        }

        @Override
        public boolean end() {
            return this.remaining.stream().anyMatch(List::isEmpty);
        }

    }

    @Override
    public <X> ComposableParser<X> empty() {
        return new ComposableParserImpl<>(Set.of(List.of()));
    }

    @Override
    public <X> ComposableParser<X> one(X x) {
        return new ComposableParserImpl<>(Set.of(List.of(x)));
    }

    @Override
    public <X> ComposableParser<X> fromList(List<X> list) {
        return new ComposableParserImpl<>(Set.of(list));
    }

    @Override
    public <X> ComposableParser<X> fromAnyList(Set<List<X>> input) {
        return new ComposableParserImpl<>(input);
    }

    @Override
    public <X> ComposableParser<X> seq(ComposableParser<X> parser, List<X> list) {
       final ComposableParserImpl<X> ref = (ComposableParserImpl<X>) parser;
       final Set<List<X>> set = ref.remaining.stream()
       .map(seq -> {
            final List<X> newSeq = new ArrayList<>(seq);
            newSeq.addAll(list);
            return List.copyOf(newSeq);
       })
       .collect(Collectors.toSet());
       return new ComposableParserImpl<>(set);
    }

    @Override
    public <X> ComposableParser<X> or(ComposableParser<X> p1, ComposableParser<X> p2) {
        final ComposableParserImpl<X> ref1 = (ComposableParserImpl<X>) p1;
        final ComposableParserImpl<X> ref2 = (ComposableParserImpl<X>) p2;
        final Set<List<X>> set = Stream.concat(ref1.remaining.stream(), ref2.remaining.stream())
        .map(List::copyOf)
        .collect(Collectors.toSet());
        return new ComposableParserImpl<>(set);
    }

}
