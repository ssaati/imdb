package ir.saeid.imdb.index;

import ir.saeid.imdb.model.Movie;
import ir.saeid.imdb.model.PageResponse;
import ir.saeid.imdb.model.Person;

import java.util.List;
import java.util.Map;

public interface ImdbIndex {
    void reset();
    void addPerson(Person person);
    void addMovie(Movie movie);
    void setMovieRating(String movieId, Double ratings, Integer votes);
    void addCrews(String column, List<String> directors, List<String> writers);
    void addPersonMovies(String movieId, String personId, String category);
    PageResponse<Movie> getSameDirectorWriter(int page, int size);
    List<Movie> getSharedActorMovies(String actor1, String actor2);
    Map<Integer, Movie> getBestOfGenresOfYear(String genre);
    void updateAllMoviesRank();
}
