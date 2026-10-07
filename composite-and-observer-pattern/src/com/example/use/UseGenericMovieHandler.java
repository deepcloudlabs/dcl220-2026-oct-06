package com.example.use;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.example.domain.Kitap;
import com.example.handler.GenericHandler;

/**
 *
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 */
public class UseGenericMovieHandler {

	public static void main(String[] args) {

        try {
            XMLReader parser = XMLReaderFactory.createXMLReader();
            GenericHandler<Kitap> handler = new GenericHandler<Kitap>(Kitap.class);
            parser.setContentHandler(handler);
            parser.parse("resources/books.xml");
            for (Kitap kitap: handler.getElements(Kitap.class)) {
                System.err.println("Başlık: " + kitap.getBaşlık());
                System.err.println("------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
