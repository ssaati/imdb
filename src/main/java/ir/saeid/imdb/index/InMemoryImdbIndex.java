package ir.saeid.imdb.index;

import ir.saeid.imdb.model.Movie;
import ir.saeid.imdb.model.PageResponse;
import ir.saeid.imdb.model.Person;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryImdbIndex implements ImdbIndex{
    Long linesProcessed=0L;
    Map<String, Movie> movieIndex = new HashMap<>();
    Map<String, Person> peopleIndex = new HashMap<>();
    List<Movie> sameDirectorAndWriter = new ArrayList<>();
    Map<String, List<String>> actorMovies = new HashMap<>();
    // we save best Movie of each genre of each year
    HashMap<String, Map<Integer, Movie>> bestOfGenresOfYear = new HashMap<>();

    @Override
    public void reset() {
        linesProcessed=0L;
        movieIndex = new HashMap<>();
        peopleIndex = new HashMap<>();
        sameDirectorAndWriter = new ArrayList<>();
        actorMovies = new HashMap<>();
        bestOfGenresOfYear = new HashMap<>();
    }

    @Override
    public void addPerson(Person person) {
        incrementAndLog("person");
//        System.out.println("importing: " + person);
        if(person.getDeathYear() >0)
            peopleIndex.put(person.getId(), person);
    }

    @Override
    public void addMovie(Movie movie) {
        incrementAndLog("movie");
//        System.out.println("importing :" + movie);
        movieIndex.put(movie.getId(), movie);
    }

    @Override
    public void setMovieRating(String movieId, Double ratings, Integer votes) {
        incrementAndLog("rating");
        Movie movie = movieIndex.get(movieId);
        if(movie !=null) {
            movie.setAverageRating(ratings);
            movie.setNumOfVotes(votes);
        }
    }

    @Override
    public void addCrews(String movieId, List<String> directors, List<String> writers) {
        incrementAndLog("crew");
        if(writers ==null || directors ==null)
            return;
        for (String director : directors) {
            for (String writer : writers) {
                if(director.equals(writer)) {
                    Movie movie = movieIndex.get(movieId);
                    Person person = peopleIndex.get(director);
                    if(person != null && person.getDeathYear() == 0){
                        sameDirectorAndWriter.add(movie);
                    }
//                    System.out.println("same director and writer: " + movie);
                }
            }
        }
    }

    @Override
    public void addPersonMovies(String movieId, String personId, String category) {
        incrementAndLog("personmovie");
        if(category !=null){
            if(category.equals("actor") || category.equals("actress")){
                List<String> movies = actorMovies.get(personId);
                if(movies == null) {
                    movies = new ArrayList<>();
                    actorMovies.put(personId, movies);
                }
//                System.out.println("add movie:" + movieId + " to person: " + personId);
                movies.add(movieId);
            }
        }
    }

    private void incrementAndLog(String entity) {
        Runtime runtime = Runtime.getRuntime();
        linesProcessed ++;
        if(linesProcessed %1_000_000 == 0) {
            System.out.println(entity + ": " + linesProcessed + ", memory used: " + (runtime.totalMemory() - runtime.freeMemory())/ 1024/1024 + "MB");
        }
    }

    @Override
    public PageResponse<Movie> getSameDirectorWriter(int page, int size) {
        int start = page * size;
        int end = start + size;
        if(end > sameDirectorAndWriter.size())
            end = sameDirectorAndWriter.size();
        return new PageResponse<>(sameDirectorAndWriter.subList(start, end), page, size, sameDirectorAndWriter.size());
    }

    @Override
    public List<Movie> getSharedActorMovies(String actor1, String actor2) {
        List<String> actor1Movies = actorMovies.get(actor1);
        List<String> actor2Movies = actorMovies.get(actor2);
        if(actor1Movies == null || actor2Movies == null)
            return List.of();
        List<String> sharedMovies = new ArrayList<>(actor1Movies);
        sharedMovies.retainAll(actor2Movies);
        List<Movie> sharedMovies2 = new ArrayList<>();
        for (String sharedMovie : sharedMovies) {
            sharedMovies2.add(movieIndex.get(sharedMovie));
        }
        return sharedMovies2;
    }

    @Override
    public Map<Integer, Movie> getBestOfGenresOfYear(String genre) {
        return bestOfGenresOfYear.get(genre);
    }

    @Override
    public void updateAllMoviesRank() {
        for (Movie movie : movieIndex.values()) {
            updateMovieRank(movie);
        }
    }
    private void updateMovieRank(Movie movie) {
        for (String genre : movie.getGenres()) {
            Map<Integer, Movie> yearsBestMovies = bestOfGenresOfYear.get(genre);
            if(yearsBestMovies == null){
                yearsBestMovies = new HashMap<>();
                bestOfGenresOfYear.put(genre, yearsBestMovies);
            }
            Movie existingBestOfYearMovie = yearsBestMovies.get(movie.getYear());
            if (existingBestOfYearMovie == null || movie.hasBetterRank(existingBestOfYearMovie)) {
                yearsBestMovies.put(movie.getYear(), movie);
            }
        }
    }

}
