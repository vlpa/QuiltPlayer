package com.quiltplayer.model.jotify;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.quiltplayer.core.factory.SpotifyObjectFactory;
import com.quiltplayer.core.repo.spotify.JotifyRepository;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.external.covers.model.filesystem.LocalImageFileSystem;
import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.model.Album;
import com.quiltplayer.model.Artist;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.SongCollection;
import com.quiltplayer.model.StringId;
import com.quiltplayer.model.impl.LocalImageImpl;
import com.quiltplayer.properties.Configuration;

import de.felixbruns.jotify.media.Track;

@Configurable
public class JotifyAlbum implements Album {

    private Logger log = Logger.getLogger(JotifyAlbum.class);

    private static final long serialVersionUID = 1L;

    private de.felixbruns.jotify.media.Album spotifyAlbum;

    private Artist artist;

    private SongCollection songCollection;

    @Autowired
    private JotifyRepository jotifyRepository;

    public JotifyAlbum(de.felixbruns.jotify.media.Album album) {
        this.spotifyAlbum = album;

        setupArtist();
    }

    private void setupArtist() {
        artist = SpotifyObjectFactory.getArtist(spotifyAlbum.getArtist());
    }

    private void setupCollection() {

        songCollection = new JotifySongCollection();
        List<Song> songs = new ArrayList<Song>();

        if (spotifyAlbum.getTracks().isEmpty()) {
            spotifyAlbum = jotifyRepository.getInstance().browse(spotifyAlbum);
        }

        for (Track track : spotifyAlbum.getTracks()) {
            songs.add(SpotifyObjectFactory.getTrack(track, this));
        }

        songCollection.setSongs(songs);
    }

    @Override
    public void addLocalImage(LocalImage image) {
    }

    @Override
    public void addSong(Song song) {
    }

    @Override
    public void changeFrontImage(Album album, LocalImage toFrontImage) {
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public LocalImage getFrontImage() {
        return null;
    }

    @Override
    public StringId getId() {
        return new StringId(spotifyAlbum.getId());
    }

    @Override
    public List<LocalImage> getImages() {
        List<LocalImage> images = new ArrayList<LocalImage>();

        File localImagePath = new File(Configuration.ALBUM_COVERS_PATH, spotifyAlbum
                .getCover()
                + ".jpg");

        Image image = null;

        if (!localImagePath.exists()) {
            log.debug("Didn't find cached image, stream&create...");

            image = jotifyRepository.getInstance().image(spotifyAlbum.getCover());

            if (StringUtils.isNotBlank(spotifyAlbum.getCover())) {
                try {
                    ImageIO.write((RenderedImage) image, "jpg", localImagePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LocalImage localImage = new LocalImageFileSystem();

            localImage.setLargeImage(localImagePath);

            localImage.setSmallImage(createImage(localImagePath, ImageSizes.SMALL));
            localImage.setMediumImage(createImage(localImagePath, ImageSizes.MEDIUM));
        }

        try {
            image = ImageIO.read(localImagePath);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        LocalImage li = new LocalImageImpl(localImagePath);
        images.add(li);

        return images;
    }

    private File createImage(File file, ImageSizes size) {
        // TODO this already exist in ImageFetcher.
        try {
            BufferedImage i = ImageIO.read(file);

            BufferedImage image = ImageUtils.scalePicture(i, size.getSize());

            File newFile = new File(file.getParent(), size.name() + "-" + file.getName());

            newFile.createNewFile();

            ImageIO.write(image, "jpg", newFile);

            return newFile;

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLastModified() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getReleaseId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SongCollection getSongCollection() {
        if (songCollection == null)
            setupCollection();

        return songCollection;
    }

    @Override
    public String getTitle() {
        return spotifyAlbum.getName();
    }

    @Override
    public String getTitleToPublish() {
        return spotifyAlbum.getName();
    }

    @Override
    public String getYear() {
        return spotifyAlbum.getYear() + "";
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public void setId(StringId albumId) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setImages(List<LocalImage> images) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setLabel(String label) {
        throw new NotImplementedException();
    }

    @Override
    public void setLastModified(long timestamp) {
        throw new NotImplementedException();
    }

    @Override
    public void setReleaseId(String releaseId) {
        throw new NotImplementedException();
    }

    @Override
    public void setSongCollection(SongCollection songCollection) {
        throw new NotImplementedException();
    }

    @Override
    public void setTitle(String title) {
        throw new NotImplementedException();
    }

    @Override
    public void setTitleToPublish(String titleToPublish) {
        throw new NotImplementedException();
    }

    @Override
    public void setYear(String year) {
        throw new NotImplementedException();
    }

    @Override
    public int compareTo(Album o) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return the spotifyAlbum
     */
    public final de.felixbruns.jotify.media.Album getSpotifyAlbum() {
        return spotifyAlbum;
    }

    /**
     * @param spotifyAlbum
     *            the spotifyAlbum to set
     */
    public final void setSpotifyAlbum(de.felixbruns.jotify.media.Album spotifyAlbum) {
        this.spotifyAlbum = spotifyAlbum;
    }

    public boolean isPlayable() {
        if (spotifyAlbum.getTracks().get(0).getFiles().isEmpty()
                || StringUtils.isEmpty(spotifyAlbum.getTracks().get(0).getFiles().get(0).getId()))
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getType()
     */
    @Override
    public String getType() {
        return Album.TYPE_SPOTIFY;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        throw new NotImplementedException("");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#getSpotifyId()
     */
    @Override
    public String getSpotifyId() {
        return spotifyAlbum.getId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#setSpotifyId(java.lang.String)
     */
    @Override
    public void setSpotifyId(String spotifyId) {
        throw new NotImplementedException("");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.Album#deleteImages()
     */
    @Override
    public void deleteImages() {
        // TODO Auto-generated method stub

    }
}