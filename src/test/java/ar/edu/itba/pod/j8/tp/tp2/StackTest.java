package ar.edu.itba.pod.j8.tp.tp2;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static junit.framework.Assert.assertEquals;

public class StackTest {
    private static final int PUSH_QTY = 100;
    private static final int THREAD_COUNT = 100;

    private Stack stack;
    private ExecutorService pool;

    @Before
    public void before() {
        stack = new StackSafe();
        pool = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    @Test
    public void stackTest() throws InterruptedException {
        List<Future<Integer>> futureList = new ArrayList<>();
        for (int  i = 0; i < THREAD_COUNT; i++) {
            futureList.add(pool.submit(push));
        }

        futureList.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        assertEquals(PUSH_QTY * THREAD_COUNT, stack.getTop());

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.SECONDS);
    }

    private final Callable<Integer> push = () -> {
        int i;
        for (i = 0; i < PUSH_QTY; i++) {
            stack.push(i);
        }
        return i;
    };
}
