package com.quiltplayer.external.covers;

import java.io.InputStream;

import org.xml.sax.helpers.DefaultHandler;

import com.quiltplayer.external.covers.discogs.AlbumRelease;
import com.quiltplayer.external.covers.discogs.Artist;
import com.quiltplayer.external.covers.discogs.ArtistRelease;
import com.quiltplayer.external.covers.discogs.Format;
import com.quiltplayer.external.covers.discogs.Image;
import com.quiltplayer.external.covers.discogs.Label;
import com.quiltplayer.external.covers.discogs.Resp;
import com.quiltplayer.external.covers.discogs.Track;
import com.thoughtworks.xstream.XStream;

/**
 * Parses XML to object.
 * 
 * @author Vlado Palczynski
 */
public class XStreamXmlParser extends DefaultHandler {

    /**
     * @param stream
     *            the stream to set.
     * @return Resp
     * @throws Exception
     *             if problems occur.
     */
    public final Resp parseArtist(final InputStream stream) throws Exception {
        XStream xStream = new XStream();

        xStream.alias("resp", Resp.class);
        xStream.aliasAttribute(Resp.class, "requests", "requests");
        xStream.alias("artist", Artist.class);
        xStream.alias("image", Image.class);
        xStream.alias("url", String.class);
        xStream.alias("name", String.class);
        xStream.alias("release", ArtistRelease.class);

        xStream.useAttributeFor(ArtistRelease.class, "id");
        xStream.useAttributeFor(Image.class, "height");
        xStream.useAttributeFor(Image.class, "type");
        xStream.useAttributeFor(Image.class, "uri");
        xStream.useAttributeFor(Image.class, "uri150");
        xStream.useAttributeFor(Image.class, "width");

        Resp resp = (Resp) xStream.fromXML(stream);

        return resp;
    }

    /**
     * @param stream
     *            the stream to set.
     * @return Resp
     * @throws Exception
     *             if problems occur.
     */
    public final Resp parseRelease(final InputStream stream) throws Exception {
        XStream xStream = new XStream();

        xStream.alias("resp", Resp.class);
        xStream.alias("release", AlbumRelease.class);
        xStream.alias("image", Image.class);
        xStream.alias("artist", String.class);
        xStream.alias("label", Label.class);
        xStream.alias("format", String.class);
        xStream.alias("genre", String.class);
        xStream.alias("style", String.class);
        xStream.alias("track", Track.class);
        xStream.alias("extraartist", Artist.class);
        xStream.alias("format", Format.class);
        xStream.alias("description", String.class);
        xStream.alias("artists", Track.class);

        xStream.useAttributeFor(AlbumRelease.class, "id");
        xStream.useAttributeFor(Format.class, "name");
        xStream.useAttributeFor(Format.class, "qty");
        xStream.useAttributeFor(Label.class, "name");
        xStream.useAttributeFor(Image.class, "height");
        xStream.useAttributeFor(Image.class, "type");
        xStream.useAttributeFor(Image.class, "uri");
        xStream.useAttributeFor(Image.class, "uri150");
        xStream.useAttributeFor(Image.class, "width");

        Resp resp = (Resp) xStream.fromXML(stream);

        return resp;
    }

}
