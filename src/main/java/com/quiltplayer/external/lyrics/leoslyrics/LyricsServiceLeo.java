package com.quiltplayer.external.lyrics.leoslyrics;

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
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.quiltplayer.external.lyrics.LyricsEvent;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.external.lyrics.LyricsService;
import com.quiltplayer.external.lyrics.Status;
import com.quiltplayer.model.Lyrics;
import com.quiltplayer.model.impl.LyricsImpl;

@Service
public class LyricsServiceLeo implements LyricsService {

    private static final String EXISTS = "http://api.leoslyrics.com/api_search.php?auth=quiltplayer&artist=%s&songtitle=%s";

    private static final String QUERY = "http://api.leoslyrics.com/api_lyrics.php?auth=quiltplayer&hid=%s";

    @Autowired
    private LyricsListener lyricsListener;

    private String artistName;

    private String title;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            String hid = parseHidValue(artistName, title);

            if (hid != null) {
                final Lyrics l = new LyricsImpl();
                parseLyrics(hid, l);

                lyricsListener.lyricsEvent(new LyricsEvent(Status.FOUND, l.getLyrics()));

                return;
            }

            if (title.contains("(")) {
                title = title.substring(0, title.indexOf('('));

                hid = parseHidValue(artistName, title.trim());

                if (hid != null) {
                    final Lyrics l = new LyricsImpl();
                    parseLyrics(hid, l);

                    lyricsListener.lyricsEvent(new LyricsEvent(Status.FOUND, l.getLyrics()));

                    return;
                }
            }

            lyricsListener.lyricsEvent(new LyricsEvent(Status.NOT_FOUND, ""));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.external.lyrics.LyricsService#getLyrics(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void getLyrics(String artistName, String title) {
        this.artistName = artistName;

        this.title = title;

        //Thread t = new Thread(this);
        //t.start();

    }

    private void parseLyrics(String hid, Lyrics l) throws MalformedURLException, ParserConfigurationException,
            SAXException, IOException, XPathExpressionException {
        URL url = new URL(String.format(QUERY, new Object[] { hid }));

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

    private String parseHidValue(final String artistName, final String title) throws ParserConfigurationException,
            SAXException, IOException, XPathExpressionException, MalformedURLException {

        URL url = new URL(String.format(EXISTS, new Object[] { artistName, title }));

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

    private Document getDocument(URL url) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(url.toString());
        return doc;
    }
}
