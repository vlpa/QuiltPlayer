package com.quiltplayer.external.lyrics.lyricsfly;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.quiltplayer.external.lyrics.LyricsEvent;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.external.lyrics.LyricsService;
import com.quiltplayer.external.lyrics.Status;

/**
 * http://lyricsfly.com/api/
 * 
 * @author Vlado Palczynski
 * 
 */
@Service
public class LyricsServiceLyricsFly implements LyricsService {

    /* To search by artist and title combination: */
    private static final String QUERY = "http://api.lyricsfly.com/api/api.php?i=%s&a=%s&t=%s";

    @Autowired
    private LyricsListener lyricsListener;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    private String code = "963483002885003db-temporary.API.access";

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.external.lyrics.LyricsService#getLyrics(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void getLyrics(String artistName, String title) {

        String lyrics = "";

        // if (title.contains("(")) {
        // title = title.substring(0, title.indexOf('('));

        try {
            URL url = new URL(String.format(QUERY, new Object[] { code, artistName, title }));

            Document doc = getDocument(url);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("//start/sg/tx/text()");

            Object result = expr.evaluate(doc, XPathConstants.NODESET);

            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                lyrics = nodes.item(i).getNodeValue();
            }

            lyricsListener.lyricsEvent(new LyricsEvent(Status.FOUND, lyrics));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document getDocument(URL url) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(url.toString());
        return doc;
    }
}
