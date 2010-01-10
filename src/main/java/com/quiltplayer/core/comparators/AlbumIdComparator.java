package com.quiltplayer.core.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.quiltplayer.model.Album;

/**
 * Compare by id (String).
 *
 * @author Vlado Palczynski
 */
/**
 * @author vlado
 */
public class AlbumIdComparator implements Serializable, Comparator<Album> {
    /**
     *
     */
    private static final long serialVersionUID = 3059333377911336655L;

    /**
     * @param a1
     *            the a1 to set.
     * @param a2
     *            the a2 to set.
     * @return Integer
     */
    @Override
    public final int compare(final Album a1, final Album a2) {
        if (a1.getImages() == null) {
            return 1;
        }
        else if (a2.getImages() == null) {
            return -1;
        }

        return a1.getId().getId().compareTo(a2.getId().getId());
    }
}
