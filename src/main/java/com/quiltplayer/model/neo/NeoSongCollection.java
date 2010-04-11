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
package com.quiltplayer.model.neo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import com.quiltplayer.core.comparators.TrackNumberComparator;
import com.quiltplayer.core.storage.neo.QuiltPlayerRelationshipTypes;
import com.quiltplayer.model.Song;
import com.quiltplayer.model.SongCollection;

/**
 * Neo implementation for SongCollection.
 * 
 * @author Vlado Palczynski
 */
public class NeoSongCollection implements SongCollection {
    private Node node;

    private TrackNumberComparator comparator;

    public NeoSongCollection(Node node) {
        this.node = node;

        comparator = new TrackNumberComparator();
    }

    /*
     * @see com.quiltplayer.model.SongCollection#getSongs()
     */
    @Override
    public List<Song> getSongs() {
        final List<Song> songs = new ArrayList<Song>();

        Transaction tx = NeoTx.beginTx();

        for (Relationship rel : node.getRelationships(QuiltPlayerRelationshipTypes.HAS_SONG,
                Direction.OUTGOING)) {
            songs.add(new NeoSong(rel.getEndNode()));
        }

        Collections.sort(songs, comparator);

        NeoTx.finishTx(tx);

        return songs;
    }

    /*
     * @see com.quiltplayer.model.SongCollection#setSongs(java.lang.Object)
     */
    @Override
    public void setSongs(List<Song> songs) {
        // Nada
    }

}
