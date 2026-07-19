package ir.saeid.imdb.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.saeid.imdb.model.Movie;
import ir.saeid.imdb.model.PageResponse;
import ir.saeid.imdb.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("search")
@RestController
public class SearchController {
    @Autowired
    ImdbService imdbService;

    @Operation(summary = "return movies which has at least one person who was in director and writer role")
    @GetMapping("same-director-writer")
    public PageResponse<Movie> getSameDirectorWriter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size){
        PageResponse<Movie> sameDirectorWriter = imdbService.getSameDirectorWriter(page, size);
        return sameDirectorWriter;
    }

    @Operation(summary = "accepts two actor id and returns movie which both of theme played at")
    @GetMapping("shared-actors")
    public List<Movie> getSharedActorMovies(@RequestParam String actor1, @RequestParam String actor2){
        return imdbService.getSharedActorMovies(actor1, actor2);
    }

    @Operation(summary = "returns best of specified genre for each year")
    @GetMapping("best-of-genres")
    public Map<Integer, Movie> getBestOfGenresOfYear(@RequestParam String genre){
        return imdbService.getBestOfGenresOfYear(genre);
    }
}
