package model;

import java.util.Objects;

public class Movie {
    private int movieId;
    private String movieTitle;

    public Movie() {}

    public Movie(int movieId, String movieTitle) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getMovieId() == movie.getMovieId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieId());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                '}';
    }
}
