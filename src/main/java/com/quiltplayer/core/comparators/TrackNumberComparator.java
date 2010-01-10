package com.quiltplayer.core.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.quiltplayer.model.Song;

/**
 * Compare by id (String).
 *
 * @author Vlado Palczynski
 */
public class TrackNumberComparator implements Serializable, Comparator<Song> {
    /**
     *
     */
    private static final long serialVersionUID = -540235262818378215L;

    /**
     * @param s1
     *            the s1 to set.
     * @param s2
     *            the s2 to set.
     * @return int
     */
    @Override
    public final int compare(final Song s1, final Song s2) {
        if (s1 == null && s2 == null) {
            return 0;
        } else if (s1 == null) {
            return -1;
        } else if (s2 == null) {
            return 1;
        } else if (s1.getTrackNumber() == null && s2.getTrackNumber() == null) {
            return 0;
        } else if (s1.getTrackNumber() == null) {
            return -1;
        } else if (s2.getTrackNumber() == null) {
            return 1;
        }

        return s1.getTrackNumber().intValue() - s2.getTrackNumber().intValue();
    }
}
