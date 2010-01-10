package com.quiltplayer.external.lyrics.lyricwiki;

import java.nio.charset.Charset;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.lyricswiki.LyricWikiLocator;
import org.lyricswiki.LyricWikiPortType;
import org.lyricswiki.LyricsResult;

import com.quiltplayer.external.lyrics.LyricsEvent;
import com.quiltplayer.external.lyrics.LyricsListener;
import com.quiltplayer.external.lyrics.LyricsService;
import com.quiltplayer.external.lyrics.Status;

/**
 * Service for getting lyrics, using lyricwiki.org. No longer up&running...
 * 
 * @author Vlado Palczynski
 */
@Deprecated
public class LyricsServiceLyricWiki implements Runnable, LyricsService {
    /**
     * Logger.
     */
    private Logger log = Logger.getLogger(LyricsServiceLyricWiki.class);

    /**
     * The listener.
     */
    private LyricsListener listener;

    private String artistName;

    private String songTitle;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        String lyrics = getLyrics();

        LyricsEvent e = new LyricsEvent(Status.FOUND, lyrics);

        listener.lyricsEvent(e);
    }

    /**
     * @param urlString
     *            the urlString to use.
     * @return String.
     */
    private String getLyrics() {

        LyricWikiLocator service = new LyricWikiLocator();
        LyricWikiPortType port = null;
        LyricsResult lyrics = null;

        try {
            port = service.getLyricWikiPort();
        }
        catch (ServiceException e) {
            log.error(e.getMessage());
        }

        try {
            lyrics = port.getSong(this.artistName, this.songTitle);
        }
        catch (RemoteException e) {
            log.error(e.getMessage());
        }

        return new String(lyrics.getLyrics().getBytes(), Charset.forName("UTF-8"));
    }

    /**
     * Get the lyrcis.
     * 
     * @param artistName
     *            the artistName to set.
     * @param title
     *            the title to set.
     */
    public void getLyrics(String artistName, String title) {

        this.artistName = artistName;
        this.songTitle = title;

        Thread t = new Thread(this);
        t.start();
    }

    /**
     * @param listener
     *            the listener to set.
     */
    public final void setLyricsListener(final LyricsListener listener) {
        this.listener = listener;
    }
}
