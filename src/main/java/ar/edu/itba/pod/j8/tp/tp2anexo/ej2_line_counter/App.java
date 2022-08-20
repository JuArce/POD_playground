package ar.edu.itba.pod.j8.tp.tp2anexo.ej2_line_counter;

import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) {
        String dir = "src/main/java/ar/edu/itba/pod/j8/tp/";
        try {
            new LinesCounter(dir).run();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
