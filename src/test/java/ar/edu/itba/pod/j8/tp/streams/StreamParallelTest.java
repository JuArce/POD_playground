package ar.edu.itba.pod.j8.tp.streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Class to show the use of parallels. Is not a test as concurrent testing is
 * not simple :-S
 *
 * @author Marcelo
 * @since Aug 6, 2015
 */
public class StreamParallelTest {
    private static final Integer[] intArray = { 5, 2, 9, 1, 3, 4, 7, 6, 8 };

    @Test
    public final void stream_as_is() {
        assertEquals("what we obtain?", "5, 2, 9, 1, 3, 4, 7, 6, 8",
                Stream.of(intArray).map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Test
    public final void stream_ordered() {
        final Comparator<Integer> naturalOrder = Integer::compare;
        final Stream<Integer> stream = Stream.of(intArray);
        assertEquals("what we obtain?", "1, 2, 3, 4, 5, 6, 7, 8, 9", time_action("serialized", () -> stream.sorted(naturalOrder))
                .map(Object::toString)
                    .collect(Collectors.joining(", ")));
    }

    @Test
    public final void stream_reversed_ordered() {
        final Comparator<Integer> naturalOrder = Integer::compare;
        final Stream<Integer> stream = Stream.of(intArray);
        assertEquals("what we obtain?", "9, 8, 7, 6, 5, 4, 3, 2, 1",
                time_action("reversed", () -> stream.sorted(naturalOrder.reversed()))
                        .map(Object::toString)
                            .collect(Collectors.joining(", ")));
    }

    @Test
    public final void stream_parallel_ordered() {
        final Comparator<Integer> naturalOrder = Integer::compare;
        final Stream<Integer> parallel = Stream.of(intArray).parallel();
        assertEquals("what we obtain?", "1, 2, 3, 4, 5, 6, 7, 8, 9", time_action("parallel", () -> parallel.sorted(naturalOrder))
                .map(Object::toString)
                    .collect(Collectors.joining(", ")));
    }

    @Test
    public final void stream_parallel() {
        Stream.of(intArray).parallel().forEach(System.out::print);
        //fail("each time the impression is different as concurrent execution is not deterministic.");
    }

    private Stream<Integer> time_action(String type, Supplier<Stream<Integer>> f) {
        Instant start = Instant.now();
        Stream<Integer> stream = f.get();
        Instant end = Instant.now();
        System.out.println(type + " duration: " + Duration.between(start, end).toNanos());
        return stream;
    }
}
