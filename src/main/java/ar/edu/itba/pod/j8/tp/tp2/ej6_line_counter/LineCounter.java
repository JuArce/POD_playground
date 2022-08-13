package ar.edu.itba.pod.j8.tp.tp2.ej6_line_counter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class LineCounter {

    private final String directory;
    private long lines;
    private final ExecutorService pool;

    public LineCounter(String directory) {
        this.directory = directory;
        this.pool = Executors.newCachedThreadPool();
    }

    public long run() throws InterruptedException {
        lines = 0;
        List<Future<Long>> futures = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
            stream.forEach(file -> futures.add(pool.submit(new FileLineCounter(file))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        futures.forEach(f -> {
            try {
                lines += f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.SECONDS);

        return lines;
    }
}

class FileLineCounter implements Callable<Long> {
    private final Path path;

    public FileLineCounter(Path path) {
        this.path = path;
    }

    @Override
    public Long call() {
        if (!Files.isRegularFile(path)) {
            System.out.printf("%s is not a regular file\n", path);
            return 0L;
        }

        long lines = 0;
        try (Stream<String> stream = Files.lines(path)) {
            lines = stream.count();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("File " + path + " has " + lines + " lines");
        return lines;
    }
}
