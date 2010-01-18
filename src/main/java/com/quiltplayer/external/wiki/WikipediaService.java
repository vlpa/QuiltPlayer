package com.quiltplayer.external.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Service for wikipedia questions.
 * 
 * @author Vlado Palczynski
 * 
 */
public class WikipediaService implements Runnable {

    private static final String QUERY = "http://en.wikipedia.org/w/api.php?action=parse&page=%s&format=%s";

    private static final String NO_ARTICLE = "Wikipedia does not have an article with this exact name";

    private static String result = "";

    private static String page = "";

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        URL url;

        try {
            url = new URL(String.format(QUERY, new Object[] { page, "xml" }));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(url.toString());

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("//api/parse/text/text()");

            Object result = expr.evaluate(doc, XPathConstants.NODESET);

            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                result = nodes.item(i).getNodeValue();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("DONE!");
    }

    public String getWikiContentForPageName(String pageName) throws MalformedURLException,
            IOException, UnsupportedEncodingException {

        page = pageName.replace(" ", "_");

        Thread t = new Thread(this);
        t.run();

        return result;
    }

    public boolean exists(String pageName) {

        final long currentTimeStamp = System.currentTimeMillis();

        try {
            URL url = new URL(String.format(QUERY, new Object[] { pageName, "xml" }));
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection
                    .getInputStream()));

            String s;
            while ((s = in.readLine()) != null) {
                if (s.contains(NO_ARTICLE)) {
                    System.out.println("Time to ping wikipedia: "
                            + (System.currentTimeMillis() - currentTimeStamp));
                    return true;
                }
            }
            in.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Time to ping wikipedia: "
                + (System.currentTimeMillis() - currentTimeStamp));

        return false;
    }
}
