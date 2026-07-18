package ir.saeid.imdb.service;

import ir.saeid.imdb.index.ImdbIndex;
import ir.saeid.imdb.model.DataUtils;
import ir.saeid.imdb.model.Movie;
import ir.saeid.imdb.model.PageResponse;
import ir.saeid.imdb.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

@Service
public class ImdbService {
    @Value("${imdb.gzip.folder}")
    String imdbGzipFolder;

    @Autowired
    ImdbIndex imdbIndex;
    public void importAllImdbFiles() {
        imdbIndex.reset();
        importTzipFile(imdbGzipFolder + "/" + "name.basics.tsv.gz", columns -> {
            imdbIndex.addPerson(new Person(columns));
        });
        importTzipFile(imdbGzipFolder + "/" + "title.basics.tsv.gz", columns -> {
            imdbIndex.addMovie(new Movie(columns));
        });
        importTzipFile(imdbGzipFolder + "/" + "title.ratings.tsv.gz", columns -> {
            imdbIndex.setMovieRating(columns[0], DataUtils.getDouble(columns[1]), DataUtils.getInteger(columns[2]));
        });
        imdbIndex.updateAllMoviesRank();
        importTzipFile(imdbGzipFolder + "/" + "title.crew.tsv.gz", columns -> {
            imdbIndex.addCrews(columns[0], DataUtils.getList(columns[1]), DataUtils.getList(columns[2]));
        });
        importTzipFile(imdbGzipFolder + "/" + "title.principals.tsv.gz", columns -> {
            imdbIndex.addPersonMovies(columns[0], columns[2], columns[3]);
        });
    }

    private void importTzipFile(String filePath, Consumer<String[]> consumer) {
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(filePath));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));
            //ignore first line
            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                consumer.accept(line.split("\t"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PageResponse<Movie> getSameDirectorWriter(int page, int size) {
        return imdbIndex.getSameDirectorWriter(page, size);
    }

    public List<Movie> getSharedActorMovies(String actor1, String actor2) {
        return imdbIndex.getSharedActorMovies(actor1, actor2);
    }

    public Map<Integer, Movie> getBestOfGenresOfYear(String genre) {
        return imdbIndex.getBestOfGenresOfYear(genre);
    }
}
