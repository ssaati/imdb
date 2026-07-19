#IMDB Dataset API

A Spring Boot application which imports IMDB data through it's offline files and expose some APIs on these data.

The request counter counts every request including request to requestcounter itself.

</> Bash
git clone https://github.com/ssaati/imdb.git

cd imdb

mvn clean package
java -jar target/imdb.jar

Dataset setup

Download
-title.basics.tsv.gz
-title.ratings.tsv.gz
-title.crew.tsv.gz
-title.principals.tsv.gz
-name.basics.tsv.gz

Place them somewhere and set the director address in application.properties with key imdb.gzip.folder

API documentation

Import data from files:
http://localhost:8080/import/all

Search same director and writer which is alive:
http://localhost:8080/search/same-director-writer

Find best of specified genre for each year:
http://localhost:8080/search/best-of-genres?genre=horor

Find movies with shared actors:
http://localhost:8080/search/shared-actors?actor1=nm1309758&actor2=nm0085156
