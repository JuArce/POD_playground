package ar.edu.itba.pod.j8.tp.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.FileFilter;
import java.security.PrivilegedAction;
import java.util.concurrent.Callable;

import org.junit.Before;
import org.junit.Test;

import ar.edu.itba.pod.j8.tp.lambda.LambdaExecutor;
import ar.edu.itba.pod.j8.tp.lambda.LambdaScopingHelloWorld;

/**
 * Test to learn about lambda. Uses {@link LambdaExecutor} and {@link LambdaScopingHelloWorld} most require
 * you to read and figure out what is wrong with assertion to make it pass.
 * 
 * @author Marcelo
 * @since Jul 29, 2015
 */
public class LambdaTest {

    private LambdaExecutor lambdas;

    @Before
    public final void before() {
        lambdas = spy(new LambdaExecutor());
    }

    @Test
    public final void lambda_have_syntaxis_sugars() throws Exception {
        // this test is to show how it is done (so we make it pass for you)
        // full syntaxis
        assertTrue(lambdas.weirdEquals((x, y) -> {
            // many more lines (actually not many, a few, with many, call a method :))
                return x - y - 1;
            }));

        assertFalse(lambdas.weirdEquals((x, y) -> x - y)); // one liner: return and ';' optional
        assertTrue(lambdas.rootDirectoryComplies(file -> file.exists()));// one parameter: no parenthesis
        assertEquals("A", lambdas.run(() -> "A")); // no parameters, empty ()
        lambdas.run(() -> System.out.println("do nothing")); // void lambda just write the code
    }

    @Test
    public final void interface_can_be_exchanged_to_lambda() throws Exception {
        // Replace the interfaces for lambdas (hint: look above)
        // And correct
        assertEquals(Integer.valueOf(0), lambdas.run(() -> 0));

        assertEquals("", lambdas.run(() -> ""));
    }

    @Test
    public final void lambda_target_type_is_inferred_on_variable_assignment() {
        final FileFilter filter = (file -> file.canWrite());
        // fix the assertion
        assertTrue(lambdas.rootDirectoryComplies(filter));
    }

    @Test
    public final void same_lambda_inferred_by_assignation_type() throws Exception {
        final Callable<String> callable = () -> "done";
        final PrivilegedAction<String> action = () -> "done";
        // what's the output
        assertEquals("done", callable.call());
        assertEquals("done", action.run());
    }

    @Test
    @SuppressWarnings("unchecked")
    public final void lambda_type_is_inferred_on_return_type() throws Exception {
        assertEquals("hola", lambdas.run(() -> "hola"));

        // TODO preguntar
        // which one is called?
        verify(lambdas, times(1)).run(any(Callable.class));
        verify(lambdas, times(0)).run(any(PrivilegedAction.class));
        verify(lambdas, times(0)).run(any(Runnable.class));

        reset(lambdas);
        assertEquals(Integer.valueOf(1), lambdas.run(() -> 1));
        // which one is called?
        verify(lambdas, times(0)).run(any(Callable.class));
        verify(lambdas, times(1)).run(any(PrivilegedAction.class));
        verify(lambdas, times(0)).run(any(Runnable.class));
    }

    @Test
    public final void captured_variables_need_to_be_effectively_final() {
        final int finalVariable = 1;
        int effectivelyFinalVariable = 2;

        // what's the value
        assertEquals(Integer.valueOf(3), lambdas.run(() -> finalVariable + effectivelyFinalVariable));

        // FIXME THIS DOES NOT COMPILE THE VARIABLE IS NO LONGER EFFECTIVELY FINAL
//         assertEquals(Integer.valueOf(3), lambdas.run(() -> effectivelyFinalVariable = 3));

        // FIXME THIS DOES NOT WORK DUE TO SHADOWING OF THE VARIABLE
//         assertEquals(new Integer(3), lambdas.weirdEquals((x, effectivelyFinalVariable) -> 1));
    }

    @Test
    public final void lambda_lexical_scope() throws Exception {
        // what's the value
        assertEquals("Hello, world!", new LambdaScopingHelloWorld().toString());
        assertEquals("Hello, world!", new LambdaScopingHelloWorld().r1.call());
    }
}
