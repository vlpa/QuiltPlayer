package com.quiltplayer.external.lyrics.lyricsfly;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.quiltplayer.external.lyrics.LyricsEvent;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.external.lyrics.LyricsService;
import com.quiltplayer.external.lyrics.Status;
import com.quiltplayer.model.Lyrics;
import com.quiltplayer.model.impl.LyricsImpl;

/**
 * http://lyricsfly.com/api/
 * 
 * @author Vlado Palczynski
 * 
 */
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

    private String code = "0febc5f3fcf7b93b3-temporary.API.access";

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.external.lyrics.LyricsService#getLyrics(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void getLyrics(String artistName, String title) {
        try {
            String hid = parseHidValue(artistName, title);

            if (hid != null) {
                final Lyrics l = new LyricsImpl();
                parseLyrics(hid, l);

                lyricsListener.lyricsEvent(new LyricsEvent(Status.FOUND, l.getLyrics()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseLyrics(String hid, Lyrics l) throws MalformedURLException,
            ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        URL url = new URL(String.format(QUERY, new Object[] { hid, hid, hid }));

        Document doc = getDocument(url);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile("//leoslyrics/lyric/text/text()");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);

        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            l.setLyrics(nodes.item(i).getNodeValue());
        }

        expr = xpath.compile("//leoslyrics/lyric/writer/text()");

        result = expr.evaluate(doc, XPathConstants.NODESET);

        nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            l.setWriter(nodes.item(i).getNodeValue());
        }

    }

    private String parseHidValue(final String artistName, final String title)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException, MalformedURLException {

        URL url = new URL(String.format(QUERY, new Object[] { code, artistName, title }));

        Document doc = getDocument(url);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile("//@hid");

        Object result = expr.evaluate(doc, XPathConstants.NODESET);

        final int size = ((NodeList) result).getLength();

        if (size == 1) {
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength();) {
                return nodes.item(i).getNodeValue();
            }
        }

        return null;
    }

    private Document getDocument(URL url) throws ParserConfigurationException, SAXException,
            IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(url.toString());
        return doc;
    }
}
