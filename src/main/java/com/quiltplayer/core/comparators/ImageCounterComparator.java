package com.quiltplayer.core.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.model.neo.NeoLocalImage;

/**
 * Compare by counter.
 * 
 * @author Vlado Palczynski
 */
public class ImageCounterComparator implements Serializable,
        Comparator<LocalImage> {
    /**
     * 
     */
    private static final long serialVersionUID = 1380207571876710427L;

    /**
     * @param li1
     *            the li1 to set.
     * @param li2
     *            the li2 to set.
     * @return Integer
     */
    @Override
    public final int compare(final LocalImage li1, final LocalImage li2) {
        if (li1 == null) {
            return 1;
        } else if (li2 == null) {
            return -1;
        }

        return ((NeoLocalImage) li1).getCounter()
                - ((NeoLocalImage) li2).getCounter();
    }
}
