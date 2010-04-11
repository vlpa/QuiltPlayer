package com.quiltplayer.external.covers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.quiltplayer.external.covers.comparators.ImageSizeComparator;
import com.quiltplayer.external.covers.comparators.ReleaseYearComparator;
import com.quiltplayer.external.covers.discogs.Album;
import com.quiltplayer.external.covers.discogs.AlbumRelease;
import com.quiltplayer.external.covers.discogs.ArtistRelease;
import com.quiltplayer.external.covers.discogs.Format;
import com.quiltplayer.external.covers.discogs.Image;
import com.quiltplayer.external.covers.discogs.Resp;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.external.covers.model.filesystem.LocalImageFileSystem;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.properties.Configuration;

/**
 * Discogs web service implementation for looking up information about album.
 * 
 * @author Vlado Palczynski
 */
public class WebServiceAlbum extends CommonWebserviceMethods {

    /**
     * Logger.
     */
    private Logger log = Logger.getLogger(WebServiceAlbum.class);

    /**
     * @param resp
     *            the resp to set
     * @param album
     *            the album to set.
     */
    public final Album getAlbumFromDiscogs(final Resp resp, final String albumTitle,
            final int songs, final File imageRepo) {
        XStreamXmlParser parser = new XStreamXmlParser();

        try {
            if (resp == null) {
                log.debug("Resp was null");

                return null;
            }

            Album album = new Album();

            album.setArtist(resp.getArtist());

            /*
             * Get all id:s matching the album name.
             */
            List<String> albumId = getReleasesMatchingAlbum(albumTitle, resp);

            if (albumId.size() > 0) {
                /*
                 * Get all releases from the id:s.
                 */
                List<AlbumRelease> responses = getResponsesFromAlbumIds(parser, albumId);

                List<AlbumRelease> nominees = getResponsesWithSameSongCount(albumTitle, responses,
                        songs);

                /*
                 * If match on the song count, choose the appropriate release in nominees. If no
                 * match on song count, choose the first release TODO add that if no match on songs
                 * is made that the selection should be album + cd.
                 */
                if (nominees.size() != 0) {
                    extractImages(album, nominees, imageRepo);
                    compareYear(album, nominees);
                }
                else {
                    extractImages(album, responses, imageRepo);
                }

                return album;
            }
        }
        catch (Exception e) {
            e.printStackTrace();

            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * As digitally remastered albums are out there this must be made, otherwise inaccurate release
     * years on oldies.
     * 
     * @param album
     * @param nominees
     */
    private void compareYear(Album album, List<AlbumRelease> nominees) {
        if (nominees.size() > 1) {
            Collections.sort(nominees, new ReleaseYearComparator());
            album.setYear(nominees.get(0).getReleased());
        }
    }

    /**
     * Extract images
     * 
     * @param album
     * @param nominees
     * @throws MalformedURLException
     * @throws IOException
     */
    private void extractImages(final Album album, final List<AlbumRelease> nominees,
            final File imageRepo) throws MalformedURLException, IOException {
        if (nominees.size() > 0) {
            AlbumRelease selectedRelease = null;
            /*
             * Pick the one with the most images, and choose CD-releases before other.
             */
            Collections.sort(nominees, new ImageSizeComparator());

            boolean b = false;

            for (AlbumRelease release : nominees) {
                // Could be 0
                if (release.getImages() != null) {
                    for (Format format : release.getFormats()) {
                        if (format.getName().equals("CD")) {
                            selectedRelease = release;
                            b = true;

                            break;
                        }
                    }
                    if (b) {
                        break;
                    }
                }

            }

            if (!b) {
                /*
                 * Take the one with the most pictures
                 */
                selectedRelease = nominees.get(0);
            }

            album.setImages(new ArrayList<LocalImage>());
            album.setLabels(selectedRelease.getLabels());

            createImages(album, selectedRelease, imageRepo);
        }
    }

    /**
     * Create images.
     * 
     * @param album
     * @param selectedRelease
     * @throws MalformedURLException
     * @throws IOException
     */
    private void createImages(final Album album, final AlbumRelease selectedRelease,
            final File imageRepo) throws MalformedURLException, IOException {

        if (selectedRelease != null && selectedRelease.getImages() != null
                && selectedRelease.getImages().size() > 0) {
            for (Image image : selectedRelease.getImages()) {
                String[] array = image.getUri().split("/");

                File file = new File(imageRepo + System.getProperty("file.separator")
                        + array[array.length - 1]);

                checkIfImageExists(album, file, image.getUri());

                LocalImage localImage = new LocalImageFileSystem();

                localImage.setHeight(image.getHeight());
                localImage.setWidth(image.getWidth());
                localImage.setLargeImage(file);

                localImage.setSmallImage(createImage(file, ImageSizes.SMALL));
                localImage.setMediumImage(createImage(file, ImageSizes.MEDIUM));

                localImage.setType(image.getType());

                album.setYear(selectedRelease.getReleased());
                album.setReleaseId(selectedRelease.getId());

                album.getImages().add(localImage);
            }
        }
    }

    /**
     * @param file
     * @param image
     */
    private void checkIfImageExists(Album album, File file, String uri)
            throws MalformedURLException, IOException {
        if (!file.exists()) {
            try {
                log.debug("Image file in album " + album + " does not exist, downloading...");

                URL imageUrl = new URL(uri);
                BufferedImage i = ImageIO.read(imageUrl);
                ImageIO.write(i, "jpg", file);
            }
            catch (javax.imageio.IIOException e) {
                log.debug("Image file does not exist on discogs!");

                return;
            }
        }
        else {
            log.debug("Image file " + file.getName() + " already exist.");
        }

    }

    private File createImage(File file, ImageSizes size) throws MalformedURLException, IOException {
        try {
            BufferedImage i = ImageIO.read(file);

            BufferedImage image = ImageUtils.scalePicture(i, size.getSize());

            File newFile = new File(file.getParent(), size.name() + "-" + file.getName());

            boolean b = newFile.createNewFile();

            log.debug("Creating new file returned: " + b);

            ImageIO.write(image, "jpg", newFile);

            return newFile;
        }
        catch (javax.imageio.IIOException e) {

            log.debug("Image file does not exist on discogs!");

            return null;
        }
    }

    /**
     * Get responses from Album.
     * 
     * @param parser
     * @param albumId
     * @return
     * @throws MalformedURLException
     * @throws Exception
     * @throws IOException
     */
    @SuppressWarnings("deprecation")
    private List<AlbumRelease> getResponsesFromAlbumIds(final XStreamXmlParser parser,
            final List<String> albumId) throws MalformedURLException, Exception, IOException {

        List<AlbumRelease> responses = new ArrayList<AlbumRelease>();

        for (String id : albumId) {
            log.debug("Webservice call for release with id: " + id);

            URL url = new URL("http://www.discogs.com/release/" + id + "?f=xml&api_key="
                    + Configuration.DISCOGS_API_KEY);

            InputStream stream = null;
            try {
                stream = getStreamResponse(url);
            }
            catch (Exception e) {
                if (e.getMessage().contains("400")) {
                    Thread.currentThread().stop();
                }
                /*
                 * Remove the id as this release is probably removed from Discogs...
                 */
                continue;
            }

            responses.add(parser.parseRelease(stream).getAlbumRelease());

            stream.close();
        }
        return responses;
    }

    /**
     * @param album
     * @param resp
     * @return
     */
    private List<String> getReleasesMatchingAlbum(final String albumTitle, final Resp resp) {
        List<String> albumId = new ArrayList<String>();
        for (ArtistRelease release : resp.getArtist().getReleases()) {
            if (release.getTitle().equalsIgnoreCase(albumTitle)) {
                albumId.add(release.getId());
            }
        }

        return albumId;
    }

    /**
     * @param album
     * @param releases
     * @return
     */
    private List<AlbumRelease> getResponsesWithSameSongCount(final String albumTitle,
            final List<AlbumRelease> releases, final int songs) {
        List<AlbumRelease> nominees = new ArrayList<AlbumRelease>();

        for (AlbumRelease release : releases) {
            if (release.getTracklist().size() == songs) {
                nominees.add(release);
            }
        }

        return nominees;
    }
}
