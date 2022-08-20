package ar.edu.itba.pod.j8.tp.tp2anexo.ej2_line_counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinesCounter {

    private final Path path;

    public LinesCounter(String directory) {
        this.path = Paths.get(directory);
    }

    public void run() throws InterruptedException, ExecutionException {
        final List<Path> paths = getFiles();
        final ExecutorService executor = Executors.newCachedThreadPool();
        final StringBuffer response = new StringBuffer(); // StringBuffer is thread-safe
        final List<CompletableFuture<String>> tasks = new ArrayList<>();

        paths.forEach(p -> {
            CompletableFuture<Long> linesTask = CompletableFuture.supplyAsync(new FileLinesCounter(p), executor);
            CompletableFuture<Long> sizeTask = CompletableFuture.supplyAsync(new FileSize(p), executor);
            CompletableFuture<String> combined = linesTask.thenCombineAsync(sizeTask, (lines, size) -> String.format("%s: %d lines, %d bytes", p, lines, size));
            combined.whenComplete((r, e) -> response.append(r).append("\n"));
            tasks.add(combined);
        });

        CompletableFuture[] taskArray = tasks.toArray(new CompletableFuture[0]);
        CompletableFuture.allOf(taskArray).get();
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(response);
    }

    private List<Path> getFiles() {
        try (Stream<Path> stream = Files.walk(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * This class is used to count the lines of a file.
     */
    public static class FileLinesCounter implements Supplier<Long> {
        private final Path path;

        public FileLinesCounter(Path path) {
            this.path = path;
        }

        @Override
        public Long get() {
            if (!Files.isRegularFile(path)) {
                return 0L;
            }

            long lines = 0;
            try (Stream<String> stream = Files.lines(path)) {
                lines = stream.count();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return lines;
        }
    }


    /**
     * This class is used to get the size of a file.
     */
    public static class FileSize implements Supplier<Long> {
        private final Path path;

        public FileSize(Path path) {
            this.path = path;
        }

        @Override
        public Long get() {
            if (!Files.isRegularFile(path)) {
                return 0L;
            }

            long size = 0;
            try {
                size = Files.size(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return size;
        }
    }
}
