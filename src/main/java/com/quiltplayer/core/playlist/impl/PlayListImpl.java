package com.quiltplayer.core.playlist.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.quiltplayer.core.playlist.PlayList;
import com.quiltplayer.model.Song;

/**
 * Playlist implementation.
 * 
 * @author Vlado Palczynski
 */

@Component
public class PlayListImpl implements PlayList {

    private List<Song> list = new ArrayList<Song>();

    int index = 0;

    public void addSong(Song song) {
        list.add(song);
    }

    public void addSongs(Collection<Song> songs) {
        for (Song song : songs) {
            addSong(song);
        }
    }

    public void nextSong() {
        if (index + 1 >= list.size()) {
            index = 0;
        }
        else {
            index++;
            ;
        }
    }

    public void prevSong() {
        if (index - 1 < 0) {
            index = list.size() - 1;
        }
        else {
            index--;
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Song getCurrentSong() {
        return list.get(index);
    }

    public void jumpToSong(Song song) {
        if (list.contains(song)) {
            index = list.indexOf(song);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.playlist.PlayList#changeAlbum(java.util.Collection)
     */
    @Override
    public void changeAlbum(Collection<Song> songs) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.playlist.PlayList#clearPlaylist()
     */
    @Override
    public void clearPlaylist() {
        list.clear();
        index = 0;
    }
}
