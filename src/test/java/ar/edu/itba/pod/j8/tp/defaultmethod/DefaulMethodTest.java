package ar.edu.itba.pod.j8.tp.defaultmethod;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ar.edu.itba.pod.j8.tp.defaultmethod.AbstractingHelloInterface;
import ar.edu.itba.pod.j8.tp.defaultmethod.BaseHelloInterfaz;
import ar.edu.itba.pod.j8.tp.defaultmethod.ClassInterfaceConflictingHello;
import ar.edu.itba.pod.j8.tp.defaultmethod.ConflictingInterfacesHello;
import ar.edu.itba.pod.j8.tp.defaultmethod.MultipleInheritanceInterfaceHello;
import ar.edu.itba.pod.j8.tp.defaultmethod.OverridingHelloInterface;
import ar.edu.itba.pod.j8.tp.defaultmethod.ReversedMultipleInheritanceInterfaceHello;

/**
 * Test to try the default method inheritance.
 * 
 * This test requires a lot of interfaces and implementations to be able to see the inheritance rules so it is
 * important to check those outs to understand how this works
 *
 * @author Marcelo
 * @since Jul 29, 2015
 */
public class DefaulMethodTest {

    @Test
    public final void direct_default_method() {
        final BaseHelloInterfaz imp = new BaseHelloInterfaz() {
        };

        assertEquals("", imp.hello());
    }

    @Test
    public final void overrided_default_method() {
        final OverridingHelloInterface imp = new OverridingHelloInterface() {
        };
        assertEquals("", imp.hello());
    }

    @Test
    public final void abracted_default_method() {
        final AbstractingHelloInterface imp = new AbstractingHelloInterface() {

            @Override
            public String hello() {
                return "local hello";
            }

        };
        assertEquals("", imp.hello());
    }

    @Test
    public final void dependant_interface_conflict_resolution() {
        assertEquals("", new MultipleInheritanceInterfaceHello().hello());
        assertEquals("", new ReversedMultipleInheritanceInterfaceHello().hello());
    }

    @Test
    public final void conflict_class_interface_class_wins() {
        assertEquals("", new ClassInterfaceConflictingHello().hello());
    }

    @Test
    public final void conflict_two_interfaces_override_needed() {
        // FIXME go to the class and delete the method implemented to check the error due to the conflict
        assertEquals("", new ConflictingInterfacesHello().hello());
    }
}
