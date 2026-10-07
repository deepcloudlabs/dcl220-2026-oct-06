package com.example.use;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.example.handler.SimpleMovieHandler;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class UseSimpleMovieHandler {
	public static void main(String[] args) throws SAXException {
		try {
			XMLReader parser = XMLReaderFactory.createXMLReader();
			SimpleMovieHandler handler = new SimpleMovieHandler();
			parser.setContentHandler(handler);
			parser.parse("resources/movies.xml");
			System.err.println("# of movies: " + handler.getCounter());
		} catch (Exception e) {
		}
	}
}
