package com.quiltplayer.external.covers.comparators;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.quiltplayer.external.covers.discogs.AlbumRelease;

/**
 * Compare by year.
 * 
 * @author Vlado Palczynski
 */
public class ReleaseYearComparator implements Serializable, Comparator<AlbumRelease> {
    private static final long serialVersionUID = 3921027261463293294L;

    /*
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public final int compare(final AlbumRelease o1, final AlbumRelease o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        else if (o1 == null) {
            return 1;
        }
        else if (o2 == null) {
            return -1;
        }
        else if (StringUtils.isBlank(o1.getReleased()) && StringUtils.isBlank(o2.getReleased())) {
            return 0;
        }
        else if (StringUtils.isBlank(o1.getReleased())) {
            return -1;
        }
        else if (StringUtils.isBlank(o2.getReleased())) {
            return 1;
        }

        return o1.getReleased().compareTo(o2.getReleased());
    }
}
