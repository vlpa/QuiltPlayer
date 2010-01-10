/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.core.repo.neo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.quiltplayer.core.repo.ArtistRepository;
import com.quiltplayer.core.storage.ArtistStorage;
import com.quiltplayer.model.Artist;

/**
 * @author Vlado Palczynski
 */
@Repository
public class ArtistRepositoryNeo implements ArtistRepository {

    private static final Logger log = Logger.getLogger(ArtistRepositoryNeo.class);

    @Autowired
    private ArtistStorage artistStorage;

    private Map<String, List<Artist>> alfabeticArtistMap = null;

    private int artistCounter;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.repo.ArtistRepository#getArtists()
     */
    @Override
    public List<Artist> getArtists() {
        return (List<Artist>) artistStorage.getArtists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.repo.ArtistRepository#getArtistsByChars()
     */
    @Override
    public Map<String, List<Artist>> getArtistsByChars() {
        buildAlfabeticArtistMap((List<Artist>) artistStorage.getArtists());

        return alfabeticArtistMap;
    }

    /**
     * Sorts all the artist in Lists depending on their artist names first character.
     */
    private void buildAlfabeticArtistMap(List<Artist> artists) {
        if (artistCounter != artistStorage.countArtists()) {
            rebuildAlfabeticArtistMap(artists);
        }
    }

    /**
     * Sorts all the artist in Lists depending on their artist names first character.
     */
    private void rebuildAlfabeticArtistMap(Collection<Artist> artists) {
        log.debug("Rebuilding artist cache.");

        artistCounter = artistStorage.countArtists();

        alfabeticArtistMap = new TreeMap<String, List<Artist>>();

        if (artists != null) {
            for (final Artist artist : artists) {
                if (StringUtils.isNotBlank(artist.getArtistName().getName())) {
                    String character = artist.getArtistName().getName().toUpperCase().substring(0,
                            1);

                    List<Artist> albumSet = alfabeticArtistMap.get(character);

                    if (albumSet == null) {
                        albumSet = new ArrayList<Artist>();
                    }

                    albumSet.add(artist);

                    alfabeticArtistMap.put(character, albumSet);
                }
            }

            for (String s : alfabeticArtistMap.keySet()) {
                Collections.sort(alfabeticArtistMap.get(s), new Comparator<Artist>() {

                    /*
                     * (non-Javadoc)
                     * 
                     * @see java.util.Comparator#compare(java.lang.Object , java.lang.Object)
                     */
                    @Override
                    public int compare(Artist o1, Artist o2) {
                        return o1.getArtistName().getName().compareTo(o2.getArtistName().getName());
                    }

                });
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.repo.ArtistRepository#rebuildArtistCache(java.util .Collection)
     */
    @Override
    public void rebuildArtistCache(Collection<Artist> artists) {
        rebuildAlfabeticArtistMap(artists);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.core.repo.ArtistRepository#search(java.lang.String)
     */
    @Override
    public List<Artist> search(String artistName) {
        // TODO Auto-generated method stub
        return null;
    }
}
