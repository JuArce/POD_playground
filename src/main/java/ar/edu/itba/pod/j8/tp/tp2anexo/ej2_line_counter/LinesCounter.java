package ar.edu.itba.pod.j8.tp.tp2anexo.ej2_line_counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinesCounter {

    private final Path path;
    private long lines;

    public LinesCounter(String directory) {
        this.path = Paths.get(directory);
    }

    public long run() {
        final List<Path> paths = getFiles();
        final ExecutorService executor = Executors.newCachedThreadPool();
        final StringBuffer response = new StringBuffer(); // StringBuffer is thread-safe
        final List<CompletableFuture<String>> tasks = new ArrayList<>();

        paths.forEach(p -> {
            CompletableFuture<Long> linesTask = CompletableFuture.supplyAsync()
        });


        CompletableFuture countLines = ;
        CompletableFuture fileSize = ;
        CompletableFuture fileLinesCounter = ;
    }

    private List<Path> getFiles() {
        try (Stream<Path> stream = Files.walk(path)) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

public class FileLinesCounter implements Supplier<Long> {
    private final Path path;

    public FileLinesCounter(Path path) {
        this.path = path;
    }

    @Override
    public Long get() {
        if ()
    }
}

public class FileSize implements Supplier<Long> {

}
