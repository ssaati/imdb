package ir.saeid.imdb.model;

import java.util.Arrays;
import java.util.List;

import static ir.saeid.imdb.model.DataUtils.getInteger;

public class Movie {
    String id;
    String title;
    int year = 0;
    List<String> genres;
    double averageRating = 0;
    int numOfVotes = 0;
    public Movie() {
    }

    public Movie(String[] columns) {
        this.id = columns[0];
        this.title = columns[2];
        this.year = getInteger(columns[5]);
        this.setGenres(Arrays.asList(columns[8].split(",")));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumOfVotes() {
        return numOfVotes;
    }

    public void setNumOfVotes(Integer numOfVotes) {
        this.numOfVotes = numOfVotes;
    }

    @Override
    public String toString() {
        return "title: " + title + ", genres:" + genres + ", year:" + year;
    }
    public Boolean hasBetterRank(Movie other){
        if(this.averageRating > other.averageRating)
            return true;
        if(this.averageRating == other.averageRating)
            return this.numOfVotes > other.numOfVotes;
        return false;
    }
}
