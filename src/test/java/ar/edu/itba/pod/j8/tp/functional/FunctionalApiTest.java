package ar.edu.itba.pod.j8.tp.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import org.junit.Test;

import ar.edu.itba.pod.j8.tp.functional.NonFunctionalInterface;

/**
 * Test to try out the Function API Each test has a definition and some assertions that need to be fixed to
 * understand.
 *
 * Though in the test the function variables are assigned lambdas they could be used using the new Idiom but we
 * don't do it to keep the test shorts and readable:
 *
 * @author Marcelo
 * @since Jul 30, 2015
 */
public final class FunctionalApiTest {

    @Test
    public final void predicates_test_conditions() {
        final Predicate<String> containsPredicate = test -> test.equals("sa");
        assertTrue(containsPredicate.test(""));
        assertFalse(containsPredicate.test(""));

        // Composition:
        assertTrue(containsPredicate.negate().test(""));

        final Predicate<String> nullPredicate = (test -> test == null);
        assertFalse(nullPredicate.or(containsPredicate).test(""));
        assertTrue(nullPredicate.or(containsPredicate).test(""));

        // FIXME: To think
        // FIXME: assertWHAT?(nullPredicate.or(containsPredicate).test(null));
        // FIXME Uncomment and explain error assertTrue(containsPredicate.or(nullPredicate).test(null));
    }

    @Test
    public final void consumer_uses_received_values() {
        final Consumer<String> consumer = (value) -> assertEquals("sa", value);
        consumer.accept("");
        // Composition can make a "chain" of responsibility
        consumer.andThen((value) -> System.out.println(value)).accept("");
    }

    @Test
    public final void function_reives_and_returns_values() {
        final Function<String, Integer> intValue = (x -> Integer.parseInt(x));
        assertEquals(new Integer(0), intValue.apply("3"));
        // composition
        assertEquals(new Integer(0), intValue.andThen(x -> x + 1).apply("3"));
        assertEquals(new Integer(0), intValue.compose(x -> x + "1").apply("3"));
    }

    @Test
    public final void supplier_returns_a_value() {
        final Supplier<Integer> supplier = () -> 1;
        assertEquals(new Integer(0), supplier.get());
    }

    @Test
    public final void unary_operator_receives_one_value() {
        final UnaryOperator<Integer> inc = (x -> x + 1);
        assertEquals(new Integer(0), inc.apply(3));
        // Composition:
        assertEquals(new Integer(0), inc.andThen(x -> x * 2).apply(3));
        assertEquals(new Integer(0), inc.compose((final Integer x) -> x * 2).apply(3)); // FIXME why the type for x?
    }

    @Test
    public final void binary_operator_receives_two_values() {
        final BinaryOperator<Integer> adder = (x, y) -> x + y;
        assertEquals(new Integer(0), adder.apply(4, 5));
        // Composition (receives a Function not an operator
        assertEquals(new Integer(0), adder.andThen((x) -> x * 2).apply(4, 5));
    }

    @Test
    public final void some_more_specific_functions() {
        final DoublePredicate pressisionPredicate = x -> x < 0.001;
        assertFalse(pressisionPredicate.test(0.3));
        final IntBinaryOperator binaryOperator = (x, y) -> x - y;
        assertEquals(-3, binaryOperator.applyAsInt(1, 2));
        // Many more...
    }

    @Test
    public final void functional_interface_annotation() {
        @SuppressWarnings("unused")
        final NonFunctionalInterface interFace = mock(NonFunctionalInterface.class);
        fail("go to the interface and comment the method to check the annotation function, then erase this instruction");
    }
}
