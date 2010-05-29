package com.quiltplayer.external.covers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class CommonWebserviceMethods {

    protected static final String DISCOGS_API_KEY = "6dfeb90be3";

    protected XStreamXmlParser parser = new XStreamXmlParser();

    /**
     * Get inputstream. Do not forget to close the connection.
     * 
     * @param url
     *            the url to set.
     * @return InputStream
     * @throws IOException
     *             if problems occur.
     */
    public static InputStream getStreamResponse(final URL url) throws IOException {
        InputStream resultingInputStream = null;

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // allow both GZip encodings
        con.setRequestProperty("Accept-Encoding", "gzip");

        String encoding = con.getContentEncoding();

        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            resultingInputStream = new GZIPInputStream(con.getInputStream());
        }
        else {
            resultingInputStream = con.getInputStream();
        }

        if (con != null)
            con = null;

        return resultingInputStream;
    }
}
