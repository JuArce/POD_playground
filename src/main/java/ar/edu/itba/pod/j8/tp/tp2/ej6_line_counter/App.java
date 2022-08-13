package ar.edu.itba.pod.j8.tp.tp2.ej6_line_counter;

public class App {
    public static void main(String[] args) throws InterruptedException {
        String dir = "src/main/java/ar/edu/itba/pod/j8/tp/tp2";
        LineCounter lineCounter = new LineCounter(dir);
        System.out.println("Dir " + dir + " has " + lineCounter.run() + " lines");
    }
}
