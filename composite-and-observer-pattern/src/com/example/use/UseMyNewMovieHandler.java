package com.example.use;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.example.domain.Movie;
import com.example.handler.MovieHandler;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class UseMyNewMovieHandler {
	public static void main(String[] args) {

		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			MovieHandler handler = new MovieHandler();
			handler.setBeginYear(1970);
			handler.setEndYear(1979);
			parser.setContentHandler(handler);
			parser.parse("resources/movies.xml");
			for (Movie movie : handler.getMovies()) {
				System.err.println("Movie Title: " + movie.getTitle());
				System.err.println("Movie Genres: " + movie.getGenres());
				System.err.println("Movie Year: " + movie.getYear());
				System.err.println("IMDB: " + movie.getImdb());
				System.err.println("------------------------------------------");
			}
		} catch (Exception e) {
		}

	}
}
