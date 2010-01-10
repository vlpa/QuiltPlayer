package com.quiltplayer.external.covers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.quiltplayer.external.covers.discogs.Resp;

/**
 * Discogs web service implementation for looking up information about artist.
 * 
 * @author Vlado Palczynski
 */
public class WebServiceArtist extends CommonWebserviceMethods {

    private static Map<String, Resp> artistMap = new HashMap<String, Resp>();

    private static XStreamXmlParser parser = new XStreamXmlParser();

    private static Logger log = Logger.getLogger(WebServiceArtist.class);

    /**
     * 
     * @param album
     *            the album to set.
     * @return Resp
     * @throws MalformedURLException
     *             if problem occur.
     * @throws Exception
     *             if problem occur.
     * @throws IOException
     *             if problem occur.
     */
    public Resp getArtistInfo(final String artistName, final String albumTitle)
            throws MalformedURLException, Exception, IOException {
        Resp resp = null;

        Validate.notNull(artistName);

        if (artistMap.get(artistName.toLowerCase()) != null) {
            log.debug("Found artist in cache");

            return artistMap.get(artistName.toLowerCase());
        }
        else {
            resp = getArtist(artistName);

            if (resp != null) {
                addArtistResp(resp, artistName);

                return resp;
            }

        }

        return null;
    }

    /**
     * Add artist to Resp
     * 
     * @param resp
     *            the resp to set
     * @param albumId
     *            the albumId to set.
     */
    private static void addArtistResp(final Resp resp, final String artistName) {
        log.debug("Adding artist with id " + artistName + " to artistMap...");

        artistMap.put(artistName.toLowerCase(), resp);
    }

    /**
     * Get artist.
     * 
     * @param artistName
     *            the artistName to set.
     * @param album
     *            the album to set.
     * @return Resp
     * @throws Exception
     *             if problems occur.
     */
    protected final Resp getArtist(String artistName) throws Exception {

        artistName = artistName.replace(" ", "+").toLowerCase();

        Resp resp = null;
        InputStream stream = null;

        try {
            URL url = new URL("http://www.discogs.com/artist/" + artistName + "?f=xml&api_key="
                    + DISCOGS_API_KEY);

            log.debug("Webservice call for artist: " + artistName);

            try {
                stream = getStreamResponse(url);
            }
            catch (FileNotFoundException e) {
                log.debug("Artist " + artistName + " does not exist on discogs!");

                return null;
            }
            catch (IOException e) {
                log.error(e.getMessage());

                if (e.getMessage().contains("400")) {
                    Thread.currentThread().stop();
                }

                return null;
            }

            resp = parser.parseArtist(stream);

            if (resp.getArtist() == null) {
                log.debug("Couldn't find artist with name: " + artistName);

                return null;
            }

            System.out.println(resp.getRequests());

        }
        finally {
            if (stream != null)
                stream.close();
        }

        return resp;
    }
}
