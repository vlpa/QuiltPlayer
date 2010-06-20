package com.quiltplayer.core.repo.spotify;

import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;
import javax.sound.sampled.LineUnavailableException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.quiltplayer.properties.Configuration;

import de.felixbruns.jotify.Jotify;
import de.felixbruns.jotify.JotifyConnection;
import de.felixbruns.jotify.exceptions.AuthenticationException;
import de.felixbruns.jotify.exceptions.ConnectionException;
import de.felixbruns.jotify.media.Album;
import de.felixbruns.jotify.media.Artist;
import de.felixbruns.jotify.media.File;
import de.felixbruns.jotify.media.Result;
import de.felixbruns.jotify.media.Track;
import de.felixbruns.jotify.player.PlaybackListener;

/**
 * Wrapper class for JotifyPool.
 * 
 * @author Vlado Palczynski
 * 
 */
@Repository
public class JotifyRepository {

    private static final Logger log = Logger.getLogger(JotifyRepository.class);

    public enum Type {
        BROWSE, IMAGE, PLAY
    }

    private static Jotify browseJotify;

    private static Jotify playJotify;

    private static Jotify imageJotify;

    @PostConstruct
    public void init() {
        initConnection(Type.BROWSE);
        initConnection(Type.IMAGE);
        initConnection(Type.PLAY);
    }

    private static void login(Jotify jotify) {
        try {
            jotify.login(Configuration.getInstance().getSpotifyProperties().getSpotifyUserName(), Configuration
                    .getInstance().getSpotifyProperties().getSpotifyPassword());
        }
        catch (ConnectionException e) {
            e.printStackTrace();
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public static void initConnection(Type type) {
        if (type == Type.BROWSE) {
            browseJotify = new JotifyConnection();
            login(browseJotify);
        }
        if (type == Type.IMAGE) {
            imageJotify = new JotifyConnection();
            login(imageJotify);
        }
        if (type == Type.PLAY) {
            playJotify = new JotifyConnection();
            login(playJotify);
        }

        log.debug("Initialising jotify connection: " + type);
    }

    /**
     * Get image from Spotify. Only try twice so no flooding with connection attempts if something is down.
     * 
     * @param id
     * @return
     */
    public static Image getImage(String id) {
        try {
            return imageJotify.image(id);
        }
        catch (TimeoutException e) {
            initConnection(Type.IMAGE);
            try {
                return imageJotify.image(id);
            }
            catch (Exception e1) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Album browseAlbum(Album album) {
        return browseAlbum(album.getId());
    }

    public static Album browseAlbum(String id) {
        try {
            return browseJotify.browseAlbum(id);
        }
        catch (TimeoutException e) {
            initConnection(Type.BROWSE);
            try {
                return browseJotify.browseAlbum(id);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static Artist browseArtist(Artist artist) {
        return browseArtist(artist.getId());
    }

    public static Artist browseArtist(String id) {
        try {
            return browseJotify.browseArtist(id);
        }
        catch (TimeoutException e) {
            initConnection(Type.BROWSE);
            try {
                return browseJotify.browseArtist(id);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static Track browseTrack(String id) {
        try {
            return browseJotify.browseTrack(id);
        }
        catch (TimeoutException e) {
            initConnection(Type.BROWSE);
            try {
                return browseJotify.browseTrack(id);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static Result search(String query) {
        try {
            return browseJotify.search(query);
        }
        catch (TimeoutException e) {
            initConnection(Type.BROWSE);
            try {
                return browseJotify.search(query);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    public static void pause() {
        playJotify.pause();
    }

    public static void play() {
        playJotify.play();
    }

    public static void play(Track track, PlaybackListener listener) {
        try {
            playJotify.play(track, File.BITRATE_160, listener);
        }
        catch (TimeoutException e) {
            initConnection(Type.PLAY);
            try {
                playJotify.play(track, File.BITRATE_160, listener);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        playJotify.stop();
    }
}
