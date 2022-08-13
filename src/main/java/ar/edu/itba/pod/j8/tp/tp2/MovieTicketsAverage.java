package ar.edu.itba.pod.j8.tp.tp2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieTicketsAverage {
    private int tope;
    private final List<Movie> movies;

    /** */
    public MovieTicketsAverage(int tope, List<Movie> movies) {
        super();
        this.tope = tope;
        this.movies = new ArrayList<>(movies);
    }

    public synchronized double average() {
        return movies.stream().filter(movie -> movie.getYear() > getTope())
                .collect(Collectors.averagingInt(Movie::getTicketsSold));
    }

    public synchronized int getTope() {
        return tope;
    }

    public synchronized void setTope(int tope) {
        this.tope = tope;
    }

    public synchronized List<Movie> getMovies() {
        return movies;
    }

    public synchronized void addMovie(Movie movie) {
        movies.add(movie);
    }
}

class Movie {
    private final int year;
    private final String title;
    private final int ticketsSold;

    public Movie(int year, String title, int ticketsSold) {
        super();
        this.year = year;
        this.title = title;
        this.ticketsSold = ticketsSold;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }
}
