package ar.edu.itba.pod.j8.tp.optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.pod.j8.tp.model.Car;
import ar.edu.itba.pod.j8.tp.model.Person;
import ar.edu.itba.pod.j8.tp.model.Car.Type;
import ar.edu.itba.pod.j8.tp.model.Person.Sex;

/**
 * Test to check the {@link Optional} functionality
 *
 * @author Marcelo
 * @since Jul 30, 2015
 */
public class OptionalTest {
    private Car car;
    private Person person;
    private Person otherPerson;
    private Optional<Person> emptyOptional;
    private Optional<Person> nonEmpty;

    @Before
    public final void before() {
        person = new Person("mik", LocalDate.of(2015, 9, 13), Sex.MALE, "mick@persons.com");
        car = new Car(person, Type.PICKUP);
        otherPerson = new Person("ana", LocalDate.of(2015, 9, 13), Sex.FEMALE, "ana@persons.com");
        nonEmpty = Optional.of(person);
        emptyOptional = Optional.empty(); // without the improved type inference this should be
                                          // Optional.<Person>empty()
    }

    @Test
    public final void old_school_conditional() {
        if (!emptyOptional.isPresent()) {
            assertEquals(null, nonEmpty.get()); // shouldn't be here
        }

        if (nonEmpty.isPresent()) {
            assertEquals(null, nonEmpty.get()); // get the value
        }
    }

    @Test
    public final void new_school_conditional() {
        emptyOptional.ifPresent(p -> fail("should not reach here'")); // is like it es
        nonEmpty.ifPresent(p -> assertEquals(person, null));// get the value
    }

    @Test
    public final void orElse() {
        assertEquals(null, nonEmpty.orElse(otherPerson));
        assertEquals(null, emptyOptional.orElse(otherPerson));
    }

    @Test
    public final void orElseGet() {
        assertEquals(null, nonEmpty.orElseGet(() -> otherPerson));
        assertEquals(null, emptyOptional.orElseGet(() -> otherPerson));
    }

    @Test()
    public final void or_throw() {
        // full doesn't go to the or throw returns the value
        assertEquals(person, nonEmpty.orElseThrow(IllegalStateException::new));
        // empty throws (so what should the test "expect"
        emptyOptional.orElseThrow(IllegalStateException::new);
        fail("shouldn't get here");
    }

    @Test
    public final void map() {
        // turns an Optional<T> into an Optional<U> (internally as mapper returns U)
        emptyOptional.map(Person::getCar).ifPresent(c -> fail("shouldn't have a car"));// ugly to break this..
        nonEmpty.map(Person::getCar).ifPresent(c -> assertEquals(null, c));
    }

    @Test
    public final void flatmap() {
        // turns an Optional<T> into an Optional<U> the mapper needs to return Optional<U>
        nonEmpty.flatMap(p -> Optional.ofNullable(p.getCar())).ifPresent(c -> assertEquals(car, c));
        emptyOptional.flatMap(p -> Optional.ofNullable(p.getCar())).ifPresent(
                c -> fail("shouldn't have a car"));// ugly to break this..
    }

    @Test()
    public final void filter() {
        // if we leave the condition what this test should expect?
        nonEmpty.filter(p -> p.getCar() == null).orElseThrow(IllegalArgumentException::new);
        nonEmpty.filter(p -> p.getCar() != null).orElseThrow(IllegalArgumentException::new);
    }

    @Test()
    public final void get_throws_on_empty() {
        emptyOptional.get(); // what should the test expect?
    }

    @Test(expected = NullPointerException.class)
    public final void of_throws_null() {
        Optional.of(null);
    }
}
