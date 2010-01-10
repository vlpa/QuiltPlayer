package com.quiltplayer.core.comparators;

import java.io.Serializable;
import java.util.Comparator;

import com.quiltplayer.model.Album;

/**
 * Compare if album has image or not.
 * 
 * @author Vlado Palczynski
 */
public class AlbumImageComparator implements Serializable, Comparator<Album>
{
    /**
     * 
     */
    private static final long serialVersionUID = -1132934238602099243L;

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(final Album a1, final Album a2)
    {
        if (a1 == null && a2 == null)
        {
            return 0;
        }
        else if (a1 == null)
        {
            return -1;
        }
        else if (a2 == null)
        {
            return 1;
        }
        else if (a1.getFrontImage() != null && a2.getFrontImage() == null)
        {
            return -1;
        }
        else if (a1.getFrontImage() == null && a2.getFrontImage() != null)
        {
            return 1;
        }

        return a1.getTitle().compareTo(a2.getTitle());
    }
}
